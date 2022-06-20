package tqs.project.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqs.project.datamodels.RegisterDTO;
import tqs.project.exceptions.UserAlreadyExistsException;
import tqs.project.exceptions.UserNotFoundException;
import tqs.project.model.User;
import tqs.project.service.UserService;

@RestController
@RequestMapping("/api/users")
@Validated
@CrossOrigin
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers(){
        log.info("GET Request -> All Users");

        List<User> users = userService.getAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/login/{email}")
    public ResponseEntity<Object> loginUser(@PathVariable String email){
        log.info("GET Request -> User Data for login email");

        User userLog;
        try {
            userLog = userService.login(email);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        log.info("Login -> User logged in");
        return new ResponseEntity<>(userLog, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody RegisterDTO reg){

        log.info("POST Request -> User Data for register");

        User userReg;
        try {
            userReg = userService.register(reg);
        } catch (UserAlreadyExistsException e) {
            log.info("Register -> User already exists");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        log.info("Register -> User registered");
        return new ResponseEntity<>(userReg, HttpStatus.CREATED);
    }
}
