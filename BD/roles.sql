-- Table: comercio.roles

-- DROP TABLE IF EXISTS comercio.roles;

CREATE TABLE IF NOT EXISTS comercio.roles
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 1000000000000000 CACHE 1 ),
    nombre character varying(100) COLLATE pg_catalog."default" NOT NULL,
    descripcion character varying(150) COLLATE pg_catalog."default" NOT NULL,
    estado integer NOT NULL,
    idusuario bigint NOT NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (id),
    CONSTRAINT "FK_ROLES_USUARIOS" FOREIGN KEY (idusuario)
        REFERENCES comercio.usuarios (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS comercio.roles
    OWNER to postgres;
-- Index: fki_FK_ROLES_USUARIOS

-- DROP INDEX IF EXISTS comercio."fki_FK_ROLES_USUARIOS";

CREATE INDEX IF NOT EXISTS "fki_FK_ROLES_USUARIOS"
    ON comercio.roles USING btree
    (idusuario ASC NULLS LAST)
    TABLESPACE pg_default;