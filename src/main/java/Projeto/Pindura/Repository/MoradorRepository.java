package Projeto.Pindura.Repository;

import Projeto.Pindura.Model.Morador;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoradorRepository extends JpaRepository<Morador, Long> {

    // Pesquisa por nome, já aceitando Sort para atender ordenação crescente/decrescente
    List<Morador> findByNomeContainingIgnoreCase(String nome, Sort sort);
}
