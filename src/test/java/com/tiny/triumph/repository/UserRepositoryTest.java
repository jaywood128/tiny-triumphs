package com.tiny.triumph.repository;

import com.tiny.triumph.model.User;
import com.tiny.triumph.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(
        locations = "classpath:application-integration-test.properties")
@Import(TestEntityManager.class)
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setFirstName("Roy");
        user.setLastName("Kent");
        user.setEmail("rkent@richmond.io");
        entityManager.persist(user);

        entityManager.flush();

        Optional<User> savedUser = Optional.ofNullable(userRepository.findById(user.getId()).orElse(null));
        assertNotNull(savedUser.get());
        assertEquals("Roy", savedUser.get().getFirstName());
        assertEquals("Kent", savedUser.get().getLastName());
        assertEquals("rkent@richmond.io", savedUser.get().getEmail());
    }
    @Test
    public void test_user_delete(){
        // Create a user
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("jdoe@example.com");
        entityManager.persist(user);

        // Flush changes to the database
        entityManager.flush();

        // Delete the user
        userRepository.delete(user);

        // Flush changes to the database
        entityManager.flush();

        // Try to find the user by ID
        Optional<User> deletedUser = userRepository.findById(user.getId());

        // Assert that the user cannot be found
        assertFalse(deletedUser.isPresent());
    }
}
