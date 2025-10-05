-- ==========================================
-- Criação das tabelas
-- ==========================================

-- Tabela de usuários
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='rs_user' AND xtype='U')
CREATE TABLE rs_user (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    avatar_url VARCHAR(500),
    score INT NOT NULL
);

-- Tabela de ingredientes
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='ingredient' AND xtype='U')
CREATE TABLE ingredient (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    default_unit VARCHAR(50) NOT NULL
);

-- Tabela de receitas
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='recipe' AND xtype='U')
CREATE TABLE recipe (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    portions INT,
    prep_time INT,
    difficulty VARCHAR(50) NOT NULL,
    category VARCHAR(50) NOT NULL,
    likes INT DEFAULT 0,
    image_url VARCHAR(500),
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_recipe_user FOREIGN KEY (user_id) REFERENCES rs_user(id) ON DELETE CASCADE
);

-- Tabela de ligação IngredientRecipe
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='ingredient_recipe' AND xtype='U')
CREATE TABLE ingredient_recipe (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    recipe_id BIGINT NOT NULL,
    ingredient_id BIGINT NOT NULL,
    quantity FLOAT NOT NULL,
    unit VARCHAR(50) NOT NULL,
    CONSTRAINT fk_ir_recipe FOREIGN KEY (recipe_id) REFERENCES recipe(id) ON DELETE CASCADE,
    CONSTRAINT fk_ir_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredient(id) ON DELETE CASCADE
);

-- ==========================================
-- Populando tabela de ingredientes
-- ==========================================
INSERT INTO ingredient (name, default_unit) VALUES
('Açúcar', 'GRAMAS'),
('Sal', 'GRAMAS'),
('Pimenta-do-reino', 'GRAMAS'),
('Alho', 'UNIDADES'),
('Cebola', 'UNIDADES'),
('Tomate', 'UNIDADES'),
('Ovo', 'UNIDADES'),
('Leite', 'ML'),
('Farinha de trigo', 'GRAMAS'),
('Manteiga', 'GRAMAS'),
('Queijo', 'GRAMAS'),
('Presunto', 'GRAMAS'),
('Frango', 'GRAMAS'),
('Carne bovina', 'GRAMAS'),
('Peixe', 'GRAMAS'),
('Arroz', 'GRAMAS'),
('Feijão', 'GRAMAS'),
('Batata', 'GRAMAS'),
('Cenoura', 'GRAMAS'),
('Azeite', 'ML'),
('Óleo', 'ML'),
('Ervilha', 'GRAMAS'),
('Milho', 'GRAMAS'),
('Leite condensado', 'ML'),
('Creme de leite', 'ML'),
('Chocolate', 'GRAMAS'),
('Coco ralado', 'GRAMAS'),
('Fermento em pó', 'GRAMAS'),
('Sal marinho', 'GRAMAS'),
('Salsinha', 'GRAMAS'),
('Cebolinha', 'GRAMAS'),
('Orégano', 'GRAMAS'),
('Manjericão', 'GRAMAS'),
('Alecrim', 'GRAMAS'),
('Gengibre', 'GRAMAS'),
('Canela', 'GRAMAS'),
('Mel', 'ML'),
('Limão', 'UNIDADES'),
('Laranja', 'UNIDADES');

-- ==========================================
-- Criando usuário de exemplo
-- ==========================================
INSERT INTO rs_user (email, name, avatar_url, score) VALUES
('exemplo@teste.com', 'Exemplo User', 'https://i.pravatar.cc/150?img=1', 0);

-- ==========================================
-- Criando receitas de exemplo
-- ==========================================
INSERT INTO recipe (title, description, portions, prep_time, difficulty, category, likes, image_url, user_id)
VALUES
('Bolo de Chocolate', 'Bolo simples de chocolate', 8, 60, 'EASY', 'DESSERT', 0, 'https://example.com/bolo.jpg', 1),
('Salada Caesar', 'Salada com frango e molho Caesar', 2, 20, 'MEDIUM', 'SALAD', 0, 'https://example.com/caesar.jpg', 1);

-- ==========================================
-- Ligando ingredientes às receitas
-- ==========================================
-- Ingredientes do Bolo de Chocolate (id da receita = 1)
INSERT INTO ingredient_recipe (recipe_id, ingredient_id, quantity, unit) VALUES
(1, 1, 200, 'GRAMAS'),  -- Açúcar
(1, 9, 300, 'GRAMAS'),  -- Farinha de trigo
(1, 27, 200, 'GRAMAS'), -- Chocolate
(1, 8, 250, 'ML'),      -- Leite
(1, 29, 10, 'GRAMAS'),  -- Fermento em pó
(1, 10, 100, 'GRAMAS'); -- Manteiga

-- Ingredientes da Salada Caesar (id da receita = 2)
INSERT INTO ingredient_recipe (recipe_id, ingredient_id, quantity, unit) VALUES
(2, 13, 200, 'GRAMAS'), -- Frango
(2, 5, 1, 'UNIDADES'),  -- Cebola
(2, 30, 20, 'GRAMAS'),  -- Salsinha
(2, 31, 10, 'GRAMAS'),  -- Cebolinha
(2, 20, 30, 'ML');      -- Azeite
