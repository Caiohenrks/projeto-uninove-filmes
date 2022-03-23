/*
 * LinkedIn: https://www.linkedin.com/in/caiohenrks/
 * Github: https://github.com/Caiohenrks
 */
package br.com.projetouninove.filmes.service;

import br.com.projetouninove.filmes.domain.Autor;
import br.com.projetouninove.filmes.domain.Filme;
import br.com.projetouninove.filmes.exception.EntidadeEmUsoException;
import br.com.projetouninove.filmes.exception.EntidadeNaoEncontradaException;
import br.com.projetouninove.filmes.repository.AutorRepository;
import br.com.projetouninove.filmes.repository.FilmeRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

/**
 * @Caiohenrks
 */
@Service
public class FilmeService {

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private AutorRepository autorRepository;

    public Filme salvar(Filme filme) {
        Long id = filme.getAutor().getId();
        Optional<Autor> autor = autorRepository.findById(id);

        if (autor.isEmpty()) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe cadastro de autor com o códito %d", id));
        }
        filme.setAutor(autor.get());
        return filmeRepository.save(filme);
    }

    public void remover(Long id) {
        try {
            filmeRepository.deleteById(id);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Filme de código %d não existe", id));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Filme de código %d não pode ser removida, pois está em uso", id));
        }
    }
}
