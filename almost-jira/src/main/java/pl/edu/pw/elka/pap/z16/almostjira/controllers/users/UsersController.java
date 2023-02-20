package pl.edu.pw.elka.pap.z16.almostjira.controllers.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.edu.pw.elka.pap.z16.almostjira.exceptions.LoginAlreadyInUseException;
import pl.edu.pw.elka.pap.z16.almostjira.exceptions.ResourceNotFoundException;
import pl.edu.pw.elka.pap.z16.almostjira.models.UserForm;
import pl.edu.pw.elka.pap.z16.almostjira.services.UserService;
import pl.edu.pw.elka.pap.z16.almostjira.utils.ResponseHandler;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsersController {
    private final UserService userService;
    private static final String MSG = "success";
    public UsersController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping()
    public ResponseEntity<Object> createUser(@RequestBody UserForm newUser){
        try {
            return ResponseHandler.generateResponse(MSG, HttpStatus.CREATED, userService.createUser(newUser));
        } catch (LoginAlreadyInUseException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, null);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") String userId, @RequestBody UserForm u){
        try {
            return ResponseHandler.generateResponse(MSG, HttpStatus.OK, userService.updateUser(u, userId));
        } catch (LoginAlreadyInUseException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, null);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") String userId){
        try {
            userService.deleteUser(userId);
            return ResponseHandler.generateResponse(MSG, HttpStatus.NO_CONTENT, "User deleted successfully!");
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") String userId){
        try {
            return ResponseHandler.generateResponse(MSG, HttpStatus.OK, userService.getUserById(userId));
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        return ResponseHandler.generateResponse(MSG, HttpStatus.OK, userService.getAllUsers());
    }


    @PutMapping
    public ResponseEntity<Object> loginAttempt(String login, String password){
        try {
            return ResponseHandler.generateResponse(MSG, HttpStatus.OK, userService.login(login, password));
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }
}
