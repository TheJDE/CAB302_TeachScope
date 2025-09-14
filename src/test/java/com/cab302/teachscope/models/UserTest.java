package com.cab302.teachscope.models;

import com.cab302.teachscope.models.dao.MockUserDao;
import com.cab302.teachscope.models.dao.UserDao;
import com.cab302.teachscope.models.entities.User;
import com.cab302.teachscope.models.services.UserService;
import com.cab302.teachscope.util.PasswordUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

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
        try {
            User savedUser = userDao.getUser(userEmail);
            assertEquals(userEmail, savedUser.getEmail());
            assertTrue(savedUser.checkPasswordMatches(userPassword));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void TestSignUpEmailExistsAlready() {
        // Create existing user in DAO
        try {
            userDao.addUser(new User ("user@example.com", "V@l1Dpaswd"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
        // Create valid user
        String userEmail = "user@example.com";
        String userPassword = "V@l1Dpaswd";

        // Add user
        userService.registerUser(userEmail, userPassword);

        assertDoesNotThrow(() -> {
            userService.login(userEmail, userPassword);
        });
    }

    @Test
    void TestLoginNullEmail() {
        // Create valid user
        String userEmail = "user@example.com";
        String userPassword = "V@l1Dpaswd";

        // Add user
        userService.registerUser(userEmail, userPassword);

        assertThrows(IllegalArgumentException.class, () -> {
            userService.login(null, userPassword);
        });
    }

    @Test
    void TestLoginInvalidEmail() {
        // Create valid user
        String userEmail = "user@example.com";
        String userPassword = "V@l1Dpaswd";

        // Add user
        userService.registerUser(userEmail, userPassword);


        String wrongEmail = "otheruser@example.com";

        assertThrows(IllegalArgumentException.class, () -> {
            userService.login(wrongEmail, userPassword);
        });
    }

    @Test
    void TestLoginInvalidPassword() {
        // Create valid user
        String userEmail = "user@example.com";
        String userPassword = "V@l1Dpaswd";

        // Add user
        userService.registerUser(userEmail, userPassword);


        String wrongPassword = "otheruser@example.com";

        assertThrows(IllegalArgumentException.class, () -> {
            userService.login(userEmail, wrongPassword);
        });
    }

    @Test
    void TestLoginNullPassword() {
        // Create valid user
        String userEmail = "user@example.com";
        String userPassword = "V@l1Dpaswd";

        // Add user
        userService.registerUser(userEmail, userPassword);

        assertThrows(IllegalArgumentException.class, () -> {
            userService.login(userEmail, null);
        });
    }

    @Test
    void TestPasswordHashes() {
        // Create valid user
        String userEmail = "user@example.com";
        String userPassword = "V@l1Dpaswd";

        // Add user
        userService.registerUser(userEmail, userPassword);


        try {
            User user = userDao.getUser(userEmail);
            assertEquals(PasswordUtils.hashPassword(userPassword), user.getPasswordHash());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
