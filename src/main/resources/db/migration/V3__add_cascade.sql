-- ==========================================
-- Migration: Adiciona ON DELETE CASCADE
-- nas tabelas recipe_likes e ingredient_recipe
-- ==========================================

-- 1️ ALTERANDO TABELA recipe_likes
PRINT 'Atualizando tabela recipe_likes...';

-- Removendo constraints antigas
ALTER TABLE recipe_likes DROP CONSTRAINT IF EXISTS FK__recipe_li__recip__6D0D32F4;
ALTER TABLE recipe_likes DROP CONSTRAINT IF EXISTS FK__recipe_li__user__6E01572D;
ALTER TABLE recipe_likes DROP CONSTRAINT IF EXISTS fk_recipe_likes_recipe;
ALTER TABLE recipe_likes DROP CONSTRAINT IF EXISTS fk_recipe_likes_user;

-- Criando novamente as constraints com ON DELETE CASCADE
ALTER TABLE recipe_likes
ADD CONSTRAINT fk_recipe_likes_recipe
FOREIGN KEY (recipe_id)
REFERENCES recipe(id)
ON DELETE CASCADE;

ALTER TABLE recipe_likes
ADD CONSTRAINT fk_recipe_likes_user
FOREIGN KEY (user_id)
REFERENCES rs_user(id)
ON DELETE CASCADE;

PRINT 'recipe_likes atualizado com ON DELETE CASCADE.';


-- 2️ (Opcional) GARANTINDO que ingredient_recipe também tenha CASCADE
PRINT 'Atualizando tabela ingredient_recipe...';

-- Remove e recria constraints, por garantia
ALTER TABLE ingredient_recipe DROP CONSTRAINT IF EXISTS fk_ir_recipe;
ALTER TABLE ingredient_recipe DROP CONSTRAINT IF EXISTS fk_ir_ingredient;

ALTER TABLE ingredient_recipe
ADD CONSTRAINT fk_ir_recipe
FOREIGN KEY (recipe_id)
REFERENCES recipe(id)
ON DELETE CASCADE;

ALTER TABLE ingredient_recipe
ADD CONSTRAINT fk_ir_ingredient
FOREIGN KEY (ingredient_id)
REFERENCES ingredient(id)
ON DELETE CASCADE;

PRINT ' ingredient_recipe atualizado com ON DELETE CASCADE.';
