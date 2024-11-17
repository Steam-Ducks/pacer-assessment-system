<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

 
<div align="center">
<img src="assets/capa-readme.png" alt="capa-readme" width="100%" />
</div>

<br />
<p>Nós da equipe <b>STEAM DUCKS</b> estudantes do 2º idSemestre do curso de Banco de Dados na FATEC SJC, estamos comprometidos com o desenvolvimento do projeto "Sistema de avaliação PACER" neste repositório. O objetivo do projeto é criar uma aplicação desktop simples que permita os usuários alunos avaliarem todos os integrantes de seu grupo nos
critérios definidos pelo usuário administrador. O projeto inclui a modelagem de um banco de dados relacional. Por meio deste projeto, buscamos solidificar nossos conhecimentos em modelagem de dados e desenvolvimento de software.</p>

## Índice

♦ [Requisitos](#requisitos) <br />
♦ [Integrantes do projeto](#integrantes-do-projeto) <br />
♦ [Backlog do Produto](#backlog-do-produto) <br />
♦ [Cronograma do Projeto](#cronograma-do-projeto) <br />
♦ [Tecnologias Utilizadas](#tecnologias-utilizadas) <br />
♦ [Termo de Requisitos de Permanência](#termo-de-requisitos-de-permanência) <br />
♦ [Documentação](/documentation) <br />
    
## Requisitos

### Requisitos Funcionais:
<ol>
<li>Permitir que um aluno avalie todos os membros de sua equipe ao final de cada Sprint;</li>
<li>Permitir que o professor consiga gerar um relatório contendo a nota média por aluno para cada critério de avaliação em uma determinada Sprint;</li>
<li>Permitir que o professor consiga gerar um relatório contendo a nota média de todos os alunos de um grupo para uma determinada Sprint; </li>
<li>Fornecer uma forma de carregar informações referentes aos grupos por meio de arquivo;</li>
<li>Também deve ser possível incluir ou excluir membros em grupos no caso de realocações;</li>
<li>Permitir o gerenciamento dos critérios de avaliação. Deve ser possível incluir novos critérios, além de alterar e desativar critérios existentes. Critérios desativados não devem aparecer durante uma avaliação;</li>
<li>Permitir que o professor cadastre o calendário de Sprints para cada idSemestre. Em vez de solicitar o número da Sprint em uma avaliação, o sistema deve verificar a data atual e automaticamente assumir que ela está associada à Sprint que acabou de finalizar;</li>
<li>Usar autenticação por usuário e senha para garantir que cada aluno possa realizar apenas uma avaliação por Sprint e que apenas o professor tenha acesso aos relatórios.</li>
</ol>

### Requisitos Não Funcionais:
<ol>
<li>Manual do Usuário;</li>
<li>Guia de instalação;</li>
<li>Modelagem de Banco de Dados.</li>
</ol>
   
## Integrantes do projeto
  
| LinkedIn & GitHub | Integrantes | Função | Identificação |
|-------------------|-------------|--------|---------------|
| <a href="https://www.linkedin.com/in/alexander-silva-lima-96a0432a6?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app" target="_blank"><img src="https://img.shields.io/badge/Linkedin-blue?style=flat-square&logo=Linkedin&logoColor=white" alt="Linkedin"></a> <a href="https://github.com/alexttz" target="_blank"><img src="https://img.shields.io/badge/GitHub-111217?style=flat-square&logo=github&logoColor=white" alt="GitHub"></a>   | Alexander Silva | Scrum Master | <a href="" ><img src="assets/Dev team avatars/Avatar_Alexander.png" width="60"></a>|
| <a href="https://www.linkedin.com/in/carlos-daniel-alves-dos-santos-9516952b4?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app" target="_blank"><img src="https://img.shields.io/badge/Linkedin-blue?style=flat-square&logo=Linkedin&logoColor=white" alt="Linkedin"></a> <a href="https://github.com/darloscaniel" target="_blank"><img src="https://img.shields.io/badge/GitHub-111217?style=flat-square&logo=github&logoColor=white" alt="GitHub"></a> | Carlos Daniel | Dev Team | <a href="" ><img src="assets/Dev team avatars/Avatar_Carlos.png" width="60"></a>||
| <a href="https://www.linkedin.com/in/isabelly-sousa?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app" target="_blank"><img src="https://img.shields.io/badge/Linkedin-blue?style=flat-square&logo=Linkedin&logoColor=white" alt="Linkedin"></a> <a href="https://github.com/61isabelly" target="_blank"><img src="https://img.shields.io/badge/GitHub-111217?style=flat-square&logo=github&logoColor=white" alt="GitHub"></a> | Isabelly Sousa | Dev Team | <a href="" ><img src="assets/Dev team avatars/Avatar_Isabelly.png" width="60"></a>|
| <a href="https://www.linkedin.com/in/lucas-h-acosta?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=ios_app" target="_blank"><img src="https://img.shields.io/badge/Linkedin-blue?style=flat-square&logo=Linkedin&logoColor=white" alt="Linkedin"></a> <a href="https://github.com/Lucas-heck-acosta" target="_blank"><img src="https://img.shields.io/badge/GitHub-111217?style=flat-square&logo=github&logoColor=white" alt="GitHub"></a> | Lucas Acosta | Dev Team | <a href="" ><img src="assets/Dev team avatars/Avatar_Lucas.png" width="60"></a>|
| <a href="https://www.linkedin.com/in/luiz-henrique-rabello-ferreira-3600752ba?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app" target="_blank"><img src="https://img.shields.io/badge/Linkedin-blue?style=flat-square&logo=Linkedin&logoColor=white" alt="Linkedin"></a> <a href="https://github.com/LuizHRFerreira" target="_blank"><img src="https://img.shields.io/badge/GitHub-111217?style=flat-square&logo=github&logoColor=white" alt="GitHub"></a> | Luiz Henrique | Product Owner |<img src="assets/Dev team avatars/Avatar_Luiz.png" width="60"></a>|
| <a href="https://www.linkedin.com/in/matheus-gabriel-barbosa-6b4a2320a?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=ios_app" target="_blank"><img src="https://img.shields.io/badge/Linkedin-blue?style=flat-square&logo=Linkedin&logoColor=white" alt="Linkedin"></a> <a href="https://github.com/devmatba" target="_blank"><img src="https://img.shields.io/badge/GitHub-111217?style=flat-square&logo=github&logoColor=white" alt="GitHub"></a> | Matheus Barbosa | Dev Team  | <a href="" ><img src="assets/Dev team avatars/Avatar_Matheus.png" width="60"></a>|
| <a href="https://www.linkedin.com/posts/cruz-rafaella_%C3%A9-um-prazer-compartilhar-que-finalizei-meu-activity-7212665413376081921-mEAy?utm_source=share&utm_medium=member_desktop" target="_blank"><img src="https://img.shields.io/badge/Linkedin-blue?style=flat-square&logo=Linkedin&logoColor=white" alt="Linkedin"></a> <a href="https://github.com/arafaellacruz" target="_blank"><img src="https://img.shields.io/badge/GitHub-111217?style=flat-square&logo=github&logoColor=white" alt="GitHub"></a> | Rafaella Cruz | Dev Team  | <a href="" ><img src="assets/Dev team avatars/Avatar_Rafaella.png" width="60"></a>|
| <a href="https://www.linkedin.com/in/samuel-prado-9142381b6?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=ios_app" target="_blank"><img src="https://img.shields.io/badge/Linkedin-blue?style=flat-square&logo=Linkedin&logoColor=white" alt="Linkedin"></a> <a href="https://github.com/Samuelprado99" target="_blank"><img src="https://img.shields.io/badge/GitHub-111217?style=flat-square&logo=github&logoColor=white" alt="GitHub"></a>  | Samuel Prado | Dev Team  | <a href="" ><img src="assets/Dev team avatars/Avatar_Samuel.png" width="60"></a>|
| <a href="https://www.linkedin.com/in/theo-da-rosa-smidt-838b211b4?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app" target="_blank"><img src="https://img.shields.io/badge/Linkedin-blue?style=flat-square&logo=Linkedin&logoColor=white" alt="Linkedin"></a> <a href="https://github.com/TheodaRosa" target="_blank"><img src="https://img.shields.io/badge/GitHub-111217?style=flat-square&logo=github&logoColor=white" alt="GitHub"></a>   | Theo da Rosa | Dev Team  | <a href="" ><img src="assets/Dev team avatars/Avatar_Theo.png" width="60"></a>|

## Backlog do Produto
| Rank | Prioridade | ID | User Story | Estimativa | Sprint | Requisito do parceiro |
|------|------------|----|------------|------------|--------|-----------------------|
| 1 | ![#ff0000](https://via.placeholder.com/15/ff0000/000000?text=+) ALTA       | PAS - 02  | Como um professor, eu quero criar um grupo e importar um arquivo .csv para salvar as informações dos alunos que fazem parte desse grupo para que eu possa gerenciar os dados dos integrantes de forma rápida e organizada. | 1 | 1 | 4 |
| 2 | ![#ff0000](https://via.placeholder.com/15/ff0000/000000?text=+) ALTA | PAS - 08  | Como um professor, eu quero criar semestres para dividir os grupos e organizar as equipes, de forma a facilitar a gestão e o acompanhamento do progresso dos alunos na API. | 1 | 1 | 5 |
| 3 | ![#ff0000](https://via.placeholder.com/15/ff0000/000000?text=+) ALTA | PAS - 14 | Como um aluno, eu quero dar notas para todos os integrantes da minha equipe para avaliar a contribuição de cada um no desenvolvimento da API | 3 | 1 | 1 |
| 4 | ![#ff0000](https://via.placeholder.com/15/ff0000/000000?text=+) ALTA | PAS - 12 | Como um professor, eu quero estabelecer um limite de pontos para as sprints dos grupos para que os integrantes possam usar esses pontos para avaliar uns aos outros | 3 | 1 | 1 |
| 5 | ![#ff0000](https://via.placeholder.com/15/ff0000/000000?text=+) ALTA | PAS - 16 | omo um aluno ou professor, eu quero acessar o sistema com meu e-mail e senha para que eu possa avaliar os outros integrantes do meu grupo em todos os critérios | 1 | 1 | 8 |
| 6 | ![#ff0000](https://via.placeholder.com/15/ff0000/000000?text=+) ALTA | PAS - 18 | Como um professor, eu quero criar e editar critérios de avaliação para que os alunos possa se avaliar no final de cadas Spint. | 1 | 1 | 6 |
| 7 | ![#ff0000](https://via.placeholder.com/15/ff0000/000000?text=+) ALTA | PAS - 04 | um professor, eu quero poder adicionar, remover ou modificar membros de um grupo de alunos para garantir que cada grupo tenha a composição correta do grupo da API. | 1 | 2 | 7 |
| 8 | ![#ff0000](https://via.placeholder.com/15/ff0000/000000?text=+) ALTA | PAS - 09 | Como um professor, eu quero criar e gerenciar sprints para que os alunos possam avaliar de forma sistemática o desempenho dos integrantes do grupo.| 2 | 2 | 8 |
| 10 | ![#ffff00](https://via.placeholder.com/15/ffff00/000000?text=+)MEDIO | PAS - 22  | Como um professor, eu quero gerar um relatório com a nota média de todos os alunos de um grupo para acompanhar o desempenho geral da turma.| 2 | 2 | 2 |
| 11 | ![#ffff00](https://via.placeholder.com/15/ffff00/000000?text=+)MEDIO | PAS - 24  | Como um professor, eu quero gerar um relatório com a nota média por aluno para cada critério, para que eu possa avaliar o desempenho individual dos alunos em áreas específicas." | 2 | 2 | 3 |
| 9 | ![#ff0000](https://via.placeholder.com/15/ff0000/000000?text=+) ALTA | PAS - 36 | Como um professor, eu quero cadastrar e gerenciar o calendário das sprints para planejar as datas de início e término de cada sprint, garantindo que os alunos possam se organizar e cumprir os prazos estabelecidos. | 5 | 2 | 1 |
| 12 | ![#0000ff](https://via.placeholder.com/15/0000ff/000000?text=+) BAIXO | PAS - 26 | Como um aluno, eu quero visualizar a nota média de todos os integrantes do meu grupo para compreender o desempenho geral e identificar áreas onde podemos melhorar como equipe. | 3 | 3 | 3 |
| 13 | ![#0000ff](https://via.placeholder.com/15/0000ff/000000?text=+) BAIXO | PAS - 28  | Como um aluno, eu quero visualizar minhas notas médias para cada critério de avaliação para entender meu desempenho em áreas específicas e identificar onde posso melhorar. | 3 | 3 | 2 |
| 14 | ![#0000ff](https://via.placeholder.com/15/0000ff/000000?text=+) BAIXO | PAS - 33 | Como um professor, eu quero poder mudar minha senha através do e-mail para que eu possa recuperar o acesso à minha conta de forma segura e conveniente caso eu esqueça minha senha atual. | 4 | 4 | 8 |

## Cronograma do Projeto

| Fase                   | Início         | Entrega        | Status  |
|-----------------------|----------------|-----------------|---------|
| Kick off do projeto   | 28/08/2024     | 08/09/2024      |   ✅   | 
| Sprint 1              | 09/09/2024     | 29/09/2024      |   ✅   | 
| Sprint 2              | 30/09/2024     | 20/10/2024      |   ✅   | 
| Sprint 3              | 21/10/2024     | 10/11/2024      |   🔄   | 
| Sprint 4              | 11/11/2024     | 01/12/2024      |   ❌   | 
| Feira de Soluções     | 12/12/2024     | 12/12/2024      |   ❌   | 

## Tecnologias Utilizadas

<img src="assets/tecnologias.png" alt="tecnologias" width="100%" />

## Termo de Requisitos de Permanência

### Objetivo: 
Garantir a continuidade e o comprometimento dos membros da equipe no projeto essencial para a conclusão do idSemestre.

### 1. Reuniões Fixas
- As reuniões da equipe ocorrerão todas as segundas e quintas-feiras, durante as aulas designadas para a API.
- A presença é obrigatória e as reuniões são essenciais para o progresso do projeto.

### 2. Cursos Obrigatórios
- É obrigatório que todos os membros completem os cursos de Java POO (Programação Orientada a Objetos) e MySQL.
- A conclusão desses cursos é fundamental para a compreensão e execução adequada das tarefas do projeto.

### 3. Limite de Faltas
- Cada membro tem direito a no máximo 3 "strikes" durante o período do projeto.
- "Strikes" são contabilizados por:
    - Faltas não justificadas às reuniões.
    - Falhas no desenvolvimento do programa sem justificativa adequada.
    - Atrasos não comunicados previamente ao grupo.
- A comunicação prévia é necessária para evitar a contagem de strikes em casos de imprevistos.

### 4. Atualização de Cards no Jira
- É obrigatório manter os cards atualizados no Jira com informações relevantes sobre o progresso das tarefas.
- Em caso de dificuldades ou dúvidas sobre o Jira, os membros devem buscar assistência do grupo.

### 5. Comunicação com o Grupo
- Todos os membros devem manter uma comunicação constante e efetiva com o grupo.
- A comunicação pode ser feita por WhatsApp, Slack ou pessoalmente.
- A ausência de comunicação pode impactar negativamente o andamento do projeto.

### 6. Prazos de Entrega
- É crucial respeitar os prazos de entrega estabelecidos para não prejudicar o andamento do projeto e o trabalho da equipe.
- O não cumprimento dos prazos pode resultar em impactos significativos e deve ser evitado a todo custo.

### Observação: 
O projeto é uma parte fundamental para a conclusão do idSemestre, e o comprometimento de cada membro é essencial para o sucesso coletivo. Contamos com a sua colaboração e dedicação!

<div align="center">
    <img src="assets/footer.png" alt="footer" width="100%" />
</div>

</body>
</html>
