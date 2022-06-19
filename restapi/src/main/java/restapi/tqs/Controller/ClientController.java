package restapi.tqs.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import restapi.tqs.DataModels.RegisterDTO;
import restapi.tqs.Exceptions.ClientAlreadyExistsException;
import restapi.tqs.Exceptions.ClientNotFoundException;
import restapi.tqs.Models.Client;
import restapi.tqs.Service.ClientService;

@RestController
@CrossOrigin
@RequestMapping("/clients")
public class ClientController {
    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    @GetMapping()
    public ResponseEntity<List<Client>> getAllClients() {
        log.info("GET Request -> All Client Data");
        List<Client> clients = clientService.getAllClients();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/login/{clientEmail}")
    public ResponseEntity<Client> getClientByEmail(@PathVariable String clientEmail) {
        log.info("GET Request -> Login Email ");
        Client client;
        try {
            client = clientService.login(clientEmail);
        } catch (ClientNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Client> insertClient(@RequestBody RegisterDTO clientDTO) {
        log.info("Post Request -> Insert Client: {}", clientDTO);

        Client client;
        try {
            client = clientService.insertClient(clientDTO);
        } catch (ClientAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }
}
