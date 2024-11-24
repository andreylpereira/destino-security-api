package com.senai.destino.api.services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.senai.destino.api.entities.Usuario;
import com.senai.destino.api.repositories.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private UsuarioRepository usuarioRepository;

	@Autowired
	public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = usuarioRepository.findByNome(username).orElseThrow(() -> {
			return new UsernameNotFoundException("Usuário não encontrado.");
		});
		return new User(usuario.getNome(), usuario.getSenha(), mapRolesToAuthorities(usuario));
	}

	private Collection<GrantedAuthority> mapRolesToAuthorities(Usuario usuario) {
		return List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRole()));
	}

}
