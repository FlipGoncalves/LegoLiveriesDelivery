package restapi.tqs.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restapi.tqs.DataModels.RegisterDTO;
import restapi.tqs.Exceptions.ClientAlreadyExistsException;
import restapi.tqs.Exceptions.UserAlreadyExistsException;
import restapi.tqs.Exceptions.UserNotFoundException;
import restapi.tqs.Models.Client;
import restapi.tqs.Models.User;
import restapi.tqs.Repositories.ClientRepository;
import restapi.tqs.Repositories.UserRepository;

@Service
public class ClientService {
    private static final Logger log = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Client> getAllClients() {
        log.info("Getting All Clients");

        return clientRepository.findAll();
    }

    public Client login(String email) throws UserNotFoundException {
        log.info("Login Client with email: {}", email);

        Optional<Client> client = clientRepository.findByUserEmail(email);
        
        if (client.isEmpty()) {
            throw new UserNotFoundException("User already exists: " + client);
        }

        return client.get();
    }

    public Client insertClient(RegisterDTO dto) throws UserAlreadyExistsException, ClientAlreadyExistsException {
        log.info("Registering Client: {}", dto);

        Client client = new Client();

        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        
        if (!user.isEmpty()) {
            log.info("User already exists: {}", user.get());
            throw new UserAlreadyExistsException("User already exists: " + user.get());
        }

        Optional<Client> cli = clientRepository.findByUserEmail(dto.getEmail());
        if (!cli.isEmpty()) {
            log.info("Client already exists: {}", cli.get());
            throw new ClientAlreadyExistsException("Client already exists: " + cli.get());
        }

        User usr = new User();
        
        usr.setEmail(dto.getEmail());
        usr.setPassword(dto.getPassword());
        usr.setUsername(dto.getUsername());

        client.setUser(usr);

        return clientRepository.saveAndFlush(client);
    }

}