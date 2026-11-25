<div align="center">

# 🐒 Pindura — Gestão Inteligente de Despesas Compartilhadas

<p align="center">
  <strong>Solução web full-stack para rateio transparente de despesas em repúblicas e moradias compartilhadas.</strong>
</p>

<p align="center">
  <a href="README.md">🇧🇷 Português</a> •
  <a href="README.en.md">🇺🇸 English</a>
</p>

[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.4-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)](https://www.thymeleaf.org/)
[![Bootstrap](https://img.shields.io/badge/Bootstrap-5-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white)](https://getbootstrap.com/)
[![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![H2 Database](https://img.shields.io/badge/H2_Database-Dev_Ready-003545?style=for-the-badge)](https://www.h2database.com/)
[![Status](https://img.shields.io/badge/Status-Concluído-success?style=for-the-badge)]()

</div>

---

## 📌 Visão Geral do Projeto

Viver em moradias compartilhadas e repúblicas estudantis frequentemente resulta em atrito na prestação de contas, desorganização em notas fiscais e cobranças desconfortáveis entre amigos. 

O **Pindura** foi projetado para eliminar essa complexidade: uma plataforma centralizada e de alta usabilidade que gerencia moradores, registra despesas com seus respectivos pagadores e participantes, calcula de forma automatizada o rateio proporcional das contas e exibe a chave Pix para quitação instantânea dos saldos devedores.

---

## 📸 Demonstração Visual da Interface

A aplicação foi desenhada com identidade visual autoral em tons de laranja aconchegante e mascote do **mico-leão-dourado**, oferecendo experiência intuitiva e responsiva.

| Painel Principal (Dashboard) | Gestão de Despesas e Rateios |
|:---:|:---:|
| ![Dashboard](docs/screenshots/dashboard.png) | ![Despesas](docs/screenshots/despesas.png) |

| Gestão de Moradores | Formulário de Morador |
|:---:|:---:|
| ![Moradores](docs/screenshots/moradores.png) | ![Cadastro Morador](docs/screenshots/morador-form.png) |

---

## 💡 Principais Funcionalidades

- **Dashboard Resumo**: Métricas consolidadas em tempo real com total de moradores, quantidade de despesas cadastradas e saldo financeiro global.
- **Divisão Matemática de Contas**: Algoritmo no Service Layer que calcula a cota individual de cada participante com precisão decimal (`RoundingMode.HALF_UP`).
- **Resolução de Saldos (Quem deve a Quem)**: Isolamento do pagador e cálculo exato de débitos para cada participante, associando a chave Pix do credor.
- **CRUD Completo & Robusto**: Criação, leitura, atualização e remoção de moradores e despesas com validação defensiva em dois níveis (Front-end e Back-end).
- **Filtros Dinâmicos e Ordenação**: Busca por nome ou título e ordenação ascendente/descendente (por nome, valor ou data).
- **Duplo Suporte a Banco de Dados**:
  - **MySQL** para ambiente de homologação/produção com persistência relacional.
  - **H2 in-memory** integrado com profile `dev` para inicialização imediata e testes sem dependências externas.

---

## 🏗️ Arquitetura e Engenharia de Software

O projeto adota os padrões consolidados da engenharia corporativa Java, seguindo a arquitetura em camadas (**Layered Architecture**) com estrita separação de responsabilidades (SoC) e princípios SOLID:

```mermaid
flowchart TD
    subgraph Client["Navegador Web / Cliente"]
        UI["Interface Thymeleaf + Bootstrap 5"]
    end

    subgraph Presentation["Camada de Apresentação (Controllers)"]
        RC["ResidenciaController\n(Dashboard & Métricas)"]
        MC["MoradorController\n(Rotas /moradores)"]
        DC["DespesaController\n(Rotas /despesas)"]
    end

    subgraph Business["Camada de Negócio (Services)"]
        MS["MoradorService\n(Regras & Validação)"]
        DS["DespesaService\n(Cálculo de Rateio e Saldos)"]
    end

    subgraph Persistence["Camada de Dados (JPA Repositories)"]
        MR["MoradorRepository"]
        DR["DespesaRepository"]
    end

    subgraph Database["Banco de Dados"]
        DB[("MySQL 8.x / H2 in-memory")]
    end

    UI <--> |HTTP GET / POST| Presentation
    RC --> MS
    RC --> DS
    MC --> MS
    DC --> DS
    DC --> MS
    MS --> MR
    DS --> DR
    DS --> MR
    MR <--> |Spring Data JPA / Hibernate| DB
    DR <--> |Spring Data JPA / Hibernate| DB
```

### 🧩 Modelo Entidade-Relacionamento

- **Morador (1) — (N) Despesa (Pagador)**: Uma despesa possui exatamente 1 morador responsável pelo pagamento.
- **Morador (N) — (M) Despesa (Participantes)**: Uma despesa é rateada entre múltiplos participantes através da tabela associativa `despesa_participantes`.

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Finalidade no Ecossistema |
|---|---|
| **Java 21 LTS** | Linguagem robusta com tipagem estática e alta performance. |
| **Spring Boot 3.3.4** | Framework base (Spring Web, Spring Data JPA, DevTools). |
| **Thymeleaf** | Motor de templates para renderização server-side moderna. |
| **Bootstrap 5 & Icons** | Framework visual responsivo com CSS customizado. |
| **Hibernate / JPA** | ORM para mapeamento relacional de objetos. |
| **Jakarta Validation** | Validações declarativas (`@NotBlank`, `@DecimalMin`, `@NotEmpty`). |
| **MySQL 8.x / H2** | Armazenamento de dados transacional e flexível. |
| **Maven** | Gerenciamento de ciclo de vida e dependências. |

---

## 🚀 Como Executar Localmente

### Pré-requisitos
- **Java JDK 21+** instalado e configurado no `PATH`.
- Git instalado.

### Opção 1: Execução Imediata (Zero Configuração — Banco H2 em Memória)

Para testar e avaliar a aplicação em menos de 1 minuto sem precisar de MySQL instalado:

```bash
# 1. Clone o repositório
git clone https://github.com/GuilhermeHenriqueNunes/Pindura.git
cd Pindura

# 2. Execute via Maven Wrapper com o profile 'dev' (Windows)
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev

# No Linux / macOS:
# ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Acesse: **`http://localhost:8080`**  
*(O profile `dev` já carrega dados de exemplo automaticamente para você testar todas as telas).*

---

### Opção 2: Execução com MySQL

1. Crie o banco de dados no MySQL:
```sql
CREATE DATABASE pindura_db;
```

2. Configure as variáveis de ambiente (ou execute diretamente com os parâmetros):
```bash
# Windows PowerShell
$env:DB_URL="jdbc:mysql://localhost:3306/pindura_db"
$env:DB_USERNAME="seu_usuario"
$env:DB_PASSWORD="sua_senha"
.\mvnw.cmd spring-boot:run
```

---

## 🧪 Estratégia de Testes

Para executar o pipeline de compilação e verificação de testes automatizados:

```bash
.\mvnw.cmd test
```

---

## 👨‍💻 Desenvolvedor

<table align="center">
  <tr>
    <td align="center">
      <a href="https://github.com/GuilhermeHenriqueNunes">
        <img src="https://github.com/GuilhermeHenriqueNunes.png" width="100px;" alt="Guilherme Nunes"/><br />
        <sub><b>Guilherme Nunes</b></sub>
      </a><br />
      <a href="https://www.linkedin.com/in/guilherme-henriquesilva-nunes/" title="LinkedIn">
        <img src="https://img.shields.io/badge/LinkedIn-0077B5?style=flat&logo=linkedin&logoColor=white" />
      </a>
      <a href="https://github.com/GuilhermeHenriqueNunes" title="GitHub">
        <img src="https://img.shields.io/badge/GitHub-181717?style=flat&logo=github&logoColor=white" />
      </a>
    </td>
  </tr>
</table>

---

## 📄 Licença

Este projeto está sob a licença [MIT](LICENSE).
