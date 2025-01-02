-- Table: comercio.usuarios

-- DROP TABLE IF EXISTS comercio.usuarios;

CREATE TABLE IF NOT EXISTS comercio.usuarios
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 1000000000000000 CACHE 1 ),
    correo character varying(100) COLLATE pg_catalog."default" NOT NULL,
    password character varying(200) COLLATE pg_catalog."default" NOT NULL,
    estado integer NOT NULL,
    token character varying(200) COLLATE pg_catalog."default",
    idproveedor bigint NOT NULL,
    CONSTRAINT usuarios_pkey PRIMARY KEY (id),
    CONSTRAINT "FK_USUARIOS_PROVEEDOR" FOREIGN KEY (idproveedor)
        REFERENCES comercio.proveedor (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS comercio.usuarios
    OWNER to postgres;
-- Index: fki_FK_USUARIOS_PROVEEDOR

-- DROP INDEX IF EXISTS comercio."fki_FK_USUARIOS_PROVEEDOR";

CREATE INDEX IF NOT EXISTS "fki_FK_USUARIOS_PROVEEDOR"
    ON comercio.usuarios USING btree
    (idproveedor ASC NULLS LAST)
    TABLESPACE pg_default;