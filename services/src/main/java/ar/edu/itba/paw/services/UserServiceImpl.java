package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.PasswordResetTokenDao;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.interfaces.persistence.UserVerificationTokenDao;
import ar.edu.itba.paw.interfaces.services.MailService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.PasswordResetToken;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserVerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserVerificationTokenDao userVerificationTokenDao;

    @Autowired
    private PasswordResetTokenDao passwordResetTokenDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // All users are created with NOT_VALIDATED_ROLE by default
    private static final String NOT_VALIDATED_ROLE = "NOT_VALIDATED";
    private static final String USER_ROLE = "USER";

    @Override
    public User register(String username, String password, String name, String email, String confirmationMailTemplate) {

        final User user = userDao.register(username, passwordEncoder.encode(password), name, email, Collections.singletonList(NOT_VALIDATED_ROLE), true);

        createConfirmationEmail(user, confirmationMailTemplate);

        return user;
    }

    @Override
    public void createConfirmationEmail(User user, String confirmationMailTemplate) {

        final String token = UUID.randomUUID().toString();

        userVerificationTokenDao.createVerificationToken(token, UserVerificationToken.calculateExpiryDate(), user.getId());

        Map<String, Object> emailVariables = new HashMap<>();
        emailVariables.put("token", token);

        try {
            mailService.sendEmail(user.getEmail(), "Moovify - Confirmation Email", confirmationMailTemplate, emailVariables);
        }
        catch (MessagingException e) {
            // TODO: Log and rollback and handle exception better. Por ejemplo, mandarlo a Controller y mostrar un intentar enviar el mail de nuevo
            System.out.println("Confirmation email failed to send");
        }
    }

    @Override
    public void createPasswordResetEmail(User user, String passwordResetMailTemplate) {
        
        final String token = UUID.randomUUID().toString();

        passwordResetTokenDao.createPasswordResetToken(token, PasswordResetToken.calculateExpiryDate(), user.getId());

        Map<String, Object> emailVariables = new HashMap<>();
        emailVariables.put("token", token);

        try {
            mailService.sendEmail(user.getEmail(), "Moovify - Password Reset", passwordResetMailTemplate, emailVariables);
        }
        catch (MessagingException e) {
            // TODO: Log. Mejor handling del error de mail
            System.out.println("Password reset email failed to send");
        }
    }

    @Override
    public Optional<User> confirmRegistration(String token) {
        Optional<UserVerificationToken> optToken = userVerificationTokenDao.getVerificationToken(token);

        if(!optToken.isPresent() || !optToken.get().isValid())
            return Optional.empty();

        final User user = optToken.get().getUser();

        replaceUserRole(user, USER_ROLE, NOT_VALIDATED_ROLE);

        // Delete Token. It is not needed anymore
        userVerificationTokenDao.deleteVerificationToken(user.getId());

        return Optional.of(user);
    }

    @Override
    public boolean validatePasswordResetToken(String token) {
        return passwordResetTokenDao.getResetPasswordToken(token)
                .map(PasswordResetToken::isValid)
                .orElse(false);
    }

    @Override
    public boolean hasUserLiked(String username, long postId) {
        return userDao.hasUserLiked(username, postId);
    }

    @Override
    public Optional<User> updatePassword(String password, String token) {
        Optional<PasswordResetToken> optToken = passwordResetTokenDao.getResetPasswordToken(token);

        if(!optToken.isPresent() || !optToken.get().isValid())
            return Optional.empty();

        final User user = optToken.get().getUser();

        userDao.updatePassword(user.getId(), passwordEncoder.encode(password));

        // Delete Token. It is not needed anymore
        passwordResetTokenDao.deletePasswordResetToken(user.getId());

        return Optional.of(user);
    }

    @Override
    public boolean emailExistsAndIsValidated(String email) {
        return userDao.userHasRole(email, USER_ROLE);
    }

    @Override
    public Optional<User> findById(long id) {
        return userDao.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public Collection<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    private void replaceUserRole(User user, String newRole, String oldRole) {

        userDao.replaceUserRole(user.getId(), newRole, oldRole);

        user.getRoles().removeIf(role -> role.getRole().equals(oldRole));

        user.getRoles().add(new Role(newRole));
    }


}
