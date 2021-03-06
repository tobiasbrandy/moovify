package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.interfaces.persistence.exceptions.DuplicateUniqueUserAttributeException;
import ar.edu.itba.paw.interfaces.persistence.exceptions.InvalidPaginationArgumentException;
import ar.edu.itba.paw.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    private static final String USERS = User.TABLE_NAME;
    private static final String USER_ROLE = User.USER_ROLE_TABLE_NAME;
    private static final String POSTS = Post.TABLE_NAME;
    private static final String POSTS_LIKES = PostVote.TABLE_NAME;
    private static final String COMMENTS_LIKES = CommentVote.TABLE_NAME;
    private static final String COMMENTS = Comment.TABLE_NAME;
    private static final String USERS_FOLLOWS = User.USERS_FOLLOWS;

    private static final String NATIVE_BASE_USER_FROM = "FROM " + USERS;

    private static final String NATIVE_TOTAL_LIKES_FROM = "INNER JOIN ( " +
            "SELECT " + USERS + ".user_id, coalesce(post_likes.total_likes, 0) + coalesce(comment_likes.total_likes, 0) total_likes " +

            "FROM " + USERS +

                " LEFT OUTER JOIN ( " +
                    "SELECT " + POSTS + ".user_id, SUM(" + POSTS_LIKES + ".value) total_likes " +
                    "FROM " + POSTS +
                        " INNER JOIN " + POSTS_LIKES + " ON " + POSTS + ".post_id = " + POSTS_LIKES + ".post_id " +
                    "GROUP BY " + POSTS + ".user_id " +
                ") post_likes ON " + USERS + ".user_id = post_likes.user_id " +

                "LEFT OUTER JOIN ( " +
                    "SELECT " + COMMENTS + ".user_id, SUM(" + COMMENTS_LIKES + ".value) total_likes " +
                    "FROM " + COMMENTS +
                        " INNER JOIN " + COMMENTS_LIKES + " ON " + COMMENTS + ".comment_id = " + COMMENTS_LIKES + ".comment_id " +
                    "GROUP BY " + COMMENTS + ".user_id " +
                ") comment_likes ON " + USERS + ".user_id = comment_likes.user_id " +

            ") TOTAL_LIKES ON TOTAL_LIKES.user_id = " + USERS + ".user_id";

    private static final String NATIVE_FOLLOWER_COUNT_FROM = "LEFT OUTER JOIN ( " +
            "SELECT " + USERS_FOLLOWS + ".user_follow_id, COUNT(*) follower_count " +
                "FROM " + USERS_FOLLOWS +
                " GROUP BY " + USERS_FOLLOWS + ".user_follow_id " +
            ") FOLLOWER_COUNT ON FOLLOWER_COUNT.user_follow_id = " + USERS + ".user_id";

    // Search Query Statements
    private static final String NATIVE_SEARCH_BY_USERNAME = "LOWER(" + USERS + ".username) LIKE '%' || LOWER(?) || '%'";

    private static final String NATIVE_SEARCH_BY_ROLE = USERS + ".user_id IN ( " +
            "SELECT " + USER_ROLE + ".user_id " +
            "FROM " + USER_ROLE +
            " WHERE LOWER(" + USER_ROLE + ".role_name) = LOWER(?))";

    private static final String NATIVE_ENABLED_FILTER = USERS + ".enabled = true";

    private static final String NATIVE_DISABLED_FILTER = USERS + ".enabled = false";

    private static final EnumMap<SortCriteria, String> sortCriteriaQueryMap = initializeSortCriteriaQueryMap();
    private static final EnumMap<SortCriteria, String> sortCriteriaHQLMap = initializeSortCriteriaHQLMap();

    private static EnumMap<SortCriteria, String> initializeSortCriteriaQueryMap() {

        EnumMap<SortCriteria, String> sortCriteriaQuery = new EnumMap<>(SortCriteria.class);

        sortCriteriaQuery.put(SortCriteria.NEWEST, USERS + ".creation_date desc");
        sortCriteriaQuery.put(SortCriteria.OLDEST, USERS + ".creation_date");
        sortCriteriaQuery.put(SortCriteria.VOTES, "TOTAL_LIKES.total_likes desc");
        sortCriteriaQuery.put(SortCriteria.USERNAME, USERS + ".username");
        sortCriteriaQuery.put(SortCriteria.FOLLOWERS, "coalesce(FOLLOWER_COUNT.follower_count, 0) desc");

        return sortCriteriaQuery;
    }

    private static EnumMap<SortCriteria, String> initializeSortCriteriaHQLMap() {

        EnumMap<SortCriteria, String> sortCriteriaQuery = new EnumMap<>(SortCriteria.class);

        sortCriteriaQuery.put(SortCriteria.NEWEST, "u.creationDate desc");
        sortCriteriaQuery.put(SortCriteria.OLDEST, "u.creationDate");
        sortCriteriaQuery.put(SortCriteria.VOTES, "totalLikes desc");
        sortCriteriaQuery.put(SortCriteria.USERNAME, "u.username");
        sortCriteriaQuery.put(SortCriteria.FOLLOWERS, "followerCount desc");

        return sortCriteriaQuery;
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public User register(String username, String password, String name, String email, String description, String language, Set<Role> roleNames, Image avatar, boolean enabled) throws DuplicateUniqueUserAttributeException {

        final List<Tuple> userIntegrityViolators =
                em.createQuery(
                        "SELECT u.username AS username, u.email AS email FROM User u WHERE u.username = :username OR u.email = :email",
                        Tuple.class)
                .setParameter("username", username)
                .setParameter("email", email)
                .getResultList();

        // Verify User Unique Attributes Are Not Present In Database
        if(!userIntegrityViolators.isEmpty()) {
            final EnumSet<DuplicateUniqueUserAttributeException.UniqueAttributes> duplicatedUniqueAttributes =
                    EnumSet.noneOf(DuplicateUniqueUserAttributeException.UniqueAttributes.class);

            // Taking Advantage That UniqueAttributes Enum Size Is Just 2
            if(userIntegrityViolators.size() == 2) {
                duplicatedUniqueAttributes.add(DuplicateUniqueUserAttributeException.UniqueAttributes.USERNAME);
                duplicatedUniqueAttributes.add(DuplicateUniqueUserAttributeException.UniqueAttributes.EMAIL);
            }
            else {
                final Tuple duplicatedAttribute = userIntegrityViolators.get(0);

                if(duplicatedAttribute.get(0, String.class).equals(username))
                    duplicatedUniqueAttributes.add(DuplicateUniqueUserAttributeException.UniqueAttributes.USERNAME);

                if(duplicatedAttribute.get(1, String.class).equals(email))
                    duplicatedUniqueAttributes.add(DuplicateUniqueUserAttributeException.UniqueAttributes.EMAIL);
            }

            throw new DuplicateUniqueUserAttributeException(duplicatedUniqueAttributes);
        }

        final User user = new User(LocalDateTime.now(), username, password, name, email, description, language, avatar,
                roleNames, enabled, Collections.emptySet(), Collections.emptySet(), Collections.emptySet(),
                Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet());

        em.persist(user);

        user.setTotalLikes(0L);

        LOGGER.info("Created User: {}", user.getId());

        return user;
    }

    @Override
    public void updateUsername(User user, String username) throws DuplicateUniqueUserAttributeException {

        final Long userIntegrityViolatorsCount =
                em.createQuery(
                        "SELECT count(u) AS username FROM User u WHERE u.username = :username",
                        Long.class)
                        .setParameter("username", username)
                        .getSingleResult();

        if(userIntegrityViolatorsCount > 0)
            throw new DuplicateUniqueUserAttributeException(EnumSet.of(DuplicateUniqueUserAttributeException.UniqueAttributes.USERNAME));

        user.setUsername(username);

        LOGGER.info("Updating user {} username from {} to {}", user.getId(), user.getUsername(), username);

        em.persist(user);
    }

    @Override
    public Optional<User> findUserById(long id) {
        return findByCriteria("id", id);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return findByCriteria("username", username);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return findByCriteria("email", email);
    }

    @Override
    public PaginatedCollection<User> getAllUsers(Boolean enabled, SortCriteria sortCriteria, int pageNumber, int pageSize) {

        LOGGER.info("Search All Users Order By {}. Page number {}, Page Size {}", sortCriteria, pageNumber, pageSize);

        return queryUsers("", enabled, sortCriteria, pageNumber, pageSize, null);
    }

    @Override
    public PaginatedCollection<User> getFollowedUsers(User user, Boolean enabled, SortCriteria sortCriteria, int pageNumber, int pageSize) {

        LOGGER.info("Get All followed users Order By {}. Page number {}, Page Size {}", sortCriteria, pageNumber, pageSize);

        return queryUsers(
                "WHERE " +
                        USERS + ".user_id IN ( " +
                        "SELECT " + USERS_FOLLOWS + ".user_follow_id " +
                        "FROM " + USERS_FOLLOWS +
                        " WHERE " + USERS_FOLLOWS + ".user_id = ?)",
                enabled, sortCriteria, pageNumber, pageSize, new Object[]{ user.getId() });
    }

    @Override
    public PaginatedCollection<User> searchUsers(String query, Boolean enabled, SortCriteria sortCriteria, int pageNumber, int pageSize) {

        LOGGER.info("Search Users By Name {} Order By {}. Page number {}, Page Size {}", query, sortCriteria, pageNumber, pageSize);

        return queryUsers(
                "WHERE " + NATIVE_SEARCH_BY_USERNAME,
                enabled, sortCriteria, pageNumber, pageSize, new Object[]{ query });
    }

    @Override
    public PaginatedCollection<User> searchUsersByRole(String query, Role role, Boolean enabled, SortCriteria sortCriteria, int pageNumber, int pageSize) {

        LOGGER.info("Search Users By Name {} And Role {} Order By {}. Page number {}, Page Size {}", query, role, sortCriteria, pageNumber, pageSize);

        return queryUsers(
                "WHERE " + NATIVE_SEARCH_BY_USERNAME +
                                    " AND " + NATIVE_SEARCH_BY_ROLE,
                enabled, sortCriteria, pageNumber, pageSize, new Object[]{ query, role.name() });
    }

    private String buildNativeFromStatement() {
        return NATIVE_BASE_USER_FROM + " " + NATIVE_TOTAL_LIKES_FROM + " " + NATIVE_FOLLOWER_COUNT_FROM;
    }

    private String buildNativeOrderByStatement(SortCriteria sortCriteria) {

        if(!sortCriteriaQueryMap.containsKey(sortCriteria)) {
            LOGGER.error("SortCriteria Native implementation not found for {} in UserDaoImpl", sortCriteria);
            throw new IllegalArgumentException();
        }

        return "ORDER BY " + sortCriteriaQueryMap.get(sortCriteria);
    }

    private String buildHQLOrderByStatement(SortCriteria sortCriteria) {

        if(!sortCriteriaHQLMap.containsKey(sortCriteria)) {
            LOGGER.error("SortCriteria HQL implementation not found for {} in UserDaoImpl", sortCriteria);
            throw new IllegalArgumentException();
        }

        return "ORDER BY " + sortCriteriaHQLMap.get(sortCriteria);
    }

    private String buildNativePaginationStatement(int pageNumber, int pageSize) {

        if(pageNumber < 0 || pageSize <= 0) {
            LOGGER.error("Invalid pagination argument found in UserDaoImpl. pageSize: {}, pageNumber: {}", pageSize, pageNumber);
            throw new InvalidPaginationArgumentException();
        }

        return String.format("LIMIT %d OFFSET %d", pageSize, pageNumber * pageSize);
    }

    private String addEnabledFilter(String nativeWhereStatement, Boolean enabled) {
        if(enabled == null)
            return nativeWhereStatement;

        final StringBuilder sb;

        if(nativeWhereStatement != null)
            sb = new StringBuilder(nativeWhereStatement.trim());
        else
            sb = new StringBuilder();

        if(sb.length() == 0)
            sb.append("WHERE ");
        else
            sb.append(" AND ");

        if(enabled)
            sb.append(NATIVE_ENABLED_FILTER);
        else
            sb.append(NATIVE_DISABLED_FILTER);

        return sb.toString();
    }

    private void addParamsToNativeQuery(Query query, Object[] params) {
        if(params == null)
            return;

        int i = 1;

        for(Object param : params) {
            query.setParameter(i, param);
            i++;
        }
    }

    private PaginatedCollection<User> queryUsers(String nativeWhereStatement, Boolean enabled, SortCriteria sortCriteria, int pageNumber, int pageSize, Object[] params) {

        final String nativeSelect = "SELECT " + USERS + ".user_id";

        final String nativeCountSelect = "SELECT COUNT(DISTINCT " + USERS + ".user_id)";

        final String nativeFrom = buildNativeFromStatement();

        final String nativeWhere = addEnabledFilter(nativeWhereStatement, enabled);

        final String nativeOrderBy = buildNativeOrderByStatement(sortCriteria);

        final String HQLOrderBy = buildHQLOrderByStatement(sortCriteria);

        final String nativePagination = buildNativePaginationStatement(pageNumber, pageSize);

        final String nativeCountQuery = String.format("%s %s %s", nativeCountSelect, nativeFrom, nativeWhere);

        final String nativeQuery = String.format("%s %s %s %s %s",
                nativeSelect, nativeFrom, nativeWhere, nativeOrderBy, nativePagination);

        final String fetchQuery = String.format(
                "SELECT u, " +
                "coalesce((SELECT sum(postLikes.value) from u.posts posts left outer join posts.votes postLikes), 0) + " +
                "coalesce((SELECT sum(commentLikes.value) from u.comments comments left outer join comments.votes commentLikes), 0)" +
                        " AS totalLikes, " +
                "u.followers.size AS followerCount " +
                "FROM User u " +
                "WHERE u.id IN :userIds " +
                "GROUP BY u " +
                "%s", HQLOrderBy);

        LOGGER.debug("QueryUsers nativeCountQuery: {}", nativeCountQuery);
        LOGGER.debug("QueryUsers nativeQuery: {}", nativeQuery);
        LOGGER.debug("QueryUsers fetchQuery: {}", fetchQuery);

        // Calculate Total User Count Disregarding Pagination (To Calculate Pages Later)
        final Query totalUsersNativeQuery = em.createNativeQuery(nativeCountQuery);

        addParamsToNativeQuery(totalUsersNativeQuery, params);

        final long totalUsers = ((Number) totalUsersNativeQuery.getSingleResult()).longValue();

        if(totalUsers == 0) {
            LOGGER.debug("QueryUsers Total Count == 0");
            return new PaginatedCollection<>(Collections.emptyList(), pageNumber, pageSize, totalUsers);
        }

        // Calculate Which Users To Load And Load Their Ids
        final Query userIdsNativeQuery = em.createNativeQuery(nativeQuery);

        addParamsToNativeQuery(userIdsNativeQuery, params);

        @SuppressWarnings("unchecked")
        final Collection<Long> userIds =
                ((List<Number>)userIdsNativeQuery.getResultList())
                .stream().map(Number::longValue).collect(Collectors.toList());

        if(userIds.isEmpty()) {
            LOGGER.debug("QueryUsers Empty Page");
            return new PaginatedCollection<>(Collections.emptyList(), pageNumber, pageSize, totalUsers);
        }

        // Get Users Based on Ids
        final Collection<Tuple> fetchQueryResult = em.createQuery(fetchQuery, Tuple.class)
                .setParameter("userIds", userIds)
                .getResultList();

        // Map Tuples To Users
        final Collection<User> users = fetchQueryResult.stream().map(tuple -> {

            final User user = tuple.get(0, User.class);

            user.setTotalLikes(tuple.get(1, Long.class));
            user.setFollowerCount(tuple.get(2, Integer.class));

            return user;

        }).collect(Collectors.toList());

        return new PaginatedCollection<>(users, pageNumber, pageSize, totalUsers);
    }

    private <T> Optional<User> findByCriteria(String field, T value) {

        LOGGER.info("Find User By {}: {}", field, value);

        final CriteriaBuilder cb = em.getCriteriaBuilder();

        final CriteriaQuery<User> q = cb.createQuery(User.class);

        final Root<User> u = q.from(User.class);

        q.select(u);

        q.where(cb.equal(u.get(field), value));

        return em.createQuery(q).getResultList().stream().findFirst();
    }
}
