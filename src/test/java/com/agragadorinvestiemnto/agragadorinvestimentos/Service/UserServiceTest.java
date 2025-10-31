package com.agragadorinvestiemnto.agragadorinvestimentos.Service;

import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.UserRequestDTO;
import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.UserResponseDTO;
import com.agragadorinvestiemnto.agragadorinvestimentos.Models.User;
import com.agragadorinvestiemnto.agragadorinvestimentos.Repository.UserRepository;
import com.agragadorinvestiemnto.agragadorinvestimentos.exceptions.EmailNotFoundException;
import com.agragadorinvestiemnto.agragadorinvestimentos.mapper.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


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

            UserRequestDTO requestDTO = new UserRequestDTO(
                    "NameTest", "test123", "test@gmail.com", "Admin"
            );

            User user = new User();
            user.setUsername(requestDTO.username());
            user.setPassword(requestDTO.password());
            user.setEmail(requestDTO.email());
            user.setRole(requestDTO.role());

            UserResponseDTO responseDTO = new UserResponseDTO(
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole()
            );

            // mocka o mapper de entrada
            when(userMapper.toEntity(requestDTO)).thenReturn(user);

            // mocka o repository
            when(userRepository.save(any(User.class))).thenReturn(user);

            // mocka o mapper de saída
            when(userMapper.toTResponse(user)).thenReturn(responseDTO);

            // Act
            var output = userService.createUser(requestDTO);

            // Assert
            assertNotNull(output);
            assertEquals(responseDTO.username(), output.username());
            assertEquals(responseDTO.email(), output.email());
            assertEquals(responseDTO.role(), output.role());

            // assert - parte 2: interação com o repositório
            verify(userRepository, times(1)).save(userArgumentCaptor.capture());
            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(user.getUsername(), userCaptured.getUsername());
            assertEquals(user.getPassword(), userCaptured.getPassword());
            assertEquals(user.getEmail(), userCaptured.getEmail());
            assertEquals(user.getRole(), userCaptured.getRole());
        }

        @Test
        @DisplayName("Should throw exception when repository save fails")
        void shouldFailWhenSaveFails() {
            // arrange: cria DTO de entrada
            UserRequestDTO requestDTO = new UserRequestDTO("NameTest","test123",
                    "test@gmail.com","Admin");
            // arrange: cria User que seria retornado pelo mapper
            User user = new User(UUID.randomUUID(),"NameTest","test123",
                    "test@gmail.com","Admin",Instant.now(),Instant.now());
            // mocka o mapper para retornar o User correto
            doReturn(user).when(userMapper).toEntity(requestDTO);
            // mocka repository para lançar exceção ao salvar
            doThrow(new RuntimeException("Database error"))
                    .when(userRepository).save(any(User.class));
            // act & assert: verifica se a exceção esperada é lançada
            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                userService.createUser(requestDTO);
            });
            // assert: verifica se a mensagem da exceção está correta
            assertEquals("Database error", exception.getMessage());
        }

        @Test // Indica que este método é um teste unitário
        @DisplayName("Should return null when mapper returns null") // Nome amigável que descreve o teste
        void shouldReturnNullWhenMapperReturnsNull() {

            // Cria um DTO de entrada simulando os dados do usuário
            UserRequestDTO requestDTO = new UserRequestDTO("NameTest","test123",
                    "test@gmail.com","Admin");

            // Configura o mock do mapper para retornar null para qualquer UserRequestDTO
            when(userMapper.toEntity(any(UserRequestDTO.class))).thenReturn(null);

            // Chama o método createUser do serviço com o DTO de entrada
            var output = userService.createUser(requestDTO);

            // Verifica se o retorno do método é null, já que o mapper retornou null
            assertNull(output); // passa se output == null
        }
    }

    @Nested
    class findUserById{

        @Test
        @DisplayName("Should get user by id with sucess when optional is present")
        void shouldGetUserByIdWithSucessWhenOptionalIsPresent(){
            // Arrange - cria DTO de entrada e Entity
            UserRequestDTO requestDTO = new UserRequestDTO(
                    "NameTest", "test123", "test@gmail.com", "Admin"
            );

            User user = new User();
            user.setId(UUID.randomUUID()); // define um ID real
            user.setUsername(requestDTO.username());
            user.setPassword(requestDTO.password());
            user.setEmail(requestDTO.email());
            user.setRole(requestDTO.role());

            UserResponseDTO responseDTO = new UserResponseDTO(
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole()
            );

            // Mocka repository para retornar o Optional<User>
            when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

            // Mocka mapper de saída
            when(userMapper.toTResponse(user)).thenReturn(responseDTO);

            // Act
            var output = userService.findById(user.getId());

            // Assert - valida retorno do serviço
            assertNotNull(output);
            assertEquals(responseDTO.username(), output.username());
            assertEquals(responseDTO.email(), output.email());
            assertEquals(responseDTO.role(), output.role());

            // Verifica que o repository foi chamado com o UUID correto
            verify(userRepository, times(1)).findById(user.getId());
        }

        @Test
        @DisplayName("Should throw exception when user not found by id")
        void shouldThrowExceptionWhenUserNotFoundById() {
            // Arrange - cria um UUID fictício
            UUID userId = UUID.randomUUID();

            // Mocka repository retornando Optional.empty()
            when(userRepository.findById(userId)).thenReturn(Optional.empty());

            // Act & Assert - espera RuntimeException com mensagem "Id not found"
            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                userService.findById(userId);
            });

            // Verifica a mensagem da exceção
            assertEquals("Id not found", exception.getMessage());

            // Verifica que o repository foi chamado exatamente 1 vez com o UUID correto
            verify(userRepository, times(1)).findById(userId);
        }
    }

    @Nested
    class ListUsers{

        @Test
        @DisplayName("Should get all users with success")
        void shouldGetAllUsersWithSuccess() {
            // Arrange - cria DTO de entrada e Entity
            UserRequestDTO requestDTO = new UserRequestDTO(
                    "NameTest", "test123", "test@gmail.com", "Admin"
            );

            User user = new User();
            user.setId(UUID.randomUUID()); // define um ID real
            user.setUsername(requestDTO.username());
            user.setPassword(requestDTO.password());
            user.setEmail(requestDTO.email());
            user.setRole(requestDTO.role());

            UserResponseDTO responseDTO = new UserResponseDTO(
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole()
            );

            // Mocka repository para retornar uma lista com 1 usuário
            when(userRepository.findAll()).thenReturn(List.of(user));

            // Mocka mapper de saída
            when(userMapper.toTResponse(user)).thenReturn(responseDTO);

            // Act
            var output = userService.findAll();

            // Assert - valida retorno do serviço
            assertNotNull(output); // garante que não é nulo
            assertEquals(1, output.size()); // garante que tem 1 elemento

            var result = output.get(0); // pega o primeiro resultado

            // Valida conteúdo do DTO de resposta
            assertEquals(responseDTO.username(), result.username());
            assertEquals(responseDTO.email(), result.email());
            assertEquals(responseDTO.role(), result.role());

            // Verifica que o repository foi chamado apenas 1x
            verify(userRepository, times(1)).findAll();

            // Verifica que o mapper também foi chamado 1x
            verify(userMapper, times(1)).toTResponse(user);
        }

        @Test
        @DisplayName("Should return empty list when no users are found")
        void shouldReturnEmptyListWhenNoUsersAreFound() {
            // Arrange - simula repositório vazio
            when(userRepository.findAll()).thenReturn(List.of()); // lista vazia

            // Act
            var output = userService.findAll();

            // Assert - valida que a lista está vazia
            assertNotNull(output); // o retorno não deve ser nulo
            assertTrue(output.isEmpty()); // garante que a lista está vazia

            // Verifica interações
            verify(userRepository, times(1)).findAll(); // repositório chamado 1x
            verify(userMapper, never()).toTResponse(any(User.class)); // mapper nunca é chamado
        }
    }

    @Nested
    class deleteUserByEmail{
        @Test
        @DisplayName("Should delete user with success")
        void shouldDeleteUserWithSuccess() {
            // Arrange - cria DTO de entrada e Entity
            UserRequestDTO requestDTO = new UserRequestDTO(
                    "NameTest", "test123", "test@gmail.com", "Admin"
            );
            User user = new User();
            user.setId(UUID.randomUUID()); // define um ID real
            user.setUsername(requestDTO.username());
            user.setPassword(requestDTO.password());
            user.setEmail(requestDTO.email());
            user.setRole(requestDTO.role());

            // Mocka repository para encontrar o usuário pelo email
            when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

            // Act - executa o método de deleção
            userService.delete(user.getEmail());

            // Assert - verifica que o repositório foi chamado corretamente
            verify(userRepository, times(1)).findByEmail(user.getEmail());
            verify(userRepository, times(1)).delete(user);
        }

        @Test
        @DisplayName("Should throw exception when email not found on delete")
        void shouldThrowExceptionWhenEmailNotFoundOnDelete() {
            // Arrange - cria DTO de entrada com email inexistente
            String emailInexistente = "notfound@gmail.com";

            // Mocka repository para retornar vazio (email não encontrado)
            when(userRepository.findByEmail(emailInexistente)).thenReturn(Optional.empty());

            // Act & Assert - espera que a exceção seja lançada
            assertThrows(EmailNotFoundException.class, () -> {
                userService.delete(emailInexistente);
            });

            // Verifica que o método delete NUNCA foi chamado
            verify(userRepository, never()).delete(any(User.class));
        }
    }

    @Nested
    class UpdateUser{

        @Test
        @DisplayName("Should update user by email when user exists and username and password is filled")
        void shouldUpdateUserByEmailWhenUserExistsAndPasswordIsFilled(){
            // Arrange - cria DTO de entrada e Entity
            UserRequestDTO requestDTO = new UserRequestDTO(
                    "NameTest", "test123", "test@gmail.com", "Admin"
            );
            User user = new User();
            user.setId(UUID.randomUUID()); // define um ID real
            user.setUsername(requestDTO.username());
            user.setPassword(requestDTO.password());
            user.setEmail(requestDTO.email());
            user.setRole(requestDTO.role());
            UserResponseDTO responseDTO = new UserResponseDTO(
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole()
            );
            // Mocka mapper para montar a entidade a partir do email e request
            when(userMapper.toAlter(user.getEmail(), requestDTO)).thenReturn(user);

            // Mocka repositório para salvar
            when(userRepository.save(any(User.class))).thenReturn(user);

            // Mocka mapper para converter de volta o resultado
            when(userMapper.toTResponse(user)).thenReturn(responseDTO);

            // Act - executa o método de alteração
            var output = userService.alterByEmail(user.getEmail(), requestDTO);

            // Assert - verifica resultado
            assertNotNull(output);
            assertEquals(responseDTO.username(), output.username());
            assertEquals(responseDTO.email(), output.email());
            assertEquals(responseDTO.role(), output.role());

            // Verifica que o repositório salvou uma vez
            verify(userRepository, times(1)).save(userArgumentCaptor.capture());

            // Captura o usuário salvo e valida campos
            var userCaptured = userArgumentCaptor.getValue();
            assertEquals(user.getUsername(), userCaptured.getUsername());
            assertEquals(user.getPassword(), userCaptured.getPassword());
            assertEquals(user.getEmail(), userCaptured.getEmail());
            assertEquals(user.getRole(), userCaptured.getRole());
        }

        @Test
        @DisplayName("Should not update user when mapper returns null (email not found)")
        void shouldNotUpdateUserWhenMapperReturnsNull() {
            // Arrange
            UserRequestDTO requestDTO = new UserRequestDTO(
                    "NameTest", "test123", "notfound@gmail.com", "Admin"
            );

            when(userMapper.toAlter(requestDTO.email(), requestDTO)).thenReturn(null);

            // Act
            userService.alterByEmail(requestDTO.email(), requestDTO);

            // Assert
            verify(userRepository, never()).save(any(User.class));
            verify(userMapper, never()).toTResponse(any());
        }

        }

    }







