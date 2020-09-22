package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.PostDao;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Repository
public class PostDaoImpl implements PostDao {

    // Constants with Table Names
    private static final String POSTS = TableNames.POSTS.getTableName();
    private static final String MOVIES = TableNames.MOVIES.getTableName();
    private static final String POST_MOVIE = TableNames.POST_MOVIE.getTableName();
    private static final String TAGS = TableNames.TAGS.getTableName();
    private static final String COMMENTS = TableNames.COMMENTS.getTableName();
    private static final String POST_CATEGORY = TableNames.POST_CATEGORY.getTableName();
    private static final String USERS = TableNames.USERS.getTableName();
    private static final String USER_ROLE = TableNames.USER_ROLE.getTableName();
    private static final String ROLES = TableNames.ROLES.getTableName();
    private static final String MOVIE_TO_MOVIE_CATEGORY = TableNames.MOVIE_TO_MOVIE_CATEGORY.getTableName();
    private static final String MOVIE_CATEGORIES = TableNames.MOVIE_CATEGORIES.getTableName();

    /*
    *
    * Each SELECT, FROM and MAPPER static variable depend on each other.
    * If one is changed it is necessary to change the others as well to match the design decisions.
    * It is important that they are design only assuming the pre-existence and execution of
    * the BASE_POST static variables (the one Model linked with this Dao). That way, any other
    * can be made optional (currently the case of MOVIES and COMMENTS).
    * All functionality involving this constants is encapsulated in the FetchRelationSelector.
    * Use this enum to access Select, From and Mapper for each desired relation. In this enum the
    * relations are also categorized as required and not required.
    *
    * Additional requirements of each segment are made explicit below:
    *
    * - ALL:
    *   - All segments must abide to the aliases defined in BASE_POST_SELECT to access Post properties.
    *         Currently p_column_name.
    *
    *   - In BASE_POST_ROW_MAPPER a post_id to Post Map must be maintained for others to use.
    *
    *   - The use of LinkedHashSet, LinkedHashMap and List collections is of importance to maintain query order.
    *
    * - USER:
    *   - In BASE_POST_ROW_MAPPER, the roles Collection from User must be a Set to guaranty uniqueness.
    *
    * - TAGS:
    *   - In BASE_POST_ROW_MAPPER, the tags Collection must be a Set to guaranty uniqueness.
    *
    * - MOVIES:
    *   - In BASE_POST_ROW_MAPPER, the movies Collection must be a Set to guaranty uniqueness.
    *
    * - COMMENTS:
    *   - Users to whom the comments belong are brought without their roles.
    *
    */

    private enum FetchRelationSelector {
        POST(BASE_POST_SELECT, BASE_POST_FROM, true, null),
        CATEGORY(CATEGORY_SELECT, CATEGORY_FROM, true, null),
        USER(USER_SELECT, USER_FROM, true, null),
        TAGS(TAGS_SELECT, TAGS_FROM, true, null),
        MOVIES(MOVIES_SELECT, MOVIES_FROM, false, FetchRelation.MOVIES),
        COMMENTS(COMMENTS_SELECT, COMMENTS_FROM, false, FetchRelation.COMMENTS);

        private static final Map<EnumSet<FetchRelation>, ResultSetExtractor<Collection<Post>>>
                relationsToRSEMap = initializeRelationsToRSEMap();

        private final String select;
        private final String from;
        private final boolean required;
        private final FetchRelation relation;

        FetchRelationSelector(String select, String from, boolean required, FetchRelation relation) {
            this.select = select;
            this.from = from;
            this.required = required;
            this.relation = relation;
        }

        public static EnumSet<FetchRelationSelector> getSelectorsFromFetchRelations(EnumSet<FetchRelation> fetchRelations) {
            EnumSet<FetchRelationSelector> result = EnumSet.noneOf(FetchRelationSelector.class);

            for(FetchRelationSelector selector : FetchRelationSelector.values()) {
                if(selector.isRequired() || fetchRelations.contains(selector.getRelation()))
                    result.add(selector);
            }

            return result;
        }

        public static ResultSetExtractor<Collection<Post>> getMapper(EnumSet<FetchRelation> fetchRelations) {
            return relationsToRSEMap.get(fetchRelations);
        }

