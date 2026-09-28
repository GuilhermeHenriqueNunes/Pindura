package Projeto.Pindura.Controller;

import Projeto.Pindura.Model.Morador;
import Projeto.Pindura.Service.MoradorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/moradores")
public class MoradorController {

    private final MoradorService moradorService;

    public MoradorController(MoradorService moradorService) {
        this.moradorService = moradorService;
    }

    // Listagem, com pesquisa por nome e ordenação (?pesquisa=&direcao=asc|desc)
    @GetMapping
    public String listar(@RequestParam(required = false) String pesquisa,
                          @RequestParam(defaultValue = "asc") String direcao,
                          Model model) {
        model.addAttribute("moradores", moradorService.listar(pesquisa, direcao));
        model.addAttribute("pesquisa", pesquisa);
        model.addAttribute("direcao", direcao);
        return "moradores/lista";
    }

    // Formulário de cadastro
    @GetMapping("/novo")
    public String novoFormulario(Model model) {
        model.addAttribute("morador", new Morador());
        return "moradores/formulario";
    }

    // Formulário de edição, já preenchido
    @GetMapping("/{id}/editar")
    public String editarFormulario(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("morador", moradorService.buscarPorId(id));
            return "moradores/formulario";
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/moradores";
        }
    }

    // Salva cadastro novo ou edição (o id, se vier preenchido no form, define qual dos dois)
    @PostMapping
    public String salvar(@Valid @ModelAttribute("morador") Morador morador,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // Mantem os dados digitados e exibe os erros de validacao no proprio formulario
            return "moradores/formulario";
        }

        boolean novoCadastro = morador.getId() == null;
        moradorService.salvar(morador);

        redirectAttributes.addFlashAttribute("mensagemSucesso",
                novoCadastro ? "Morador cadastrado com sucesso!" : "Morador atualizado com sucesso!");
        return "redirect:/moradores";
    }

    // Exclusão
    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            moradorService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Morador excluído com sucesso!");
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
        }
        return "redirect:/moradores";
    }
}
