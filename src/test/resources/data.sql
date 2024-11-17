
CREATE TABLE user_account (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,    -- 自增的用户ID
                              username VARCHAR(255) NOT NULL UNIQUE,    -- 用户名，不能为空且唯一
                              password VARCHAR(255) NOT NULL,           -- 密码，不能为空
                              mobile VARCHAR(20),                       -- 手机号，长度根据实际需求设置
                              city VARCHAR(100),                        -- 用户所在城市
                              company VARCHAR(255),                     -- 用户所在公司名
                              companyId VARCHAR(255),                   -- 用户所在公司ID
                              staffPhoto VARCHAR(255),                  -- 用户头像URL或文件路径
                              roleId VARCHAR(255),                          -- 用户角色，可以根据实际需要设置角色类型
                              project_name VARCHAR(255) DEFAULT 'Scaffold',   -- 项目名称
                              created_by VARCHAR(255),                  -- 创建人
                              updated_by VARCHAR(255),                  -- 修改人
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 创建时间，默认当前时间
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  -- 修改时间，默认当前时间，每次更新时自动修改
);

CREATE TABLE roles (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,         -- 自增的角色ID
                       role_name VARCHAR(255) NOT NULL UNIQUE,        -- 角色名，不能为空且唯一
                       menus TEXT,                                    -- 路由权限点，JSON 格式的字符串
                       points TEXT,                                   -- 按钮权限点，JSON 格式的字符串
                       created_by VARCHAR(255),                       -- 创建人
                       updated_by VARCHAR(255),                       -- 修改人
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 创建时间，默认为当前时间
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 修改时间，每次更新时自动修改
                       additional_property VARCHAR(255)               -- 扩展属性
);


INSERT INTO user_account (
    username,
    password,
    mobile,
    city,
    company,
    companyId,
    staffPhoto,
    roleId,
    created_by,
    updated_by
)
VALUES (
           'sa',                            -- 用户名
           '123456',             -- 密码，假设是已加密的密码
           '13812345678',                         -- 手机号
           'Shanghai',                            -- 用户所在城市
           'Tech Company Ltd.',                   -- 用户所在公司名
           '123456789',                           -- 用户所在公司ID
           '/images/avatar/johndoe.jpg',          -- 用户头像URL或文件路径
           '1',                               -- 用户角色
           'admin',                               -- 创建人
           'admin'                                -- 修改人
       );


    INSERT INTO roles (
    role_name,
    menus,
    points,
    created_by,
    updated_by,
    additional_property
)
VALUES (
    'ADMIN',                                         -- 角色名
    '["dashboard", "settings", "user_management"]',   -- 路由权限点，JSON 格式字符串
    '["edit", "delete", "create"]',                   -- 按钮权限点，JSON 格式字符串
    'admin',                                         -- 创建人
    'admin',                                         -- 修改人
    'Administrator role with full permissions'       -- 扩展属性
    );


