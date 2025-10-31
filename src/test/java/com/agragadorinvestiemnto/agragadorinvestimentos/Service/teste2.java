package com.agragadorinvestiemnto.agragadorinvestimentos.Service;

import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.UserResponseDTO;


import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.UserRequestDTO; import com.agragadorinvestiemnto.agragadorinvestimentos.Models.User; import com.agragadorinvestiemnto.agragadorinvestimentos.Repository.UserRepository; import com.agragadorinvestiemnto.agragadorinvestimentos.mapper.UserMapper; import org.junit.jupiter.api.DisplayName; import org.junit.jupiter.api.Nested; import org.junit.jupiter.api.Test; import org.junit.jupiter.api.extension.ExtendWith; import org.mockito.ArgumentCaptor; import org.mockito.Captor; import org.mockito.InjectMocks; import org.mockito.Mock; import org.mockito.junit.jupiter.MockitoExtension; import java.time.Instant; import java.util.List; import java.util.Optional; import java.util.UUID; import static org.junit.jupiter.api.Assertions.*; import static org.mockito.ArgumentMatchers.any; import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class teste2 {




    @ExtendWith(MockitoExtension.class)
    class UserServiceTest {

        @Mock
        private UserRepository userRepository;

        @Mock
        private UserMapper userMapper;

        @InjectMocks
        private UserService userService;

        @Captor
        private ArgumentCaptor<User> userArgumentCaptor;

        @Nested
        class creatUser{

            @Test
            @DisplayName("Shoudl create a user with sucess")
            void shouldCreateUserWithSucess() {
                //arrange
                UserRequestDTO requestDTO = new UserRequestDTO("NameTest","test123",
                        "test@gmail.com","Admin");
                User user = new User(UUID.randomUUID(),"NameTest",
                        "test123","test@gmail.com","Admin",Instant.now(),Instant.now());
                UserResponseDTO responseDTO = new UserResponseDTO("NameTest",
                        "test@gmail.com","Admin");

                doReturn(user).when(userMapper).toEntity(requestDTO);
                doReturn(user).when(userRepository).save(any(User.class));
                doReturn(responseDTO).when(userMapper).toTResponse(user);

                //act
                var output = userService.createUser(requestDTO);

                //assert
                assertNotNull(output);

                var userCaptured = userArgumentCaptor.getValue();

                assertEquals(requestDTO.username(), userCaptured.getUsername());
                assertEquals(requestDTO.password(), userCaptured.getPassword());
                assertEquals(requestDTO.email(), userCaptured.getEmail());
                assertEquals(requestDTO.role(), userCaptured.getRole());

            }
        }



    }
}
