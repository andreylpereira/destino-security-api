package com.senai.destino.api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Destino {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	 @Column(name = "nome", nullable = false)
	private String nome;
	 @Column(name = "localizacao", nullable = false)
	private String localizacao;
	 @Column(name = "descricao", nullable = false)
	private String descricao;
	 @Column(name = "tipo", nullable = false)
	private String tipo;
	private double avaliacao;
	private int quantidadeAvaliacoes;

	public Destino() {
	}

	public Destino(Long id, String nome, String localizacao, String descricao, String tipo, double avaliacao,
			int quantidadeAvaliacoes) {
		this.id = id;
		this.nome = nome;
		this.localizacao = localizacao;
		this.descricao = descricao;
		this.tipo = tipo;
		this.avaliacao = avaliacao;
		this.quantidadeAvaliacoes = quantidadeAvaliacoes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(double avaliacao) {
		this.avaliacao = avaliacao;
	}

	public int getQuantidadeAvaliacoes() {
		return quantidadeAvaliacoes;
	}

	public void setQuantidadeAvaliacoes(int quantidadeAvaliacoes) {
		this.quantidadeAvaliacoes = quantidadeAvaliacoes;
	}

}