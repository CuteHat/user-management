-- Insert users
INSERT INTO USERS (username, email, password, active)
VALUES ('healthycartman', 'ericcartman@gmail.com', '$2a$10$ZymzxI3d9sK6Uk5K7bykDumTuCDCJUMf07Kzg7KDC0Ci.2Ou/WPdW',
        true),
       ('administrator', 'administrator@gmail.com', '$2a$10$ZymzxI3d9sK6Uk5K7bykDumTuCDCJUMf07Kzg7KDC0Ci.2Ou/WPdW',
        true);

WITH ADMINISTRATOR_ROLE_ID AS (SELECT id FROM ROLES WHERE name = 'ADMINISTRATOR'),
     ADMINISTRATOR_USER_ID AS (SELECT ID from USERS where username = 'administrator')

INSERT
INTO USER_ROLES(user_id, role_id)
VALUES ((SELECT id FROM ADMINISTRATOR_USER_ID),
        (SELECT id FROM ADMINISTRATOR_ROLE_ID));