        public String getSelect() {
            return select;
        }

        public String getFrom() {
            return from;
        }

        public boolean isRequired() {
            return required;
        }

        private FetchRelation getRelation() {
            return relation;
        }

        private static Map<EnumSet<FetchRelation>, ResultSetExtractor<Collection<Post>>> initializeRelationsToRSEMap() {
            Map<EnumSet<FetchRelation>, ResultSetExtractor<Collection<Post>>> relationsToRSEMap = new HashMap<>();
            Set<EnumSet<FetchRelation>> relationsCombinations = new HashSet<>();

            // Add all FetchRelation combinations. TODO: Better way?
            relationsCombinations.add(EnumSet.noneOf(FetchRelation.class));
            relationsCombinations.add(EnumSet.of(FetchRelation.MOVIES));
            relationsCombinations.add(EnumSet.of(FetchRelation.COMMENTS));
            relationsCombinations.add(EnumSet.allOf(FetchRelation.class));

            for(EnumSet<FetchRelation> relationSet : relationsCombinations)
                relationsToRSEMap.put(relationSet, buildResultSetExtractor(relationSet));

            return relationsToRSEMap;
        }

        private static ResultSetExtractor<Collection<Post>> buildResultSetExtractor(EnumSet<FetchRelation> fetchRelations) {
            return (rs) -> {

                // Important use of LinkedHashMap to maintain Post insertion order
                final Map<Long, Post> idToPostMap = new LinkedHashMap<>();
                final Map<Long, Role> idToUserRoleMap = new HashMap<>();
                final Map<Long, Movie> idToMovieMap = new HashMap<>();
                final Map<Long, Comment> idToCommentMap = new HashMap<>();
                final Map<Long, MovieCategory> idToMovieCategoryMap = new HashMap<>();
                final Map<Long, Collection<Comment>> childrenWithoutParentMap = new HashMap<>();

                while(rs.next()) {

                    // Base Mapper Required
                    BASE_POST_ROW_MAPPER.accept(rs, idToPostMap);

                    // User Mapper Required - All posts belong to a User. This User has many Roles
                    USER_ROW_MAPPER.accept(rs, idToPostMap, idToUserRoleMap);

                    // Tags Mapper Required - Business decision
                    TAGS_ROW_MAPPER.accept(rs, idToPostMap);

                    if (fetchRelations.contains(FetchRelation.MOVIES))
                        MOVIES_ROW_MAPPER.accept(rs, idToPostMap, idToMovieMap, idToMovieCategoryMap);

                    if (fetchRelations.contains(FetchRelation.COMMENTS))
                        COMMENTS_ROW_MAPPER.accept(rs, idToPostMap, idToCommentMap, childrenWithoutParentMap);
                }

                return idToPostMap.values();
            };
        }
    }

    private enum FilterCriteria {
        BY_POST_TITLE_MOVIE_TITLE_AND_TAGS(
                "( " +
                        POSTS + ".title ILIKE '%' || ? || '%'" +
                        " OR " + POSTS + ".post_id IN (" +
                            " SELECT " + TAGS + ".post_id FROM " + TAGS +
                            " WHERE " +  TAGS + ".tag ILIKE '%' || ? || '%' )" +
                        " OR " + POSTS + ".post_id IN ( " +
                            "SELECT " + POST_MOVIE + ".post_id " +
                            " FROM " + POST_MOVIE +
                            " INNER JOIN " + MOVIES + " ON " + POST_MOVIE + ".movie_id = " + MOVIES + ".movie_id " +
                            " WHERE " + MOVIES + ".title ILIKE '%' || ? || '%')" +
                        " )"
        ),

        POSTS_OLDER_THAN(
                POSTS + ".creation_date >= ?"
        ),

        BY_POST_CATEGORY(
                POST_CATEGORY + ".name ILIKE ?"
        );

        final String filterQuery;

        FilterCriteria(String filterQuery) {
            this.filterQuery = filterQuery;
        }
    }

    private static final EnumMap<SortCriteria,String> sortCriteriaQueryMap = initializeSortCriteriaQuery();

