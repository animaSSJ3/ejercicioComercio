-- Table: comercio.productos

-- DROP TABLE IF EXISTS comercio.productos;

CREATE TABLE IF NOT EXISTS comercio.productos
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 1000000000000000 CACHE 1 ),
    codigo character varying(200) COLLATE pg_catalog."default" NOT NULL,
    nombre character varying(100) COLLATE pg_catalog."default" NOT NULL,
    detalle character varying(200) COLLATE pg_catalog."default" NOT NULL,
    precio integer NOT NULL,
    idcategoria bigint NOT NULL,
    idproveedor bigint NOT NULL,
    CONSTRAINT productos_pkey PRIMARY KEY (id),
    CONSTRAINT "FK_PRODUCTOS_CATEGORIAS" FOREIGN KEY (idcategoria)
        REFERENCES comercio.categorias (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT "FK_PRODUCTOS_PROVEEDOR" FOREIGN KEY (idproveedor)
        REFERENCES comercio.proveedor (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS comercio.productos
    OWNER to postgres;
-- Index: fki_FK_PRODUCTOS_CATEGORIAS

-- DROP INDEX IF EXISTS comercio."fki_FK_PRODUCTOS_CATEGORIAS";

CREATE INDEX IF NOT EXISTS "fki_FK_PRODUCTOS_CATEGORIAS"
    ON comercio.productos USING btree
    (idcategoria ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: fki_FK_PRODUCTOS_PROVEEDOR

-- DROP INDEX IF EXISTS comercio."fki_FK_PRODUCTOS_PROVEEDOR";

CREATE INDEX IF NOT EXISTS "fki_FK_PRODUCTOS_PROVEEDOR"
    ON comercio.productos USING btree
    (idproveedor ASC NULLS LAST)
    TABLESPACE pg_default;