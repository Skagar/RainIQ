package com.rainiq.auth_service.security;
import com.rainiq.auth_service.entity.User;
import com.rainiq.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      User user =userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User Not found with email "+ email));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword()==null?"": user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole().name()))

        );
    }
}
