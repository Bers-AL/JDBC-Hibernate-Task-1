CREATE OR REPLACE PROCEDURE sp_install_users_api()
LANGUAGE plpgsql
AS $$
BEGIN

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    last_name VARCHAR(50),
    age SMALLINT
    );

CREATE OR REPLACE PROCEDURE sp_create_users_table()
  LANGUAGE sql
  AS $p$
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    last_name VARCHAR(50),
    age SMALLINT
    );
$p$;

CREATE OR REPLACE PROCEDURE sp_drop_users_table()
  LANGUAGE sql
  AS $p$
DROP TABLE IF EXISTS users;
$p$;

CREATE OR REPLACE PROCEDURE sp_clean_users_table()
  LANGUAGE sql
  AS $p$
    TRUNCATE TABLE users;
  $p$;

  CREATE OR REPLACE PROCEDURE sp_save_user(p_name VARCHAR, p_last_name VARCHAR, p_age SMALLINT)
  LANGUAGE sql
  AS $p$
    INSERT INTO users(name, last_name, age) VALUES (p_name, p_last_name, p_age);
  $p$;

  CREATE OR REPLACE PROCEDURE sp_remove_user_by_id(p_id BIGINT)
  LANGUAGE sql
  AS $p$
DELETE FROM users WHERE id = p_id;
$p$;

CREATE OR REPLACE FUNCTION fn_get_user_by_id(p_id BIGINT)
  RETURNS TABLE(id BIGINT, name VARCHAR, last_name VARCHAR, age SMALLINT)
  LANGUAGE sql
  AS $p$
SELECT u.id, u.name, u.last_name, u.age
FROM users u
WHERE u.id = p_id;
$p$;

CREATE OR REPLACE FUNCTION fn_get_all_users()
  RETURNS TABLE(id BIGINT, name VARCHAR, last_name VARCHAR, age SMALLINT)
  LANGUAGE sql
  AS $p$
SELECT u.id, u.name, u.last_name, u.age
FROM users u
ORDER BY u.id;
$p$;

END;
$$;