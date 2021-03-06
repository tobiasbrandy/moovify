package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.PasswordResetTokenDao;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.interfaces.persistence.UserVerificationTokenDao;
import ar.edu.itba.paw.interfaces.persistence.exceptions.DuplicateUniqueUserAttributeException;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.MailService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.services.exceptions.*;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.Post;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.Silent.class)

@Transactional
public class UserServiceTest {

    private static final long ID = 1L;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ENCODED_PASSWORD = "encoded";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String DESCRIPTION = "description";
    private static final String TOKEN = "token";
    private static final byte[] IMAGE = new byte[10];
    private static final int PAGE_NUMBER = 0;
    private static final int PAGE_SIZE = 2;
    private static final int TOTAL_COUNT = 4;
    private static final long AVATAR_ID = 6;
    private static final long DEFAULT_AVATAR_ID = 0;

    @Mock
    private UserDao dao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserVerificationTokenDao userVerificationTokenDao;

    @Mock
    private PasswordResetTokenDao passwordResetTokenDao;

    @Mock
    private MailService mailService;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private final UserServiceImpl userService = new UserServiceImpl();

    /*
     * - register                   -> 0 (tested)
     * - updateAvatar               -> 1
     * - getAvatar                  -> 1
     * - createConfirmationEmail    -> 2
     * - createPasswordResetEmail   -> 2
     * - confirmRegistration        -> 1
     * - validatePasswordResetToken -> 1
     * - updatePassword             -> 2
     */

    @Test
    public void testRegister() throws DuplicateUniqueUserAttributeException {

        User userMock = Mockito.when(Mockito.mock(User.class).getAvatarId()).thenReturn(DEFAULT_AVATAR_ID).getMock();

        UserService userServiceSpy = Mockito.spy(userService);

        Mockito.when(dao.register(
                Mockito.anyString(),    // username
                Mockito.anyString(),    // password
                Mockito.anyString(),    // name
                Mockito.anyString(),    // email
                Mockito.anyString(),    // description
                Mockito.anyString(),    // language
                Mockito.anySet(),       // roles
                Mockito.any(),       // image
                Mockito.anyBoolean()    // enabled
        )).thenReturn(userMock);

        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn(PASSWORD);

        Mockito.doNothing().when(userServiceSpy).createConfirmationEmail(
                Mockito.any(User.class),
                Mockito.anyString());

        User user = userServiceSpy.register(USERNAME, PASSWORD, NAME, EMAIL, DESCRIPTION, "");

        Mockito.verify(dao).register(Mockito.anyString(), // username
                Mockito.anyString(),    // password
                Mockito.anyString(),    // name
                Mockito.anyString(),    // email
                Mockito.anyString(),    // description
                Mockito.anyString(),    // language
                Mockito.anySet(),       // roles
                Mockito.isNull(),       // no hay img -> null
                Mockito.anyBoolean()    // enabled
        );

        Assert.assertEquals(DEFAULT_AVATAR_ID, user.getAvatarId());
    }

    @Test
    public void testRegisterPasswordEncoded() throws DuplicateUniqueUserAttributeException {

        User userMock = Mockito.when(Mockito.mock(User.class).getPassword()).thenReturn(ENCODED_PASSWORD).getMock();

        UserService userServiceSpy = Mockito.spy(userService);

        Mockito.when(imageService.uploadImage(Mockito.any(byte[].class), Mockito.anyString())).thenReturn(Mockito.mock(Image.class));

        Mockito.when(dao.register(
                Mockito.anyString(),    // username
                Mockito.anyString(),    // password
                Mockito.anyString(),    // name
                Mockito.anyString(),    // email
                Mockito.anyString(),    // description
                Mockito.anyString(),    // language
                Mockito.anySet(),       // roles
                Mockito.any(),          // image
                Mockito.anyBoolean()    // enabled
        )).thenReturn(userMock);

        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn(ENCODED_PASSWORD);

        Mockito.doNothing().when(userServiceSpy).createConfirmationEmail(
                Mockito.any(User.class),
                Mockito.anyString()
        );

        User user = userServiceSpy.register(USERNAME, PASSWORD, NAME, EMAIL, DESCRIPTION, "");


        Mockito.verify(dao).register(
                Mockito.anyString(),            // username
                Mockito.eq(ENCODED_PASSWORD),   // password
                Mockito.anyString(),            // name
                Mockito.anyString(),            // email
                Mockito.anyString(),            // description
                Mockito.anyString(),            // language
                Mockito.anySet(),               // roles
                Mockito.any(),                  // image
                Mockito.anyBoolean()            // enabled
        );
        Assert.assertEquals(ENCODED_PASSWORD, user.getPassword());
    }

