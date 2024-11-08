![Cabeçalho do Manual](/assets/Manual%20do%20Usuario%20-%20cabeçalho.jpeg)

## Índice

[1. Introdução](#introdução) <br />
[2. Requisitos do Sistema](#requisitos-do-sistema) <br />
[3. Download e Preparação](#download-e-preparação) <br />
[4. Configuração Inicial](#configuração-inicial) <br />
[5. Solução de Problemas](#solução-de-problemas) <br />
[6. Suporte e Contato](#suporte-e-contato) <br />

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

1. **Download do Sistema:**  
   - Acesse o link de download: [Sistema RECAP](https://github.com/Steam-Ducks/pacer-assessment-system/blob/sprint-3/pacerAssessment/Sistema%20RECAP.jar)
   - Baixe o instalador na pasta desejada.


2. **Preparação do Ambiente:** 
   - Verifique se o Java JDK e MySQL estão instalados. 
   - Caso contrário, instale seguindo os links abaixo:

     - **Java JDK:** [Download JDK 20](https://www.oracle.com/java/technologies/javase/jdk20-archive-downloads.html) (Escolha a opção Windows x64 Installer)
     - **MySQL:** [Download MySQL Workbench](https://dev.mysql.com/downloads/workbench/)
     - **Baixe o arquivo executável:** [clique aqui](https://github.com/Steam-Ducks/pacer-assessment-system/blob/sprint-3/pacerAssessment/Sistema%20RECAP.jar) e siga o exemplo da imagem abaixo):


   <div align="center">
        <img src="/assets/exemplo-download.PNG" alt="exemplo-download" width="500">
    </div>
  
### 4. Configuração Inicial

   1. **Configuração do Banco de Dados:**

      - Crie uma base de dados: `sistema_recap`.
      - Execute o script de configuração: `sistema_recap.sql`.

   2. Com o banco criado, abra o arquivo executável do Sistema RECAP e acesse com os dados abaixo:
      - **Usuário:** professor@email.com
      - **Senha:** senha123

### 5. Solução de Problemas

- **Erro ao Conectar ao Banco de Dados:** 
  - Verifique se o serviço MySQL está em execução nos ‘Serviços’ do Windows, e se as credenciais estão corretas.

- **Java não Encontrado:** 
  - Confirme que o Java JDK está instalado e o `JAVA_HOME` está configurado com o caminho correto para o arquivo instalado.

- **Sistema Não Abre:** 
  - Certifique-se de que todos os requisitos do sistema foram atendidos. Se mesmo assim os problemas persistirem, entre em contato com o suporte.

### 6. Suporte e Contato

- **Equipe de Suporte:** Steam Ducks
- **E-mail:** steamduckss@gmail.com
