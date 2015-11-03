-- -----------------------------------------------------
-- Table `LABORATORIO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LABORATORIO` (
  `ID_laboratorio` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  'cantidad_equipos' INT,
  'videobeam' BOOL,
  PRIMARY KEY (`ID_laboratorio`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SOLICITUD`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SOLICITUD` (
  `ID_solicitud` INT NOT NULL AUTO_INCREMENT,
  `Laboratorio_id` INT NOT NULL,
  `Software` VARCHAR (300) NOT NULL,
  'Link_licencia' VARCHAR (800) NOT NULL,
  'Link_descarga' VARCHAR (800) NOT NULL,
  'Estado' BOOL,
  `Fecha_radicacion` DATETIME NOT NULL,
  'Fecha_posible_instalacion' DATETIME,
  'Fecha_respuesta' DATETIME,
  'Justificacion' VARCHAR (2000),
  `Usuario_id` INT NOT NULL,
  PRIMARY KEY (`ID_solicitud`),
  --INDEX `fk_COMENTARIOS_CLIENTES_idx` (`CLIENTES_id` ASC),
  CONSTRAINT `fk_COMENTARIOS_CLIENTES`
    FOREIGN KEY (`Usuario_id`)
    REFERENCES `USUARIO` (`ID_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;