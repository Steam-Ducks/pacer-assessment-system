![Cabeçalho do Manual](/assets/Manual%20do%20Usuario%20-%20cabeçalho.jpeg)

## Índice

- [Introdução](#introdução)
- [Requisitos do Sistema](#requisitos-do-sistema)
- [Download e Preparação](#download-e-preparação)
- [Configuração Inicial](#configuração-inicial)
- [Solução de Problemas](#solução-de-problemas)
- [Suporte e Contato](#suporte-e-contato)

---

### 1. Introdução

**Descrição do Sistema:**  Sistema de Avaliação PACER, uma aplicação desktop que permite que alunos avaliem seus colegas de equipe com base em critérios definidos por um professor.

**Versão do Sistema:** 1.0.0

### 2. Requisitos do Sistema

- **Sistema Operacional:** Windows 10 ou superior
- **Memória RAM:** Mínimo 4 GB
- **Espaço em Disco:** Mínimo 16 KB
- **Java JDK:** Versão 20 ou superior
- **MySQL:** Versão 8.0.0 CE

### 3. Download e Preparação

**Download do Sistema:**  
Acesse o link de download: [Sistema RECAP](https://github.com/Steam-Ducks/pacer-assessment-system/blob/sprint-3/pacerAssessment/Sistema%20RECAP.jar)
Baixe o instalador na pasta desejada.

**Preparação do Ambiente:**  
Verifique se o Java JDK e MySQL estão instalados. Caso contrário, instale seguindo os links abaixo:

- **Java JDK:** [Download JDK 20](https://www.oracle.com/java/technologies/javase/jdk20-archive-downloads.html) (Escolha a opção Windows x64 Installer)
- **MySQL:** [Download MySQL Workbench](https://dev.mysql.com/downloads/workbench/)

### 4. Configuração Inicial

**Configuração do Banco de Dados:**

1. Crie uma base de dados: `sistema_recap`.
2. Execute o script de configuração: `sistema_recap.sql`.

3. Com o banco criado, abra o arquivo executável do Sistema RECAP e acesse com os dados abaixo:

   - **Usuário:** professor@email.com
   - **Senha:** senha123

### 5. Solução de Problemas

- **Erro ao Conectar ao Banco de Dados:** Verifique se o serviço MySQL está em execução nos ‘Serviços’ do Windows, e se as credenciais estão corretas.

- **Java não Encontrado:** Confirme que o Java JDK está instalado e o `JAVA_HOME` está configurado com o caminho correto para o arquivo instalado.

- **Sistema Não Abre:** Certifique-se de que todos os requisitos do sistema foram atendidos. Se mesmo assim os problemas persistirem, entre em contato com o suporte.

### 6. Suporte e Contato

- **Equipe de Suporte:** Steam Ducks
- **E-mail:** steamduckss@gmail.com
