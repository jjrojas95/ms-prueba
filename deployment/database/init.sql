GRANT ALL PRIVILEGES ON DATABASE postgres TO docker;

\c postgres docker;

CREATE TABLE commerces (
    id serial PRIMARY KEY,
    name VARCHAR(50),
    document_number VARCHAR(50) NOT NULL,
    document_type VARCHAR(2) NOT NULL
    );

CREATE TABLE buttons (
    id serial PRIMARY KEY,
    hash VARCHAR(50) NOT NULL,
    name VARCHAR(50),
    commerce_id INT,
    CONSTRAINT commerce_id
      FOREIGN KEY(commerce_id)
      REFERENCES commerces(id)
    );

INSERT INTO commerces(name, document_number, document_type) VALUES
  ('menganito', '100000000', 'CC'),
  ('sultanito', '100000001', 'TI'),
  ('peranito', '100000002', 'RC'),
  ('merencejo', '100000003', 'CE'),
  ('juan', '100000004', 'CC');

INSERT INTO buttons(hash, name, commerce_id) VALUES
  ('HSF2RT', 'camisetas', '1'),
  ('HSF3RT', 'blusas', '1'),
  ('BCM3RT', 'botones', '3'),
  ('ISM3RT', 'casas', '2'),
  ('BCM2RT', 'reportes', '3'),
  ('PSF2RT', 'camisetas', '4'),
  ('BRM3RT', 'botones', '5'),
  ('PSF3RT', 'blusas', '4'),
  ('BRM2RT', 'reportes', '5');

