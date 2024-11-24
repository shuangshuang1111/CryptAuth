INSERT INTO user_account
(id, username, password, mobile, city, company, project_name, created_by, updated_by, created_at, updated_at,
 company_id, role_id, staff_photo)
VALUES (1, 'sa', '$2a$04$B8jA60P6y7BgZN6hHBbftOHnFEmIXCYbyNqh2UKh5cQeX1YCm8GB2', '13812345678', 'Shanghai',
        'Tech Company Ltd.', 'Scaffold', 'admin', 'admin', '2024-11-15 20:59:22', '2024-11-22 17:39:30', '123', '1',
        'path/to/photo.jpg');


INSERT INTO roles
(id, role_name, menus, points, created_by, updated_by, created_at, updated_at, additional_property)
VALUES (1, 'ADMIN', '["dashboard", "settings", "user_management"]', '["edit", "delete", "create"]', 'admin', 'admin',
        '2024-11-15 20:59:22', '2024-11-15 20:59:22', 'Administrator role with full permissions');