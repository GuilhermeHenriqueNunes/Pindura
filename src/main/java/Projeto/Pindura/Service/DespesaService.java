package Projeto.Pindura.Service;

import Projeto.Pindura.Model.Despesa;
import Projeto.Pindura.Model.Morador;
import Projeto.Pindura.Repository.DespesaRepository;
import Projeto.Pindura.Repository.MoradorRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class DespesaService {

    private final DespesaRepository despesaRepository;
    private final MoradorRepository moradorRepository;

    public DespesaService(DespesaRepository despesaRepository, MoradorRepository moradorRepository) {
        this.despesaRepository = despesaRepository;
        this.moradorRepository = moradorRepository;
    }

    /**
     * Lista despesas, aplicando pesquisa por título (se informada) e ordenação.
     *
     * @param titulo          termo de pesquisa; null/vazio retorna todas
     * @param campoOrdenacao "titulo" (padrão), "valor" ou "data"
     * @param direcao         "asc" (padrão) ou "desc"
     */
    public List<Despesa> listar(String titulo, String campoOrdenacao, String direcao) {
        Sort sort = Sort.by(campoValido(campoOrdenacao));
        sort = "desc".equalsIgnoreCase(direcao) ? sort.descending() : sort.ascending();

        if (titulo != null && !titulo.isBlank()) {
            return despesaRepository.findByTituloContainingIgnoreCase(titulo, sort);
        }
        return despesaRepository.findAll(sort);
    }

    private String campoValido(String campo) {
        // Protege contra Sort de um campo inexistente vindo de um parametro de URL
        if ("valor".equalsIgnoreCase(campo)) {
            return "valor";
        }
        if ("data".equalsIgnoreCase(campo)) {
            return "data";
        }
        return "titulo";
    }

    public Despesa buscarPorId(Long id) {
        return despesaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Despesa não encontrada (id: " + id + ")"));
    }

    /**
     * Salva uma despesa (nova ou existente), resolvendo pagador e participantes
     * a partir dos IDs vindos do formulário.
     */
    public Despesa salvar(Despesa despesa, Long pagadorId, List<Long> participantesIds) {
        Morador pagador = moradorRepository.findById(pagadorId)
                .orElseThrow(() -> new NoSuchElementException("Pagador inválido"));
        despesa.setPagador(pagador);

        if (participantesIds == null || participantesIds.isEmpty()) {
            throw new IllegalArgumentException("Selecione ao menos um participante");
        }
        List<Morador> participantes = moradorRepository.findAllById(participantesIds);
        despesa.setParticipantes(participantes);

        return despesaRepository.save(despesa);
    }

    public void excluir(Long id) {
        if (!despesaRepository.existsById(id)) {
            throw new NoSuchElementException("Despesa não encontrada (id: " + id + ")");
        }
        despesaRepository.deleteById(id);
    }

    /**
     * Regra de negócio principal (Fase 4): calcula quanto cada participante deve
     * ao pagador. O próprio pagador não aparece no mapa, pois sua parcela já foi
     * coberta ao pagar o total da despesa.
     */
    public Map<Morador, BigDecimal> calcularSaldos(Despesa despesa) {
        BigDecimal parcela = despesa.getValorPorParticipante();
        Map<Morador, BigDecimal> saldos = new LinkedHashMap<>();

        for (Morador participante : despesa.getParticipantes()) {
            if (participante.equals(despesa.getPagador())) {
                continue;
            }
            saldos.put(participante, parcela);
        }
        return saldos;
    }
}
