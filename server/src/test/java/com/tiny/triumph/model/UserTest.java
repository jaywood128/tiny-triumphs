package com.tiny.triumph.model;


import com.tiny.triumph.config.TestConfig;
import com.tiny.triumph.enums.Role;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(TestConfig.class)
@ComponentScan
public class UserTest {

    @Mock
    private User userMock;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this); // Initialize Mockito
    }


    @Mock
    private List<Todo> mockTodoList;

    @InjectMocks
    private User user;

    @BeforeEach
    public void setup() {
        user = new User(1, "Ted", "Lasso", "ted.lasso@richmond.io", mockTodoList, Role.ADMIN);
    }

    @Test
    public void testGetFirstName() {
        // given
        String expectedFirstName = "Ted";
        when(mockTodoList.isEmpty()).thenReturn(true);
        // Stub out first name
        when(userMock.getFirstName()).thenReturn("Ted");

        // when
        String actualFirstName = userMock.getFirstName();

        // then
        assertEquals(expectedFirstName, actualFirstName);
    }
    @Test
    public void testGetLastName() {
        // given
        String expectedLastName = "Lasso";
        when(mockTodoList.isEmpty()).thenReturn(true);
        // Stub out first name
        when(userMock.getLastName()).thenReturn("Lasso");

        // when
        String actualLastName = userMock.getLastName();

        // then
        assertEquals(expectedLastName, actualLastName);
    }
}
