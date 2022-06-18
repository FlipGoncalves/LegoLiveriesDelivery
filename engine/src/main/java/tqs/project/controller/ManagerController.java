package tqs.project.controller;

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
import tqs.project.model.Manager;
import tqs.project.service.ManagerService;

@RestController
@RequestMapping("/api/manager")
@Validated
@CrossOrigin
public class ManagerController {
    private static final Logger log = LoggerFactory.getLogger(StatisticController.class);

    @Autowired
    private ManagerService managerService;

    @GetMapping("/{email}")
    public ResponseEntity<Object> login(@PathVariable String email){
        log.info("GET Request -> Manager Data for login email");

        Manager userLog = managerService.getUser(email);

        if (userLog == null) {
            log.info("Login ->  Manager does not exist");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        log.info("Login -> Manager logged in");
        return new ResponseEntity<>(userLog, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Object> register(@RequestBody RegisterDTO reg){

        log.info("POST Request -> Manager Data for register");

        Manager manager;
        try {
            manager = managerService.register(reg);
        } catch (UserAlreadyExistsException e) {
            log.info("Register -> Manager already exists");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        log.info("Register -> Manager registered");
        return new ResponseEntity<>(manager, HttpStatus.CREATED);
    }
}
