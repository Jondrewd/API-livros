package com.apilivros.Adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apilivros.Dto.AccountCredentialsDTO;
import com.apilivros.Services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    AuthService authService;

    @SuppressWarnings("rawtypes")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody AccountCredentialsDTO data){
        if (checkParamIsNotNull(data)) 
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request.");
        authService.register(data);
        return ResponseEntity.ok().body(data);
    }
    @SuppressWarnings("rawtypes")
    @Operation(summary = "Authenticates a user and returns a token.")
    @PostMapping(value = "/signin")
    public ResponseEntity signin(@RequestBody AccountCredentialsDTO data){
        if (checkParamIsNotNull(data)) 
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request.");
        var token = authService.signin(data);
        if (token == null) 
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request.");
        return token;
    }

    @SuppressWarnings("rawtypes")
    @Operation(summary = "Refresh token for authenticated user and returns a token.")
    @PutMapping(value = "/refresh/{username}")
    public ResponseEntity refreshToken(@PathVariable("username") String username,
    @RequestHeader("Authorization") String refreshToken){
        if (checkParamIsNotNull(username, refreshToken)) 
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request.");
        var token = authService.refreshToken(username, refreshToken);
        if (token == null) 
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request.");
        return token;
    }

    private boolean checkParamIsNotNull(String username, String refreshToken){
        return refreshToken == null || refreshToken.isBlank() || username == null || username.isBlank();
    }
    
    private boolean checkParamIsNotNull(AccountCredentialsDTO data){
        return data == null || data.getUsername() == null || data.getUsername().isBlank() ||
        data.getPassword() == null || data.getPassword().isBlank();
    }
   
}
