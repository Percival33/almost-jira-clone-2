package pl.edu.pw.elka.pap.z16.almostjira.controllers.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.elka.pap.z16.almostjira.exceptions.ClientNotAuthorizedException;
import pl.edu.pw.elka.pap.z16.almostjira.exceptions.LoginAlreadyInUseException;
import pl.edu.pw.elka.pap.z16.almostjira.models.UserForm;
import pl.edu.pw.elka.pap.z16.almostjira.services.UserService;
import pl.edu.pw.elka.pap.z16.almostjira.utils.ResponseHandler;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsersController {
    @Autowired
    UserService userService;


    @PostMapping()
    public ResponseEntity<Object> createUser(@RequestBody UserForm newUser){
        try {
            return ResponseHandler.generateResponse("success", HttpStatus.CREATED, userService.createUser(newUser));
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equals(LoginAlreadyInUseException.getName()))
                return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, null);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") String user_id, @RequestBody UserForm u){
        try {
            return ResponseHandler.generateResponse("success", HttpStatus.OK, userService.updateUser(u, user_id));
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equals(LoginAlreadyInUseException.getName()))
                return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, null);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") String user_id){
        try {
            userService.deleteUser(user_id);
            return ResponseHandler.generateResponse("success", HttpStatus.NO_CONTENT, "User deleted successfully!");
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") String user_id){
        try {
            return ResponseHandler.generateResponse("success", HttpStatus.OK, userService.getUserById(user_id));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        try {
            return ResponseHandler.generateResponse("success", HttpStatus.OK, userService.getAllUsers());
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }


    @PutMapping
    public ResponseEntity<Object> loginattempt(String login, String password){
        try {
            return ResponseHandler.generateResponse("success", HttpStatus.OK, userService.login(login, password));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    // lista uzytkownikow
    // getery i setery dla uzytkownikow o danym loginie
}
