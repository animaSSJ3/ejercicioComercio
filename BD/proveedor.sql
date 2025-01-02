-- Table: comercio.proveedor

-- DROP TABLE IF EXISTS comercio.proveedor;

CREATE TABLE IF NOT EXISTS comercio.proveedor
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 1000000000000000 CACHE 1 ),
    nombre character varying(100) COLLATE pg_catalog."default" NOT NULL,
    primer_apellido character varying(100) COLLATE pg_catalog."default" NOT NULL,
    segundo_apellido character varying(100) COLLATE pg_catalog."default" NOT NULL,
    fecha_nacimiento date NOT NULL,
    edad integer NOT NULL,
    telefono character varying(10) COLLATE pg_catalog."default" NOT NULL,
    rfc character varying(18) COLLATE pg_catalog."default" NOT NULL,
    estado integer NOT NULL,
    CONSTRAINT proveedor_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS comercio.proveedor
    OWNER to postgres;