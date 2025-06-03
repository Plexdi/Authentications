package authentication.com.Authentications.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import authentication.com.Authentications.Interface.userInterface;
import authentication.com.Authentications.models.UserModel;

@Service
public class AuthenticationsServices {
    @Autowired 
    private userInterface userInterface; //connect to user interface 

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public UserModel logedUser(UserModel user){
        UserModel userFromDB = userInterface.findByUsername(user.getUsername());
        if (userFromDB != null && passwordEncoder.matches(user.getPassword(), userFromDB.getPassword())){
            return userFromDB;
        }
        throw new IllegalArgumentException("Invalid username or password");
    }


}
