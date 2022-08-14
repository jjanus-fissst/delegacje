CREATE TABLE IF NOT EXISTS masterdata_checkpoint
(
    ID BIGINT PRIMARY KEY
);

ALTER TABLE masterdata_checkpoint
    ADD COLUMN IF NOT EXISTS spel_expression varchar(255) not NULL,
    ADD COLUMN IF NOT EXISTS description varchar(255) not NULL;

INSERT INTO masterdata_checkpoint(ID, spel_expression, description)
VALUES (1, 'countryCode != ''PL''', 'Is the flight ticked purchased?'),
       (2, 'countryCode != ''PL''', 'Is covid test done?'),
       (3, 'T(java.time.temporal.ChronoUnit).DAYS.between(startDate,endDate) >= 2', 'Is the hotel booked?'),
       (4, 'true', 'Is diet paid off?'),
       (5, 'true', 'Is meeting schedule definied?')
ON CONFLICT (ID) DO
    UPDATE SET spel_expression = EXCLUDED.spel_expression, description = EXCLUDED.description;