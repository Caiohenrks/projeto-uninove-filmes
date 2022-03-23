/*
 * LinkedIn: https://www.linkedin.com/in/caiohenrks/
 * Github: https://github.com/Caiohenrks
 */
package br.com.projetouninove.filmes.service;

import br.com.projetouninove.filmes.domain.Autor;
import br.com.projetouninove.filmes.exception.EntidadeEmUsoException;
import br.com.projetouninove.filmes.exception.EntidadeNaoEncontradaException;
import br.com.projetouninove.filmes.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

/**
 * @Caiohenrks
 */
@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public Autor salvar(Autor autor) {
        return autorRepository.save(autor);
    }

    public void excluir(Long id) {
        try {
            autorRepository.deleteById(id);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Autor de código %d não existe", id));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Autor de código %d não pode ser removida, pois está em uso", id));
        }
    }
}
