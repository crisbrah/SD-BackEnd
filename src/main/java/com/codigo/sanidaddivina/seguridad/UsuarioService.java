package com.codigo.sanidaddivina.seguridad;

import com.codigo.sanidaddivina.entities.MiembroEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsuarioService {
    UserDetailsService userDetailService();
    List<MiembroEntity> getUsuarios();
}
