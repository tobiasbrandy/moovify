package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.*;
import ar.edu.itba.paw.models.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class SearchServiceImplTest {

    private static final int PAGE_SIZE = 5;
    private static final int PAGE_NUMBER = 0;
    private static final int TOTAL_COUNT = 10;
    private static final long CATEGORY_ID = 123L;
    private static final String ROLE = "user";
    private static final String QUERY = "Query";
    private static final String DECADE = "1920s";
    private static final String PERIOD = "pastWeek";
    private static final String MOVIE_CATEGORY = "action";
    private static final String POST_CATEGORY = "watchlist";

    @Mock
    private PostDao postDao;

    @Mock
    private MovieDao movieDao;

    @Mock
    private UserDao userDao;

    @Mock
    private CommentDao commentDao;

    @InjectMocks
    private final SearchServiceImpl searchService = new SearchServiceImpl(
            Mockito.when(Mockito.mock(PostCategoryDao.class)
                    .getAllPostCategories()).thenReturn(
                            Collections.singletonList(new PostCategory(CATEGORY_ID, LocalDateTime.now(), POST_CATEGORY))
            ).getMock(),
            Mockito.when(Mockito.mock(MovieCategoryDao.class)
                    .getAllCategories()).thenReturn(
                            Collections.singletonList(new MovieCategory(CATEGORY_ID, CATEGORY_ID, MOVIE_CATEGORY))
            ).getMock());

    @Test
    public void testSearchPosts() {

        Mockito.when(postDao.searchPosts(
                Mockito.anyString(),
                Mockito.anyBoolean(),
                Mockito.any(PostDao.SortCriteria.class),
                Mockito.anyInt(),
                Mockito.anyInt())
        ).thenReturn(new PaginatedCollection<>(new ArrayList<>(), PAGE_NUMBER, PAGE_SIZE, TOTAL_COUNT));

        Optional<PaginatedCollection<Post>> posts = searchService.searchPosts(QUERY, null, null, true, null, PAGE_NUMBER, PAGE_SIZE);

        Mockito.verify(postDao).searchPosts(
                Mockito.anyString(),
                Mockito.anyBoolean(),
                Mockito.isA(PostDao.SortCriteria.class),
                Mockito.intThat(e -> e >= 0),
                Mockito.intThat(e -> e > 0)
        );

        Assert.assertTrue(posts.isPresent());
    }

    @Test
    public void testSearchPostsByCategory() {

        Mockito.when(postDao.searchPostsByCategory(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyBoolean(),
                Mockito.any(PostDao.SortCriteria.class),
                Mockito.anyInt(),
                Mockito.anyInt())
        ).thenReturn(new PaginatedCollection<>(new ArrayList<>(), PAGE_NUMBER, PAGE_SIZE, TOTAL_COUNT));

        Optional<PaginatedCollection<Post>> posts = searchService.searchPosts(QUERY, POST_CATEGORY, null, true, null, PAGE_NUMBER, PAGE_SIZE);

        Mockito.verify(postDao).searchPostsByCategory(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyBoolean(),
                Mockito.any(PostDao.SortCriteria.class),
                Mockito.intThat(e -> e >= 0),
                Mockito.intThat(e -> e > 0)
        );

        Assert.assertTrue(posts.isPresent());
    }

    @Test
    public void testSearchPostsByPeriod() {

        Mockito.when(postDao.searchPostsOlderThan(
                Mockito.anyString(),
                Mockito.any(LocalDateTime.class),
                Mockito.anyBoolean(),
                Mockito.any(PostDao.SortCriteria.class),
                Mockito.anyInt(),
                Mockito.anyInt())
        ).thenReturn(new PaginatedCollection<>(new ArrayList<>(), PAGE_NUMBER, PAGE_SIZE, TOTAL_COUNT));

        Optional<PaginatedCollection<Post>> posts = searchService.searchPosts(QUERY, null, PERIOD, true, null, PAGE_NUMBER, PAGE_SIZE);

        Mockito.verify(postDao).searchPostsOlderThan(
                Mockito.anyString(),
                Mockito.any(LocalDateTime.class),
                Mockito.anyBoolean(),
                Mockito.any(PostDao.SortCriteria.class),
                Mockito.intThat(e -> e >= 0),
                Mockito.intThat(e -> e > 0)
        );

        Assert.assertTrue(posts.isPresent());
    }

    @Test
    public void testSearchPostsByCategoryAndPeriod() {

        Mockito.when(postDao.searchPostsByCategoryAndOlderThan(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(LocalDateTime.class),
                Mockito.anyBoolean(),
                Mockito.any(PostDao.SortCriteria.class),
                Mockito.anyInt(),
                Mockito.anyInt())
        ).thenReturn(new PaginatedCollection<>(new ArrayList<>(), PAGE_NUMBER, PAGE_SIZE, TOTAL_COUNT ));

        Optional<PaginatedCollection<Post>> posts = searchService.searchPosts(QUERY, POST_CATEGORY, PERIOD, true, null, PAGE_NUMBER, PAGE_SIZE);

        Mockito.verify(postDao).searchPostsByCategoryAndOlderThan(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(LocalDateTime.class),
                Mockito.anyBoolean(),
                Mockito.any(PostDao.SortCriteria.class),
                Mockito.anyInt(),
                Mockito.anyInt()
        );

        Assert.assertTrue(posts.isPresent());
    }

    @Test
    public void testSearchPostsFail() {

        Optional<PaginatedCollection<Post>> posts = searchService.searchPosts(null, null, null, true, null, PAGE_NUMBER, PAGE_SIZE);

        Assert.assertFalse(posts.isPresent());
    }

    @Test
    public void testSearchMovies() {

        Mockito.when(movieDao.searchMovies(
                Mockito.anyString(),
                Mockito.any(MovieDao.SortCriteria.class),
                Mockito.anyInt(),
                Mockito.anyInt())
        ).thenReturn(new PaginatedCollection<>(new ArrayList<>(), PAGE_NUMBER, PAGE_SIZE, TOTAL_COUNT ));

        Optional<PaginatedCollection<Movie>> movie = searchService.searchMovies(QUERY, null, null, null, PAGE_NUMBER, PAGE_SIZE);

        Mockito.verify(movieDao).searchMovies(
                Mockito.anyString(),
                Mockito.any(MovieDao.SortCriteria.class),
                Mockito.intThat(e -> e >= 0),
                Mockito.intThat(e -> e > 0)
        );

        Assert.assertTrue(movie.isPresent());
    }

    @Test
    public void testSearchMoviesByCategory() {

        Mockito.when(movieDao.searchMoviesByCategory(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(MovieDao.SortCriteria.class),
                Mockito.anyInt(),
                Mockito.anyInt())
        ).thenReturn(new PaginatedCollection<>(new ArrayList<>(), PAGE_NUMBER, PAGE_SIZE, TOTAL_COUNT ));

        Optional<PaginatedCollection<Movie>> movie = searchService.searchMovies(QUERY, MOVIE_CATEGORY, null, null, PAGE_NUMBER, PAGE_SIZE);

        Mockito.verify(movieDao).searchMoviesByCategory(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(MovieDao.SortCriteria.class),
                Mockito.intThat(e -> e >= 0),
                Mockito.intThat(e -> e > 0)
        );

        Assert.assertTrue(movie.isPresent());
    }

    @Test
    public void testSearchMoviesByDecade() {

        Mockito.when(movieDao.searchMoviesByReleaseDate(
                Mockito.anyString(),
                Mockito.any(LocalDate.class),
                Mockito.any(LocalDate.class),
                Mockito.any(MovieDao.SortCriteria.class),
                Mockito.anyInt(),
                Mockito.anyInt())
        ).thenReturn(new PaginatedCollection<>(new ArrayList<>(), PAGE_NUMBER, PAGE_SIZE, TOTAL_COUNT ));

        Optional<PaginatedCollection<Movie>> movie = searchService.searchMovies(QUERY, null, DECADE, null, PAGE_NUMBER, PAGE_SIZE);

        Mockito.verify(movieDao).searchMoviesByReleaseDate(
                Mockito.anyString(),
                Mockito.any(LocalDate.class),
                Mockito.any(LocalDate.class),
                Mockito.any(MovieDao.SortCriteria.class),
                Mockito.intThat(e -> e >= 0),
                Mockito.intThat(e -> e > 0)
        );

        Assert.assertTrue(movie.isPresent());
    }

    @Test
    public void testSearchMoviesByCategoryAndDecade() {

        Mockito.when(movieDao.searchMoviesByCategoryAndReleaseDate(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(LocalDate.class),
                Mockito.any(LocalDate.class),
                Mockito.any(MovieDao.SortCriteria.class),
                Mockito.anyInt(),
                Mockito.anyInt())
        ).thenReturn(new PaginatedCollection<>(new ArrayList<>(), PAGE_NUMBER, PAGE_SIZE, TOTAL_COUNT ));

        Optional<PaginatedCollection<Movie>> movie = searchService.searchMovies(QUERY, MOVIE_CATEGORY, DECADE, null, PAGE_NUMBER, PAGE_SIZE);

        Mockito.verify(movieDao).searchMoviesByCategoryAndReleaseDate(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(LocalDate.class),
                Mockito.any(LocalDate.class),
                Mockito.any(MovieDao.SortCriteria.class),
                Mockito.intThat(e -> e >= 0),
                Mockito.intThat(e -> e > 0)
        );

        Assert.assertTrue(movie.isPresent());
    }

    @Test
    public void testSearchMoviesFail() {

        Optional<PaginatedCollection<Movie>> movie = searchService.searchMovies(null, null, null, null, PAGE_NUMBER, PAGE_SIZE);

        Assert.assertFalse(movie.isPresent());
    }

    @Test
    public void testSearchUsers() {

        Mockito.when(userDao.searchUsers(
                Mockito.anyString(),
                Mockito.anyBoolean(),
                Mockito.any(UserDao.SortCriteria.class),
                Mockito.anyInt(),
                Mockito.anyInt())
        ).thenReturn(new PaginatedCollection<>(new ArrayList<>(), PAGE_NUMBER, PAGE_SIZE, TOTAL_COUNT ));

        Optional<PaginatedCollection<User>> user = searchService.searchUsers(QUERY, null, true, null, PAGE_NUMBER, PAGE_SIZE);

        Mockito.verify(userDao).searchUsers(
                Mockito.anyString(),
                Mockito.anyBoolean(),
                Mockito.any(UserDao.SortCriteria.class),
                Mockito.intThat(e -> e >= 0),
                Mockito.intThat(e -> e > 0)
        );

        Assert.assertTrue(user.isPresent());
    }

    @Test
    public void testSearchUsersByRole() {

        Mockito.when(userDao.searchUsersByRole(
                Mockito.anyString(),
                Mockito.any(Role.class),
                Mockito.anyBoolean(),
                Mockito.any(UserDao.SortCriteria.class),
                Mockito.anyInt(),
                Mockito.anyInt())
        ).thenReturn(new PaginatedCollection<>(new ArrayList<>(), PAGE_NUMBER, PAGE_SIZE, TOTAL_COUNT ));

        Optional<PaginatedCollection<User>> user = searchService.searchUsers(QUERY, ROLE, true, null, PAGE_NUMBER, PAGE_SIZE);

        Mockito.verify(userDao).searchUsersByRole(
                Mockito.anyString(),
                Mockito.any(Role.class),
                Mockito.anyBoolean(),
                Mockito.any(UserDao.SortCriteria.class),
                Mockito.intThat(e -> e >= 0),
                Mockito.intThat(e -> e > 0)
        );

        Assert.assertTrue(user.isPresent());
    }

    @Test
    public void testSearchUsersFail() {

        Optional<PaginatedCollection<User>> user = searchService.searchUsers(null, null, true, null, PAGE_NUMBER, PAGE_SIZE);

        Assert.assertFalse(user.isPresent());
    }

//    @Test
//    public void testSearchDeletedPosts() {
//
//        Mockito.when(postDao.searchDeletedPosts(
//                Mockito.anyString(),
//                Mockito.any(PostDao.SortCriteria.class),
//                Mockito.anyInt(),
//                Mockito.anyInt())
//        ).thenReturn(new PaginatedCollection<>(new ArrayList<>(), PAGE_NUMBER, PAGE_SIZE, TOTAL_COUNT ));
//
//        Optional<PaginatedCollection<Post>> posts = searchService.searchPosts(QUERY, null, null, false, "newest",  PAGE_NUMBER, PAGE_SIZE);
//
//        Mockito.verify(postDao).searchDeletedPosts(
//                Mockito.anyString(),
//                Mockito.any(PostDao.SortCriteria.class),
//                Mockito.intThat(e -> e >= 0),
//                Mockito.intThat(e -> e > 0)
//        );
//
//        Assert.assertTrue(posts.isPresent());
//    }

    @Test
    public void testSearchDeletedPostsFail() {

        Optional<PaginatedCollection<Post>> posts = searchService.searchPosts(null, null, null, false, "newest",  PAGE_NUMBER, PAGE_SIZE);

        Assert.assertFalse(posts.isPresent());
    }

//    @Test
//    public void testSearchDeletedComments() {
//
//        Mockito.when(commentDao.searchComments(
//                Mockito.anyString(),
//                Mockito.anyBoolean(),
//                Mockito.any(CommentDao.SortCriteria.class),
//                Mockito.anyInt(),
//                Mockito.anyInt())
//        ).thenReturn(new PaginatedCollection<>(new ArrayList<>(), PAGE_NUMBER, PAGE_SIZE, TOTAL_COUNT ));
//
//        Optional<PaginatedCollection<Comment>> comments = searchService.searchDeletedComments(QUERY, "newest",  PAGE_NUMBER, PAGE_SIZE);
//
//        Mockito.verify(commentDao).searchDeletedComments(
//                Mockito.anyString(),
//                Mockito.any(CommentDao.SortCriteria.class),
//                Mockito.intThat(e -> e >= 0),
//                Mockito.intThat(e -> e > 0)
//        );
//
//        Assert.assertTrue(comments.isPresent());
//    }

//    @Test
//    public void testSearchDeletedCommentsFail() {
//
//        Optional<PaginatedCollection<Comment>> comments = searchService.searchDeletedComments(null, "newest",  PAGE_NUMBER, PAGE_SIZE);
//
//        Assert.assertFalse(comments.isPresent());
//    }

//    @Test
//    public void testSearchDeletedUsers() {
//
//        Mockito.when(userDao.searchDeletedUsers(
//                Mockito.anyString(),
//                Mockito.any(UserDao.SortCriteria.class),
//                Mockito.anyInt(),
//                Mockito.anyInt())
//        ).thenReturn(new PaginatedCollection<>(new ArrayList<>(), PAGE_NUMBER, PAGE_SIZE, TOTAL_COUNT ));
//
//        Optional<PaginatedCollection<User>> users = searchService.searchDeletedUsers(QUERY, "newest",  PAGE_NUMBER, PAGE_SIZE);
//
//        Mockito.verify(userDao).searchDeletedUsers(
//                Mockito.anyString(),
//                Mockito.any(UserDao.SortCriteria.class),
//                Mockito.intThat(e -> e >= 0),
//                Mockito.intThat(e -> e > 0)
//        );
//
//        Assert.assertTrue(users.isPresent());
//    }

//    @Test
//    public void testSearchDeletedUsersFail() {
//
//        Optional<PaginatedCollection<User>> users = searchService.searchDeletedUsers(null, "newest",  PAGE_NUMBER, PAGE_SIZE);
//
//        Assert.assertFalse(users.isPresent());
//    }
}