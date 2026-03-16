CREATE TABLE IF NOT EXISTS task (
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(255)    NOT NULL,
    description TEXT            NOT NULL,
    completed   BOOLEAN         NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_task_completed_created
    ON task (completed, created_at DESC);