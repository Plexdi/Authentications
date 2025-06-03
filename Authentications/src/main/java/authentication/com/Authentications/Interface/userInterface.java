package authentication.com.Authentications.Interface;

import org.springframework.data.jpa.repository.JpaRepository;

import authentication.com.Authentications.models.UserModel;;

public interface userInterface extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String username);
}
