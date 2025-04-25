package com.apilivros;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.http.MediaType;

import com.apilivros.Domain.Profile;
import com.apilivros.Domain.User;
import com.apilivros.Adapters.ProfileRestController;
import com.apilivros.Dto.ProfileDTO;
import com.apilivros.Dto.ProfileEditDTO;
import com.apilivros.Repository.ProfileRepository;
import com.apilivros.Services.ProfileService;

import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;


class ProfileControllerTests {

    @InjectMocks
    private ProfileRestController controller;

    @Mock
    private ProfileService service;

    @Mock
    private ProfileRepository repository;

    private MockMvc mockMvc;
    
    @BeforeEach
    void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    MockHttpServletRequest mockRequest = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));
}

    @Test
    void testFindById() {
        Profile profile = new Profile(1L, new User(), "User1", "icon1.png", "Bio1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        ProfileDTO profileDTO = new ProfileDTO(profile);
        when(service.findById(1L)).thenReturn(profileDTO);

        ResponseEntity<ProfileDTO> response = controller.findById(1L);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo("User1");
        verify(service, times(1)).findById(1L);
    }

    @Test
    void testFindByusername() {
        Profile profile = new Profile(1L, new User(), "User1", "icon1.png", "Bio1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        ProfileDTO profileDTO = new ProfileDTO(profile);
        when(service.findByusername("User1")).thenReturn(profileDTO);

        ResponseEntity<ProfileDTO> response = controller.findByusername("User1");

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo("User1");
        verify(service, times(1)).findByusername("User1");
    }

    @Test
    void testInsert() {
        Profile profile = new Profile(1L, new User(), "User1", "icon1.png", "Bio1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        ProfileDTO profileDTO = new ProfileDTO(profile);

        when(service.insert(profileDTO)).thenReturn(profileDTO);

        ResponseEntity<ProfileDTO> response = controller.insert(profileDTO);

        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo("User1");
        verify(service, times(1)).insert(profileDTO);
    }

    @Test
    void testDelete() {
        doNothing().when(service).delete(1L);

        ResponseEntity<Void> response = controller.delete(1L);

        assertThat(response.getStatusCode().value()).isEqualTo(204);
        verify(service, times(1)).delete(1L);
    }

    @Test
void update_ShouldReturnUpdatedProfile_WhenValidRequest() {
    ProfileEditDTO profileEditDTO = new ProfileEditDTO();
    profileEditDTO.setUsername("UpdatedUser");
    profileEditDTO.setBiography("Updated biography");
    profileEditDTO.setUrlIcon("updated-icon-url");

    ProfileDTO updatedProfileDTO = new ProfileDTO();
    updatedProfileDTO.setId(1L);
    updatedProfileDTO.setUsername("UpdatedUser");
    updatedProfileDTO.setBiography("Updated biography");
    updatedProfileDTO.setUrlIcon("updated-icon-url");

    when(service.update(1L, profileEditDTO)).thenReturn(updatedProfileDTO);

    ResponseEntity<ProfileDTO> response = controller.update(1L, profileEditDTO);

    assertThat(response.getStatusCode().value()).isEqualTo(200);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getUsername()).isEqualTo("UpdatedUser");
    assertThat(response.getBody().getBiography()).isEqualTo("Updated biography");
    assertThat(response.getBody().getUrlIcon()).isEqualTo("updated-icon-url");
    verify(service, times(1)).update(1L, profileEditDTO);
}

    @Test
    void follow_ShouldReturnOk_WhenFollowIsSuccessful() throws Exception {
        Long idFollower = 1L;
        Long idProfile = 1L;

        doNothing().when(service).follow(idFollower, idProfile);

        mockMvc.perform(put("/profile/{idFollower}/follow/{idProfile}", idFollower, idProfile)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).follow(idFollower, idProfile);
    }

    @Test
    void follow_ShouldThrowBadRequest_WhenFollowerTriesToFollowSelf() {
        Long idFollower = 1L;

        doThrow(new IllegalArgumentException("Um perfil não pode seguir a si mesmo."))
                .when(service).follow(idFollower, idFollower);
        try {
            controller.follow(idFollower, idFollower);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Um perfil não pode seguir a si mesmo.");
        }
        verify(service, times(1)).follow(idFollower, idFollower);
    }

    @Test
    void follow_ShouldThrowNotFound_WhenProfileToFollowDoesNotExist() {
        Long idFollower = 1L;
        Long idProfile = 999L;

        doThrow(new EntityNotFoundException("Perfil a ser seguido não encontrado."))
                .when(service).follow(idFollower, idProfile);
        try {
            controller.follow(idFollower, idProfile);
        } catch (EntityNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Perfil a ser seguido não encontrado.");
        }

        verify(service, times(1)).follow(idFollower, idProfile);
    }
}

