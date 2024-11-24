DROP TABLE IF EXISTS user_account;

CREATE TABLE user_account
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,      -- H2 使用 AUTO_INCREMENT，去掉 BIGINT(20)
    username     VARCHAR(255) NOT NULL,                  -- H2 不支持 COLLATE 和 CHARACTER SET
    password     VARCHAR(255) NOT NULL,
    mobile       VARCHAR(255),
    city         VARCHAR(255),
    company      VARCHAR(255),
    project_name VARCHAR(255) DEFAULT 'Scaffold',        -- 默认值 'Scaffold'
    created_by   VARCHAR(255),
    updated_by   VARCHAR(255),
    created_at   TIMESTAMP    DEFAULT CURRENT_TIMESTAMP, -- 默认时间戳
    updated_at   TIMESTAMP    DEFAULT CURRENT_TIMESTAMP, -- 默认时间戳
    company_id   VARCHAR(255),
    role_id      VARCHAR(255),
    staff_photo  VARCHAR(255),
    CONSTRAINT unique_username UNIQUE (username)         -- H2 使用 CONSTRAINT 来设置唯一约束
);


DROP TABLE IF EXISTS roles;

CREATE TABLE roles
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,   -- H2 使用 AUTO_INCREMENT，而不需要指定类型长度
    role_name           VARCHAR(255) NOT NULL,               -- H2 不支持 COLLATE 和 CHARACTER SET，所以去掉
    menus               TEXT,
    points              TEXT,
    created_by          VARCHAR(255),
    updated_by          VARCHAR(255),
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- H2 支持 CURRENT_TIMESTAMP 默认值
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- H2 默认值和 MySQL 相同
    additional_property VARCHAR(255),
    CONSTRAINT unique_role_name UNIQUE (role_name)           -- 在 H2 中使用 CONSTRAINT 来设置唯一约束
);
