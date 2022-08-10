CREATE TABLE IF NOT EXISTS DELEGATION(
    ID BIGSERIAL PRIMARY KEY,
    BEGIN_DATE DATE NOT NULL,
    END_DATE DATE NOT NULL,
    FIRST_NAME VARCHAR(100) NOT NULL,
    LAST_NAME VARCHAR(100) NOT NULL,
    CITY VARCHAR(255) NOT NULL,
    COUNTRY VARCHAR(255) NOT NULL,
    DESCRIPTION TEXT NOT NULL
    );

CREATE TABLE IF NOT EXISTS COMMENT(
    ID BIGSERIAL PRIMARY KEY,
    AUTHOR VARCHAR(200) NOT NULL,
    DATE DATE NOT NULL,
    CONTENT TEXT NOT NULL,
    DELEGATION_ID BIGINT NOT NULL,
    PARENT_ID BIGINT,
    CONSTRAINT DELEGATION_COMMENT_FOREIGN_KEY
        FOREIGN KEY(DELEGATION_ID)
            REFERENCES DELEGATION(ID),
    CONSTRAINT COMMENT_COMMENT_FOREIGN_KEY
        FOREIGN KEY(PARENT_ID)
            REFERENCES COMMENT(ID)
    );

CREATE TABLE IF NOT EXISTS MASTERDATA_CHECKPOINT(
    ID BIGINT PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS DELEGATION_CHECKLIST(
    ID BIGSERIAL PRIMARY KEY,
    DELEGATION_ID BIGINT NOT NULL,
    CHECKPOINT_ID BIGINT NOT NULL,
    COMMENT TEXT NOT NULL,
    DESCRIPTION TEXT NOT NULL,
    IS_CHECKED BOOLEAN DEFAULT FALSE,
    CONSTRAINT DELEGATION_CHECKLIST_FOREIGN_KEY
        FOREIGN KEY(DELEGATION_ID)
            REFERENCES DELEGATION(ID),
    CONSTRAINT MASTER_DATA_CHECKLIST_FOREIGN_KEY
        FOREIGN KEY(CHECKPOINT_ID)
            REFERENCES MASTERDATA_CHECKPOINT(id)
);