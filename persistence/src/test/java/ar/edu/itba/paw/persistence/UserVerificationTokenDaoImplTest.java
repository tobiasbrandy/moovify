package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserVerificationToken;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Optional;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Rollback
public class UserVerificationTokenDaoImplTest {

    private static final long USER1_ID = InsertHelper.USER1_ID;
    private static final long USER2_ID = InsertHelper.USER2_ID;

    private static final long TOKEN_ID = 1L;
    private static final String INVALID_TOKEN = "Invalid Token";
    private static final String TOKEN = "XeRUE6wVBX8D8ETYLghHN44VBLcvSIJrtSNJwsGjxMGbArkljlgKPtw1auDsbPKmuPAuJNRLHghP2coHhJe2XkBqlqElp3XIWXttR453gGH3KWq0kVLJo6zPHvHNq8isU6AyQUNqE1tEkiA7labxfr345f2VDnj7QxjsVIMrfHco00H5HM5STlGxIWNQp6kzixCklaId4v9BHx6wYgSLbuixTOk37l9BLpsrja7Bue9RIo6tsDpIQPM0fv";
    private static final LocalDateTime EXPIRATORY_DATE = LocalDateTime.of(2021,8,6,8,6);

    @Autowired
    private UserVerificationTokenDaoImpl userVerificationTokenDao;

    @Autowired
    private DataSource ds;

    @PersistenceContext
    private EntityManager em;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void testSetUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    @Sql("classpath:user1.sql")
    public void testCreateUserVerificationToken() {

        // Pre conditions
        JdbcTestUtils.deleteFromTables(jdbcTemplate, UserVerificationToken.TABLE_NAME);

        User user = em.find(User.class, USER1_ID);

        // Exercise
        UserVerificationToken userVT = userVerificationTokenDao.createVerificationToken(TOKEN, EXPIRATORY_DATE, user);

        em.flush();

        final int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, UserVerificationToken.TABLE_NAME, String.format("user_id = %d", USER1_ID));

        // Post conditions
        Assert.assertEquals(1, count);
        Assert.assertEquals(TOKEN, userVT.getToken());
        Assert.assertEquals(user, userVT.getUser());
    }

    @Test
    @Sql("classpath:user1.sql")
    @Sql("classpath:user-verification-token.sql")
    public void testGetVerificationToken() {

        // Exercise
        Optional<UserVerificationToken> userVT = userVerificationTokenDao.getVerificationToken(TOKEN);

        // Post conditions
        Assert.assertTrue(userVT.isPresent());
        Assert.assertEquals(TOKEN, userVT.get().getToken());
    }

    @Test
    @Sql("classpath:user1.sql")
    @Sql("classpath:user-verification-token.sql")
    public void testGetVerificationTokenWithInvalidToken() {

        // Exercise
        Optional<UserVerificationToken> userVT = userVerificationTokenDao.getVerificationToken(INVALID_TOKEN);

        // Post conditions
        Assert.assertFalse(userVT.isPresent());
    }

    @Test
    @Sql("classpath:user1.sql")
    @Sql("classpath:user-verification-token.sql")
    public void testFindVerificationTokenByUser() {

        // Pre conditions
        User user = em.find(User.class, USER1_ID);

        // Exercise
        Optional<UserVerificationToken> userVT = userVerificationTokenDao.findVerificationTokenByUser(user);

        // Post conditions
        Assert.assertTrue(userVT.isPresent());
        Assert.assertEquals(TOKEN, userVT.get().getToken());
        Assert.assertEquals(user, userVT.get().getUser());
    }

    @Test
    @Sql("classpath:user1.sql")
    @Sql("classpath:user2.sql")
    @Sql("classpath:user-verification-token.sql")
    public void testFindVerificationTokenByInvalidUser() {

        // Pre conditions
        User user = em.find(User.class, USER2_ID);

        // Exercise
        Optional<UserVerificationToken> userVT = userVerificationTokenDao.findVerificationTokenByUser(user);

        // Post conditions
        Assert.assertFalse(userVT.isPresent());
    }

    @Test
    @Sql("classpath:user1.sql")
    @Sql("classpath:user-verification-token.sql")
    public void testDeleteVerificationToken() {

        // Pre conditions
        UserVerificationToken userVT = em.find(UserVerificationToken.class, TOKEN_ID);

        // Exercise
        userVerificationTokenDao.deleteVerificationToken(userVT);

        em.flush();

        final int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, UserVerificationToken.TABLE_NAME, String.format("user_id = %d", USER1_ID));

        // Post conditions
        Assert.assertEquals(0, count);
    }
}