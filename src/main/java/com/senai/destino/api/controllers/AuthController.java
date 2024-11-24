package com.senai.destino.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senai.destino.api.dtos.AuthDTO;
import com.senai.destino.api.dtos.AuthResponseDTO;
import com.senai.destino.api.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;	

	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> auth(@RequestBody AuthDTO authDTO) {
		return authService.login(authDTO);
	}

}