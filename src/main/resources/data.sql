INSERT INTO questions (content)
VALUES ('Ile to jest 2 + 2?');

INSERT INTO answers (content, correct, question_id)
VALUES
    ('3', false, 1),
    ('4', true, 1),
    ('5', false, 1),
    ('22', false, 1);
