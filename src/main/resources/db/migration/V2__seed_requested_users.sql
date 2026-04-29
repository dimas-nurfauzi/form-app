INSERT INTO users (id, name, email, password, created_at, updated_at) VALUES
(1, 'User 1', 'user1@webtech.id', '$2a$10$tI4Xivipn1rhEoDZXGoSaeYsdXbfNWZsZhso2FRc0yUcAwSgYjuk2', NOW(), NOW()),
(2, 'User 2', 'user2@webtech.id', '$2a$10$Ux5tqAuIoTedxgoy8rvESeHkbyJLPYq79pxvUU5wei9z9xBVFQJ7u', NOW(), NOW()),
(3, 'User 3', 'user3@worldskills.org', '$2a$10$VSblMC.Mkdx6YGID8w5tdOkfTUHyhe5UgE6O0jkt/r8vAhVPTRP9O', NOW(), NOW());

SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));