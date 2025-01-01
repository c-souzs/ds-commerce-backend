package com.souzs.dscommerce.controllers;

import com.souzs.dscommerce.dto.UserDTO;
import com.souzs.dscommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMe() {
        UserDTO result = userService.findMe();

        return ResponseEntity.ok(result);
    }
}
