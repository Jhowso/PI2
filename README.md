# 🎭 **Sistema de Gestão de Ingressos para Teatro**

## 📄 **Descrição do Projeto**
Este é um sistema de gestão de ingressos para teatro desenvolvido como parte de um projeto acadêmico, com foco em aplicar conceitos de Engenharia de Software e Programação Orientada a Objetos (POO). O sistema foi desenvolvido em **Java Desktop**, utilizando **Swing** e **JavaFX** para a interface gráfica e armazenamento de dados em arquivos de texto (.txt).

O software tem como objetivo principal gerenciar o cadastro de usuários, compra e impressão de ingressos, além de gerar estatísticas de vendas e ocupação.

---

## 🚀 **Funcionalidades Principais**
1. **Cadastro de Usuários:**
   - Registro de nome, CPF, endereço e data de nascimento.
   - Validação de CPF e prevenção de duplicidade na base de usuários.

2. **Compra de Ingressos:**
   - Seleção de peças e horários disponíveis.
   - Escolha de áreas específicas do teatro (Plateia, Frisas, Camarotes, etc.).
   - Reserva de assentos e cálculo do valor do ingresso.

3. **Impressão de Ingressos:**
   - Geração de comprovantes com base no CPF do cliente.

4. **Estatísticas de Vendas:**
   - Peças mais e menos lucrativas.
   - Sessões com maior e menor ocupação de poltronas.
   - Lucro médio por peça e por área.

5. **Persistência de Dados:**
   - Armazenamento de usuários e vendas em arquivos de texto.
   - Carregamento de dados automaticamente ao iniciar o sistema.

---

## 🛠️ **Tecnologias Utilizadas**
- **Linguagem de Programação:** Java  
- **Interface Gráfica:** Swing
- **Armazenamento de Dados:** Arquivos de texto (.txt)  
- **Padrão de Projeto:** Programação Orientada a Objetos (POO)  
- **Modelagem:** Diagramas UML (Casos de Uso, Sequência e Domínio)

---

## 📚 **Referências**
- **IEEE 830-1998:** Diretrizes para Especificação de Requisitos de Software.  
- **Livro:** *Código Limpo – Habilidades Práticas do Agile Software* (Robert C. Martin).  
- **Documentação UML:** Modelagem de sistemas com foco em engenharia de software.

---

## 🎓 Criadores

Este projeto foi desenvolvido por:

- **Ryan Doriguetto**  
  Estudante universitário com foco em desenvolvimento de software, programação orientada a objetos e projetos acadêmicos aplicados.  

- **Jarbas Benedito**  
  Estudante universitario e Colaborador no desenvolvimento do sistema, com experiência em lógica de programação, análise de requisitos e documentação.  

Ambos trabalharam de forma colaborativa para entregar uma solução prática e bem documentada.

---

## ⚙️ **Como Executar o Projeto**
### **Pré-requisitos:**
- **Java JDK 8+** instalado no ambiente.  
- Editor de código ou IDE como **Eclipse** ou **IntelliJ IDEA**.  

---

## 🌟 Objetivos Acadêmicos
### Este projeto foi desenvolvido com os seguintes objetivos:

- **Aplicar boas práticas de codificação baseadas no livro Código Limpo.
- **Desenvolver um sistema seguindo o padrão IEEE 830 para especificação de requisitos.
- **Demonstrar domínio em Java Desktop com persistência de dados em arquivos.
- **Criar uma solução escalável e extensível para sistemas de bilhetagem.

---

## 📎 Licença
Este projeto está sob a licença MIT. Consulte o arquivo LICENSE para mais informações.

---

## Class Diagram

```mermaid
classDiagram
    class Erros {
        <<exception>>
        +public Erros(String message)
    }

    class Ingresso {
        - cpfCliente : String
        - nomePeca : String
        - sessao : String
        - horario : String
        - identificacao : String
        - poltrona : int
        - preco : double
        + carregarIngressos() : void
        + buscarPorCPF(cpf : String) : Ingresso
    }

    class MenuCadastro {
        + abrirTelaCadastro() : void
    }

    class MenuCompra {
        + sessoes : String[]
        + nomePecas : String[]
        + nomeHorario : String[]
        + preco : double[]
        + abrirTelaCompra(cpf : String) : void
        + reservarAssento(peca : int, horario : int, sessao : int, linha : int, coluna : int, identificacao : String) : void
    }

    class MenuEstatistica {
        + abrirTelaEstatisticas() : void
        + calcularLucroMedioPorPeca() : double
        + determinarIngressosVendidos() : int
    }

    class MenuImpressaoIngresso {
        + abrirTelaImpressaoIngresso() : void
        + imprimirIngresso(ingresso : Ingresso) : void
    }

    class MenuPrincipal {
        + exibirMenuPrincipal() : void
    }

    class Usuario {
        - nome : String
        - cpf : String
        - telefone : String
        - endereco : String
        - dataNascimento : String
        + carregarUsuarios() : void
        + cadastrarUsuario(nome : String, cpf : String, telefone : String, endereco : String, dataNascimento : String) : void
        + validarUsuario(cpf : String) : boolean
    }

    %% Relacionamentos
    Erros --|> Exception : extends
    MenuPrincipal --> MenuCadastro : "Abre tela de cadastro"
    MenuPrincipal --> MenuCompra : "Abre tela de compra"
    MenuPrincipal --> MenuEstatistica : "Abre tela de estatísticas"
    MenuCompra --> Ingresso : "Cria ingressos"
    MenuCompra --> MenuImpressaoIngresso : "Envia dados para impressão"
    MenuCompra --> Usuario : "Valida CPF do usuário"
    MenuEstatistica --> Ingresso : "Calcula dados com base nos ingressos"
    MenuImpressaoIngresso --> Ingresso : "Obtém informações do ingresso"

