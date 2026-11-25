package Projeto.Pindura;

import Projeto.Pindura.Model.Despesa;
import Projeto.Pindura.Model.Morador;
import Projeto.Pindura.Repository.DespesaRepository;
import Projeto.Pindura.Repository.MoradorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class PinduraApplication {

	public static void main(String[] args) {
		SpringApplication.run(PinduraApplication.class, args);
	}

	@Bean
	@Profile("dev")
	public CommandLineRunner seedDemoData(MoradorRepository moradorRepo, DespesaRepository despesaRepo) {
		return args -> {
			if (moradorRepo.count() == 0) {
				Morador gui = moradorRepo.save(new Morador("Guilherme Nunes", "guilherme@pix.com.br"));
				Morador marcos = moradorRepo.save(new Morador("Marcos Cordeiro", "marcos@pix.com.br"));
				Morador joao = moradorRepo.save(new Morador("João Silva", "joao@pix.com.br"));

				despesaRepo.save(new Despesa("Compras do Mês - Mercado", new BigDecimal("360.00"), LocalDate.now().minusDays(3), "Abastecimento da despensa comunitária", gui, List.of(gui, marcos, joao)));
				despesaRepo.save(new Despesa("Internet Fibra 500MB", new BigDecimal("120.00"), LocalDate.now().minusDays(5), "Fatura mensal de internet da república", marcos, List.of(gui, marcos, joao)));
				despesaRepo.save(new Despesa("Botijão de Gás", new BigDecimal("110.00"), LocalDate.now().minusDays(8), "Troca do botijão da cozinha", gui, List.of(gui, marcos)));
			}
		};
	}
}
