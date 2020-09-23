package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Post;
import ar.edu.itba.paw.models.User;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

    User register(String username, String password, String name, String email);

    Optional<User> confirmRegistration(String token);

    String createVerificationToken(long userId);

    String createPasswordResetToken(long userId);

    boolean validatePasswordResetToken(String token);

    Optional<User> updatePassword(String password, String token);

    boolean emailExistsAndIsValidated(String email);

    Optional<User> findById(long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Collection<Post> findPostsByUserId(long user_id);

    Collection<Post> getAllUsers(long user_id);

    Collection<User> getAllUsers();
}