    private static EnumMap<SortCriteria, String> initializeSortCriteriaQuery() {
        EnumMap<SortCriteria, String> sortCriteriaQuery = new EnumMap<>(SortCriteria.class);
        sortCriteriaQuery.put(SortCriteria.NEWEST, POSTS + ".creation_date desc");
        sortCriteriaQuery.put(SortCriteria.OLDEST, POSTS + ".creation_date");
        return sortCriteriaQuery;
    }

    // Mapper and Select for simple post retrieval.
    private static final String BASE_POST_SELECT = "SELECT " +
            // Posts Table Columns - Alias: p_column_name
            POSTS + ".post_id p_post_id, " +
            POSTS + ".creation_date p_creation_date, " +
            POSTS + ".title p_title, " +
            POSTS + ".body p_body, " + POSTS +
            ".word_count p_word_count";

    private static final String CATEGORY_SELECT =
            POST_CATEGORY + ".category_id pc_category_id, " +
            POST_CATEGORY + ".creation_date pc_creation_date, " +
            POST_CATEGORY + ".name pc_name";

    private static final String USER_SELECT =
            USERS + ".user_id u_user_id, " +
            USERS + ".creation_date u_creation_date, " +
            USERS + ".username u_username, " +
            USERS + ".password u_password, " +
            USERS + ".name u_name, " +
            USERS + ".email u_email, " +
            USERS + ".role_id r_role_id, " +
            USERS + ".role r_role ";

    private static final String TAGS_SELECT = TAGS + ".tag p_tag";

    private static final String MOVIES_SELECT =
            MOVIES + ".movie_id m_movie_id, " +
            MOVIES + ".creation_date m_creation_date, " +
            MOVIES + ".release_date m_release_date, " +
            MOVIES + ".title m_title, " +
            MOVIES + ".original_title m_original_title, " +
            MOVIES + ".tmdb_id m_tmdb_id, " +
            MOVIES + ".imdb_id m_imdb_id, " +
            MOVIES + ".original_language m_original_language, " +
            MOVIES + ".overview m_overview, " +
            MOVIES + ".popularity m_popularity, " +
            MOVIES + ".runtime m_runtime, " +
            MOVIES + ".vote_average m_vote_average, " +
            MOVIES + ".category_id mc_category_id, " +
            MOVIES + ".tmdb_category_id mc_tmdb_category_id, " +
            MOVIES + ".name mc_name";

    private static final String COMMENTS_SELECT =
            COMMENTS + ".comment_id c_comment_id, " +
            "coalesce(" + COMMENTS + ".parent_id, 0) c_parent_id, " +
            COMMENTS + ".post_id c_post_id, " +
            COMMENTS + ".creation_date c_creation_date, " +
            COMMENTS + ".body c_body, " +

            // Users in Post Comments Come without roles
            COMMENTS + ".cu_user_id, " +
            COMMENTS + ".cu_creation_date, " +
            COMMENTS + ".cu_username, " +
            COMMENTS + ".cu_password, " +
            COMMENTS + ".cu_name, " +
            COMMENTS + ".cu_email";

    private static final String BASE_POST_FROM = "FROM " + POSTS;

    private static final String CATEGORY_FROM =
            "INNER JOIN " + POST_CATEGORY + " ON " + POSTS + ".category_id = " + POST_CATEGORY + ".category_id";

    private static final String USER_FROM =
            "INNER JOIN ( " +
                    "SELECT " +
                    USERS + ".user_id, " + USERS + ".creation_date, " + USERS + ".username, " + USERS + ".password, " +
                    USERS + ".name, " + USERS + ".email, " + ROLES + ".role_id, " + ROLES + ".role " +
                    "FROM " + USERS +
                    " INNER JOIN " + USER_ROLE + " ON " + USERS + ".user_id = " + USER_ROLE + ".user_id " +
                    "INNER JOIN " + ROLES + " ON " + USER_ROLE + ".role_id = " + ROLES + ".role_id " +
            ") " + USERS + " ON " + USERS + ".user_id = " + POSTS + ".user_id";

