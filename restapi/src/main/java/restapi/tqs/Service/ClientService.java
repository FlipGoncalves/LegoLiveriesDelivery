package restapi.tqs.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restapi.tqs.DataModels.RegisterDTO;
import restapi.tqs.Exceptions.ClientAlreadyExistsException;
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

    public Client insertClient(RegisterDTO dto) throws UserNotFoundException, ClientAlreadyExistsException {
        log.info("Registering Client: {}", dto);

        Client client = new Client();

        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found: " + dto.toString());
        }

        if (clientRepository.findByUserEmail(dto.getEmail()) != null) {
            throw new ClientAlreadyExistsException("Client already exists: " + dto.toString());
        }

        client.setUser(user.get());

        return clientRepository.saveAndFlush(client);
    }

}
