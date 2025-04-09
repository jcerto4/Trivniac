use trivniac;

-- DELETE FROM game_rounds;
-- DELETE FROM game_results;
-- DELETE FROM player;
-- ALTER TABLE player AUTO_INCREMENT = 1;
-- ALTER TABLE game_results AUTO_INCREMENT = 1;


INSERT INTO player (username, password) VALUES
	('Alice1', 'alice123'),
	('Bob2', 'bob123'),
	('Charlie3', 'charlie123'),
	('Dana4', 'dana123'),
	('Eli5', 'eli123'),
    ('Fiona6', 'fiona123'),
	('George7', 'george123'),
	('Hannah8', 'hannah123'),
	('Ian9', 'ian123'),
	('Julia10', 'julia123');

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
	(6, 65, 'Classic'),
  (6, 70, 'Blitz'),
  (6, 55, 'Survival'),
  (7, 85, 'Classic'),
  (7, 70, 'Blitz'),
  (7, 60, 'Survival'),
  (8, 90, 'Classic'),
  (8, 85, 'Blitz'),
  (8, 75, 'Survival'),
  (9, 45, 'Classic'),
  (9, 55, 'Blitz'),
  (9, 45, 'Survival'),
  (10, 65, 'Classic'),
  (10, 70, 'Blitz'),
  (10, 65, 'Survival');
    
