package ru.otus.lesson25.service;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.otus.lesson25.model.Role;
import ru.otus.lesson25.model.User;
import java.util.Collection;
import java.util.List;

public interface UserService extends UserDetailsService {
    public User findByUsername(String username);
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<Role> roles);
}
