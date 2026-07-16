-- V2 — Imports et tickets (ref. rapport §4).
-- Le J1 (S2) cree les tables ; la persistance des tickets et le mapping de colonnes viennent au J2.

CREATE TABLE imports (
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    filename        VARCHAR(512) NOT NULL,
    file_type       VARCHAR(8)   NOT NULL,
    uploaded_by     BIGINT       NOT NULL,
    row_count       INTEGER      NOT NULL DEFAULT 0,
    status          VARCHAR(20)  NOT NULL,
    column_mapping  JSONB,                       -- rempli au J2 (mapping de colonnes)
    extraction_meta JSONB,                       -- stats/erreurs de parsing (J2+)
    created_at      TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT fk_imports_user       FOREIGN KEY (uploaded_by) REFERENCES users (id),
    CONSTRAINT ck_imports_file_type  CHECK (file_type IN ('CSV','XLSX','JSON','TXT','PDF','DOCX','EML')),
    CONSTRAINT ck_imports_status     CHECK (status IN
        ('PENDING','EXTRACTING','AWAITING_VALIDATION','PROCESSING','DONE','FAILED'))
);

CREATE INDEX ix_imports_uploaded_by ON imports (uploaded_by);

CREATE TABLE tickets (
    id             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    external_ref   VARCHAR(255),
    import_id      BIGINT,
    source         VARCHAR(10)  NOT NULL,
    customer_email VARCHAR(320),
    subject        TEXT,
    body           TEXT,
    language       VARCHAR(2),
    status         VARCHAR(16)  NOT NULL DEFAULT 'NEW',
    sla_due_at     TIMESTAMPTZ,
    merged_into_id BIGINT,
    created_at     TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT fk_tickets_import      FOREIGN KEY (import_id) REFERENCES imports (id) ON DELETE SET NULL,
    CONSTRAINT fk_tickets_merged      FOREIGN KEY (merged_into_id) REFERENCES tickets (id) ON DELETE SET NULL,
    CONSTRAINT uq_tickets_external_ref UNIQUE (external_ref),   -- idempotence par ref (S2-J3) ; NULL multiples autorises
    CONSTRAINT ck_tickets_source      CHECK (source IN ('FILE','WEBHOOK','EMAIL','MANUAL')),
    CONSTRAINT ck_tickets_status      CHECK (status IN ('NEW','ANALYZED','IN_PROGRESS','RESOLVED','MERGED')),
    CONSTRAINT ck_tickets_language    CHECK (language IS NULL OR language IN ('fr','en'))
);

CREATE INDEX ix_tickets_import_id  ON tickets (import_id);
CREATE INDEX ix_tickets_status_sla ON tickets (status, sla_due_at);
-- Index GIN full-text (subject/body) : ajoute en S4 (recherche) avec la config FR/EN dediee.
