package br.com.projetouninove.filmes.controller;

import br.com.projetouninove.filmes.domain.Filme;
import br.com.projetouninove.filmes.exception.EntidadeEmUsoException;
import br.com.projetouninove.filmes.exception.EntidadeNaoEncontradaException;
import br.com.projetouninove.filmes.repository.FilmeRepository;
import br.com.projetouninove.filmes.service.FilmeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/filmes")
public class FilmeController {

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private FilmeService cadastroFilme;

    @GetMapping
    public List<Filme> listar() {
        return filmeRepository.listar();
    }

    @GetMapping("/{filmeId}")
    public ResponseEntity<Filme> buscar(@PathVariable Long filmeId) {
        Filme filme = filmeRepository.buscar(filmeId);

        if (filme != null) {
            return ResponseEntity.ok(filme);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Filme filme) {
        try {
            filme = cadastroFilme.salvar(filme);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(filme);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{filmeId}")
    public ResponseEntity<?> atualizar(@PathVariable Long filmeId,
            @RequestBody Filme filme) {
        try {
            Filme filmeAtual = filmeRepository.buscar(filmeId);

            if (filmeAtual != null) {
                BeanUtils.copyProperties(filme, filmeAtual, "id");

                filmeAtual = cadastroFilme.salvar(filmeAtual);
                return ResponseEntity.ok(filmeAtual);
            }

            return ResponseEntity.notFound().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PatchMapping("/{filmeId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long filmeId,
            @RequestBody Map<String, Object> campos) {
        Filme filmeAtual = filmeRepository.buscar(filmeId);

        if (filmeAtual == null) {
            return ResponseEntity.notFound().build();
        }
        merge(campos, filmeAtual);
        return atualizar(filmeId, filmeAtual);

    }
        @DeleteMapping("/{filmeId}")
    public ResponseEntity<?> remover(@PathVariable Long filmeId) {
        try {
            cadastroFilme.remover(filmeId);
            return ResponseEntity.noContent().build();
   
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();

        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    public void merge(Map<String, Object> dadosOrigem, Filme filmeDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        
        Filme filmeOrigem = objectMapper.convertValue(dadosOrigem, Filme.class);
        
        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Filme.class, nomePropriedade);
            field.setAccessible(true);
            
            Object novoValor = ReflectionUtils.getField(field, filmeOrigem);

            ReflectionUtils.setField(field, filmeDestino, novoValor);
        });
    }
    
    

}
