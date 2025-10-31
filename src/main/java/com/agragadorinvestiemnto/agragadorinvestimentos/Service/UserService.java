package com.agragadorinvestiemnto.agragadorinvestimentos.Service;

import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.AccountResponseDto;
import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.CreateAccountDTO;
import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.UserRequestDTO;
import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.UserResponseDTO;
import com.agragadorinvestiemnto.agragadorinvestimentos.Models.Account;
import com.agragadorinvestiemnto.agragadorinvestimentos.Models.BillingAddress;
import com.agragadorinvestiemnto.agragadorinvestimentos.Models.User;
import com.agragadorinvestiemnto.agragadorinvestimentos.Repository.*;
import com.agragadorinvestiemnto.agragadorinvestimentos.exceptions.EmailNotFoundException;
import com.agragadorinvestiemnto.agragadorinvestimentos.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AccountRepository accountRepository;
    private final AccountStockRepository accountStockRepository;
    private final BillingAddresRepository billingAddresRepository;
    private final StockRepository stockRepository;


    public UserService(UserRepository userRepository, UserMapper userMapper,
                       AccountRepository accountRepository, AccountStockRepository accountStockRepository,
                       BillingAddresRepository billingAddresRepository,
                       StockRepository stockRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.accountRepository = accountRepository;
        this.accountStockRepository = accountStockRepository;
        this.billingAddresRepository = billingAddresRepository;
        this.stockRepository = stockRepository;
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

    public UserResponseDTO createAccount(String email,CreateAccountDTO createAccountDTO){
        User user = userRepository.findByEmail(email).orElseThrow(()->
                new EmailNotFoundException("Email not found!"));

        Account account = new Account();
        account.setDescription(createAccountDTO.description());
        account.setUser(user);
        account.setAccountStocks(new ArrayList<>());
        accountRepository.save(account);

        BillingAddress billingAddress = new BillingAddress();
        billingAddress.setAccount(account);
        billingAddress.setNumber(createAccountDTO.number());
        billingAddress.setStreet(createAccountDTO.street());
        billingAddresRepository.save(billingAddress);


        UserResponseDTO responseDTO = userMapper.toTResponse(user);
        return responseDTO;
    }

    public List<AccountResponseDto> findAllAccounts(){
        List<Account> accounts = accountRepository.findAll();
        List<AccountResponseDto> responseDTOS = accounts.stream().map(
                account -> new AccountResponseDto(
                        account.getId(),
                        account.getDescription())).
                collect(Collectors.toList());
        return responseDTOS;
    }




}
