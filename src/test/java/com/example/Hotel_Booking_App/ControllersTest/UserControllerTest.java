package com.example.Hotel_Booking_App.ControllersTest;

import com.example.Hotel_Booking_App.Controllers.UserController;
import com.example.Hotel_Booking_App.Dto.UserDTO;
import com.example.Hotel_Booking_App.Entitys.User;
import com.example.Hotel_Booking_App.Mapper.MapperUtil;
import com.example.Hotel_Booking_App.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private MapperUtil mapperUtil;

    @InjectMocks
    private UserController userController;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1L);
        user.setUsername("JohnDoe");
        user.setPassword("password");
        // Add other properties if needed

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("JohnDoe");
        // Add other properties if needed
    }

    @Test
    public void testRegisterUser() {
        when(userService.saveUser(any(User.class))).thenReturn(user);
        when(mapperUtil.toUserDTO(any(User.class))).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.registerUser(user);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userDTO.getId(), response.getBody().getId());
        assertEquals(userDTO.getUsername(), response.getBody().getUsername());
    }

    @Test
    public void testGetUserById() {
        when(userService.findUserById(anyLong())).thenReturn(user);
        when(mapperUtil.toUserDTO(any(User.class))).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.getUserById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userDTO.getId(), response.getBody().getId());
        assertEquals(userDTO.getUsername(), response.getBody().getUsername());
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = Arrays.asList(user, user);
        List<UserDTO> userDTOs = Arrays.asList(userDTO, userDTO);

        when(userService.findAllUsers()).thenReturn(users);
        when(mapperUtil.toUserDTO(any(User.class))).thenReturn(userDTO);

        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userDTOs.size(), response.getBody().size());
        for (int i = 0; i < userDTOs.size(); i++) {
            assertEquals(userDTOs.get(i).getId(), response.getBody().get(i).getId());
            assertEquals(userDTOs.get(i).getUsername(), response.getBody().get(i).getUsername());
        }
    }
}
