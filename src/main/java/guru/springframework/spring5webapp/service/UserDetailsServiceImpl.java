package guru.springframework.spring5webapp.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("user".equals(s)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new User("user", new BCryptPasswordEncoder().encode("password"), authorities);
        } else if ("admin".equals(s)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return new User("admin", new BCryptPasswordEncoder().encode("password"), authorities);
        } else {
            throw new UsernameNotFoundException("Usuario o contraseña incorrecta");
        }
    }
}
