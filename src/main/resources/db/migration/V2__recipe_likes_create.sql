IF NOT EXISTS (
    SELECT * FROM sysobjects WHERE name = 'recipe_likes' AND xtype = 'U'
)
BEGIN
    CREATE TABLE recipe_likes (
        id BIGINT IDENTITY(1,1) NOT NULL,
        recipe_id BIGINT NOT NULL,
        user_id BIGINT NOT NULL,
        PRIMARY KEY (recipe_id, user_id),
        FOREIGN KEY (recipe_id) REFERENCES recipe(id),
        FOREIGN KEY (user_id) REFERENCES rs_user(id)
    );
END;
