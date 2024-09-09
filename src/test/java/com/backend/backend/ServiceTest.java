package com.backend.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.backend.backend.model.Pessoa;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ServiceTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void deveCriarUmCadastro() throws Exception{
        String Url = "http://localhost:8081/api/post";
        Pessoa novaPessoa = new Pessoa(null, "mateus12", "119472619", "439919382");
        ResponseEntity<Void> criaResponse = restTemplate.postForEntity(Url, novaPessoa, Void.class);
        assertThat(criaResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void pegaPessoaPorId() throws Exception{
        String Url = "http://localhost:8081/api/1";
        ResponseEntity<String> criaResponse = restTemplate.getForEntity(Url, String.class);
        assertThat(criaResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContext = JsonPath.parse(criaResponse);
        Number id = documentContext.read("$.id");
        assertThat(id).isEqualTo(1);
    }

    @Test
    void deveAtualizarCadastro() throws Exception {
        String url = "http://localhost:8081/api/1";
        Pessoa pessoaAtualizada = new Pessoa(1L, "mateus", "11947261940", "43991938209");
        HttpEntity<Pessoa> requestEntity = new HttpEntity<>(pessoaAtualizada);
        ResponseEntity<Void> updateResponse = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Void.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deveDeletarCadastro() throws Exception {
        String url = "http://localhost:8081/api/1";
        ResponseEntity<Void> criaResponse = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        assertThat(criaResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
