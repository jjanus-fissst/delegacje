CREATE TABLE IF NOT EXISTS COMMENT_TO_CHECKPOINT(
    ID BIGSERIAL PRIMARY KEY,
    CHECKPOINT_ID BIGINT NOT NULL,
    CHECKPOINT_COMMENT TEXT NOT NULL,
    DATE TIMESTAMP NOT NULL,
    CONSTRAINT DELEGATION_CHECKLIST_FOREIGN_KEY
        FOREIGN KEY(CHECKPOINT_ID)
            REFERENCES DELEGATION_CHECKLIST(id)
    );

ALTER TABLE DELEGATION_CHECKLIST
DROP COLUMN comment;