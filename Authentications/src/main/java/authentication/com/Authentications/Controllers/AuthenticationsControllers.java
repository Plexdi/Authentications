package authentication.com.Authentications.Controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import authentication.com.Authentications.DTO.LoginResponse;
import authentication.com.Authentications.Interface.userInterface;
import authentication.com.Authentications.models.UserModel;


@RestController
@RequestMapping("/api/Authentications")
public class AuthenticationsControllers {
    @Autowired private authentication.com.Authentications.Services.AuthenticationsServices authenticationService; // connect to the server 

    @Autowired
    private userInterface userInterface; 

    @GetMapping("/me")
    public ResponseEntity<?> getLoggedInUserInfo(Authentication authentication) {
        String username = authentication.getName(); // Comes from the JWT
        UserModel user = userInterface.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody UserModel user){
        try{
            UserModel newUser = authenticationService.createUser(user);
            return ResponseEntity.ok("User has successfully Registered");
        } catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody UserModel user){
        try {
            LoginResponse loggedUser = authenticationService.logedUser(user);
            return ResponseEntity.ok(loggedUser); // Return the LoginResponse object
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
