package com.agragadorinvestiemnto.agragadorinvestimentos.Controllers;

import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.UserRequestDTO;
import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.UserResponseDTO;
import com.agragadorinvestiemnto.agragadorinvestimentos.Models.User;
import com.agragadorinvestiemnto.agragadorinvestimentos.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO requestDTO){
        return ResponseEntity.ok().body(userService.createUser(requestDTO));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id){
        return ResponseEntity.ok().body(userService.findById(id));

    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        return ResponseEntity.ok().body(userService.findAll());
    }

    @PutMapping("/update/{email}")
    public ResponseEntity<UserResponseDTO> updateUserById(@PathVariable String email,@Valid @RequestBody UserRequestDTO requestDTO){
        return ResponseEntity.ok().body(userService.alterByEmail(email, requestDTO));
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<Void> deleteUserByEmail(@PathVariable String email){
        userService.delete(email);
        return ResponseEntity.noContent().build();
    }

}
