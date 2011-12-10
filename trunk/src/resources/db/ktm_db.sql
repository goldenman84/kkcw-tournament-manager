SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `ktm` DEFAULT CHARACTER SET utf8 ;
USE `ktm` ;

-- -----------------------------------------------------
-- Table `ktm`.`tournament`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ktm`.`tournament` ;

CREATE  TABLE IF NOT EXISTS `ktm`.`tournament` (
  `name` VARCHAR(45) CHARACTER SET 'utf8' NOT NULL ,
  `date` DATE NULL ,
  PRIMARY KEY (`name`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ktm`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ktm`.`category` ;

CREATE  TABLE IF NOT EXISTS `ktm`.`category` (
  `name` VARCHAR(45) CHARACTER SET 'utf8' NOT NULL ,
  `tournament_name` VARCHAR(45) CHARACTER SET 'utf8' NOT NULL ,
  `mode` ENUM('single elimination','double elimination') NOT NULL DEFAULT 'single elimination' ,
  PRIMARY KEY (`name`, `tournament_name`)) 
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ktm`.`club`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ktm`.`club` ;

CREATE  TABLE IF NOT EXISTS `ktm`.`club` (
  `name` VARCHAR(45) CHARACTER SET 'utf8' NOT NULL ,
  `location` VARCHAR(45) CHARACTER SET 'utf8' NULL ,
  `description` VARCHAR(45) CHARACTER SET 'utf8' NULL ,
  PRIMARY KEY (`name`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ktm`.`fighter`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ktm`.`fighter` ;

CREATE  TABLE IF NOT EXISTS `ktm`.`fighter` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `firstname` VARCHAR(45) CHARACTER SET 'utf8' NOT NULL ,
  `lastname` VARCHAR(45) CHARACTER SET 'utf8' NOT NULL ,
  `age` INT NOT NULL ,
  `size` INT NOT NULL ,
  `club_name` VARCHAR(45) CHARACTER SET 'utf8' NOT NULL ,
  PRIMARY KEY (`id`, `firstname`, `lastname`) ,
  INDEX `fk_fighter_club1` (`club_name` ASC) ,
  CONSTRAINT `fk_fighter_club1`
    FOREIGN KEY (`club_name` )
    REFERENCES `ktm`.`club` (`name` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ktm`.`fight`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ktm`.`fight` ;

CREATE  TABLE IF NOT EXISTS `ktm`.`fight` (
  `fightnumber` INT NOT NULL AUTO_INCREMENT ,
  `category_name` VARCHAR(45) CHARACTER SET 'utf8' NOT NULL ,
  `category_tournament_name` VARCHAR(45) CHARACTER SET 'utf8' NOT NULL ,
  `fighter_1_id` INT NOT NULL ,
  `fighter_1_firstname` VARCHAR(45) NOT NULL ,
  `fighter_1_lastname` VARCHAR(45) NOT NULL ,
  `fighter_2_id` INT NOT NULL ,
  `fighter_2_firstname` VARCHAR(45) NOT NULL ,
  `fighter_2_lastname` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`fightnumber`, `category_name`, `category_tournament_name`) ,
  INDEX `fk_fight_fighter1` (`fighter_1_id` ASC, `fighter_1_firstname` ASC, `fighter_1_lastname` ASC) ,
  INDEX `fk_fight_fighter2` (`fighter_2_id` ASC, `fighter_2_firstname` ASC, `fighter_2_lastname` ASC) ,
  INDEX `fk_fight_category1` (`category_name` ASC, `category_tournament_name` ASC) ,
  CONSTRAINT `fk_fight_fighter1`
    FOREIGN KEY (`fighter_1_id` , `fighter_1_firstname` , `fighter_1_lastname` )
    REFERENCES `ktm`.`fighter` (`id` , `firstname` , `lastname` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_fight_fighter2`
    FOREIGN KEY (`fighter_2_id` , `fighter_2_firstname` , `fighter_2_lastname` )
    REFERENCES `ktm`.`fighter` (`id` , `firstname` , `lastname` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_fight_category1`
    FOREIGN KEY (`category_name` , `category_tournament_name` )
    REFERENCES `ktm`.`category` (`name` , `tournament_name` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ktm`.`fight_area`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ktm`.`fight_area` ;

CREATE  TABLE IF NOT EXISTS `ktm`.`fight_area` (
  `name` INT NOT NULL ,
  PRIMARY KEY (`name`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ktm`.`fight_takes_place`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ktm`.`fight_takes_place` ;

CREATE  TABLE IF NOT EXISTS `ktm`.`fight_takes_place` (
  `fight_fightnumber` INT NOT NULL ,
  `fight_area_name` INT NOT NULL ,
  PRIMARY KEY (`fight_fightnumber`, `fight_area_name`) ,
  INDEX `fk_fight_has_fight_area_fight_area1` (`fight_area_name` ASC) ,
  INDEX `fk_fight_has_fight_area_fight1` (`fight_fightnumber` ASC) ,
  CONSTRAINT `fk_fight_has_fight_area_fight1`
    FOREIGN KEY (`fight_fightnumber` )
    REFERENCES `ktm`.`fight` (`fightnumber` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_fight_has_fight_area_fight_area1`
    FOREIGN KEY (`fight_area_name` )
    REFERENCES `ktm`.`fight_area` (`name` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ktm`.`result`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ktm`.`result` ;

CREATE  TABLE IF NOT EXISTS `ktm`.`result` (
  `fight_fightnumber` INT NOT NULL ,
  `result_state` ENUM('undecided','won','lost') NOT NULL ,
  PRIMARY KEY (`fight_fightnumber`) ,
  INDEX `fk_result_fight1` (`fight_fightnumber` ASC) ,
  CONSTRAINT `fk_result_fight1`
    FOREIGN KEY (`fight_fightnumber` )
    REFERENCES `ktm`.`fight` (`fightnumber` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ktm`.`category_has_fighter`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ktm`.`category_has_fighter` ;

CREATE  TABLE IF NOT EXISTS `ktm`.`category_has_fighter` (
  `category_name` VARCHAR(45) CHARACTER SET 'utf8' NOT NULL ,
  `category_tournament_name` VARCHAR(45) CHARACTER SET 'utf8' NOT NULL ,
  `fighter_id` INT NOT NULL ,
  `fighter_firstname` VARCHAR(45) CHARACTER SET 'utf8' NOT NULL ,
  `fighter_lastname` VARCHAR(45) CHARACTER SET 'utf8' NOT NULL ,
  INDEX `fk_category_has_fighter_fighter1` (`fighter_id` ASC, `fighter_firstname` ASC, `fighter_lastname` ASC) ,
  INDEX `fk_category_has_fighter_category1` (`category_name` ASC, `category_tournament_name` ASC) ,
  PRIMARY KEY (`category_name`, `category_tournament_name`, `fighter_id`, `fighter_firstname`, `fighter_lastname`) ,
  CONSTRAINT `fk_category_has_fighter_category1`
    FOREIGN KEY (`category_name` , `category_tournament_name` )
    REFERENCES `ktm`.`category` (`name` , `tournament_name` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_category_has_fighter_fighter1`
    FOREIGN KEY (`fighter_id` , `fighter_firstname` , `fighter_lastname` )
    REFERENCES `ktm`.`fighter` (`id` , `firstname` , `lastname` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
