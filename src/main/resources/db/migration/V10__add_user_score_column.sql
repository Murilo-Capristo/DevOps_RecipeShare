ALTER TABLE epicuser
ADD score INTEGER;

UPDATE epicuser
SET score = 0 WHERE score != 0;