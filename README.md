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
<p>Nós da equipe <b>STEAM DUCKS</b> estudantes do 2º semestre do curso de Banco de Dados na FATEC SJC, estamos comprometidos com o desenvolvimento do projeto "Sistema de avaliação PACER" neste repositório. O objetivo do projeto é criar uma aplicação desktop simples que permita os usuários alunos avaliarem todos os integrantes de seu grupo nos
critérios definidos pelo usuário administrador. O projeto inclui a modelagem de um banco de dados relacional. Por meio deste projeto, buscamos solidificar nossos conhecimentos em modelagem de dados e desenvolvimento de software.</p>

## Índice

♦ [Requisitos](#requisitos) <br />
♦ [Integrantes do projeto](#integrantes-do-projeto) <br />
♦ [Backlog do Produto](#backlog-do-produto) <br />
♦ [Validações feitas com o Cliente](#validações-feitas-com-o-cliente) <br />
♦ [Termo de Requisitos de Permanência](Termo-de-Requisitos-de-Permanência) <br />
♦ [Tecnologias Utilizadas](#tecnologias-utilizadas) <br />
♦ [Como Usar](#como-usar) <br />
♦ [Boas práticas](#boas-práticas) <br />
♦ [Contato](#contato) <br />
    
## Requisitos

### Requisitos Funcionais:
<ol>
<li>Permitir que um aluno avalie todos os membros de sua equipe ao final de cada Sprint;</li>
<li>Permitir que o professor consiga gerar um relatório contendo a nota média por aluno para cada critério de avaliação em uma determinada Sprint;</li>
<li>Permitir que o professor consiga gerar um relatório contendo a nota média de todos os alunos de um grupo para uma determinada Sprint; </li>
<li>Fornecer uma forma de carregar informações referentes aos grupos por meio de arquivo;</li>
<li>Também deve ser possível incluir ou excluir membros em grupos no caso de realocações;</li>
<li>Permitir o gerenciamento dos critérios de avaliação. Deve ser possível incluir novos critérios, além de alterar e desativar critérios existentes. Critérios desativados não devem aparecer durante uma avaliação;</li>
<li>Permitir que o professor cadastre o calendário de Sprints para cada semestre. Em vez de solicitar o número da Sprint em uma avaliação, o sistema deve verificar a data atual e automaticamente assumir que ela está associada à Sprint que acabou de finalizar;</li>
<li>Usar autenticação por usuário e senha para garantir que cada aluno possa realizar apenas uma avaliação por Sprint e que apenas o professor tenha acesso aos relatórios.</li>
</ol>

### Requisitos Não Funcionais:
<ol>
<li>Manual do Usuário;</li>
<li>Guia de instalação;</li>
<li>Modelagem de Banco de Dados.</li>
</ol>
   
## Integrantes do projeto
  
<div align="center">
  <img src="assets/integrantes.png" alt="Equipe e funções" width="600px" />
</div>

## Backlog do Produto
<a href="https://docs.google.com/spreadsheets/d/1DYEea0bZCwJRRKwjq7ib3rXRjN1lnyTx6z3y3kyuVuM/edit?gid=1029324717#gid=1029324717" target="_blank">♦ Backlog Dinâmico</a>
| Rank | Prioridade | ID        | User Story                                                                                                                                             | Estimativa | Sprint | Requisito do parceiro |
|------|------------|-----------|--------------------------------------------------------------------------------------------------------------------------------------------------------|------------|--------|-----------------------|
| 1    | ![#ff0000](https://via.placeholder.com/15/ff0000/000000?text=+) ALTA       | PAS - 05  | Como um professor, eu quero criar e vincular alunos a um grupo para que possam se avaliar no final de cada sprint                                      | 1          | 1      | 4                     |
| 2    | ![#ff0000](https://via.placeholder.com/15/ff0000/000000?text=+) ALTA       | PAS - 02  | Como um professor, eu quero importar um arquivo .csv para salvar as informações dos alunos para que ele possa acessar a plataforma e avaliar os integrantes de seu respectivo grupo | 1          | 1      |  2                    |
| 3    | ![#ff0000](https://via.placeholder.com/15/ff0000/000000?text=+) ALTA       | PAS - 08  | Como um professor, eu quero criar semestres para dividir os grupos, definir e organizar as equipes e os critérios que serão avaliados                  | 1          | 1      | 5                     |
| 4    | ![#ff0000](https://via.placeholder.com/15/ff0000/000000?text=+) ALTA       | PAS - 12  | Como um professor, eu quero avaliar as sprints dos grupos, pois isso permite que os integrantes utilizem esses pontos para avaliar todos os integrantes do grupo | 3          | 1      | 1                     |
| 5    | ![#ff0000](https://via.placeholder.com/15/ff0000/000000?text=+) ALTA       | PAS - 14  | Como um aluno, eu quero dar notas para todos os integrantes da minha equipe, pois essa nota será considerada para a avaliação final do semestre        | 3          | 1      | 1                     |
| 6    | ![#ff0000](https://via.placeholder.com/15/ff0000/000000?text=+) ALTA       | PAS - 16  | Como um aluno ou professor, eu quero acessar o sistema com meu e-mail e senha, para garantir que cada aluno possa realizar apenas uma avaliação por Sprint e que apenas o professor tenha acesso ao relatório | 1          | 1      | 8                    |
| 7    | ![#ff0000](https://via.placeholder.com/15/ff0000/000000?text=+) ALTA       | PAS - 18  | Como um professor, eu quero criar e editar critérios de avaliação, pois isso permite definir e ajustar os critérios de forma clara e flexível conforme as necessidades do projeto | 1          | 1      | 6                     |
| 8    | ![#ff0000](https://via.placeholder.com/15/ff0000/000000?text=+) ALTA       | PAS - 36  | Como um professor, eu quero cadastrar o calendário das sprints para que delimite o começo e final de cada sprint                                      | 5          | 2      |  7                    |
| 9    | ![#ff0000](https://via.placeholder.com/15/ff0000/000000?text=+) ALTA       | PAS - 04  | Como um professor, eu quero poder alterar membros de um grupo para que caso haja alguma alteração de membros, eu adeque no sistema                      | 1          | 2      | 8                     |
| 10   | ![#ff0000](https://via.placeholder.com/15/ff0000/000000?text=+) ALTA       | PAS - 09  | Como um professor, eu quero criar sprints para que ao final de cada uma os alunos avaliem todos os integrantes de seu grupo com o total de pontos que o professor colocar | 2          | 2      |  1                    |
| 11   | ![#ffff00](https://via.placeholder.com/15/ffff00/000000?text=+) MEDIO      | PAS - 22  | Como um professor, eu quero gerar um relatório contendo a nota média de todos os alunos de um determinado grupo em uma determinada sprint para identificar o grau de desempenho de cada integrante do grupo | 2          | 2      | 2                     |
| 12   | ![#ffff00](https://via.placeholder.com/15/ffff00/000000?text=+) MEDIO      | PAS - 24  | Como um professor eu quero gerar um relatório contendo a nota média por aluno para cada critério de avaliação em uma determinada sprint para que eu identifique o grau de desempenho do aluno | 2          | 2      | 3                     |
| 13   | ![#0000ff](https://via.placeholder.com/15/0000ff/000000?text=+) BAIXO      | PAS - 26  | Como um aluno, eu quero visualizar a nota média de todos os integrantes do meu grupo em uma determinada sprint para identificar o grau de desempenho do meu grupo pela visão do meu grupo | 3          | 3      | 3                     |
| 14   | ![#0000ff](https://via.placeholder.com/15/0000ff/000000?text=+) BAIXO      | PAS - 28  | Como um aluno, eu quero visualizar as minhas notas médias para cada critério de avaliação em uma determinada sprint para que eu identifique o resultado do meu trabalho pela visão da equipe | 3          | 3      | 2                     |
| 15   | ![#0000ff](https://via.placeholder.com/15/0000ff/000000?text=+) BAIXO      | PAS - 33  | Como um professor, eu quero poder mudar senha pelo e-mail para resgatar o meu acesso                                                                      | 4          | 4      | 8                     |
| 16   | ![#0000ff](https://via.placeholder.com/15/0000ff/000000?text=+) BAIXO      | PAS - 34  | Como um aluno, eu quero poder mudar senha pelo e-mail para resgatar o meu acesso                                                                        | 4          | 4      |   8                   |


## Validações feitas com o Cliente

| Sprint | Validações                                                                                                                                     | Respostas                                                                       |
|--------|------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------|
| 1      | Em relação aos grupos que realizarão as sprints, além dos integrantes e do nome do grupo, há mais alguma informações que deseja vincular a esses grupos? | link do git                                                                    |
| 1      | Seria de seu interesse separar os grupos por turmas? Por exemplo, "Grupos do 2º Semestre de Banco de Dados 2024"                             | sim, preciso separar por semestre/turma                                        |
| 1      | O professor será o único a cadastrar os alunos ou ele pode liberar um acesso para que o próprio aluno faça o seu cadastro?                    | o professor cadastra                                                           |
| 1      | Seria interessante então poder cadastrar vários professores?                                                                                 | A princípio só tem 1                                                           |
| 1      | Em relação ao professor que vai acessar ao sistema, ele vai ter acesso a todos os grupos dos alunos de várias turmas diferentes ou somente os grupos de uma turma específica? | Por semestre ele vai ter acesso a somente uma turma, mas no semestre seguinte a turma será diferente (a não ser que todo mundo reprove) |
| 1      | Em relação aos critérios que o professor vai colocar na avaliação do aluno, há um limite máximo para a quantidade de critérios?                | A princípio não                                                                |
| 1      | Todo mundo que for usar o sistema vai ter que se identificar para entrar e usar o sistema. Essa identificação inicial eu e meu time achamos interessante usar um e-mail e senha. O que você acha? | Pode ser                                                                       |
| 1      | Podemos criar tela para cadastrar os alunos individualmente?  |	Pode, mas a prioridade para isso é baixa pois a forma principal de cadastro deve ser por importação de arquivo
| 1      | Qual informação o professor já deve visualizar ao fazer o login?                                                                             | Ele já deve acessar diretamente em gerar relatórios.                           |
| 1      | Faz sentido manter um botão de lembrar-me na tela de login?                                                                                  | Não porque o sistema vai ser instalado em computadores públicos                  |
| 1      | Podemos elaborar um nome e um ícone para o aplicativo?                                                                                      | Pode, porém tem que validar com o cliente antes                               |
| 1      | Quando for gerar um relatório, quais opções de filtro deseja ter? Por exemplo, as notas médias dos alunos por grupo, por sprint, por turma, por data, etc. | Isso está no doc que passei no começo                                          |
| 1      | Nós já combinamos que é interessante que, ao abrir o sistema, o professor visualize a tela de exportação de relatórios. No caso dos alunos, além da avaliação de sprint, há alguma outra função que eles devem acessar? E qual tela eles devem visualizar logo ao fazer o login? | O aluno só avalia. Fora do período de avaliação pode deixar sem nada ou mostrar o resultado final do grupo (as médias) |

## Termo de Requisitos de Permanência

### Objetivo: 
Garantir a continuidade e o comprometimento dos membros da equipe no projeto essencial para a conclusão do semestre.

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
O projeto é uma parte fundamental para a conclusão do semestre, e o comprometimento de cada membro é essencial para o sucesso coletivo. Contamos com a sua colaboração e dedicação!

## Tecnologias Utilizadas

![Git](https://img.shields.io/badge/Git-333333?style=for-the-badge&logo=git)
![GitHub](https://img.shields.io/badge/GitHub-333333?style=for-the-badge&logo=github)
![Java](https://img.shields.io/badge/Java-333333?style=for-the-badge&logo=java)
![SQL](https://img.shields.io/badge/SQL-333333?style=for-the-badge&logo=sqlite)
![VSCode](https://img.shields.io/badge/VSCode-333333?style=for-the-badge&logo=visualstudiocode)
![Jira](https://img.shields.io/badge/Jira-333333?style=for-the-badge&logo=jira)
![Scrum](https://img.shields.io/badge/Scrum-333333?style=for-the-badge&logo=scrum)
![Slack](https://img.shields.io/badge/Slack-333333?style=for-the-badge&logo=slack)

## Como Usar
<ol>
    <li>Clone o repositório:</li>
</ol>
<pre><code>git clone https://github.com/Steam-Ducks/pacer-assessment-system</code></pre>
<ol start="2">
    <li>Abra o código-fonte no IntelliJ.</li>
    <li>Execute o programa e siga as instruções para utilizar as operações desejadas.</li>
    <li>Após fazer alterações nos arquivos, adicione-os ao controle de versão:</li>
</ol>
<pre><code>git add .</code></pre>
<ol start="5">
    <li>Faça um commit das mudanças com uma mensagem descritiva:</li>
</ol>
<pre><code>git commit -m "Alteração do layout dos critérios de avaliação."</code></pre>
<ol start="6">
    <li>Envie as alterações para o repositório remoto:</li>
</ol>
<pre><code>git push origin main</code></pre>

 ## Boas práticas

 1. Sempre nomeie arquivos, funções ou funcionalidades utilizando camelCase é uma convenção de nomenclatura, onde as palavras são unidas sem espaços e cada palavra subsequente é capitalizada, exceto a primeira, como por exemplo o próprio nome da convenção: camelCase.
 2. Ao dar nome aos arquivos, funções ou ao fazer os commits, faça em inglês para exercitar o idioma e também é amplamente aceito como a língua padrão na indústria de tecnologia e programação. Usar nomes em inglês ajuda a manter a consistência e a interoperabilidade entre diferentes projetos e equipes ao redor do mundo.
 3. Ao realizar os commits utilize o pradrão: emoji + tipo do commit + breve descrição do que foi feito, se tiver dúvidas, dê uma olhada no repositório: 
[**Padrões de Commits**](https://github.com/arafaellacruz/padroes-de-commits)

## Contato

Se você tiver alguma dúvida, sugestão ou apenas quiser trocar uma ideia, sinta-se à vontade para me enviar um e-mail em [steamduckss@gmail.com](mailto:steamduckss@gmail.com). 
Estamos ansiosos para ouvir de você!

## 🌟 Conheça o Time Brilhante 🌟

### Alexsander Silva
🚀 [GitHub: Alexsander Silva](https://github.com/alexttz)  
🔗 [LinkedIn: Alexsander Silva](https://www.linkedin.com/in/alexander-silva-lima-96a0432a6?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app)

### Carlos Daniel
🚀 [GitHub: Carlos Daniel](https://github.com/darloscaniel)  
🔗 [LinkedIn: Carlos Daniel](https://www.linkedin.com/in/carlos-daniel-alves-dos-santos-9516952b4?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app)

### Isabelly Souse
🚀 [GitHub: Isabelly Souse](https://github.com/61isabelly)  
🔗 [LinkedIn: Isabelly Souse](https://www.linkedin.com/in/isabelly-sousa?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app)

### Lucas Acosta
🚀 [GitHub: Lucas Acosta](https://github.com/Lucas-heck-acosta)  
🔗 [LinkedIn: Lucas Acosta](https://www.linkedin.com/in/https://www.linkedin.com/in/lucas-h-acosta?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=ios_appd)

### Luiz Henrique
🚀 [GitHub: Luiz Henrique](https://github.com/LuizHRFerreira)  
🔗 [LinkedIn: Luiz Henrique](https://www.linkedin.com/in/luiz-henrique-rabello-ferreira-3600752ba?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app)

### Matheus Barbosa
🚀 [GitHub: Matheus Barbosa](https://github.com/devmatba)  
🔗 [LinkedIn: Matheus Barbosa](https://www.linkedin.com/in/matheus-gabriel-barbosa-6b4a2320a/)

### Rafaella Cruz
🚀 [GitHub: Rafaella Cruz](https://github.com/arafaellacruz)  
🔗 [LinkedIn: Rafaella Cruz](https://www.linkedin.com/posts/cruz-rafaella_%C3%A9-um-prazer-compartilhar-que-finalizei-meu-activity-7212665413376081921-mEAy?utm_source=share&utm_medium=member_desktop)

### Samuel Prado
🚀 [GitHub: Samuel Prado](https://github.com/Samuelprado99)  
🔗 [LinkedIn: Samuel Prado](https://www.linkedin.com/in/samuel-prado-9142381b6?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=ios_app)

### Theo da Rosa
🚀 [GitHub: Theo da Rosa](https://github.com/TheodaRosa)  
🔗 [LinkedIn: Theo da Rosa](https://www.linkedin.com/in/theo-da-rosa-smidt-838b211b4?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app)

🔥 Entre em contato com a gente e descubra o que estamos criando juntos! 🔥

---
<div align="center">
    <img src="assets/footer.new.png" alt="footer" width="100%" />
</div>

</body>
</html>
