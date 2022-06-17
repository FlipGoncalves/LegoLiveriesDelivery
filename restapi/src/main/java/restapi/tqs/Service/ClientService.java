package restapi.tqs.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restapi.tqs.DataModels.RegisterDTO;
import restapi.tqs.Exceptions.ClientAlreadyExistsException;
import restapi.tqs.Exceptions.ClientNotFoundException;
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

    public Client login(String email) throws ClientNotFoundException{
        log.info("Login Client: {}", email);

        Optional<Client> client = clientRepository.findByUserEmail(email);

        if (client.isEmpty()){
            throw new ClientNotFoundException("Client with email " + email + "was not found");
        }

        return client.get();

    }

    public Client insertClient(RegisterDTO dto) throws ClientAlreadyExistsException {
        log.info("Registering Client: {}", dto);

        Client client = new Client();
        
        User user = createOrGetUser(dto);

        if (clientRepository.findByUserEmail(dto.getEmail()).isPresent()) {
            throw new ClientAlreadyExistsException("Client already exists: " + dto.toString());
        }

        client.setUser(user);

        client = clientRepository.saveAndFlush(client);

        user.setClient(client);

        return client;
    }

    public User createOrGetUser(RegisterDTO dto){
        Optional<User> userOptional = userRepository.findByEmail(dto.getEmail());
        
        User user = new User();
        
        if (userOptional.isEmpty()) {
            user.setEmail(dto.getEmail());
            user.setUsername(dto.getUsername());
            user.setPassword(dto.getPassword());
            user = userRepository.saveAndFlush(user);
        } else{
            user = userOptional.get();
        }

        return user;
    }

}