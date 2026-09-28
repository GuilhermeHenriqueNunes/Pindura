package Projeto.Pindura.Service;

import Projeto.Pindura.Model.Morador;
import Projeto.Pindura.Repository.MoradorRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MoradorService {

    private final MoradorRepository moradorRepository;

    public MoradorService(MoradorRepository moradorRepository) {
        this.moradorRepository = moradorRepository;
    }

    /**
     * Lista moradores, aplicando pesquisa por nome (se informada) e ordenação por nome.
     *
     * @param nome    termo de pesquisa; null/vazio retorna todos
     * @param direcao "asc" (padrão) ou "desc"
     */
    public List<Morador> listar(String nome, String direcao) {
        Sort sort = Sort.by("nome");
        sort = "desc".equalsIgnoreCase(direcao) ? sort.descending() : sort.ascending();

        if (nome != null && !nome.isBlank()) {
            return moradorRepository.findByNomeContainingIgnoreCase(nome, sort);
        }
        return moradorRepository.findAll(sort);
    }

    public Morador buscarPorId(Long id) {
        return moradorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Morador não encontrado (id: " + id + ")"));
    }

    public Morador salvar(Morador morador) {
        return moradorRepository.save(morador);
    }

    public void excluir(Long id) {
        if (!moradorRepository.existsById(id)) {
            throw new NoSuchElementException("Morador não encontrado (id: " + id + ")");
        }
        moradorRepository.deleteById(id);
    }
}
