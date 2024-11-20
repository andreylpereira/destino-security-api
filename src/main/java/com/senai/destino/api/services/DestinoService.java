package com.senai.destino.api.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.senai.destino.api.dtos.DestinoDTO;
import com.senai.destino.api.entities.Destino;
import com.senai.destino.api.repositories.DestinoRepository;

@Service
public class DestinoService {

	private DestinoRepository destinoRepository;

	@Autowired
	public DestinoService(DestinoRepository destinoRepository) {
		this.destinoRepository = destinoRepository;
	}

	public ResponseEntity<Destino> cadastrar(DestinoDTO destinoDTO) {

		boolean isBlank = (destinoDTO.getDescricao().isBlank() || destinoDTO.getLocalizacao().isBlank()
				|| destinoDTO.getNome().isBlank() || destinoDTO.getTipo().isBlank());

		if (isBlank) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		try {
			Destino destino = new Destino();
			BeanUtils.copyProperties(destinoDTO, destino);

			Destino novoDestino = destinoRepository.save(destino);
			return new ResponseEntity<>(novoDestino, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	public ResponseEntity<List<Destino>> listarDestinos() {
		List<Destino> destinos = destinoRepository.findAll();
		if (!destinos.isEmpty()) {
			return new ResponseEntity<>(destinos, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

	public ResponseEntity<Destino> recuperarDestino(Long id) throws NotFoundException {
		Destino destino = destinoRepository.findById(id).orElseThrow(() -> new NotFoundException());
		return new ResponseEntity<>(destino, HttpStatus.OK);
	}

	public ResponseEntity<Destino> excluirDestino(Long id) throws NotFoundException {
		Destino destino = destinoRepository.findById(id).orElseThrow(() -> new NotFoundException());
		destinoRepository.delete(destino);
		return new ResponseEntity<>(destino, HttpStatus.OK);
	}

	public ResponseEntity<List<Destino>> listarDestinosPorNomeLocalizacao(String nome, String localizacao) {
		List<Destino> destinos;

		if (nome != null && !nome.isBlank() && localizacao != null && !localizacao.isBlank()) {
			destinos = destinoRepository.findByNomeAndLocalizacao(nome, localizacao);
		} else if (nome != null && !nome.isBlank()) {
			destinos = destinoRepository.findByNome(nome);
		} else if (localizacao != null && !localizacao.isBlank()) {
			destinos = destinoRepository.findByLocalizacao(localizacao);
		} else {
			destinos = destinoRepository.findAll();
		}
		return new ResponseEntity<>(destinos, HttpStatus.OK);
	}

	public ResponseEntity<Destino> atualizarAvaliacao(double nota, Long id) throws NotFoundException {

		boolean isValid = (nota >= 0.00 && nota <= 10.0) && (id > 0);

		if (isValid) {
			Destino destino = destinoRepository.findById(id).orElseThrow(() -> new NotFoundException());

			int quantidadeAvaliada = destino.getQuantidadeAvaliacoes() + 1;
			double avaliacaoAtualizada = ((destino.getAvaliacao() * destino.getQuantidadeAvaliacoes()) + nota)
					/ quantidadeAvaliada;

			destino.setAvaliacao(avaliacaoAtualizada);
			destino.setQuantidadeAvaliacoes(quantidadeAvaliada);

			destinoRepository.save(destino);

			return new ResponseEntity<>(destino, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
