
CREATE TABLE user_account (
                              id INT AUTO_INCREMENT PRIMARY KEY,       -- 用户ID，主键，自增
                              username VARCHAR(255) NOT NULL UNIQUE,    -- 用户名，不能为空，并且唯一
                              password VARCHAR(255) NOT NULL,           -- 密码，不能为空
                              role VARCHAR(50) NOT NULL                 -- 角色，不能为空
);



INSERT INTO user_account (id,username, password,role) VALUES (123456,'sa', '123456','admin');




