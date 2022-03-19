/*
 * LinkedIn: https://www.linkedin.com/in/caiohenrks/
 * Github: https://github.com/Caiohenrks
 */
package br.com.projetouninove.filmes.repository;

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
public class FilmeRepositoryImpl implements FilmeRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Filme> listar() {
        return em.createQuery("from Filme", Filme.class)
                .getResultList();
    }

    @Override
    public Filme buscar(Long id) {
        return em.find(Filme.class, id);
    }

    @Transactional
    @Override
    public Filme salvar(Filme filme) {
        return em.merge(filme);
    }

    @Transactional
    @Override
    public void remover(Long id) {
        Filme filme = buscar(id);
        em.remove(filme);
    }

}
