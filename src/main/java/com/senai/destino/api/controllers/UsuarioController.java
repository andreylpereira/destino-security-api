package com.senai.destino.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senai.destino.api.dtos.UsuarioDTO;
import com.senai.destino.api.services.UsuarioService;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	

	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping("/")
	public ResponseEntity<UsuarioDTO> cadastrarDestino(@RequestBody UsuarioDTO usuarioDTO) {
		return usuarioService.cadastrar(usuarioDTO);
	}

	@GetMapping("/")
	public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
		return usuarioService.listarUsuarios();
	}

	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDTO> recuperarUsuarioPorId(@PathVariable Long id) throws NotFoundException {
		return usuarioService.recuperarUsuario(id);
	}

}




	