    @Test
    public void testUpdateName() {
        String name = "testName";

        User user = Mockito.mock(User.class);
        Mockito.when(user.getName()).thenReturn("");

        userService.updateName(user, name);

        Mockito.verify(user).setName(name);
    }

    @Test
    public void testUpdateNameDoNothing() {
        String name = "testName";

        User user = Mockito.mock(User.class);
        Mockito.when(user.getName()).thenReturn(name);

        userService.updateName(user, name);

        Mockito.verify(user, Mockito.never()).setName(Mockito.anyString());
    }

    @Test
    public void testUpdateUsername() throws DuplicateUniqueUserAttributeException {
        String username = "testUsername";

        User user = Mockito.mock(User.class);
        Mockito.when(user.getUsername()).thenReturn("");

        userService.updateUsername(user, username);

        Mockito.verify(dao).updateUsername(user, username);
    }

    @Test
    public void testUpdateUsernameDoNothing() throws DuplicateUniqueUserAttributeException {
        String username = "testUsername";

        User user = Mockito.mock(User.class);
        Mockito.when(user.getUsername()).thenReturn(username);

        userService.updateUsername(user, username);

        Mockito.verify(dao, Mockito.never()).updateUsername(Mockito.any(User.class), Mockito.anyString());
    }

    @Test
    public void testUpdateDescription() {
        String description = "testDescription";

        User user = Mockito.mock(User.class);
        Mockito.when(user.getDescription()).thenReturn("");

        userService.updateDescription(user, description);

        Mockito.verify(user).setDescription(description);
    }

    @Test
    public void testUpdateDescriptionDoNothing() {
        String description = "testDescription";

        User user = Mockito.mock(User.class);
        Mockito.when(user.getDescription()).thenReturn(description);

        userService.updateDescription(user, description);

        Mockito.verify(user, Mockito.never()).setDescription(Mockito.anyString());
    }

    @Test
    public void testUpdatePassword() {
        String password = "testPassword";
        String encodedPassword = "encoded";

        User user = Mockito.mock(User.class);
        Mockito.when(user.getPassword()).thenReturn("");

        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn(encodedPassword);

        userService.updatePassword(user, password);

        Mockito.verify(passwordEncoder).encode(password);
        Mockito.verify(user).setPassword(encodedPassword);
    }

    @Test
    public void testUpdatePasswordDoNothing() {
        String password = "testPassword";

        User user = Mockito.mock(User.class);
        Mockito.when(user.getPassword()).thenReturn(password);

        userService.updatePassword(user, password);

        Mockito.verify(user, Mockito.never()).setPassword(Mockito.anyString());
    }

    @Test
    public void testUpdateAvatarEmptyByteArray() {

        User user = Mockito.mock(User.class);

        userService.updateAvatar(user, new byte[0], Image.DEFAULT_TYPE);

        Mockito.verify(imageService, Mockito.never()).uploadImage(Mockito.any(byte[].class), Mockito.anyString());
        Mockito.verify(user).setAvatar(null);
    }

    @Test
    public void testUpdateAvatar() {

        Image image = Mockito.mock(Image.class);

        User user = Mockito.mock(User.class);
        byte[] data = new byte[10];

        Mockito.when(imageService.uploadImage(Mockito.eq(data), Mockito.anyString())).thenReturn(image);

        userService.updateAvatar(user, data, Image.DEFAULT_TYPE);

        Mockito.verify(imageService).uploadImage(Mockito.eq(data), Mockito.anyString());
        Mockito.verify(user).setAvatar(image);
    }

    @Test
    public void testGetDefaultAvatar() {

        Mockito.when(imageService.findImageByPath(Mockito.anyString())).thenReturn(Optional.of(new byte[10]));

        User user = Mockito.mock(User.class);

        Mockito.when(user.getAvatarId()).thenReturn(User.DEFAULT_AVATAR_ID);

        userService.getAvatar(user);

        Mockito.verify(imageService).findImageByPath(Mockito.anyString());
    }

