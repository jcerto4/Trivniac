use trivniac;

-- DELETE FROM game_rounds;
-- DELETE FROM game_results;
-- DELETE FROM player;
-- ALTER TABLE player AUTO_INCREMENT = 1;
-- ALTER TABLE game_results AUTO_INCREMENT = 1;


INSERT INTO player (username, password) VALUES
	('ace', 'alice123'),
	('bob', 'bob123'),
	('char3', 'charlie123'),
	('em123', 'dana123'),
	('han789', 'eli123'),
    ('lisa71', 'fiona123'),
	('dave40', 'george123'),
	('kam7', 'hannah123'),
	('jake54', 'ian123'),
	('los119', 'julia123');

INSERT INTO game_results (player_id, score, game_mode) VALUES
  (1, 240, 'Classic'),
  (1, 90, 'Blitz'),
  (1, 60, 'Survival'),
  (2, 80, 'Classic'),
  (2, 30, 'Blitz'),
  (2, 50, 'Survival'),
  (3, 85, 'Classic'),
  (3, 75, 'Blitz'),
  (3, 40, 'Survival'),
  (4, 95, 'Classic'),
  (4, 65, 'Blitz'),
  (4, 60, 'Survival'),
  (5, 55, 'Classic'),
  (5, 45, 'Blitz'),
  (5, 70, 'Survival'),
  (6, 65, 'Classic'),
  (6, 70, 'Blitz'),
  (6, 55, 'Survival'),
  (7, 25, 'Classic'),
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
  (10, 50, 'Survival');
    
