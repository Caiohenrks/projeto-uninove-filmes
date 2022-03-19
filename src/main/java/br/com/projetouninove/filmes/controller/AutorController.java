/*
 * LinkedIn: https://www.linkedin.com/in/caiohenrks/
 * Github: https://github.com/Caiohenrks
 */
package br.com.projetouninove.filmes.controller;

import br.com.projetouninove.filmes.domain.Autor;
import br.com.projetouninove.filmes.exception.EntidadeEmUsoException;
import br.com.projetouninove.filmes.exception.EntidadeNaoEncontradaException;
import br.com.projetouninove.filmes.repository.AutorRepository;
import br.com.projetouninove.filmes.service.AutorService;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *  @Caiohenrks
 */
@RestController
@RequestMapping(value = "/autores")
public class AutorController {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private AutorService cadastroAutor;

    @GetMapping
    public List<Autor> listar() {
        return autorRepository.listar();
    }

    @GetMapping("/{autorId}")
    public ResponseEntity<Autor> buscar(@PathVariable Long autorId) {
        Autor autor = autorRepository.buscar(autorId);

        if (autor != null) {
            return ResponseEntity.ok(autor);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Autor adicionar(@RequestBody Autor autor) {
        return cadastroAutor.salvar(autor);
    }

    @PutMapping("/{autorId}")
    public ResponseEntity<Autor> atualizar(@PathVariable Long autorId,
            @RequestBody Autor autor) {
        Autor autorAtual = autorRepository.buscar(autorId);

        if (autorAtual != null) {
            BeanUtils.copyProperties(autor, autorAtual, "id");

            autorAtual = cadastroAutor.salvar(autorAtual);
            return ResponseEntity.ok(autorAtual);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{autorId}")
    public ResponseEntity<?> remover(@PathVariable Long autorId) {
        try {
            cadastroAutor.excluir(autorId);
            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();

        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

}

