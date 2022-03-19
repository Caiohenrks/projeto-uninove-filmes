/*
 * LinkedIn: https://www.linkedin.com/in/caiohenrks/
 * Github: https://github.com/Caiohenrks
 */
package br.com.projetouninove.filmes.repository;

import br.com.projetouninove.filmes.domain.Autor;
import br.com.projetouninove.filmes.domain.Filme;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Caiohenrks
 */
@Repository
public class AutorRepositoryImpl implements AutorRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Autor> listar() {
        return em.createQuery("from Autor", Autor.class)
                .getResultList();
    }

    @Override
    public Autor buscar(Long id) {
        return em.find(Autor.class, id);
    }

    @Transactional
    @Override
    public Autor salvar(Autor autor) {
        return em.merge(autor);
    }

    @Transactional
    @Override
    public void remover(Long id) {
        Autor autor = buscar(id);
        em.remove(autor);
    }

}
