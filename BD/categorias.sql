-- Table: comercio.categorias

-- DROP TABLE IF EXISTS comercio.categorias;

CREATE TABLE IF NOT EXISTS comercio.categorias
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 1000000000000 CACHE 1 ),
    nombre character varying(100) COLLATE pg_catalog."default" NOT NULL,
    estado integer NOT NULL,
    CONSTRAINT categorias_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS comercio.categorias
    OWNER to postgres;