    private static final String TAGS_FROM =
            "LEFT OUTER JOIN " + TAGS + " ON " + POSTS + ".post_id = " + TAGS + ".post_id";

    private static final String MOVIES_FROM =
            "LEFT OUTER JOIN (" + " SELECT " +
                    MOVIES + ".movie_id, " +
                    MOVIES + ".creation_date, " +
                    MOVIES + ".release_date, " +
                    MOVIES + ".title, " +
                    MOVIES + ".original_title, " +
                    MOVIES + ".tmdb_id, " +
                    MOVIES + ".imdb_id, " +
                    MOVIES + ".original_language, " +
                    MOVIES + ".overview, " +
                    MOVIES + ".popularity, " +
                    MOVIES + ".runtime, " +
                    MOVIES + ".vote_average, " +
                    MOVIE_CATEGORIES + ".category_id, " +
                    MOVIE_CATEGORIES + ".tmdb_category_id, " +
                    MOVIE_CATEGORIES + ".name, " +
                    "post_id" +
                    " FROM "+ POST_MOVIE +
                    " INNER JOIN " + MOVIES + " ON " + POST_MOVIE+ ".movie_id = " + MOVIES + ".movie_id" +
                    " INNER JOIN " + MOVIE_TO_MOVIE_CATEGORY + " ON " + MOVIE_TO_MOVIE_CATEGORY + ".tmdb_id = " + MOVIES + ".tmdb_id" +
                    " INNER JOIN " + MOVIE_CATEGORIES + " ON " + MOVIE_TO_MOVIE_CATEGORY + ".tmdb_category_id = " + MOVIE_CATEGORIES + ".tmdb_category_id" +
                    ") " + MOVIES + " on " + MOVIES + ".post_id = " + POSTS + ".post_id";

    private static final String COMMENTS_FROM =
            "LEFT OUTER JOIN ( " +
                    "SELECT " +
                    COMMENTS + ".comment_id, " +
                    "coalesce(" + COMMENTS + ".parent_id, 0) parent_id, " +
                    COMMENTS + ".post_id, " +
                    COMMENTS + ".creation_date, " +
                    COMMENTS + ".body, " +
                    USERS + ".user_id cu_user_id, " +
                    USERS + ".creation_date cu_creation_date, " +
                    USERS + ".username cu_username, " +
                    USERS + ".password cu_password, " +
                    USERS + ".name cu_name, " +
                    USERS + ".email cu_email " +
                    "FROM " + USERS +
                    " INNER JOIN " + COMMENTS + " ON " + USERS + ".user_id = " + COMMENTS + ".user_id " +
            ") " + COMMENTS + " ON " + POSTS + ".post_id = " + COMMENTS + ".post_id";


    public static final ResultSetMonoConsumer<Map<Long, Post>> BASE_POST_ROW_MAPPER = (rs, idToPostMap) -> {
        final long post_id = rs.getLong("p_post_id");

        if (!idToPostMap.containsKey(post_id)) {
            idToPostMap.put(post_id,
                    new Post(
                            post_id, rs.getObject("p_creation_date", LocalDateTime.class),
                            rs.getString("p_title"), rs.getString("p_body"),
                            rs.getInt("p_word_count"),

                            new PostCategory(rs.getLong("pc_category_id"),
                                    rs.getObject("pc_creation_date", LocalDateTime.class),
                                    rs.getString("pc_name")),

                            new User(rs.getLong("u_user_id"), rs.getObject("p_creation_date", LocalDateTime.class),
                                    rs.getString("u_username"), rs.getString("u_password"),
                                    rs.getString("u_name"), rs.getString("u_email"), new HashSet<>()),

                            // tags, movies, comments
                            new LinkedHashSet<>(), new LinkedHashSet<>(), new ArrayList<>()
                    )
            );
        }
    };

    private static final ResultSetBiConsumer<Map<Long, Post>, Map<Long, Role>> USER_ROW_MAPPER = (rs, idToPostMap, idToUserRoleMap) -> {
        final long post_id = rs.getLong("p_post_id");
        final long role_id = rs.getLong("r_role_id");

        if(!idToUserRoleMap.containsKey(role_id))
            idToUserRoleMap.put(role_id, new Role(role_id, rs.getString("r_role")));

        idToPostMap.get(post_id).getUser().getRoles().add(idToUserRoleMap.get(role_id));
    };

