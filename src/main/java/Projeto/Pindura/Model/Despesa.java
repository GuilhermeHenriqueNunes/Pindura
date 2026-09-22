package Projeto.Pindura.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "despesas")
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título é obrigatório")
    @Column(nullable = false)
    private String titulo;

    @NotNull(message = "O valor é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
    @Column(nullable = false)
    private BigDecimal valor;

    @NotNull(message = "A data é obrigatória")
    @Column(nullable = false)
    private LocalDate data;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    // Relacionamento: Uma despesa tem 1 Morador como pagador
    @NotNull(message = "O pagador é obrigatório")
    @ManyToOne
    @JoinColumn(name = "pagador_id", nullable = false)
    private Morador pagador;

    // Relacionamento: Uma despesa pode ter vários Moradores como participantes
    @NotEmpty(message = "Selecione ao menos um participante")
    @ManyToMany
    @JoinTable(
            name = "despesa_participantes",
            joinColumns = @JoinColumn(name = "despesa_id"),
            inverseJoinColumns = @JoinColumn(name = "morador_id")
    )
    private List<Morador> participantes = new ArrayList<>();

    public Despesa() {
    }

    public Despesa(String titulo, BigDecimal valor, LocalDate data, String descricao, Morador pagador, List<Morador> participantes) {
        this.titulo = titulo;
        this.valor = valor;
        this.data = data;
        this.descricao = descricao;
        this.pagador = pagador;
        this.participantes = participantes;
    }

    // Método auxiliar para o cálculo da regra de negócio (divisão de valor)
    public BigDecimal getValorPorParticipante() {
        if (participantes == null || participantes.isEmpty() || valor == null) {
            return BigDecimal.ZERO;
        }
        return valor.divide(BigDecimal.valueOf(participantes.size()), 2, java.math.RoundingMode.HALF_UP);
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Morador getPagador() {
        return pagador;
    }

    public void setPagador(Morador pagador) {
        this.pagador = pagador;
    }

    public List<Morador> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Morador> participantes) {
        this.participantes = participantes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Despesa despesa = (Despesa) o;
        return Objects.equals(id, despesa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
