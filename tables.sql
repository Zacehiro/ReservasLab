-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2015-11-03 18:57:17.842


-- tables
-- Table LABORATORIO
CREATE TABLE LABORATORIO (
    ID_laboratorio int  NOT NULL,
    nombre varchar(300)  NOT NULL,
    cantidad_equipos int  NOT NULL,
    videobeam bool  NOT NULL,
    CONSTRAINT LABORATORIO_pk PRIMARY KEY (ID_laboratorio)
);

-- Table SISTEMA_OPERATIVO
CREATE TABLE SISTEMA_OPERATIVO (
    ID_sistema_operativo int  NOT NULL,
    nombre varchar(255)  NOT NULL,
    version varchar(300)  NOT NULL,
    ID_laboratorio int  NOT NULL,
    CONSTRAINT SISTEMA_OPERATIVO_pk PRIMARY KEY (ID_sistema_operativo)
);

-- Table SOFTWARE
CREATE TABLE SOFTWARE (
    ID_software int  NOT NULL,
    nombre varchar(300)  NOT NULL,
    version varchar(300)  NOT NULL,
    Laboratorio_id int  NOT NULL,
    CONSTRAINT SOFTWARE_pk PRIMARY KEY (ID_software)
);

-- Table SOLICITUD
CREATE TABLE SOLICITUD (
    ID_solicitud int  NOT NULL,
    Laboratorio_id int  NOT NULL,
    Software varchar(300)  NOT NULL,
    Link_licencia varchar(800)  NOT NULL,
    Link_descarga varchar(800)  NOT NULL,
    Estado varchar(8)  NULL,
    Fecha_radicacion date  NOT NULL,
    Fecha_posible_instalacion date  NULL,
    Fecha_respuesta date  NULL,
    Justificacion varchar(2000)  NULL,
    Usuario_id int  NOT NULL,
    CONSTRAINT SOLICITUD_pk PRIMARY KEY (ID_solicitud)
);

-- Table USUARIO
CREATE TABLE USUARIO (
    ID_usuario int  NOT NULL,
    nombre varchar(255)  NOT NULL,
    email varchar(255)  NOT NULL,
    tipo_usuario int  NOT NULL,
    UNIQUE INDEX Usuario_ak_1 (email),
    CONSTRAINT USUARIO_pk PRIMARY KEY (ID_usuario)
);


-- foreign keys
-- Reference:  SISTEMA_OPERATIVO_LABORATORIO (table: SISTEMA_OPERATIVO)


ALTER TABLE SISTEMA_OPERATIVO ADD CONSTRAINT SISTEMA_OPERATIVO_LABORATORIO FOREIGN KEY (ID_laboratorio)
    REFERENCES LABORATORIO (ID_laboratorio);
-- Reference:  Software_Laboratorio (table: SOFTWARE)


ALTER TABLE SOFTWARE ADD CONSTRAINT Software_Laboratorio FOREIGN KEY (Laboratorio_id)
    REFERENCES LABORATORIO (ID_laboratorio);
-- Reference:  Solicitud_Laboratorio (table: SOLICITUD)


ALTER TABLE SOLICITUD ADD CONSTRAINT Solicitud_Laboratorio FOREIGN KEY (Laboratorio_id)
    REFERENCES LABORATORIO (ID_laboratorio);
-- Reference:  Solicitud_Usuario (table: SOLICITUD)


ALTER TABLE SOLICITUD ADD CONSTRAINT Solicitud_Usuario FOREIGN KEY (Usuario_id)
    REFERENCES USUARIO (ID_usuario);



-- End of file.