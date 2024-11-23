package com.senai.destino.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senai.destino.api.config.JWTGenerator;
import com.senai.destino.api.dtos.AuthDTO;
import com.senai.destino.api.dtos.AuthResponseDTO;
import com.senai.destino.api.repositories.UsuarioRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private AuthenticationManager authenticationManager;
	private UsuarioRepository usuarioRepository;
	private PasswordEncoder passwordEncoder;
	private JWTGenerator jwtGenerator;

	@Autowired
	public AuthController(AuthenticationManager authenticationManager, UsuarioRepository usuarioRepository,
			PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
		this.authenticationManager = authenticationManager;
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtGenerator = jwtGenerator;

	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthDTO authDTO) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getNome(), authDTO.getSenha()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtGenerator.generateToken(authentication);

		return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.ACCEPTED);
	}

}