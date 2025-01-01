package com.souzs.dscommerce.services;

import com.souzs.dscommerce.dto.UserDTO;
import com.souzs.dscommerce.entities.Role;
import com.souzs.dscommerce.entities.User;
import com.souzs.dscommerce.projections.UserDetailsProjection;
import com.souzs.dscommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result = userRepository.searchUserAndRolesByEmail(username);

        if(result.isEmpty()) throw new UsernameNotFoundException("Email not found.");

        User user = new User();
        UserDetailsProjection first = result.getFirst();
        user.setEmail(first.getUsername());
        user.setPassword(first.getPassword());

        result.forEach(projection -> {
            Role roleProjection = new Role(projection.getRoleId(), projection.getAuthority());
            user.addRole(roleProjection);
        });

        return user;
    }

    protected User authenticated() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
            String username = jwtPrincipal.getClaim("username");

            return userRepository.findByEmail(username).get();
        } catch (Exception e) {
            throw new UsernameNotFoundException("Email not found");
        }
    }

    @Transactional(readOnly = true)
    public UserDTO findMe() {
        return new UserDTO(authenticated());
    }
}
