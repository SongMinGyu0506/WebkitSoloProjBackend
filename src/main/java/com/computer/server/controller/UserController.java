package com.computer.server.controller;

import com.computer.server.entity.domain.AccountLog;
import com.computer.server.entity.domain.UserEntity;
import com.computer.server.entity.dto.AccountLogDTO;
import com.computer.server.entity.dto.LoginDTO;
import com.computer.server.entity.dto.ResponseDTO;
import com.computer.server.entity.dto.SignUpDTO;
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
    @PostMapping("/signup")
    public ResponseEntity<?>registerUser(@RequestBody SignUpDTO userDTO){
        try {
            UserEntity user = UserEntity.builder()
                    .email(userDTO.getEmail())
                    .name(userDTO.getName())
                    .password(userDTO.getPassword())
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
        UserEntity user = userService.getByCredentials(userDTO.getEmail(),userDTO.getPassword());
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
        SignUpDTO userDTO = new SignUpDTO(userEntity);
        return ResponseEntity.ok().body(userDTO);
    }
    @PutMapping("/modify")
    public ResponseEntity<?> submitUserModify(@AuthenticationPrincipal String userId,@RequestBody SignUpDTO dto) {
        UserEntity userEntity = SignUpDTO.toUserEntity(dto);
        UserEntity entity = userService.update(userEntity);
        SignUpDTO dtos = new SignUpDTO(entity);
        List<SignUpDTO> resList = new ArrayList<>();
        resList.add(dtos);
        ResponseDTO response = ResponseDTO.<SignUpDTO>builder().data(resList).build();
        return ResponseEntity.ok().body(response);
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
