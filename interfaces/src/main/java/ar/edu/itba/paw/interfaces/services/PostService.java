package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.persistence.PostDao;
import ar.edu.itba.paw.interfaces.services.exceptions.*;
import ar.edu.itba.paw.models.*;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface PostService {

    Post register(String title, String body, PostCategory category, User user, Set<String> tags, Set<Long> movies);

    void deletePost(Post post) throws DeletedDisabledModelException;

    void restorePost(Post post) throws RestoredEnabledModelException;

    void likePost(Post post, User user, int value) throws IllegalPostLikeException;

    void editPost(User editor, Post post, String newBody) throws MissingPostEditPermissionException, IllegalPostEditionException;

    void guaranteePostEditionPermissions(User editor, Post post) throws IllegalPostEditionException, MissingPostEditPermissionException;

    int getVoteValue(Post post, User user);

    Optional<Post> findPostById(long id);

    PaginatedCollection<Post> getAllPosts(Boolean enabled, String sortCriteria, int pageNumber, int pageSize);

    PaginatedCollection<Post> findPostsByMovie(Movie movie, Boolean enabled, String sortCriteria, int pageNumber, int pageSize);

    PaginatedCollection<Post> findPostsByUser(User user, Boolean enabled, String sortCriteria, int pageNumber, int pageSize);

    PaginatedCollection<Post> getFollowedUsersPosts(User user, Boolean enabled, String sortCriteria, int pageNumber, int pageSize);

    PaginatedCollection<Post> getUserBookmarkedPosts(User user, Boolean enabled, String sortCriteria, int pageNumber, int pageSize);

    PaginatedCollection<PostVote> getPostVotes(Post post, int pageNumber, int pageSize);

    Collection<PostCategory> getAllPostCategories();

    Optional<PostCategory> findCategoryById(long categoryId);

    PostDao.SortCriteria getPostSortCriteria(String sortCriteriaName);

    Collection<String> getPostSortOptions();
}