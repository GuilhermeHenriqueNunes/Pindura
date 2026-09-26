package Projeto.Pindura.Controller;

import Projeto.Pindura.Model.Despesa;
import Projeto.Pindura.Service.DespesaService;
import Projeto.Pindura.Service.MoradorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ResidenciaController {

    private final MoradorService moradorService;
    private final DespesaService despesaService;

    public ResidenciaController(MoradorService moradorService, DespesaService despesaService) {
        this.moradorService = moradorService;
        this.despesaService = despesaService;
    }

    @GetMapping("/")
    public String index(Model model) {
        // Uso de Wildcard List para capturar o retorno sem avisos de raw type
        List despesas = despesaService.listar(null, "data", "desc");

        long totalMoradores = moradorService.listar(null, "asc").size();
        long totalDespesas = despesas.size();

        // Cálculo do somatório com conversão segura de tipo
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (Object obj : despesas) {
            if (obj instanceof Despesa despesa) {
                if (despesa.getValor() != null) {
                    valorTotal = valorTotal.add(despesa.getValor());
                }
            }
        }

        // Construção da lista das 5 despesas mais recentes
        List ultimasDespesas = new ArrayList<>();
        int limite = Math.min(5, despesas.size());
        for (int i = 0; i < limite; i++) {
            Object obj = despesas.get(i);
            if (obj instanceof Despesa despesa) {
                ultimasDespesas.add(despesa);
            }
        }

        // Atributos do modelo exigidos pelo escopo do Dashboard (index.html)
        model.addAttribute("nomeResidencia", "República Pindura");
        model.addAttribute("totalMoradores", totalMoradores);
        model.addAttribute("totalDespesas", totalDespesas);
        model.addAttribute("valorTotal", valorTotal);
        model.addAttribute("ultimasDespesas", ultimasDespesas);

        return "index";
    }
}