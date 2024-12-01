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

1. **Download do Sistema:**  
   - Acesse o link de download: [Sistema RECAP](https://github.com/Steam-Ducks/pacer-assessment-system/blob/sprint-3/pacerAssessment/Sistema%20RECAP.jar)
   - Baixe o instalador na pasta desejada.
        <div align="center">
        <img src="/assets/exemplo-download.PNG" alt="exemplo-download" width="500">
    </div>
  


2. **Preparação do Ambiente:** 
   - Verifique se o Java JDK e MySQL estão instalados. 
   - Caso contrário, instale seguindo os links abaixo:

     - **Java JDK:** [Download JDK 20](https://www.oracle.com/java/technologies/javase/jdk20-archive-downloads.html) (Escolha a opção Windows x64 Installer)
     - **MySQL:** [Download MySQL Workbench](https://dev.mysql.com/downloads/workbench/)
     - **Baixe o arquivo para o banco de dados:** [clique aqui](https://github.com/Steam-Ducks/pacer-assessment-system/blob/sprint-3/pacerAssessment/src/SQLDatabase/sistema_recap_DB.exe) e siga o exemplo da imagem abaixo:
       
        <div align="center">
        <img src="/assets/dowload-database.png" alt="exemplo-download" width="500">
        </div>
       
        • Após fazer o download, tente acessar o arquivo. Caso o seu Windows apareça uma mensagem como a da imagem abaixo, siga os passos a seguir:
        <div align="center">
        <img src="/assets/aviso1-download.png" alt="exemplo-download" width="500">
        </div>
       
       <div align="center">
        <img src="/assets/aviso2-download.png" alt="exemplo-download" width="500">
        </div>

### 4. Configuração Inicial

   1. **Configuração do Banco de Dados:**

      - Abra o arquivo **sistema_recap_DB** que você instalou, insira a senha de segurança *ducks* para estrair o arquivo sistema_recap_DB.sql, e abra ele no MySQL Workbench.

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

  <div align="center">
    <img src="../../assets/footer.png" alt="footer" width="100%" />
   </div>
