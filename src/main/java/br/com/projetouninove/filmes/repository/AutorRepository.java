/*
 * LinkedIn: https://www.linkedin.com/in/caiohenrks/
 * Github: https://github.com/Caiohenrks
 */
package br.com.projetouninove.filmes.repository;

import br.com.projetouninove.filmes.domain.Autor;
import java.util.List;

/**
 *
 * @author Caioh
 */
public interface AutorRepository {
    
    List<Autor> listar();
    Autor buscar(Long id);
    Autor salvar(Autor autor);
    void remover(Long id);
}
