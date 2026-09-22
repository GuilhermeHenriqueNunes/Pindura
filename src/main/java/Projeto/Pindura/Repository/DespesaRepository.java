package Projeto.Pindura.Repository;

import Projeto.Pindura.Model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    // Método para atender ao requisito de pesquisa por título
    List<Despesa> findByTituloContainingIgnoreCase(String titulo);
}
