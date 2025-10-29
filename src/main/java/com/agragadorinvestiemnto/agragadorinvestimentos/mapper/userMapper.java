package com.agragadorinvestiemnto.agragadorinvestimentos.mapper;

import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.UserRequestDTO;
import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.UserResponseDTO;
import com.agragadorinvestiemnto.agragadorinvestimentos.Models.User;
import com.agragadorinvestiemnto.agragadorinvestimentos.Repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class userMapper {

    private final UserRepository userRepository;

    public userMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User toEntity(UserRequestDTO requestDTO){
        User user = new User();
        user.setUsername(requestDTO.username());
        user.setPassword(requestDTO.password());
        user.setEmail(requestDTO.email());
        user.setRole(requestDTO.role());

        return  user;
    }

    public UserResponseDTO toTResponse(User user){
        UserResponseDTO userResponseDTO = new UserResponseDTO(user.getUsername(),
                user.getEmail(),user.getRole());
        return userResponseDTO;
    }


}
