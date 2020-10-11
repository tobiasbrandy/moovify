package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.PasswordResetTokenDao;
import ar.edu.itba.paw.models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class PasswordResetTokenDaoImplTest {

    private static final long USER_ID = 1L;
    @Autowired
    private PasswordResetTokenDao passwordResetTokenDao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert tokenInsert;

    @Before
    public void testSetUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.tokenInsert = new SimpleJdbcInsert(ds)
                .withTableName(TableNames.PASSWORD_RESET_TOKEN.getTableName())
                .usingGeneratedKeyColumns("token_id");
    }

    @Test
    public void createPasswordResetToken() {

        JdbcTestUtils.deleteFromTables(jdbcTemplate, TableNames.PASSWORD_RESET_TOKEN.getTableName());

        passwordResetTokenDao.createPasswordResetToken(
                UUID.randomUUID().toString(),
                LocalDateTime.now().plusDays(1),
                Mockito.when(Mockito.mock(User.class).getId()).thenReturn(USER_ID).getMock());

        final int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, TableNames.PASSWORD_RESET_TOKEN.getTableName(), String.format("user_id = %d", USER_ID));

        Assert.assertEquals(1, count);
    }


    @Test
    public void testDeletePasswordResetToken() {

        Map<String, Object> map = new HashMap<>();
        map.put("user_id", USER_ID);
        map.put("token", UUID.randomUUID());
        map.put("expiry", Timestamp.valueOf(LocalDateTime.now().plusDays(1)));

        tokenInsert.execute(map);

        passwordResetTokenDao.deletePasswordResetToken(Mockito.when(Mockito.mock(User.class).getId()).thenReturn(USER_ID).getMock());

        final int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, TableNames.PASSWORD_RESET_TOKEN.getTableName(), String.format("user_id = %d", USER_ID));

        Assert.assertEquals(0, count);
    }
}