    private static final ResultSetMonoConsumer<Map<Long, Post>> TAGS_ROW_MAPPER = (rs, idToPostMap) -> {
        final long post_id = rs.getLong("p_post_id");
        final String tag = rs.getString("p_tag");

        if (tag != null)
            idToPostMap.get(post_id).getTags().add(tag);
    };

    private static final ResultSetTriConsumer<Map<Long, Post>, Map<Long, Movie>, Map<Long, MovieCategory>> MOVIES_ROW_MAPPER =
            (rs, idToPostMap, idToMovieMap, idToMovieCategory) -> {

        final long post_id = rs.getLong("p_post_id");
        final long movie_id = rs.getLong("m_movie_id");
        final long movie_category_tmdb_id = rs.getLong("mc_tmdb_category_id");

        // If movies is not null. (rs.getLong returns 0 on null)
        if(movie_id != 0) {

            if(!idToMovieMap.containsKey(movie_id)) {
                idToMovieMap.put(movie_id,
                        new Movie(
                                movie_id, rs.getObject("m_creation_date", LocalDateTime.class),
                                rs.getString("m_title"),
                                rs.getString("m_original_title"),
                                rs.getLong("m_tmdb_id"),
                                rs.getString("m_imdb_id"),
                                rs.getString("m_original_language"),
                                rs.getString("m_overview"),
                                rs.getFloat("m_popularity"),
                                rs.getFloat("m_runtime"),
                                rs.getFloat("m_vote_average"),
                                rs.getObject("m_release_date", LocalDate.class),
                                // categories
                                new LinkedHashSet<>())
                );
            }

            if(!idToMovieCategory.containsKey(movie_category_tmdb_id)) {
                idToMovieCategory.put(movie_category_tmdb_id, new MovieCategory(
                        rs.getLong("mc_category_id"),
                        rs.getLong("mc_tmdb_category_id"),
                        rs.getString("mc_name")));
            }

            idToMovieMap.get(movie_id).getCategories().add(idToMovieCategory.get(movie_category_tmdb_id));

            // If the Post already had the Movie, it won't get added because the Collection is a Set
            idToPostMap.get(post_id).getMovies().add(idToMovieMap.get(movie_id));
        }
    };

    private static final ResultSetTriConsumer<Map<Long, Post>, Map<Long, Comment>, Map<Long, Collection<Comment>>> COMMENTS_ROW_MAPPER =
            (rs, idToPostMap, idToCommentMap, childrenWithoutParentMap) -> {

        final long comment_id = rs.getLong("c_comment_id");
        Comment newComment;

        // Returns 0 on null
        if(comment_id != 0 && !idToCommentMap.containsKey(comment_id)) {

            newComment = new Comment(comment_id,
                    rs.getObject("c_creation_date", LocalDateTime.class),
                    rs.getLong("c_post_id"), rs.getLong("c_parent_id"), new ArrayList<>(),
                    rs.getString("c_body"),
                    new User(rs.getLong("cu_user_id"), rs.getObject("cu_creation_date", LocalDateTime.class),
                            rs.getString("cu_username"), rs.getString("cu_password"),
                            rs.getString("cu_name"), rs.getString("cu_email"), Collections.emptyList()));

            idToCommentMap.put(comment_id, newComment);

            // Incorporate all children that appeared before currentComment
            if(childrenWithoutParentMap.containsKey(comment_id)) {
                newComment.getChildren().addAll(childrenWithoutParentMap.get(comment_id));

                // Mapping is no longer necessary
                childrenWithoutParentMap.remove(comment_id);
            }

            // Comment is root
            if (newComment.getParentId() == 0)
                idToPostMap.get(newComment.getPostId()).getComments().add(newComment);

            else {
                // If parent doesn't exist yet
                if(!idToCommentMap.containsKey(newComment.getParentId())) {

                    // Initialize Collection inside Map if necessary
                    if(!childrenWithoutParentMap.containsKey(newComment.getParentId()))
                        childrenWithoutParentMap.put(newComment.getParentId(), new ArrayList<>());

                    // Add children to Parent Children Buffer
                    childrenWithoutParentMap.get(newComment.getParentId()).add(newComment);
                }

                // Parent exists -> Add to parent
                else
                    idToCommentMap.get(newComment.getParentId()).getChildren().add(newComment);
            }
        }
    };

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert postInsert;
    private final SimpleJdbcInsert postMoviesInsert;
    private final SimpleJdbcInsert tagsInsert;

