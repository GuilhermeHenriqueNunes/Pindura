package Projeto.Pindura.Repository;

import Projeto.Pindura.Model.Despesa;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    // Pesquisa por título, já aceitando Sort para atender ordenação (título, valor ou data)
    List<Despesa> findByTituloContainingIgnoreCase(String titulo, Sort sort);
}
