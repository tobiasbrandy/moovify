package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Post;
import ar.edu.itba.paw.models.PostCategory;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface PostService {

    long register(String title, String body, long category, long user, Set<String> tags, Set<Long> movies);

    void likePost(long post_id, long user_id, boolean value);

    Optional<Post> findPostById(long id);

    Collection<Post> findPostsByMovieId(long movie_id);

    Collection<Post> findPostsByUserId(long user_id);

    Collection<Post> getAllPostsOrderByNewest();

    Collection<Post> getAllPostsOrderByOldest();

    Collection<Post> getAllPostsOrderByHottest();

    Collection<PostCategory> getAllPostCategories();
}