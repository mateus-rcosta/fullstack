package com.backend.backend.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.backend.backend.model.Pessoa;
import com.backend.backend.repository.PessoaRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private PessoaRepository pessoaRepository;

    @PostMapping("/post")
    public ResponseEntity<Void> criaPessoa(@RequestBody Pessoa novaPessoa, UriComponentsBuilder ucb) {
        Pessoa savePessoa = pessoaRepository.save(novaPessoa);
        URI posicaoNovaPessoa = ucb.path("api/{id}").buildAndExpand(savePessoa.getId()).toUri();
        return ResponseEntity.created(posicaoNovaPessoa).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> findById(@PathVariable("id") Long requestedId){
        Optional<Pessoa> pessoaOpcional = pessoaRepository.findById(requestedId);
        if(pessoaOpcional.isPresent()){
            return ResponseEntity.ok(pessoaOpcional.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> updatePessoa(@PathVariable("id") Long id, @RequestBody Pessoa pessoaAtualizada) {
        Optional<Pessoa> pessoaOpcional = pessoaRepository.findById(id);
        if (pessoaOpcional.isPresent()) {
            Pessoa pessoa = pessoaOpcional.get();
            pessoa.setNome(pessoaAtualizada.getNome());
            pessoa.setTelefone(pessoaAtualizada.getTelefone());
            pessoa.setCpf(pessoaAtualizada.getCpf());
            pessoaRepository.save(pessoa);
            return ResponseEntity.ok(pessoa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable("id") Long id) {
        Optional<Pessoa> pessoaExistente = pessoaRepository.findById(id);
        if (pessoaExistente.isPresent()) {
            pessoaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
