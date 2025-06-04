package authentication.com.Authentications.DTO;

// You might want to include user details as well, e.g., a UserDTO object
public class LoginResponse {
    private String token;
    private String message;
    private UserDTO user; 
    // You can add other fields like user details (e.g., UserDTO)

    public LoginResponse(String token, String message, UserDTO user) {
        this.token = token;
        this.message = message;
        this.user = user; 
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserDTO getUser(){
        return user; 
    }

    public void setUser(UserDTO user){
        this.user = user; 
    }
}