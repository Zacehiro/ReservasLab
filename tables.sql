-- tables
-- Table LABORATORIO
CREATE TABLE LABORATORIO (
    ID_laboratorio int  NOT NULL,
    nombre varchar(300)  NOT NULL,
    cantidad_equipos int  NOT NULL,
    videobeam bool  NOT NULL,
    CONSTRAINT LABORATORIO_pk PRIMARY KEY (ID_laboratorio)
);

-- Table Laboratorio_sistema_operativo
CREATE TABLE Laboratorio_sistema_operativo (
    LABORATORIO_ID_laboratorio int  NOT NULL,
    SISTEMA_OPERATIVO_ID_sistema_operativo int  NOT NULL,
    CONSTRAINT Laboratorio_sistema_operativo_pk PRIMARY KEY (LABORATORIO_ID_laboratorio,SISTEMA_OPERATIVO_ID_sistema_operativo)
);

-- Table SISTEMA_OPERATIVO
CREATE TABLE SISTEMA_OPERATIVO (
    ID_sistema_operativo int  NOT NULL,
    nombre varchar(255)  NOT NULL,
    version varchar(300)  NOT NULL,
    Solicitud_id int  NOT NULL,
    CONSTRAINT SISTEMA_OPERATIVO_pk PRIMARY KEY (ID_sistema_operativo)
);

-- Table SOFTWARE
CREATE TABLE SOFTWARE (
    ID_software int  NOT NULL,
    nombre varchar(300)  NOT NULL,
    version varchar(300)  NOT NULL,
    CONSTRAINT SOFTWARE_pk PRIMARY KEY (ID_software)
);

-- Table SOLICITUD
CREATE TABLE SOLICITUD (
    ID_solicitud int  NOT NULL,
    Link_licencia varchar(800)  NOT NULL,
    Link_descarga varchar(800)  NOT NULL,
    Estado varchar(8)  NULL,
    Fecha_radicacion date  NOT NULL,
    Fecha_posible_instalacion date  NULL,
    Fecha_respuesta date  NULL,
    Justificacion varchar(2000)  NULL,
    Usuario_id int  NOT NULL,
    ID_sistema_operativo int  NOT NULL,
    ID_software int  NOT NULL,
    Software_instalado bool  NOT NULL,
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

-- Table software_laboratorio
CREATE TABLE software_laboratorio (
    SOFTWARE_ID_software int  NOT NULL,
    LABORATORIO_ID_laboratorio int  NOT NULL,
    CONSTRAINT software_laboratorio_pk PRIMARY KEY (SOFTWARE_ID_software,LABORATORIO_ID_laboratorio)
);





-- foreign keys
-- Reference:  Laboratorio_sistema_operativo_LABORATORIO (table: Laboratorio_sistema_operativo)


ALTER TABLE Laboratorio_sistema_operativo ADD CONSTRAINT Laboratorio_sistema_operativo_LABORATORIO FOREIGN KEY (LABORATORIO_ID_laboratorio)
    REFERENCES LABORATORIO (ID_laboratorio);


ALTER TABLE Laboratorio_sistema_operativo ADD CONSTRAINT Laboratorio_sistema_operativo_SISTEMA_OPERATIVO FOREIGN KEY (SISTEMA_OPERATIVO_ID_sistema_operativo)
    REFERENCES SISTEMA_OPERATIVO (ID_sistema_operativo);


ALTER TABLE SOLICITUD ADD CONSTRAINT SOLICITUD_SISTEMA_OPERATIVO FOREIGN KEY (ID_sistema_operativo)    REFERENCES SISTEMA_OPERATIVO (ID_sistema_operativo);


ALTER TABLE SOLICITUD ADD CONSTRAINT SOLICITUD_SOFTWARE FOREIGN KEY (ID_software)
    REFERENCES SOFTWARE (ID_software);


ALTER TABLE SOLICITUD ADD CONSTRAINT Solicitud_Usuario FOREIGN KEY (Usuario_id)
    REFERENCES USUARIO (ID_usuario);


ALTER TABLE software_laboratorio ADD CONSTRAINT software_laboratorio_LABORATORIO FOREIGN KEY (LABORATORIO_ID_laboratorio)
    REFERENCES LABORATORIO (ID_laboratorio);


ALTER TABLE software_laboratorio ADD CONSTRAINT software_laboratorio_SOFTWARE FOREIGN KEY (SOFTWARE_ID_software)
    REFERENCES SOFTWARE (ID_software);



-- End of file.