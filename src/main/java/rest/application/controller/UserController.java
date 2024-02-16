package rest.application.controller;

import rest.application.exception.UserNotFoundException;
import rest.application.model.User;
import rest.application.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@CrossOrigin(value = "http://localhost:3000")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    //Create user
    @PostMapping(value = "/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    //Show all users
    @GetMapping(value = "/users")
    List<User> getAllUsers() {
        return repository.findAll();
    }

    //show info for a user
    @GetMapping(value = "/users/{id}")
    User getUserById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    //update user
    @PutMapping(value = "/users/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        return repository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setEmail(newUser.getEmail());
                    user.setName(newUser.getName());
                    return repository.save(user);
                }).orElseThrow(() -> new UserNotFoundException(id));
    }

    //delete user
    @DeleteMapping(value = "/users/{id}")
    String deleteUser(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        repository.deleteById(id);
        return "user with " + id + "has been deleted successfully";
    }

    //    check if user exists by username
    @GetMapping(value = "/users/check/username/")
    public boolean doesUserExistByUsername(@RequestParam String username) {
        User user = repository.findByUsername(username);
        return user != null;
    }

    //    check if email exists
    @GetMapping(value = "/users/check/email/")
    public boolean doesUserExistByEmail(@RequestParam String email) {
        User user = repository.findByEmail(email);
        return user != null;
    }
}
