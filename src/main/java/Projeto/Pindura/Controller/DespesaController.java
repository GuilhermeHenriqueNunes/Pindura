package Projeto.Pindura.Controller;

import Projeto.Pindura.Model.Despesa;
import Projeto.Pindura.Service.DespesaService;
import Projeto.Pindura.Service.MoradorService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/despesas")
public class DespesaController {

    private final DespesaService despesaService;
    private final MoradorService moradorService;

    public DespesaController(DespesaService despesaService, MoradorService moradorService) {
        this.despesaService = despesaService;
        this.moradorService = moradorService;
    }

    // Listagem, com pesquisa por titulo e ordenacao (?pesquisa=&ordenarPor=titulo|valor|data&direcao=asc|desc)
    @GetMapping
    public String listar(@RequestParam(required = false) String pesquisa,
                          @RequestParam(defaultValue = "titulo") String ordenarPor,
                          @RequestParam(defaultValue = "asc") String direcao,
                          Model model) {
        model.addAttribute("despesas", despesaService.listar(pesquisa, ordenarPor, direcao));
        model.addAttribute("pesquisa", pesquisa);
        model.addAttribute("ordenarPor", ordenarPor);
        model.addAttribute("direcao", direcao);
        return "despesas/lista";
    }

    // Formulario de cadastro
    @GetMapping("/novo")
    public String novoFormulario(Model model) {
        model.addAttribute("despesa", new Despesa());
        model.addAttribute("moradores", moradorService.listar(null, "asc"));
        return "despesas/formulario";
    }

    // Formulario de edicao, ja preenchido
    @GetMapping("/{id}/editar")
    public String editarFormulario(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("despesa", despesaService.buscarPorId(id));
            model.addAttribute("moradores", moradorService.listar(null, "asc"));
            return "despesas/formulario";
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/despesas";
        }
    }

    // Visualizacao com o calculo da divisao (regra de negocio da Fase 4)
    @GetMapping("/{id}")
    public String visualizar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Despesa despesa = despesaService.buscarPorId(id);
            model.addAttribute("despesa", despesa);
            model.addAttribute("saldos", despesaService.calcularSaldos(despesa));
            return "despesas/detalhes";
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/despesas";
        }
    }

    // Salva cadastro novo ou edicao (o id, se vier preenchido no form, define qual dos dois).
    // pagadorId/participantesIds vem do form como IDs; quem resolve para Morador de verdade
    // e valida obrigatoriedade e o DespesaService.
    @PostMapping
    public String salvar(@ModelAttribute("despesa") Despesa despesa,
                          @RequestParam(required = false) Long pagadorId,
                          @RequestParam(required = false) List<Long> participantesIds,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        try {
            boolean novoCadastro = despesa.getId() == null;
            despesaService.salvar(despesa, pagadorId, participantesIds);

            redirectAttributes.addFlashAttribute("mensagemSucesso",
                    novoCadastro ? "Despesa cadastrada com sucesso!" : "Despesa atualizada com sucesso!");
            return "redirect:/despesas";
        } catch (ConstraintViolationException | IllegalArgumentException | NoSuchElementException e) {
            model.addAttribute("erro", mensagemAmigavel(e));
            model.addAttribute("despesa", despesa);
            model.addAttribute("moradores", moradorService.listar(null, "asc"));
            return "despesas/formulario";
        }
    }

    // Exclusao
    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            despesaService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Despesa excluída com sucesso!");
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
        }
        return "redirect:/despesas";
    }

    // Converte erros de validacao (inclusive os automaticos do Hibernate ao salvar,
    // como titulo/valor/data obrigatorios) em uma mensagem legivel para o Bootstrap Alert
    private String mensagemAmigavel(Exception e) {
        if (e instanceof ConstraintViolationException cve) {
            return cve.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("; "));
        }
        return e.getMessage();
    }
}
