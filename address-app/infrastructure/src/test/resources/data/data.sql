-- Insertion des données dans la table location
INSERT INTO locations (id, latitude, longitude) VALUES
   (1, 48.8566, 2.3522), -- Paris
   (2, 40.7128, -74.0060), -- New York
   (3, 35.6895, 139.6917), -- Tokyo
   (4, 51.5074, -0.1278), -- Londres
   (5, -33.8688, 151.2093) -- Sydney
;

-- Insertion des données dans la table users
INSERT INTO users (id, firstname, lastname, location_id, birth_date, phone, email, gender) VALUES
    ('a399569c-a8ce-46cf-aad8-cc96020157d7', 'John', 'Doe', 1, '1995-06-15', '0123456789', 'john.doe@example.com', 'M'),
    ('f6bea6fa-f582-447f-9243-3809bc63921e', 'Jane', 'Smith', 2, '1992-08-21', '0987654321', 'jane.smith@example.com', 'F'),
    ('80d918b1-7186-4f18-9d9d-f70f0a78bbc4', 'Alice', 'Johnson', 3, '1987-04-05', '0678901234', 'alice.johnson@example.com', 'F'),
    ('e5c8ec1c-72b4-45c5-a8c6-dc18864d63b0', 'Bob', 'Williams', 4, '2000-12-11', '0765432109', 'bob.williams@example.com', 'M'),
    ('15f39981-f729-4467-8208-281a347af969', 'Charlie', 'Brown', 5, '1985-02-28', '0456789012', 'charlie.brown@example.com', 'F');