package cat.tecnocampus.securityjwt.service;

import cat.tecnocampus.securityjwt.domain.UserLab;
import cat.tecnocampus.securityjwt.domain.Role;
import cat.tecnocampus.securityjwt.domain.ERole;
import cat.tecnocampus.securityjwt.dto.UserCreationRequest;
import cat.tecnocampus.securityjwt.persistence.UserLabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

@Service
public class UserService {

    private final UserLabRepository userLabRepository;

    @Autowired
    public UserService(UserLabRepository userLabRepository) {
        this.userLabRepository = userLabRepository;
    }

    public ResponseEntity<String> createUser(UserCreationRequest request) {

        if (!request.role().equals("ADMIN") && !request.role().equals("USER") && !request.role().equals("MODERATOR")) {
            return ResponseEntity.badRequest().body("Invalid role. Role must be ADMIN, USER, or MODERATOR.");
        }


        UserLab user = new UserLab(request.username(), request.email(), request.password());

        Role role = new Role(ERole.valueOf(request.role()));
        user.getRoles().add(role);

        // Guardar el usuari en la base de datos
        userLabRepository.save(user);

        return ResponseEntity.ok("User created successfully with role " + request.role());
    }
}
