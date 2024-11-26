-- Criar banco de dados, se não existir
CREATE DATABASE IF NOT EXISTS sistema_recap;
USE sistema_recap;

-- Desabilitar verificações de chave estrangeira temporariamente
SET FOREIGN_KEY_CHECKS = 0;

-- Criação das tabelas, se não existirem
CREATE TABLE IF NOT EXISTS semestre (
    id_semestre INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(80) NOT NULL
);

CREATE TABLE IF NOT EXISTS equipe (
    id_equipe INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(80) NOT NULL,
    github VARCHAR(100),
    id_semestre INT,
    FOREIGN KEY (id_semestre) REFERENCES semestre(id_semestre) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS criterio (
    id_criterio INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    nome VARCHAR(80) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS semestre_criterio (
    id_semestre INT,
    id_criterio INT,
    PRIMARY KEY (id_semestre, id_criterio),
    FOREIGN KEY (id_semestre) REFERENCES semestre(id_semestre) ON DELETE CASCADE,
    FOREIGN KEY (id_criterio) REFERENCES criterio(id_criterio) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS usuario (
    email VARCHAR(80) PRIMARY KEY,
    nome VARCHAR(80) NOT NULL,
    senha VARCHAR(80) NOT NULL,
    is_professor BOOL NOT NULL,
    id_equipe INT,
    FOREIGN KEY (id_equipe) REFERENCES equipe(id_equipe) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS sprint (
    id_sprint INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(80) NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    id_semestre INT,
    FOREIGN KEY (id_semestre) REFERENCES semestre(id_semestre) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS pontuacao (
    id_pontuacao INT AUTO_INCREMENT PRIMARY KEY,
    pontos INT NOT NULL,
    id_sprint INT,
    id_equipe INT,
    FOREIGN KEY (id_sprint) REFERENCES sprint(id_sprint) ON DELETE CASCADE,
    FOREIGN KEY (id_equipe) REFERENCES equipe(id_equipe) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS avaliacao (
    id_avaliacao INT AUTO_INCREMENT PRIMARY KEY,
    nota INT NOT NULL,
    email_avaliador VARCHAR(80),
    email_avaliado VARCHAR(80),
    id_sprint INT,
    id_criterio INT,
    FOREIGN KEY (email_avaliador) REFERENCES usuario(email) ON DELETE CASCADE,
    FOREIGN KEY (email_avaliado) REFERENCES usuario(email) ON DELETE CASCADE,
    FOREIGN KEY (id_sprint) REFERENCES sprint(id_sprint) ON DELETE CASCADE,
    FOREIGN KEY (id_criterio) REFERENCES criterio(id_criterio) ON DELETE CASCADE
);

-- Reativar verificações de chave estrangeira
SET FOREIGN_KEY_CHECKS = 1;

-- Criar usuário admin com permissões
DROP USER IF EXISTS 'admin'@'localhost';
CREATE USER 'admin'@'localhost' IDENTIFIED BY '1234';
GRANT INSERT, SELECT, UPDATE, DELETE ON sistema_recap.* TO 'admin'@'localhost';

-- Criar usuário professor com restrições após a criação das tabelas
DROP USER IF EXISTS 'prof'@'localhost';
CREATE USER 'prof'@'localhost' IDENTIFIED BY 'profrecap';
GRANT INSERT, SELECT, UPDATE, DELETE ON sistema_recap.semestre TO 'prof'@'localhost';
GRANT INSERT, SELECT, UPDATE, DELETE ON sistema_recap.criterio TO 'prof'@'localhost';
GRANT INSERT, SELECT, UPDATE, DELETE ON sistema_recap.sprint TO 'prof'@'localhost';
GRANT INSERT, SELECT, UPDATE, DELETE ON sistema_recap.equipe TO 'prof'@'localhost';
GRANT INSERT, SELECT ON sistema_recap.pontuacao TO 'prof'@'localhost';
GRANT INSERT, SELECT, UPDATE ON sistema_recap.usuario TO 'prof'@'localhost';
GRANT SELECT ON sistema_recap.semestre_criterio TO 'prof'@'localhost';
GRANT SELECT ON sistema_recap.avaliacao TO 'prof'@'localhost';

-- Criar usuário aluno com as restrições após a criação das tabelas
DROP USER IF EXISTS 'aluno'@'localhost';
CREATE USER 'aluno'@'localhost' IDENTIFIED BY 'alunorecap';
GRANT INSERT, SELECT ON sistema_recap.avaliacao TO 'aluno'@'localhost';
GRANT SELECT ON sistema_recap.semestre TO 'aluno'@'localhost';
GRANT SELECT ON sistema_recap.criterio TO 'aluno'@'localhost';
GRANT SELECT ON sistema_recap.sprint TO 'aluno'@'localhost';
GRANT SELECT ON sistema_recap.equipe TO 'aluno'@'localhost';
GRANT SELECT ON sistema_recap.pontuacao TO 'aluno'@'localhost';
GRANT SELECT ON sistema_recap.usuario TO 'aluno'@'localhost';
GRANT SELECT ON sistema_recap.semestre_criterio TO 'aluno'@'localhost';

INSERT INTO semestre (nome) VALUES ('BD 2024-1'), ('ADS 2024-2');

INSERT INTO criterio (nome, descricao) VALUES
('Pontualidade', 'Avaliação de pontualidade nas entregas'),
('Trabalho em equipe', 'Avaliação do trabalho colaborativo'),
('Qualidade do código', 'Avaliação da clareza e qualidade do código'),
('Comunicação', 'Avaliação da eficácia na comunicação');

INSERT INTO equipe (nome, github, id_semestre) VALUES
('Steam Ducks', 'https://github.com/steam-ducks', 1),
('SQLutions','https://github.com/sqlutions', 1),
('CyberNexus','https://github.com/cybernexus', 1),
('AlphaCode', 'https://github.com/alphacode', 1),
('DenariusData', 'https://github.com/denariusdata', 1),
('Equipe1', 'https://github.com/ex1', 2),
('Equipe2', 'https://github.com/ex2', 2),
('Equipe3', 'https://github.com/ex3', 2),
('Equipe4', 'https://github.com/ex4', 2);

INSERT INTO sprint (nome, data_inicio, data_fim, id_semestre) VALUES
('Sprint 1', '2024-01-15', '2024-02-10', 2),
('Sprint 2', '2024-10-20', '2024-12-10', 2),
('Sprint 1', '2024-01-15', '2024-02-10', 1),
('Sprint 2', '2024-10-20', '2024-12-10', 1);

INSERT INTO usuario (email, nome, senha, is_professor, id_equipe) VALUES
('professor@fatec.com','Professor','senha123', TRUE, NULL),
('lucas@email.com', 'Lucas', 'senha123', FALSE, 1),
('rafa@rafa.com', 'Rafa', 'senha123', FALSE, 1),
('matheus@games.com', 'Matheus', 'senha123', FALSE, 1),
('samuca@samu.com', 'Samuel', 'senha123', FALSE, 1),
('theo@theo.com', 'Theo', 'senha123', FALSE, 1),
('carlao@carlinho.com', 'Carlos', 'senha123', FALSE, 1),
('isa@belly.com', 'Isabelly', 'senha123', FALSE, 1),
('alex@ssauro.com', 'Alex', 'senha123', FALSE, 1),
('louis@luis', 'Luiz', 'senha123', FALSE, 1);

COMMIT;



