package com.tiny.triumph.repository;

import com.tiny.triumph.config.TestConfig;
import com.tiny.triumph.model.User;
import com.tiny.triumph.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(
        locations = "classpath:application-integration-test.properties")
@Import({TestConfig.class, TestEntityManager.class})
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this); // Initialize Mockito

        // Mock PasswordEncoder
        // Example using Mockito:
        String encodedPassword = bCryptPasswordEncoder.encode("believe");
        User user = new User();
        user.setFirstName("Roy");
        user.setLastName("Kent");
        user.setEmail("rkent@richmond.io");
        user.setPassword(encodedPassword);
        entityManager.persist(user);
        entityManager.flush();
    }

    @Test
    public void testSaveUser() {

        Optional<User> savedUser = userRepository.findByEmail("rkent@richmond.io");
        assertTrue(savedUser.isPresent());
        assertEquals("Roy", savedUser.get().getFirstName());
        assertEquals("Kent", savedUser.get().getLastName());
        assertEquals("rkent@richmond.io", savedUser.get().getEmail());
    }

    @Test
    public void test_user_delete() {
        Optional<User> foundUser = Optional.ofNullable(userRepository.findByEmail("rkent@richmond.io").orElse(null));
        assertTrue(foundUser.isPresent());
        // Delete the user
        userRepository.delete(foundUser.get());

        // Flush changes to the database
        entityManager.flush();

        // Try to find the user by ID
        Optional<User> deletedUser = userRepository.findByEmail("rkent@richmond.io");

        // Assert that the user cannot be found
        assertFalse(deletedUser.isPresent());
    }
}
