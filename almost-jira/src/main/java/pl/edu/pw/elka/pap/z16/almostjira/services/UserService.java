package pl.edu.pw.elka.pap.z16.almostjira.services;

import org.springframework.stereotype.Service;

import pl.edu.pw.elka.pap.z16.almostjira.exceptions.LoginAlreadyInUseException;
import pl.edu.pw.elka.pap.z16.almostjira.exceptions.ResourceNotFoundException;
import pl.edu.pw.elka.pap.z16.almostjira.models.*;
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

    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow((() ->
                new ResourceNotFoundException("User", "id", id)));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(String id) {
        userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", id));
        userRepository.deleteById(id);
    }

    public User login(String login, String password) {
        var userlist = getAllUsers();
        for (User currentuser : userlist) {
            if (login.equals(currentuser.login()) && password.equals(currentuser.password()))
                return currentuser;
        }
        throw new ResourceNotFoundException("Matching data", "", login);
    }

    public User updateUser(UserForm u, String id) {
        User existingUser = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "Id", id));

        Date now = new Date();
        User updatedUser = existingUser.toBuilder()
                .firstName((u.firstName() == null) ? existingUser.firstName() : u.firstName())
                .lastName((u.lastName() == null) ? existingUser.lastName() : u.lastName())
                .login((u.login() == null) ? existingUser.login() : u.login())
                .password((u.password() == null) ? existingUser.password() : u.password())
                .lastModified(now)
                .build();

        var userlist = getAllUsers();
        for (User currentUser : userlist) {
            if (currentUser.login().equals(updatedUser.login()) && !currentUser.id().equals(updatedUser.id()))
                throw (new LoginAlreadyInUseException());
        }
        return userRepository.save(updatedUser);
    }

    public User createUser(UserForm u) {
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

        var userlist = getAllUsers();
        for (User user : userlist) {
            if (user.login().equals(newUser.login()))
                throw (new LoginAlreadyInUseException());
        }

        return userRepository.save(newUser);
    }
}
