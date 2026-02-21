package com.codigo.sanidaddivina.seguridad.impl;


import com.codigo.sanidaddivina.dao.MiembroRepository;
import com.codigo.sanidaddivina.entities.MiembroEntity;
import com.codigo.sanidaddivina.seguridad.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final MiembroRepository usuarioRepository;
    @Override
    public UserDetailsService userDetailService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return usuarioRepository.findByEmail(username).orElseThrow(()->
                        new UsernameNotFoundException("Usuario no Encontrado"));
            }
        };
    }

    @Override
    public List<MiembroEntity> getUsuarios() {
        return usuarioRepository.findAll();
    }
}
