/*
 * LinkedIn: https://www.linkedin.com/in/caiohenrks/
 * Github: https://github.com/Caiohenrks
 */
package br.com.projetouninove.filmes.repository;

import br.com.projetouninove.filmes.domain.Filme;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caioh
 */
@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long>{

}
