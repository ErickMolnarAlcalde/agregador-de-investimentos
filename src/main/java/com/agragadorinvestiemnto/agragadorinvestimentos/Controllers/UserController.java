package com.agragadorinvestiemnto.agragadorinvestimentos.Controllers;

import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.UserRequestDTO;
import com.agragadorinvestiemnto.agragadorinvestimentos.Models.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserRequestDTO requestDTO){

        //
        return null;
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id){

        //
        return null;
    }

    @GetMapping
    public List<ResponseEntity<User>> getAllUsers(){

        //
        return null;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable String id,@Valid @RequestBody UserRequestDTO requestDTO){

        //
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String id){

        return null;
    }

}
