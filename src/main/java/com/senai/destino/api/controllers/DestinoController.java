package com.senai.destino.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.senai.destino.api.dtos.DestinoDTO;
import com.senai.destino.api.entities.Destino;
import com.senai.destino.api.services.DestinoService;

@RestController
@RequestMapping("/destinos")
public class DestinoController {

	@Autowired
	private DestinoService destinoService;

	@PostMapping("/")
	public ResponseEntity<DestinoDTO> cadastrarDestino(@RequestBody DestinoDTO destinoDTO) {
		return destinoService.cadastrar(destinoDTO);
	}

	@GetMapping("/")
	public ResponseEntity<List<DestinoDTO>> listarDestinos() {
		return destinoService.listarDestinos();
	}

	@GetMapping("/{id}")
	public ResponseEntity<DestinoDTO> recuperarDestinoPorId(@PathVariable Long id) throws NotFoundException {
		return destinoService.recuperarDestino(id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Destino> excluirDestinoPorId(@PathVariable Long id) throws NotFoundException {
		return destinoService.excluirDestino(id);
	}

	@GetMapping("/pesquisar")
	public ResponseEntity<List<DestinoDTO>> recuperarPorNomeLocalizacao(@RequestParam(required = false) String nome,
			@RequestParam(required = false) String localizacao) {
		return destinoService.listarDestinosPorNomeLocalizacao(nome, localizacao);
	}

	@PatchMapping("/{id}/avaliacao")
	public ResponseEntity<DestinoDTO> avaliarDestino(@RequestParam double nota, @PathVariable Long id)
			throws NotFoundException {
		return destinoService.atualizarAvaliacao(nota, id);
	}
}