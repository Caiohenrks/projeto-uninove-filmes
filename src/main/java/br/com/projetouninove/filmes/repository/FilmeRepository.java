/*
 * LinkedIn: https://www.linkedin.com/in/caiohenrks/
 * Github: https://github.com/Caiohenrks
 */
package br.com.projetouninove.filmes.repository;

import br.com.projetouninove.filmes.domain.Filme;
import java.util.List;

/**
 *
 * @author Caioh
 */
public interface FilmeRepository {
    
    List<Filme> listar();
    Filme buscar(Long id);
    Filme salvar(Filme filme);
    void remover(Long id);
}
