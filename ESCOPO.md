# Pindura — Escopo e Plano do Projeto

## 1. Objetivo

O **Pindura** será um site simples para gerenciamento de despesas domésticas compartilhadas, pensado principalmente para uma residência com moradores, como uma república estudantil.

O sistema terá uma única residência e não terá login, cadastro de usuários ou integrações externas. Quem tiver acesso ao site poderá visualizar e gerenciar os moradores e as despesas.

O foco do projeto é demonstrar um CRUD completo, relacionamento entre entidades, persistência em banco de dados, pesquisa, ordenação, validação e organização em camadas utilizando **Java + Spring Boot + Thymeleaf + Bootstrap + MySQL**.

---

## 2. Entidades do sistema

### 👥 Morador

Cada morador terá:

- `id` — gerado automaticamente
- `nome` — obrigatório
- `chavePix` — opcional

### Operações

- [ ] Cadastrar morador
- [ ] Listar moradores
- [ ] Visualizar dados
- [ ] Editar morador
- [ ] Excluir morador
- [ ] Pesquisar por nome
- [ ] Ordenar por nome
- [ ] Ordenar em ordem crescente e decrescente
- [ ] Validar campos obrigatórios

---

### 💰 Despesa

Cada despesa terá:

- `id` — gerado automaticamente
- `titulo` — obrigatório
- `valor` — obrigatório
- `data` — obrigatória
- `pagador` — obrigatório
- `participantes` — pelo menos um participante
- `descricao` — opcional

### Relacionamentos

Uma despesa possui:

- **1 pagador**
- **1 ou vários participantes**

Um morador pode:

- pagar várias despesas;
- participar de várias despesas.

O relacionamento entre participantes deverá ser representado corretamente no banco utilizando JPA/Hibernate.

---

## 3. Telas principais

### 🏠 Página inicial / Dashboard

Ao entrar no sistema:

- mostrar o nome da residência;
- quantidade de moradores;
- quantidade de despesas;
- resumo simples das despesas;
- acesso para moradores;
- acesso para despesas.

**Não criar sistema de login.**

---

### 👥 Página de moradores

Tabela contendo os principais dados dos moradores e ações disponíveis.

Deve possuir:

- botão **Adicionar morador**;
- campo de pesquisa;
- ordenação;
- edição;
- exclusão;
- mensagens de sucesso/erro.

---

### 💰 Página de despesas

Tabela contendo os principais dados das despesas, como título, valor, pagador e data.

Deve possuir:

- botão **Nova despesa**;
- pesquisa;
- ordenação;
- edição;
- exclusão;
- mensagens de sucesso/erro.

---

### ➕ Cadastro/edição de despesa

O formulário deverá conter:

- Título
- Valor
- Data
- Pagador, selecionado entre os moradores cadastrados
- Participantes, selecionados entre os moradores cadastrados
- Descrição opcional

Ao editar, o mesmo formulário deverá ser utilizado, preenchido com os dados existentes.

---

## 4. Regra principal da aplicação

A principal regra de negócio será a divisão da despesa entre os participantes.

Exemplo:

**Despesa:** Pizza  
**Valor:** R$ 120,00  
**Pagador:** Marcos  
**Participantes:** Guilherme, Marcos e João

O sistema deverá considerar:

**R$ 120 ÷ 3 = R$ 40 por participante**

Assim, cada participante possui uma parcela de R$ 40,00. Como Marcos pagou R$ 120,00, sua própria parcela é R$ 40,00 e os outros participantes devem R$ 40,00 cada.

A chave Pix cadastrada pelo morador poderá ser apresentada como informação auxiliar para facilitar o acerto.

**Não haverá pagamento real, integração com Pix ou integração bancária.**

---

## 5. Requisitos do professor → implementação

| Requisito | Como será atendido |
|---|---|
| Cadastro de registros | Formulários de Morador e Despesa |
| Listagem de registros | Tabelas de Moradores e Despesas |
| Edição/Atualização | Botão Editar + formulário preenchido |
| Exclusão | Botão Excluir por registro |
| Pesquisa | Nome do morador / título da despesa |
| Ordenação | Nome, valor ou data, em ordem crescente/decrescente |
| Tratamento de erros | Bootstrap Alerts + Thymeleaf |
| Mensagens de sucesso | Bootstrap Alerts + Thymeleaf |
| Back-end | Java + Spring Boot |
| Front-end | Thymeleaf + Bootstrap |
| Persistência | JPA/Hibernate + MySQL |
| Controle de versão | Git + GitHub |
| Organização em camadas | Model → Repository → Service → Controller → View |

