CREATE TABLE directories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    parent_id INT DEFAULT NULL,
    CONSTRAINT fk_parent_directory
    FOREIGN KEY (parent_id)
    REFERENCES directories(id)
    ON DELETE CASCADE
);

CREATE TABLE files (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    directory_id INT,
    CONSTRAINT fk_directory
    FOREIGN KEY (directory_id)
    REFERENCES directories(id)
    ON DELETE CASCADE
);

INSERT INTO directories (name, parent_id) VALUES ('Raiz', NULL);
INSERT INTO directories (name, parent_id) VALUES ('Documentos', 1);
INSERT INTO directories (name, parent_id) VALUES ('Imagens', 1);
INSERT INTO directories (name, parent_id) VALUES ('Trabalho', 2);

INSERT INTO files (name, directory_id) VALUES ('arquivo1.txt', 2);
INSERT INTO files (name, directory_id) VALUES ('imagem1.png', 3);
INSERT INTO files (name, directory_id) VALUES ('projeto.docx', 4);
