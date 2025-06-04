package authentication.com.Authentications.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import authentication.com.Authentications.DTO.LoginResponse;
import authentication.com.Authentications.DTO.UserDTO;
import authentication.com.Authentications.Interface.userInterface;
import authentication.com.Authentications.models.UserModel;
import authentication.com.Authentications.utils.JWTUtils;

@Service
public class AuthenticationsServices {
    @Autowired 
    private userInterface userInterface; //connect to user interface 

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("{jwt.secret}")
    private String jwtSecret;

    @Autowired
    private JWTUtils jwtUtils; 


    public UserModel createUser(UserModel user){
        if (user.getName() == null || user.getEmail() == null || user.getPassword() == null || user.getUsername() == null ){
            throw new IllegalArgumentException("All fields must be filled out");
        }

        if (user.getPassword().length() < 6){
            throw new IllegalArgumentException("password length must be longer than 6 characters");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        
        return userInterface.save(user);
        
    }
        public LoginResponse logedUser(UserModel userCredentials) { // Parameter name changed for clarity
            UserModel user = userInterface.findByUsername(userCredentials.getUsername());
            if (user == null){
                throw new RuntimeException("User not found");
            }
    
            if (!passwordEncoder.matches(userCredentials.getPassword(), user.getPassword())) {
                throw new RuntimeException("Invalid password");
            }
    
            // Generate JWT Token (your existing logic)
            String token = jwtUtils.generateToken(user.getUsername());
    
            // Create UserDTO from UserModel
            UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getEmail());
    
            // Create and return LoginResponse
        return new authentication.com.Authentications.DTO.LoginResponse(token, "User Logged in Successfully!", userDTO);
        }
    } 

