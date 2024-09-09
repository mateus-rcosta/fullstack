package com.backend.backend.repository;

import org.springframework.data.repository.CrudRepository;

import com.backend.backend.model.Pessoa;

public interface PessoaRepository extends CrudRepository<Pessoa, Long>{}
