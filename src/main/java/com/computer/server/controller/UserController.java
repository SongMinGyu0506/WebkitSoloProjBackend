package com.computer.server.controller;

import com.computer.server.config.EncryptConfig;
import com.computer.server.entity.domain.AccountLog;
import com.computer.server.entity.domain.UserEntity;
import com.computer.server.entity.dto.*;
import com.computer.server.security.TokenProvider;
import com.computer.server.service.AccountLogService;
import com.computer.server.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private AccountLogService accountLogService;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private EncryptConfig encryptConfig;

    @PostMapping("/signup")
    public ResponseEntity<?>registerUser(@RequestBody SignUpDTO userDTO){
        try {
            UserEntity user = UserEntity.builder()
                    .email(userDTO.getEmail())
                    .name(userDTO.getName())
                    .password(encryptConfig.makeMD5(userDTO.getPassword()))
                    .userDate(new Date())
                    .isAdmin(false)
                    .isSecession(false)
                    .computers(new ArrayList<>())
                    .build();
            UserEntity registeredUser = userService.create(user);
            SignUpDTO responseUserDTO = SignUpDTO.builder()
                    .password(registeredUser.getPassword())
                    .email(registeredUser.getEmail())
                    .id(registeredUser.getId())
                    .name(registeredUser.getName())
                    .build();

            AccountLogDTO accountLogDTO = AccountLogDTO.builder()
                    .loginType("SIGN-UP")
                    .loginEmail(registeredUser.getEmail())
                    .loginId(registeredUser.getId())
                    .loginDate(new Date())
                    .build();
            AccountLog accountLog = AccountLogDTO.toAccountLog(accountLogDTO);
            accountLogService.save(accountLog);

            return ResponseEntity.ok().body(responseUserDTO);
        }catch(Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
    @PostMapping("/signin")
    public ResponseEntity<?>authenticate(@RequestBody LoginDTO userDTO){
        log.info("Encrypt: "+encryptConfig.makeMD5(userDTO.getPassword()));
        UserEntity user = userService.getByCredentials(userDTO.getEmail(), encryptConfig.makeMD5(userDTO.getPassword()));
        if(user != null) {
            final String token = tokenProvider.create(user);
            final LoginDTO responseUserDTO = LoginDTO.builder()
                    .email(user.getEmail())
                    .id(user.getId())
                    .token(token)
                    .build();
            final AccountLogDTO accountLog = AccountLogDTO.builder()
                    .loginDate(new Date())
                    .loginEmail(user.getEmail())
                    .loginId(user.getId())
                    .loginType("LOG-IN")
                    .build();
            AccountLog toAccountLog = AccountLogDTO.toAccountLog(accountLog);
            accountLogService.save(toAccountLog);
            return ResponseEntity.ok().body(responseUserDTO);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder().error("Login failed").build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
    @GetMapping("/modify")
    public ResponseEntity<?> modifyUser(@AuthenticationPrincipal int id) {
        UserEntity userEntity = userService.getById(id);
        log.info("TEST : "+userEntity.toString());
        //SignUpDTO userDTO = new SignUpDTO(userEntity);
        UserDTO userDTO = new UserDTO(userEntity);
        return ResponseEntity.ok().body(userDTO);
    }
    @PutMapping("/modify")
    public ResponseEntity<?> submitUserModify(@AuthenticationPrincipal int id,@RequestBody UserDTO dto) {
        log.info("Client DTO : "+dto.toString());
        UserEntity userEntity = UserDTO.toUserEntity(dto);
        log.info("Entity : "+userEntity.toString());
        UserEntity entity = userService.update(userEntity,id);
        UserDTO dtos = new UserDTO(entity);
        List<UserDTO> resList = new ArrayList<>();
        resList.add(dtos);

        final AccountLogDTO accountLog = AccountLogDTO.builder()
                .loginDate(new Date())
                .loginEmail(entity.getEmail())
                .loginId(entity.getId())
                .loginType("USER-UPDATE")
                .build();

        AccountLog toAccountLog = AccountLogDTO.toAccountLog(accountLog);
        accountLogService.save(toAccountLog);
        ResponseDTO response = ResponseDTO.<UserDTO>builder().data(resList).build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/signout")
    public ResponseEntity<?> signoutUser(@AuthenticationPrincipal int id) {
        UserEntity userEntity = userService.getById(id);
        final AccountLogDTO accountLog = AccountLogDTO.builder()
                .loginDate(new Date())
                .loginEmail(userEntity.getEmail())
                .loginId(userEntity.getId())
                .loginType("LOG-OUT")
                .build();
        AccountLog toAccountLog = AccountLogDTO.toAccountLog(accountLog);
        accountLogService.save(toAccountLog);
        return ResponseEntity.ok().body("OK");
    }

    @GetMapping("/log")
    public ResponseEntity<?> readAccountLog(@AuthenticationPrincipal int id) {
        try {
            List<AccountLog> accountLogs = accountLogService.read();
            ResponseDTO response = ResponseDTO.<AccountLog>builder()
                    .data(accountLogs)
                    .build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDTO response = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
