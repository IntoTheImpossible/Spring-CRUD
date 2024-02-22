package rest.application.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import rest.application.model.User;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUsername() {
        //arrange
        User user = User.builder()
                .username("test")
                .name("test")
                .email("mail")
                .build();
        User savedUser = userRepository.save(user);

        //act
        User foundUser = userRepository.findByUsername("test");
        //assert
        assertEquals(savedUser, foundUser);
    }

    @Test
    void findByEmail() {
        //arrange
        User user = User.builder()
                .username("test")
                .name("test")
                .email("mail")
                .build();
        User savedUser = userRepository.save(user);

        //act
        User foundUser = userRepository.findByEmail("mail");
        //assert
        assertEquals(savedUser, foundUser);
    }
}