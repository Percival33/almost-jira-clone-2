package pl.edu.pw.elka.pap.z16.almostjira.services;

import org.springframework.stereotype.Service;

import pl.edu.pw.elka.pap.z16.almostjira.exceptions.LoginAlreadyInUseException;
import pl.edu.pw.elka.pap.z16.almostjira.exceptions.ResourceNotFoundException;
import pl.edu.pw.elka.pap.z16.almostjira.models.User;
import pl.edu.pw.elka.pap.z16.almostjira.models.UserForm;
import pl.edu.pw.elka.pap.z16.almostjira.repositories.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(String id) throws ResourceNotFoundException {
        return userRepository.findById(id)
                .orElseThrow((() -> new ResourceNotFoundException("User", "id", id)));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(String id) throws ResourceNotFoundException {
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.deleteById(id);
    }

    public User login(String login, String password) throws ResourceNotFoundException {
        return getAllUsers().stream()
                .filter(user -> user.login().equals(login) && user.password().equals(password))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Matching data", "", login));
    }

    public User updateUser(UserForm u, String id) throws LoginAlreadyInUseException, ResourceNotFoundException {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));

        Date now = new Date();
        User updatedUser = existingUser.toBuilder()
                .firstName((u.firstName() == null) ? existingUser.firstName() : u.firstName())
                .lastName((u.lastName() == null) ? existingUser.lastName() : u.lastName())
                .login((u.login() == null) ? existingUser.login() : u.login())
                .password((u.password() == null) ? existingUser.password() : u.password())
                .lastModified(now)
                .build();

        checkLoginAlreadyInUse(updatedUser.login(), updatedUser.id());
        return userRepository.save(updatedUser);
    }

    public User createUser(UserForm u) throws LoginAlreadyInUseException {
        Date now = new Date();

        var newUser = User.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .firstName(u.firstName())
                .lastName(u.lastName())
                .login(u.login())
                .password(u.password())
                .projects(null)
                .createdAt(now)
                .lastModified(now)
                .build();

        checkLoginAlreadyInUse(newUser.login(), newUser.id());
        return userRepository.save(newUser);
    }

    private void checkLoginAlreadyInUse(String login, String userId) throws LoginAlreadyInUseException {
        if (getAllUsers().stream()
                .anyMatch(user -> user.login().equals(login) && !user.id().equals(userId))) {
            throw new LoginAlreadyInUseException();
        }
    }
}
