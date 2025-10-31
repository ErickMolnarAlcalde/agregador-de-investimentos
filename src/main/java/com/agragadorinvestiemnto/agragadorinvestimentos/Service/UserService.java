package com.agragadorinvestiemnto.agragadorinvestimentos.Service;

import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.UserRequestDTO;
import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.UserResponseDTO;
import com.agragadorinvestiemnto.agragadorinvestimentos.Models.User;
import com.agragadorinvestiemnto.agragadorinvestimentos.Repository.UserRepository;
import com.agragadorinvestiemnto.agragadorinvestimentos.exceptions.EmailNotFoundException;
import com.agragadorinvestiemnto.agragadorinvestimentos.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponseDTO createUser(UserRequestDTO requestDTO){
        User user = userMapper.toEntity(requestDTO);
        userRepository.save(user);

        UserResponseDTO responseDTO = userMapper.toTResponse(user);
        return responseDTO;
    }


    public List<UserResponseDTO> findAll(){
        List<User> users = userRepository.findAll();
        List<UserResponseDTO> responseDTOS = users.stream().map(userMapper::toTResponse).
                collect(Collectors.toList());
        return responseDTOS;
    }

    public UserResponseDTO findById(UUID id){
        User user = userRepository.findById(id).orElseThrow(()->
                new RuntimeException("Id not found"));

        UserResponseDTO userResponseDTO = userMapper.toTResponse(user);
        return userResponseDTO;
    }

    public UserResponseDTO alterByEmail(String email, UserRequestDTO requestDTO){
        User user = userMapper.toAlter(email,requestDTO);
        userRepository.save(user);
        if (user == null) {
            return null; // ou lançar exceção
        }
        UserResponseDTO userResponseDTO = userMapper.toTResponse(user);
        return  userResponseDTO;
    }


    public void delete(String email){
        User user = userRepository.findByEmail(email).orElseThrow(()->
                new EmailNotFoundException("Email not found!"));

        userRepository.delete(user);
    }




}
