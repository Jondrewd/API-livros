package com.apilivros;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.apilivros.Adapters.UserRestController;
import com.apilivros.Domain.User;
import com.apilivros.Dto.UserDTO;
import com.apilivros.Repository.UserRepository;
import com.apilivros.Services.UserService;

import java.util.ArrayList;


class UserControllerTests {


    @InjectMocks
    private UserRestController controller;

    @Mock
    private UserService service;

    @Mock
    private UserRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testFindById() {
        User user = new User(1L, "user1", "full name 1", "password", true, true, true, true, new ArrayList<>());
        UserDTO userDTO = new UserDTO(user);
        when(service.findById(1L)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = controller.findById(1L);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo("user1");
        verify(service, times(1)).findById(1L);
    }

    @Test
    void testFindByUsername() {
        User user = new User(1L, "user1", "full name 1", "password", true, true, true, true, new ArrayList<>());
        when(service.findByUsername("user1")).thenReturn(user);

        ResponseEntity<UserDTO> response = controller.findByUsername("user1");

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo("user1");
        verify(service, times(1)).findByUsername("user1");
    }

    @Test
    void testInsert() {
        User user = new User(1L, "user1", "full name 1", "password", true, true, true, true, new ArrayList<>());
        UserDTO userDTO = new UserDTO(user);

        when(service.fromDTO(userDTO)).thenReturn(user);
        when(service.insert(user)).thenReturn(user);

        ResponseEntity<UserDTO> response = controller.insert(userDTO);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo("user1");
        verify(service, times(1)).fromDTO(userDTO);
        verify(service, times(1)).insert(user);
    }

    @Test
    void testDelete() {
        doNothing().when(service).delete(1L);

        ResponseEntity<Void> response = controller.delete(1L);

        assertThat(response.getStatusCode().value()).isEqualTo(204);
        verify(service, times(1)).delete(1L);
    }

    @Test
    void testUpdate() {
        User user = new User(1L, "user1", "full name 1", "password", true, true, true, true, new ArrayList<>());
        UserDTO userDTO = new UserDTO(user);

        when(service.fromDTO(userDTO)).thenReturn(user);
        when(service.update(1L, user)).thenReturn(user);

        ResponseEntity<UserDTO> response = controller.update(1L, userDTO);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo("user1");
        verify(service, times(1)).fromDTO(userDTO);
        verify(service, times(1)).update(1L, user);
    }
}
