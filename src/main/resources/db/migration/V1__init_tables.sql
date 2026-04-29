CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE forms (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       slug VARCHAR(255) UNIQUE NOT NULL,
                       description TEXT,
                       limit_one_response BOOLEAN DEFAULT FALSE,
                       creator_id BIGINT NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                       CONSTRAINT fk_forms_creator
                           FOREIGN KEY (creator_id)
                               REFERENCES users(id)
                               ON DELETE CASCADE
);

CREATE TABLE allowed_domains (
                                 id BIGSERIAL PRIMARY KEY,
                                 form_id BIGINT NOT NULL,
                                 domain VARCHAR(100) NOT NULL,
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                 CONSTRAINT fk_domains_form
                                     FOREIGN KEY (form_id)
                                         REFERENCES forms(id)
                                         ON DELETE CASCADE
);

CREATE TABLE questions (
                           id BIGSERIAL PRIMARY KEY,
                           form_id BIGINT NOT NULL,
                           name VARCHAR(255) NOT NULL,
                           choice_type VARCHAR(50) NOT NULL,
                           is_required BOOLEAN DEFAULT FALSE,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                           CONSTRAINT fk_questions_form
                               FOREIGN KEY (form_id)
                                   REFERENCES forms(id)
                                   ON DELETE CASCADE
);

CREATE TABLE question_choices (
                                  id BIGSERIAL PRIMARY KEY,
                                  question_id BIGINT NOT NULL,
                                  value VARCHAR(255) NOT NULL,
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                  CONSTRAINT fk_choices_question
                                      FOREIGN KEY (question_id)
                                          REFERENCES questions(id)
                                          ON DELETE CASCADE
);

CREATE TABLE submissions (
                             id BIGSERIAL PRIMARY KEY,
                             form_id BIGINT NOT NULL,
                             user_id BIGINT NOT NULL,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                             CONSTRAINT fk_submissions_form
                                 FOREIGN KEY (form_id)
                                     REFERENCES forms(id)
                                     ON DELETE CASCADE,

                             CONSTRAINT fk_submissions_user
                                 FOREIGN KEY (user_id)
                                     REFERENCES users(id)
                                     ON DELETE CASCADE
);

CREATE TABLE answers (
                         id BIGSERIAL PRIMARY KEY,
                         submission_id BIGINT NOT NULL,
                         question_id BIGINT NOT NULL,
                         value TEXT NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                         CONSTRAINT fk_answers_submission
                             FOREIGN KEY (submission_id)
                                 REFERENCES submissions(id)
                                 ON DELETE CASCADE,

                         CONSTRAINT fk_answers_question
                             FOREIGN KEY (question_id)
                                 REFERENCES questions(id)
                                 ON DELETE CASCADE
);