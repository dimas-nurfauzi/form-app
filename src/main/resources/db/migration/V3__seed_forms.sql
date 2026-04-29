INSERT INTO forms (id, name, slug, description, limit_one_response, creator_id, created_at, updated_at) VALUES
                                                                                                            (1, 'Biodata - Web Tech Members', 'biodata', 'To save web tech members data', true, 1, NOW(), NOW()),
                                                                                                            (2, 'HTML and CSS Skills - Quiz', 'htmlcss-quiz', 'Fundamental web tests', true, 1, NOW(), NOW()),
                                                                                                            (3, 'Stacks of Web Tech Members', 'member-stacks', 'To collect all of favorite stacks', false, 2, NOW(), NOW()),
                                                                                                            (4, 'JavaScript Fundamentals', 'js-fundamentals', 'Test your JavaScript knowledge', false, 2, NOW(), NOW()),
                                                                                                            (5, 'Team Availability', 'team-availability', 'Collect team schedule', true, 3, NOW(), NOW());

INSERT INTO allowed_domains (id, form_id, domain, created_at, updated_at) VALUES
                                                                              (1, 1, 'webtech.id', NOW(), NOW()),
                                                                              (2, 2, 'webtech.id', NOW(), NOW()),
                                                                              (3, 3, 'webtech.id', NOW(), NOW()),
                                                                              (4, 4, 'webtech.id', NOW(), NOW()),
                                                                              (5, 4, 'worldskills.org', NOW(), NOW());
-- form 5 tidak ada allowed_domains → public

INSERT INTO questions (id, form_id, name, choice_type, is_required, created_at, updated_at) VALUES
-- form 1: biodata
(1,  1, 'Name',            'SHORT_ANSWER',    true,  NOW(), NOW()),
(2,  1, 'Address',         'PARAGRAPH',       false, NOW(), NOW()),
(3,  1, 'Born Date',       'DATE',            true,  NOW(), NOW()),
(4,  1, 'Sex',             'MULTIPLE_CHOICE', true,  NOW(), NOW()),
-- form 2: html css quiz
(5,  2, 'What does HTML stand for?',         'MULTIPLE_CHOICE', true,  NOW(), NOW()),
(6,  2, 'Explain the CSS box model',         'PARAGRAPH',       true,  NOW(), NOW()),
(7,  2, 'When did you start learning HTML?', 'DATE',            false, NOW(), NOW()),
(8,  2, 'Favorite CSS framework',            'DROPDOWN',        true,  NOW(), NOW()),
-- form 3: stacks
(9,  3, 'Most Favorite JS Framework',  'MULTIPLE_CHOICE', true,  NOW(), NOW()),
(10, 3, 'Most Favorite CSS Framework', 'DROPDOWN',        true,  NOW(), NOW()),
(11, 3, 'Tools you use',               'CHECKBOX',        false, NOW(), NOW()),
(12, 3, 'Years of experience',         'SHORT_ANSWER',    true,  NOW(), NOW()),
-- form 4: js fundamentals
(13, 4, 'What is closure?',            'PARAGRAPH',       true,  NOW(), NOW()),
(14, 4, 'JS data types you know',      'CHECKBOX',        true,  NOW(), NOW()),
(15, 4, 'Preferred JS runtime',        'DROPDOWN',        false, NOW(), NOW()),
-- form 5: team availability
(16, 5, 'Your Name',          'SHORT_ANSWER',    true,  NOW(), NOW()),
(17, 5, 'Available Days',     'CHECKBOX',        true,  NOW(), NOW()),
(18, 5, 'Preferred Shift',    'MULTIPLE_CHOICE', true,  NOW(), NOW()),
(19, 5, 'Additional Notes',   'PARAGRAPH',       false, NOW(), NOW());

INSERT INTO question_choices (id, question_id, value, created_at, updated_at) VALUES
-- Sex (q4)
(1,  4,  'Male',       NOW(), NOW()),
(2,  4,  'Female',     NOW(), NOW()),
-- HTML stand for (q5)
(3,  5,  'HyperText Markup Language', NOW(), NOW()),
(4,  5,  'High Tech Modern Language', NOW(), NOW()),
(5,  5,  'HyperText Modern Links',    NOW(), NOW()),
(6,  5,  'None of the above',         NOW(), NOW()),
-- CSS framework (q8)
(7,  8,  'Tailwind CSS',  NOW(), NOW()),
(8,  8,  'Bootstrap',     NOW(), NOW()),
(9,  8,  'Bulma',         NOW(), NOW()),
(10, 8,  'Materialize',   NOW(), NOW()),
-- JS Framework (q9)
(11, 9,  'React JS',   NOW(), NOW()),
(12, 9,  'Vue JS',     NOW(), NOW()),
(13, 9,  'Angular JS', NOW(), NOW()),
(14, 9,  'Svelte',     NOW(), NOW()),
-- CSS Framework (q10)
(15, 10, 'Tailwind CSS', NOW(), NOW()),
(16, 10, 'Bootstrap',    NOW(), NOW()),
(17, 10, 'Bulma',        NOW(), NOW()),
-- Tools (q11)
(18, 11, 'VS Code',    NOW(), NOW()),
(19, 11, 'Git',        NOW(), NOW()),
(20, 11, 'Docker',     NOW(), NOW()),
(21, 11, 'Postman',    NOW(), NOW()),
-- JS data types (q14)
(22, 14, 'String',    NOW(), NOW()),
(23, 14, 'Number',    NOW(), NOW()),
(24, 14, 'Boolean',   NOW(), NOW()),
(25, 14, 'Object',    NOW(), NOW()),
(26, 14, 'Symbol',    NOW(), NOW()),
-- JS runtime (q15)
(27, 15, 'Node.js',   NOW(), NOW()),
(28, 15, 'Deno',      NOW(), NOW()),
(29, 15, 'Bun',       NOW(), NOW()),
-- Available days (q17)
(30, 17, 'Monday',    NOW(), NOW()),
(31, 17, 'Tuesday',   NOW(), NOW()),
(32, 17, 'Wednesday', NOW(), NOW()),
(33, 17, 'Thursday',  NOW(), NOW()),
(34, 17, 'Friday',    NOW(), NOW()),
-- Preferred shift (q18)
(35, 18, 'Morning',   NOW(), NOW()),
(36, 18, 'Afternoon', NOW(), NOW()),
(37, 18, 'Evening',   NOW(), NOW());

SELECT setval('forms_id_seq',            (SELECT MAX(id) FROM forms));
SELECT setval('allowed_domains_id_seq',  (SELECT MAX(id) FROM allowed_domains));
SELECT setval('questions_id_seq',        (SELECT MAX(id) FROM questions));
SELECT setval('question_choices_id_seq', (SELECT MAX(id) FROM question_choices));
SELECT setval('submissions_id_seq',      (SELECT MAX(id) FROM submissions));
SELECT setval('answers_id_seq',          (SELECT MAX(id) FROM answers));