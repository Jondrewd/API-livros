package com.apilivros.Services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apilivros.Domain.Permission;
import com.apilivros.Domain.User;
import com.apilivros.Dto.AccountCredentialsDTO;
import com.apilivros.Dto.TokenDTO;
import com.apilivros.Repository.PermissionRepository;
import com.apilivros.Repository.UserRepository;
import com.apilivros.config.SecurityConfig;
import com.apilivros.security.jwt.JwtTokenProvider;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository repository;
    
    @Autowired
    private PermissionRepository permissionRepository;
    
    @Autowired
    private SecurityConfig config;

     @SuppressWarnings("rawtypes")
    public ResponseEntity register(AccountCredentialsDTO data){
        try {
            if (checkUsername(data.getUsername()) == true) {
                return new ResponseEntity<>("Já existe um usúario cadastrado com esse nome de usuario.", HttpStatus.OK);
            }else{
            User user = new User();
            user.setUsername(data.getUsername());
            user.setPassowrd(config.passwordEncoder().encode(data.getPassword()));
            Permission permissions = permissionRepository.findByName("USER").get();
            user.setPermission(Collections.singletonList(permissions));

            repository.save(user);
            return new ResponseEntity<>("Usuario registrado", HttpStatus.OK);
            }
        } catch (Exception e) {
            throw new BadCredentialsException("Erro ao registrar-se.");
        }
    }
    @SuppressWarnings("rawtypes")
    public ResponseEntity signin(AccountCredentialsDTO data){
        try {
            String username = data.getUsername();
            String password = data.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            var user = repository.findByUsername(username);

            var tokenResponse = new TokenDTO();
            if (user != null) {
                tokenResponse = tokenProvider.createAcessToken(username, user.getRoles());
            }else{
                throw new UsernameNotFoundException("Username não encontrado.");
            }
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            throw new BadCredentialsException("Username/password invalidos.");
        }
    }
    @SuppressWarnings("rawtypes")
    public ResponseEntity refreshToken(String username, String refreshToken){
            var user = repository.findByUsername(username);

            var tokenResponse = new TokenDTO();
            if (user != null) {
                tokenResponse = tokenProvider.refreshToken(refreshToken);
            }else{
                throw new UsernameNotFoundException("Username não encontrado.");
            }
            return ResponseEntity.ok(tokenResponse);
    }
    private boolean checkUsername(String name){
        if (repository.findByUsername(name) == null){ 
            return false;
        }else{
            return true;
        }
    }
}
