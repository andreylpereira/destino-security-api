package com.senai.destino.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senai.destino.api.entities.Destino;

public interface DestinoRepository extends JpaRepository<Destino, Long> {

	List<Destino> findByNomeAndLocalizacao(String nome, String localizacao);

	List<Destino> findByNome(String nome);

	List<Destino> findByLocalizacao(String localizacao);
}