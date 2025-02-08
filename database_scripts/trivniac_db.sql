-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema trivniac
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema trivniac
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `trivniac` ;
USE `trivniac` ;

-- -----------------------------------------------------
-- Table `trivniac`.`questions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trivniac`.`questions` (
  `question_id` INT NOT NULL AUTO_INCREMENT,
  `category` ENUM('History', 'Sports', 'Geography', 'Science', 'Pop-Culture', 'Wild') NOT NULL,
  `question` VARCHAR(500) NOT NULL,
  `option_1` VARCHAR(255) NOT NULL,
  `option_2` VARCHAR(255) NOT NULL,
  `option_3` VARCHAR(255) NOT NULL,
  `option_4` VARCHAR(255) NOT NULL,
  `answer` INT NOT NULL,
  PRIMARY KEY (`question_id`),
  UNIQUE INDEX `question_UNIQUE` (`question` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `trivniac`.`player`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trivniac`.`player` (
  `player_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`player_id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `trivniac`.`game_results`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trivniac`.`game_results` (
  `game_id` INT NOT NULL AUTO_INCREMENT,
  `player_id` INT NOT NULL,
  `score` INT NOT NULL,
  `game_mode` ENUM('Classic', 'Blitz', 'Survival') NOT NULL,
  PRIMARY KEY (`game_id`),
  INDEX `player_id_idx` (`player_id` ASC) VISIBLE,
  CONSTRAINT `player_id`
    FOREIGN KEY (`player_id`)
    REFERENCES `trivniac`.`player` (`player_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `trivniac`.`game_rounds`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trivniac`.`game_rounds` (
  `game_id` INT NOT NULL,
  `questions_id` INT NOT NULL,
  `is_correct` TINYINT NULL,
  PRIMARY KEY (`game_id`, `questions_id`),
  INDEX `fk_game_results_has_questions_questions1_idx` (`questions_id` ASC) VISIBLE,
  INDEX `fk_game_results_has_questions_game_results1_idx` (`game_id` ASC) VISIBLE,
  CONSTRAINT `fk_game_results_has_questions_game_results1`
    FOREIGN KEY (`game_id`)
    REFERENCES `trivniac`.`game_results` (`game_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_game_results_has_questions_questions1`
    FOREIGN KEY (`questions_id`)
    REFERENCES `trivniac`.`questions` (`question_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
