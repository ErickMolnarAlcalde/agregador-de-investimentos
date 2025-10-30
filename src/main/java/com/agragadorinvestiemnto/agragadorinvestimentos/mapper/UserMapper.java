package com.agragadorinvestiemnto.agragadorinvestimentos.mapper;

import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.UserRequestDTO;
import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.UserResponseDTO;
import com.agragadorinvestiemnto.agragadorinvestimentos.Models.User;
import com.agragadorinvestiemnto.agragadorinvestimentos.Repository.UserRepository;
import com.agragadorinvestiemnto.agragadorinvestimentos.exceptions.EmailNotFoundException;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class UserMapper {

    private final UserRepository userRepository;

    public UserMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User toEntity(UserRequestDTO requestDTO){
        User user = new User();
        user.setUsername(requestDTO.username());
        user.setPassword(requestDTO.password());
        user.setEmail(requestDTO.email());
        user.setRole(requestDTO.role());
        user.setCreationTimeStamp(Instant.now());
        user.setUpdateTimeStamp(Instant.now());

        return  user;
    }

    public UserResponseDTO toTResponse(User user){
        UserResponseDTO userResponseDTO = new UserResponseDTO(user.getUsername(),
                user.getEmail(),user.getRole());
        return userResponseDTO;
    }

    public User toAlter(String email,UserRequestDTO requestDTO){
        User user = userRepository.findByEmail(email).orElseThrow(()->
                new EmailNotFoundException("email not found!"));
        user.setUsername(requestDTO.username());
        user.setPassword(requestDTO.password());
        user.setEmail(requestDTO.email());
        user.setRole(requestDTO.role());
        user.setUpdateTimeStamp(Instant.now());

        return  user;
    }


}
