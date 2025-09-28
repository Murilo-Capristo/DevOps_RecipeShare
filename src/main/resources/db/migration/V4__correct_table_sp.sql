drop table superpoder;

CREATE TABLE superpoder
(
    id                BIGINT,
    nome              VARCHAR(255),
    descricao         VARCHAR(255),
    nivel_inutilidade INTEGER NOT NULL,
    CONSTRAINT pk_superpoder PRIMARY KEY (id)
);


INSERT INTO superpoder (id, nome, descricao, nivel_inutilidade) VALUES (1, 'Invocar uma colher', 'Conjura uma única colher... de plástico.', 5);
INSERT INTO superpoder (id, nome, descricao, nivel_inutilidade) VALUES (2, 'Mudar a cor de folhas secas', 'Permite alterar a cor de uma folha caída do chão.', 5);
INSERT INTO superpoder (id, nome, descricao, nivel_inutilidade) VALUES (3, 'Falar com pedras', 'As pedras não respondem, mas você consegue conversar com elas.', 4);
INSERT INTO superpoder (id, nome, descricao, nivel_inutilidade) VALUES (4, 'Sentir cheiros pelo cotovelo', 'Um olfato funcional, mas limitado ao cotovelo esquerdo.', 4);
INSERT INTO superpoder (id, nome, descricao, nivel_inutilidade) VALUES (5, 'Trocar o som do próprio espirro', 'Substitui seu espirro por sons como "miau" ou "pum".', 3);
INSERT INTO superpoder (id, nome, descricao, nivel_inutilidade) VALUES (6, 'Saber a previsão do tempo de ontem', 'Informa com precisão o clima do dia anterior.', 5);
INSERT INTO superpoder (id, nome, descricao, nivel_inutilidade) VALUES (7, 'Fazer barulho de clipe caindo', 'Um som exato e inútil — útil apenas em mímicas silenciosas.', 4);
INSERT INTO superpoder (id, nome, descricao, nivel_inutilidade) VALUES (8, 'Abaixar a temperatura de uma uva', 'Reduz 0.5°C da temperatura de uma única uva por minuto.', 5);
INSERT INTO superpoder (id, nome, descricao, nivel_inutilidade) VALUES (9, 'Saber a cor da meia de qualquer um', 'Desde que você não pergunte.', 3);
INSERT INTO superpoder (id, nome, descricao, nivel_inutilidade) VALUES (10, 'Fazer crescer um fio de cabelo', 'Apenas um, e só em terças-feiras.', 4);
INSERT INTO superpoder (id, nome, descricao, nivel_inutilidade) VALUES (11, 'Localizar tomadas em um cômodo', 'Mas apenas quando o cômodo está completamente iluminado.', 3);
INSERT INTO superpoder (id, nome, descricao, nivel_inutilidade) VALUES (12, 'Fazer sumir um pixel de uma tela', 'Remove 1 pixel (aleatório) por dia.', 5);
INSERT INTO superpoder (id, nome, descricao, nivel_inutilidade) VALUES (13, 'Saber se uma banana está madura', 'Desde que esteja descascada.', 2);
INSERT INTO superpoder (id, nome, descricao, nivel_inutilidade) VALUES (14, 'Sentir quando alguém bocejou perto', 'Mas só se foi há mais de 10 minutos.', 4);
INSERT INTO superpoder (id, nome, descricao, nivel_inutilidade) VALUES (15, 'Emitir cheiro de café', 'Só o cheiro, nada de café.', 2);
INSERT INTO superpoder (id, nome, descricao, nivel_inutilidade) VALUES (16, 'Fazer um botão de camisa se soltar', 'Apenas de camisas brancas.', 4);
INSERT INTO superpoder (id, nome, descricao, nivel_inutilidade) VALUES (17, 'Acelerar o crescimento de musgo', 'Em no máximo 3%.', 5);
INSERT INTO superpoder (id, nome, descricao, nivel_inutilidade) VALUES (18, 'Saber quantas vezes você piscou', 'Apenas desde a última meia-noite.', 3);