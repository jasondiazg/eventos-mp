CREATE TABLE PONENTE (id BIGINT NOT NULL, APELLIDO VARCHAR(255), EMAIL VARCHAR(255), IDENTIFICACION VARCHAR(255), NOMBRE VARCHAR(255), version INTEGER, PRIMARY KEY (id))
CREATE TABLE SEQUENCE (SEQ_NAME VARCHAR(50) NOT NULL, SEQ_COUNT DECIMAL(15), PRIMARY KEY (SEQ_NAME))
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('SEQ_GEN', 0)
