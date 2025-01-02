package com.cchys.security;


import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cchys.repositories.UsuariosRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class UsuarioLogin implements UserDetailsService {
	
	
    private final UsuariosRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.customerRepository.findByCorreoAndEstado(username,1)
                .map(customer -> {
                    final var roles = customer.getRoles();
                    final var authorities = roles
                            .stream()
                            .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                            .collect(Collectors.toList());
                    return new User(customer.getCorreo(), customer.getPassword(), authorities);
                }).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
	

}
