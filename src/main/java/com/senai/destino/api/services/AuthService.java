package com.senai.destino.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.senai.destino.api.dtos.AuthDTO;
import com.senai.destino.api.dtos.AuthResponseDTO;
import com.senai.destino.api.repositories.UsuarioRepository;
import com.senai.destino.api.security.JWTGenerator;


@Service
public class AuthService {

	private AuthenticationManager authenticationManager;
	private UsuarioRepository usuarioRepository;
	private PasswordEncoder passwordEncoder;
	private JWTGenerator jwtGenerator;

	@Autowired
	public AuthService(AuthenticationManager authenticationManager, UsuarioRepository usuarioRepository,
			PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
		this.authenticationManager = authenticationManager;
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtGenerator = jwtGenerator;

	}

	public ResponseEntity<AuthResponseDTO> login(AuthDTO authDTO) {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getNome(), authDTO.getSenha()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String token = jwtGenerator.generateToken(authentication);

			return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(new AuthResponseDTO("Erro ao autenticar."), HttpStatus.BAD_REQUEST);
		}
	}

}