    @Autowired
    public PostDaoImpl(final DataSource ds){

        jdbcTemplate = new JdbcTemplate(ds);

        postInsert = new SimpleJdbcInsert(ds)
                .withTableName(POSTS)
                .usingGeneratedKeyColumns("post_id");

        postMoviesInsert = new SimpleJdbcInsert(ds)
                .withTableName(POST_MOVIE);

        tagsInsert = new SimpleJdbcInsert(ds)
                .withTableName(TAGS);
    }
    
    @Override
    public long register(String title, String body, long categoryId, long userId, Set<String> tags, Set<Long> movies) {

        Objects.requireNonNull(title);
        Objects.requireNonNull(body);
        Objects.requireNonNull(movies);

        body = body.trim();
        LocalDateTime creationDate = LocalDateTime.now();
        int wordCount = body.split("\\s+").length;

        HashMap<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("creation_date", Timestamp.valueOf(creationDate));
        map.put("word_count", wordCount);
        map.put("body", body);
        map.put("category_id", categoryId);
        map.put("user_id", userId);

        final long postId = postInsert.executeAndReturnKey(map).longValue();

        for(Long movie_id: movies){
            map = new HashMap<>();
            map.put("movie_id", movie_id);
            map.put("post_id", postId);
            postMoviesInsert.execute(map);
        }

        if(tags != null) {
            for (String tag : tags) {
                map = new HashMap<>();
                map.put("tag", tag);
                map.put("post_id", postId);
                tagsInsert.execute(map);
            }
        }

        return postId;
    }

    // This method abstract the logic needed to perform select queries with or without movies.
    private Collection<Post> buildAndExecuteQuery(String customWhereStatement, String customOrderByStatement, Object[] args, EnumSet<FetchRelation> includedRelations){

        EnumSet<FetchRelationSelector> selectorSet = FetchRelationSelector.getSelectorsFromFetchRelations(includedRelations);

        final String select = selectorSet.stream()
                .reduce("", (ac, selector) -> ac + ", " + selector.getSelect(), String::concat)
                .substring(1); // Remove first ','

        final String from = selectorSet.stream()
                .reduce("", (ac, selector) -> ac + " " + selector.getFrom(), String::concat);

        final String query = select + " " + from + " " + customWhereStatement + " " + customOrderByStatement;

        final ResultSetExtractor<Collection<Post>> rowMapper = FetchRelationSelector.getMapper(includedRelations);

        if(args != null)
            return jdbcTemplate.query(query, args, rowMapper);

        else
            return jdbcTemplate.query(query, rowMapper);
    }
    
    private Collection<Post> buildAndExecuteQuery(String customWhereStatement, SortCriteria sortCriteria, Object[] args, EnumSet<FetchRelation> includedRelations){
        
        if(!sortCriteriaQueryMap.containsKey(sortCriteria))
            throw new IllegalArgumentException("SortCriteria implementation not found for " + sortCriteria + " in PostDaoImpl.");
        
        return buildAndExecuteQuery(customWhereStatement, buildOrderByStatement(new String[] {sortCriteriaQueryMap.get(sortCriteria)}), args, includedRelations);
    }

    private Collection<Post> searchPostsByIntersectingFilterCriteria(FilterCriteria[] filterCriteria, Object[] args, EnumSet<FetchRelation> includedRelations, SortCriteria sortCriteria){

        String customWhereStatement = buildWhereStatement(filterCriteria," AND ");

        return buildAndExecuteQuery(customWhereStatement, sortCriteria, args , includedRelations);
    }

    private String buildWhereStatement(FilterCriteria[] filters, String criteria) {
        return buildQueryStatement("WHERE", criteria,
                Arrays.stream(filters).map(f -> f.filterQuery).toArray(String[]::new));
    }

