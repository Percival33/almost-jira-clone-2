package pl.edu.pw.elka.pap.z16.almostjira.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edu.pw.elka.pap.z16.almostjira.exceptions.ResourceNotFoundException;
import pl.edu.pw.elka.pap.z16.almostjira.models.User;
import pl.edu.pw.elka.pap.z16.almostjira.repositories.UserRepository;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Test
    void shouldReturnUserById() {
        //given
        var user = User.builder()
                .id("1")
                .firstName("Maciek")
                .lastName("Kowalski")
                .login("maciek")
                .password("123")
                .createdAt(new Date())
                .lastModified(new Date())
                .build();

        given(userRepositoryMock.findById("1")).willReturn(Optional.of(user));
        given(userRepositoryMock.findById("2")).willReturn(Optional.empty());

        // when / then
        UserService service = new UserService(userRepositoryMock);
        var userData = service.getUserById("1");

        assertThat(userData).isEqualTo(user);
        assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() -> service.getUserById("2"));
    }

}