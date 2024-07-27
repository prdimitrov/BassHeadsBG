package com.bg.bassheadsbg.web.controller.userControllers;

import com.bg.bassheadsbg.service.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AllUsersControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AllUsersController allUsersController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(allUsersController).build();
    }

    @Test
    public void testAllUsers() throws Exception {
        when(userService.findAllUsers()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/users/all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/users/all-users"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"));

        verify(userService, times(1)).findAllUsers();
    }

    @Test
    public void testDisableUser() throws Exception {
        mockMvc.perform(post("/users/disable/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/all"));

        verify(userService, times(1)).disableUser(1L);
    }

    @Test
    public void testEnableUser() throws Exception {
        mockMvc.perform(post("/users/enable/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/all"));

        verify(userService, times(1)).enableUser(1L);
    }
}