    @Test
    public void testGetAvatar() {

        final long avatarId = 5L;

        User user = Mockito.mock(User.class);

        Mockito.when(user.getAvatarId()).thenReturn(avatarId);

        userService.getAvatar(user);

        Mockito.verify(imageService).findImageById(Mockito.eq(avatarId));
    }

    @Test(expected = DeletedDisabledModelException.class)
    public void testDeleteDisabledUser() throws DeletedDisabledModelException {

        User user = Mockito.mock(User.class);
        Mockito.when(user.isEnabled()).thenReturn(false);

        userService.deleteUser(user);
    }

    @Test(expected = RestoredEnabledModelException.class)
    public void testRestoreEnabledUser() throws RestoredEnabledModelException {

        User user = Mockito.mock(User.class);
        Mockito.when(user.isEnabled()).thenReturn(true);

        userService.restoreUser(user);
    }

    @Test(expected = InvalidUserPromotionException.class)
    public void testPromoteUserToAdminDisabledUser() throws InvalidUserPromotionException {

        User user = Mockito.mock(User.class);
        Mockito.when(user.isEnabled()).thenReturn(false);

        userService.promoteUserToAdmin(user);
    }

    @Test(expected = InvalidUserPromotionException.class)
    public void testPromoteUserToAdminAdminUser() throws InvalidUserPromotionException {

        User user = Mockito.mock(User.class);
        Mockito.when(user.isEnabled()).thenReturn(false);
        Mockito.when(user.isAdmin()).thenReturn(true);

        userService.promoteUserToAdmin(user);
    }

    @Test(expected = InvalidUserPromotionException.class)
    public void testPromoteUserToAdminNotValidated() throws InvalidUserPromotionException {

        User user = Mockito.mock(User.class);

        Mockito.when(user.isEnabled()).thenReturn(true);
        Mockito.when(user.isAdmin()).thenReturn(false);
        Mockito.when(user.isValidated()).thenReturn(false);

        userService.promoteUserToAdmin(user);
    }

    @Test
    public void testPromoteUserToAdminValid() throws InvalidUserPromotionException {

        User user = Mockito.mock(User.class);

        Mockito.when(user.isEnabled()).thenReturn(true);
        Mockito.when(user.isAdmin()).thenReturn(false);
        Mockito.when(user.isValidated()).thenReturn(true);

        userService.promoteUserToAdmin(user);

        Mockito.verify(user).addRole(Role.ADMIN);
    }

    @Test(expected = IllegalUserFollowException.class)
    public void testFollowDisabledUser() throws IllegalUserFollowException {

        User user = Mockito.mock(User.class);

        User followedUser = Mockito.mock(User.class);
        Mockito.when(user.isEnabled()).thenReturn(false);

        userService.followUser(user, followedUser);
    }

    @Test(expected = IllegalUserUnfollowException.class)
    public void testUnfollowDisabledUser() throws IllegalUserUnfollowException {

        User user = Mockito.mock(User.class);

        User followedUser = Mockito.mock(User.class);
        Mockito.when(user.isEnabled()).thenReturn(false);

        userService.unfollowUser(user, followedUser);
    }

    @Test(expected = IllegalPostBookmarkException.class)
    public void testBookmarkDisabledPost() throws IllegalPostBookmarkException {

        User user = Mockito.mock(User.class);

        Post bookmarkedPost = Mockito.mock(Post.class);
        Mockito.when(bookmarkedPost.isEnabled()).thenReturn(false);

        userService.bookmarkPost(user, bookmarkedPost);
    }

    @Test(expected = IllegalPostUnbookmarkException.class)
    public void testUnbookmarkDisabledPost() throws IllegalPostUnbookmarkException {

        User user = Mockito.mock(User.class);

        Post bookmarkedPost = Mockito.mock(Post.class);
        Mockito.when(bookmarkedPost.isEnabled()).thenReturn(false);

        userService.unbookmarkPost(user, bookmarkedPost);
    }

    @Test(expected = InvalidSortCriteriaException.class)
    public void testGetInvalidSortCriteria() throws InvalidSortCriteriaException {
        userService.getUserSortCriteria("INVALID");
    }
}
