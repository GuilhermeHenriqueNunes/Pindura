<div align="center">

# 🐒 Pindura — Smart Shared Expense Management

<p align="center">
  <strong>Full-stack web application designed for transparent expense splitting and balance settlement in shared student housings and co-living spaces.</strong>
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
[![Status](https://img.shields.io/badge/Status-Completed-success?style=for-the-badge)]()

</div>

---

## 📌 Executive Summary & Business Problem

Living in shared housings and student accommodations frequently leads to interpersonal friction around bill tracking, lost receipts, and awkward debt reminders between housemates.

**Pindura** was architected to eliminate financial ambiguity: a centralized, high-usability web platform that tracks residents, registers utility bills and shared expenses, automates mathematical debt division, and provides instant Pix payment key lookups for seamless debt settlement.

---

## 📸 Visual Showcase

Crafted with an original visual identity featuring warm orange tones and a custom **Golden Lion Tamarin** mascot, offering an intuitive and responsive user experience.

| Main Dashboard | Expense & Settlement Management |
|:---:|:---:|
| ![Dashboard](docs/screenshots/dashboard.png) | ![Expenses](docs/screenshots/despesas.png) |

| Resident Directory | Resident Registration Form |
|:---:|:---:|
| ![Residents](docs/screenshots/moradores.png) | ![Add Resident](docs/screenshots/morador-form.png) |

---

## 💡 Core Capabilities

- **Real-time Analytical Dashboard**: Aggregated KPIs displaying total occupants, registered expenses count, and cumulative financial metrics.
- **Fair Share Calculation Engine**: Automated mathematical split computation isolated in the service layer using strict decimal precision (`RoundingMode.HALF_UP`).
- **Debt Settlement Matrix**: Automatic exclusion of the payer's own quota and itemized calculation of owed amounts per participant, showing the creditor's Pix key.
- **Enterprise CRUD Operations**: Robust persistence layer with bidirectional entity mapping, defensive validation at both UI and domain layers.
- **Dynamic Search & Multi-criteria Sorting**: Instant filtering by resident name or bill title with ASC/DESC ordering on names, values, or dates.
- **Dual Database Strategy**:
  - **MySQL 8.x** for production-grade ACID relational persistence.
  - **In-memory H2** equipped with a `dev` profile for instant evaluation with pre-seeded demo records without external prerequisites.

---

## 🏗️ Architecture & Software Engineering

Developed under classic enterprise Java principles, adhering to a strict **Layered Architecture** with Separation of Concerns (SoC) and clean domain boundaries:

```mermaid
flowchart TD
    subgraph Client["Web Browser / Client Tier"]
        UI["Thymeleaf UI + Bootstrap 5"]
    end

    subgraph Presentation["Presentation Tier (Controllers)"]
        RC["ResidenciaController\n(Dashboard & Metrics)"]
        MC["MoradorController\n(Resident Endpoints)"]
        DC["DespesaController\n(Expense Endpoints)"]
    end

    subgraph Business["Business Tier (Services)"]
        MS["MoradorService\n(Validation & Rules)"]
        DS["DespesaService\n(Split Math & Settlement)"]
    end

    subgraph Persistence["Persistence Tier (JPA Repositories)"]
        MR["MoradorRepository"]
        DR["DespesaRepository"]
    end

    subgraph Database["Database Tier"]
        DB[("MySQL 8.x / In-Memory H2")]
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

---

## 🛠️ Tech Stack & Tooling

| Technology | Purpose |
|---|---|
| **Java 21 LTS** | Core backend language with strong typing and modern performance. |
| **Spring Boot 3.3.4** | Application framework (Spring Web, Spring Data JPA, DevTools). |
| **Thymeleaf** | Modern server-side template engine. |
| **Bootstrap 5 & Icons** | Responsive UI styling with custom design tokens. |
| **Hibernate / JPA** | ORM for clean entity lifecycle and relational mapping. |
| **Jakarta Validation** | Declarative data integrity annotations (`@NotBlank`, `@DecimalMin`, `@NotEmpty`). |
| **MySQL 8.x / H2** | Relational transactional databases. |
| **Maven Wrapper** | Deterministic dependency build and lifecycle management. |

---

## 🚀 Quickstart Guide

### Prerequisites
- **Java JDK 21+** configured in environment `PATH`.
- Git.

### Option 1: Instant Evaluation (Zero Setup — In-Memory H2)

Run the application locally in under 60 seconds with no database configuration needed:

```bash
# 1. Clone the repository
git clone https://github.com/GuilhermeHenriqueNunes/Pindura.git
cd Pindura

# 2. Run with the Maven Wrapper using the 'dev' profile (Windows)
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev

# Linux / macOS:
# ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Open your browser at: **`http://localhost:8080`**  
*(The `dev` profile automatically seeds sample residents and shared bills).*

---

### Option 2: Running with MySQL

1. Create the database:
```sql
CREATE DATABASE pindura_db;
```

2. Pass connection properties through environment variables:
```bash
# Windows PowerShell
$env:DB_URL="jdbc:mysql://localhost:3306/pindura_db"
$env:DB_USERNAME="your_user"
$env:DB_PASSWORD="your_password"
.\mvnw.cmd spring-boot:run
```

---

## 🧪 Testing & Verification

Run the automated test suite and Maven build checks:

```bash
.\mvnw.cmd test
```

---

## 👥 Authors & Collaborators

Crafted in collaboration by:

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
    <td align="center">
      <a href="https://github.com/cordeiromarcos">
        <img src="https://github.com/cordeiromarcos.png" width="100px;" alt="Marcos Cordeiro"/><br />
        <sub><b>Marcos Cordeiro</b></sub>
      </a><br />
      <a href="https://github.com/cordeiromarcos" title="GitHub">
        <img src="https://img.shields.io/badge/GitHub-181717?style=flat&logo=github&logoColor=white" />
      </a>
    </td>
  </tr>
</table>

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).
