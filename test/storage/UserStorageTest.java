package storage;

import exceptions.UserNotFoundException;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserStorageTest {

    private UserStorage storage;

    @BeforeEach
    void setUp() {
        storage = new UserStorage();
    }

    @Test
    void testAddUser() {
        User user = new User("Vladimir");
        assertTrue(storage.addUser(user));
        assertTrue(storage.hasUser("Vladimir"));
        assertEquals(1, storage.getUserCount());
    }

    @Test
    void testAddDuplicateUser() {
        User user1 = new User("Vladimir");
        User user2 = new User("Vladimir");

        assertTrue(storage.addUser(user1));
        assertFalse(storage.addUser(user2));
        assertEquals(1, storage.getUserCount());
    }

    @Test
    void testGetUser() {
        User user = new User("Vladimir");
        storage.addUser(user);

        User retrievedUser = storage.getUser("Vladimir");
        assertEquals(user.getUserName(), retrievedUser.getUserName());
    }

    @Test
    void testGetNonExistentUser() {
        assertThrows(UserNotFoundException.class, () -> {
            storage.getUser("NonExistentUser");
        });
    }

    @Test
    void testGetAllUserNames() {
        storage.addUser(new User("Vladimir"));
        storage.addUser(new User("Egor"));

        assertEquals(2, storage.getAllUserNames().size());
        assertTrue(storage.getAllUserNames().contains("Vladimir"));
        assertTrue(storage.getAllUserNames().contains("Egor"));
    }
}