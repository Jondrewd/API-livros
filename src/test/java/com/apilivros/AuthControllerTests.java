package com.apilivros;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apilivros.Adapters.AuthController;
import com.apilivros.Dto.AccountCredentialsDTO;
import com.apilivros.Dto.RegisterDTO;
import com.apilivros.Services.AuthService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class AuthControllerTests {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

   @Test
void register_WhenValidData_ReturnsOk() throws Exception {

    doAnswer(invocation -> {
        RegisterDTO data = invocation.getArgument(0);
        assertNotNull(data);
        assertEquals("user", data.getUsername());
        assertEquals("password", data.getPassword()); 
        return null;
    }).when(authService).register(any(RegisterDTO.class));

    mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\": \"user\", \"password\": \"password\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("user"));
}


    @Test
    void register_WhenInvalidData_ReturnsForbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"\", \"password\": \"\"}"))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Invalid client request."));
    }

    @Test
    void signin_WhenValidData_ReturnsToken() throws Exception {
        String token = "Bearer someToken";

        when(authService.signin(any(AccountCredentialsDTO.class))).thenReturn(ResponseEntity.ok(token));

        mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"user\", \"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(token));
    }

    @Test
    void signin_WhenInvalidData_ReturnsForbidden() throws Exception {
        mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"\", \"password\": \"\"}"))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Invalid client request."));
    }

    @Test
    void refreshToken_WhenValidData_ReturnsNewToken() throws Exception {
        String username = "user";
        String refreshToken = "someRefreshToken";
        String newToken = "Bearer newToken";

        when(authService.refreshToken(username, refreshToken)).thenReturn(ResponseEntity.ok(newToken));

        mockMvc.perform(put("/auth/refresh/{username}", username)
                .header("Authorization", refreshToken))
                .andExpect(status().isOk())
                .andExpect(content().string(newToken));
    }

    @Test
    void refreshToken_WhenInvalidData_ReturnsForbidden() throws Exception {
        mockMvc.perform(put("/auth/refresh/{username}", "user")
                .header("Authorization", ""))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Invalid client request."));
    }
}
