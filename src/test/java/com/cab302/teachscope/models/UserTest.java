package com.cab302.teachscope.models;

import com.cab302.teachscope.models.dao.MockUserDao;
import com.cab302.teachscope.models.dao.UserDao;
import com.cab302.teachscope.models.entities.User;
import com.cab302.teachscope.models.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private UserDao userDao;
    private UserService userService;

    @BeforeEach
    void setup() {
        userDao = new MockUserDao();
        userService = new UserService(userDao);
    }

    @Test
    void TestSignUpValid() {
        // Create valid user
        String userEmail = "user@example.com";
        String userPassword = "V@l1Dpaswd";

        // Add user
        userService.registerUser(userEmail, userPassword);

        // Check user was added
        User savedUser = userDao.getUser(userEmail);
        assertEquals(userEmail, savedUser.getEmail());
    }

    @Test
    void TestSignUpEmailExistsAlready() {
        // Create existing user in DAO
        userDao.addUser(new User ("user@example.com", "V@l1Dpaswd"));

        // Create valid user
        String userEmail = "user@example.com";
        String userPassword = "V@l1Dpaswd";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(userEmail, userPassword);
        });
    }

    @Test
    void TestSignUpNullEmail() {
        // Create invalid user
        String userEmail = null;
        String userPassword = "V@l1Dpaswd";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(userEmail, userPassword);
        });
    }

    @Test
    void TestSignUpInvalidEmailNoUsername() {
        // Create valid user
        String userEmail = "@example.com";
        String userPassword = "V@l1Dpaswd";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(userEmail, userPassword);
        });
    }

    @Test
    void TestSignUpInvalidEmailNoAt() {
        // Create valid user
        String userEmail = "example.com";
        String userPassword = "V@l1Dpaswd";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(userEmail, userPassword);
        });
    }

    @Test
    void TestSignUpInvalidEmailNoDomain() {
        // Create valid user
        String userEmail = "user@";
        String userPassword = "V@l1Dpaswd";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(userEmail, userPassword);
        });
    }

    @Test
    void TestSignUpNullPassword() {
        // Create valid user
        String userEmail = "user@email.com";
        String userPassword = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(userEmail, userPassword);
        });
    }

    @Test
    void TestSignUpPasswordTooShort() {
        // Create valid user
        String userEmail = "user@email.com";
        String userPassword = "Pp1#";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(userEmail, userPassword);
        });
    }

    @Test
    void TestSignUpPasswordNoChar() {
        // Create valid user
        String userEmail = "user@email.com";
        String userPassword = "Passw0rd";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(userEmail, userPassword);
        });
    }

    @Test
    void TestSignUpPasswordNoNumber() {
        // Create valid user
        String userEmail = "user@email.com";
        String userPassword = "Password!";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(userEmail, userPassword);
        });
    }

    @Test
    void TestSignUpPasswordNoUppercase() {
        // Create valid user
        String userEmail = "user@email.com";
        String userPassword = "p!assw0rd";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(userEmail, userPassword);
        });
    }

    @Test
    void TestSignUpPasswordNoLowercase() {
        // Create valid user
        String userEmail = "user@email.com";
        String userPassword = "PASSWORD!1";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(userEmail, userPassword);
        });
    }

    @Test
    void TestLoginValidDetails() {

    }

    @Test
    void TestLoginInvalidEmail() {

    }

    @Test
    void TestLoginInvalidPassword() {

    }
}
