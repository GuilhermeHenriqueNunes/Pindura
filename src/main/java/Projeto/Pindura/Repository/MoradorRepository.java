package Projeto.Pindura.Repository;

import Projeto.Pindura.Model.Morador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoradorRepository extends JpaRepository<Morador, Long> {

    // Método para atender ao requisito de pesquisa por nome
    List<Morador> findByNomeContainingIgnoreCase(String nome);
}
