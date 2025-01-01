package com.souzs.dscommerce.services;

import com.souzs.dscommerce.entities.Role;
import com.souzs.dscommerce.entities.User;
import com.souzs.dscommerce.projections.UserDetailsProjection;
import com.souzs.dscommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result = userRepository.searchUserAndRolesByEmail(username);

        if(result.isEmpty()) throw new UsernameNotFoundException("User not found.");

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
}
