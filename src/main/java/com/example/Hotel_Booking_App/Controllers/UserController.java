package com.example.Hotel_Booking_App.Controllers;
import com.example.Hotel_Booking_App.Dto.UserDTO;
import com.example.Hotel_Booking_App.Entitys.User;
import com.example.Hotel_Booking_App.Mapper.MapperUtil;
import com.example.Hotel_Booking_App.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MapperUtil mapperUtil;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(mapperUtil.toUserDTO(savedUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        return ResponseEntity.ok(mapperUtil.toUserDTO(user));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(mapperUtil::toUserDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }
}
