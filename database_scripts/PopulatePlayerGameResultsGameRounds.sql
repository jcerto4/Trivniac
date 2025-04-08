use trivniac;

-- DELETE FROM game_rounds;
-- DELETE FROM game_results;
-- DELETE FROM player;
-- ALTER TABLE player AUTO_INCREMENT = 1;
-- ALTER TABLE game_results AUTO_INCREMENT = 1;


INSERT INTO player (username, password) VALUES
	('Alice', 'alice123'),
	('Bob', 'bob123'),
	('Charlie', 'charlie123'),
	('Dana', 'dana123'),
	('Eli', 'eli123'),
    ('Fiona', 'fiona123'),
	('George', 'george123'),
	('Hannah', 'hannah123'),
	('Ian', 'ian123'),
	('Julia', 'julia123');

INSERT INTO game_results (player_id, score, game_mode) VALUES
  (1, 45, 'Classic'),
  (1, 55, 'Blitz'),
  (1, 60, 'Survival'),
  (2, 70, 'Classic'),
  (2, 65, 'Blitz'),
  (2, 50, 'Survival'),
  (3, 80, 'Classic'),
  (3, 75, 'Blitz'),
  (3, 40, 'Survival'),
  (4, 90, 'Classic'),
  (4, 85, 'Blitz'),
  (4, 60, 'Survival'),
  (5, 55, 'Classic'),
  (5, 45, 'Blitz'),
  (5, 70, 'Survival'),
   (6, 68, 'Classic'),
  (6, 72, 'Blitz'),
  (6, 58, 'Survival'),
  (7, 83, 'Classic'),
  (7, 79, 'Blitz'),
  (7, 65, 'Survival'),
  (8, 91, 'Classic'),
  (8, 86, 'Blitz'),
  (8, 74, 'Survival'),
  (9, 47, 'Classic'),
  (9, 53, 'Blitz'),
  (9, 49, 'Survival'),
  (10, 62, 'Classic'),
  (10, 70, 'Blitz'),
  (10, 68, 'Survival');
    
