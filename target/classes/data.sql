-- Clear tables (optional)
DELETE FROM books;
DELETE FROM users;

-- Insert users
INSERT INTO users (user_id, user_name) VALUES (1, 'Alice');
INSERT INTO users (user_id, user_name) VALUES (2, 'Bob');

-- Insert books (checked_out_by can be null initially)
INSERT INTO books (book_id, title, author, is_available, checked_out_by)
VALUES (1, 'Clean Code', 'Robert C. Martin', true, NULL);

INSERT INTO books (book_id, title, author, is_available, checked_out_by)
VALUES (2, 'Effective Java', 'Joshua Bloch', true, NULL);

INSERT INTO books (book_id, title, author, is_available, checked_out_by)
VALUES (3, 'Spring in Action', 'Craig Walls', true, NULL);