    private String buildOrderByStatement(String[] columns) {
        return buildQueryStatement("ORDER BY", ", ", columns);
    }

    // Separator needs to come with white spaces included
    private String buildQueryStatement(String queryStart, String separator, String[] modifiers) {
        if(modifiers == null || modifiers.length == 0)
            return "";

        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append(queryStart).append(' ');

        for(String modifier : modifiers){

            queryBuilder.append(modifier);
            queryBuilder.append(separator);
        }

        //Delete last separator
        return queryBuilder.substring(0, queryBuilder.length() - separator.length());
    }

    @Override
    public Optional<Post> findPostById(long id, EnumSet<FetchRelation> includedRelations){
        return buildAndExecuteQuery("WHERE " + POSTS + ".post_id = ?", "", 
                new Object[]{ id }, includedRelations).stream().findFirst();
    }

    @Override
    public Collection<Post> findPostsByMovieId(long movie_id, EnumSet<FetchRelation> includedRelations) {
        return buildAndExecuteQuery("WHERE " +
                        POSTS + ".post_id IN ( " +
                        "SELECT " + POST_MOVIE + ".post_id " +
                        "FROM " + POST_MOVIE +
                        " WHERE " + POST_MOVIE + ".movie_id = ?)",
                SortCriteria.NEWEST, new Object[] { movie_id }, includedRelations);
    }

    @Override
    public Collection<Post> getAllPosts(EnumSet<FetchRelation> includedRelations, SortCriteria sortCriteria) {
        return buildAndExecuteQuery("", sortCriteria, null, includedRelations);
    }
    
    @Override
    public Collection<Post> searchPosts(String query, EnumSet<FetchRelation> includedRelations, SortCriteria sortCriteria) {

        FilterCriteria[] filterCriteria = new FilterCriteria[]{
                FilterCriteria.BY_POST_TITLE_MOVIE_TITLE_AND_TAGS
        };

        Object[] args = new Object[]{
                query, query, query,
        };

        return searchPostsByIntersectingFilterCriteria(filterCriteria, args, includedRelations, sortCriteria);
    }

    @Override
    public Collection<Post> searchPostsByCategory(String query, String category, EnumSet<FetchRelation> includedRelations, SortCriteria sortCriteria) {

        FilterCriteria[] filterCriteria = new FilterCriteria[]{
                FilterCriteria.BY_POST_TITLE_MOVIE_TITLE_AND_TAGS,
                FilterCriteria.BY_POST_CATEGORY
        };

        Object[] args = new Object[]{
                query, query, query,
                category
        };

        return searchPostsByIntersectingFilterCriteria(filterCriteria, args, includedRelations, sortCriteria);
    }

    @Override
    public Collection<Post> searchPostsOlderThan(String query, LocalDateTime fromDate, EnumSet<FetchRelation> includedRelations, SortCriteria sortCriteria) {

        FilterCriteria[] filterCriteria = new FilterCriteria[]{
                FilterCriteria.BY_POST_TITLE_MOVIE_TITLE_AND_TAGS,
                FilterCriteria.POSTS_OLDER_THAN
        };

        Object[] args = new Object[]{
                query, query, query,
                Timestamp.valueOf(fromDate)
        };

        return searchPostsByIntersectingFilterCriteria(filterCriteria, args, includedRelations, sortCriteria);
    }

    @Override
    public Collection<Post> searchPostsByCategoryAndOlderThan(String query, String category, LocalDateTime fromDate, EnumSet<FetchRelation> includedRelations, SortCriteria sortCriteria) {

        FilterCriteria[] filterCriteria = new FilterCriteria[]{
                FilterCriteria.BY_POST_TITLE_MOVIE_TITLE_AND_TAGS,
                FilterCriteria.BY_POST_CATEGORY,
                FilterCriteria.POSTS_OLDER_THAN
        };

        Object[] args = new Object[]{
                query, query, query,
                category,
                Timestamp.valueOf(fromDate)
        };

        return searchPostsByIntersectingFilterCriteria(filterCriteria, args, includedRelations, sortCriteria);
    }
}