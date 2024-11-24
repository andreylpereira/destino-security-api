package com.senai.destino.api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.senai.destino.api.dtos.UsuarioDTO;
import com.senai.destino.api.entities.Usuario;
import com.senai.destino.api.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired

	private UsuarioRepository usuarioRepository;

	public ResponseEntity<UsuarioDTO> cadastrar(UsuarioDTO usuarioDTO) {
		boolean isBlank = (usuarioDTO.getRole().isBlank() || usuarioDTO.getNome().isBlank()
				|| usuarioDTO.getRole().isBlank() || usuarioDTO.getSenha().isBlank());

		if (isBlank) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		try {
			Usuario usuario = new Usuario();
			BeanUtils.copyProperties(usuarioDTO, usuario);
			usuario.setSenha(new BCryptPasswordEncoder().encode(usuarioDTO.getSenha()));
			usuarioRepository.save(usuario);
			usuarioDTO.setSenha(null);
			return new ResponseEntity<>(usuarioDTO, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
		List<Usuario> usuarios = usuarioRepository.findAll();

		if (!usuarios.isEmpty()) {
			List<UsuarioDTO> usuariosDTO = usuarios.stream().map(usuario -> {
				UsuarioDTO usuarioDTO = new UsuarioDTO();
				BeanUtils.copyProperties(usuario, usuarioDTO);
				return usuarioDTO;
			}).collect(Collectors.toList());

			return new ResponseEntity<>(usuariosDTO, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<UsuarioDTO> recuperarUsuario(Long id) throws NotFoundException {
		Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException());

		UsuarioDTO usuarioDTO = new UsuarioDTO();
		BeanUtils.copyProperties(usuario, usuarioDTO);

		return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
	}
}
