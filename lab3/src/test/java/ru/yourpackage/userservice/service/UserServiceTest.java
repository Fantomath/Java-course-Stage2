package ru.yourpackage.userservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yourpackage.userservice.dao.UserDao;
import ru.yourpackage.userservice.model.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_shouldCallDaoSave() {
        when(userDao.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        userService.createUser("test@mail.com");

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userDao).save(captor.capture());

        assertEquals("test@mail.com", captor.getValue().getEmail());
    }

    @Test
    void createUser_shouldThrowIfEmailBlank() {
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(" "));
        verifyNoInteractions(userDao);
    }

    @Test
    void removeUser_shouldCallDaoDeleteByEmail() {
        userService.removeUser("del@mail.com");
        verify(userDao).deleteByEmail("del@mail.com");
    }
}