---

## 6. Estrutura prevista do projeto

```text
src/main/java/
└── projeto/
    ├── model/
    │   ├── Morador.java
    │   └── Despesa.java
    │
    ├── repository/
    │   ├── MoradorRepository.java
    │   └── DespesaRepository.java
    │
    ├── service/
    │   ├── MoradorService.java
    │   └── DespesaService.java
    │
    └── controller/
        ├── MoradorController.java
        └── DespesaController.java

src/main/resources/
├── templates/
│   ├── index.html
│   ├── moradores/
│   └── despesas/
│
└── static/
    └── css/
```

---

## 7. Ordem de desenvolvimento

### Fase 1 — Fundação

- [ ] Criar projeto Spring Boot
- [ ] Configurar Maven
- [ ] Configurar MySQL
- [ ] Configurar JPA/Hibernate
- [ ] Configurar Thymeleaf
- [ ] Configurar Bootstrap
- [ ] Organizar Git/GitHub

### Fase 2 — Moradores

- [ ] Criar Model
- [ ] Criar Repository
- [ ] Criar Service
- [ ] Criar Controller
- [ ] Criar cadastro
- [ ] Criar listagem
- [ ] Criar edição
- [ ] Criar exclusão
- [ ] Criar pesquisa
- [ ] Criar ordenação
- [ ] Criar validações
- [ ] Criar mensagens de sucesso/erro

### Fase 3 — Despesas

- [ ] Criar Model
- [ ] Criar relacionamentos com Morador
- [ ] Criar Repository
- [ ] Criar Service
- [ ] Criar Controller
- [ ] Criar cadastro
- [ ] Criar listagem
- [ ] Criar edição
- [ ] Criar exclusão
- [ ] Criar pesquisa
- [ ] Criar ordenação
- [ ] Criar validações
- [ ] Criar mensagens de sucesso/erro

### Fase 4 — Regra de negócio

- [ ] Selecionar pagador
- [ ] Selecionar participantes
- [ ] Calcular valor individual
- [ ] Identificar quem pagou
- [ ] Calcular saldo da despesa
- [ ] Exibir chave Pix quando necessário

### Fase 5 — Finalização

- [ ] Dashboard
- [ ] Melhorar Bootstrap/CSS
- [ ] Testar todos os CRUDs
- [ ] Testar validações e erros
- [ ] Testar pesquisa
- [ ] Testar ordenação
- [ ] Testar relacionamentos
- [ ] Revisar banco de dados
- [ ] Organizar GitHub
- [ ] Criar README
- [ ] Fazer teste final completo

---

## 8. O que NÃO faz parte do projeto

Para evitar aumento desnecessário de escopo:

- ❌ Login/autenticação
- ❌ Cadastro de usuários
- ❌ Múltiplas residências
- ❌ Integração real com Pix
- ❌ Pagamentos
- ❌ Integração bancária
- ❌ IA
- ❌ Aplicativo mobile
- ❌ React
- ❌ Sistema de notificações
- ❌ E-mail
- ❌ Chat
- ❌ Funcionalidades complexas que não contribuam para os requisitos da disciplina

**Regra do projeto:** primeiro fazer tudo que está neste documento funcionar. Qualquer ideia nova só entra depois que o MVP estiver completo.

---

## 9. Definição de MVP pronto

O MVP será considerado funcional quando for possível realizar o seguinte fluxo **sem intervenção manual no banco de dados**:

> **Cadastrar moradores → cadastrar uma despesa → selecionar pagador → selecionar participantes → salvar → visualizar na tabela → pesquisar → ordenar → editar → excluir → receber mensagens de sucesso/erro.**

Depois que esse fluxo estiver funcionando, o cálculo da divisão das despesas e os refinamentos visuais poderão ser implementados.

---

## 10. Regra de prioridade

A prioridade durante o desenvolvimento será:

1. **Funcionalidade**
2. **Atendimento aos requisitos do professor**
3. **Organização do código**
4. **Validação e tratamento de erros**
5. **Interface**
6. **Funcionalidades extras**

Não adicionar funcionalidades extras enquanto os requisitos principais ainda não estiverem funcionando.
