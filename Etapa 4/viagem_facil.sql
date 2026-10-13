CREATE DATABASE IF NOT EXISTS viagemfacil CHARACTER SET utf8mb4;
USE viagemfacil;

CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf CHAR(11) NOT NULL UNIQUE,
    email VARCHAR(100),
    telefone VARCHAR(20)
);

CREATE TABLE pacote_viagem (
    id INT AUTO_INCREMENT PRIMARY KEY,
    destino VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    duracao_dias INT NOT NULL,
    preco_por_pessoa DECIMAL(10,2) NOT NULL,
    vagas_disponiveis INT NOT NULL
);

CREATE TABLE reserva (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_pacote INT NOT NULL,
    quantidade_pessoas INT NOT NULL,
    data_reserva DATE NOT NULL,
    status ENUM('PENDENTE','CONFIRMADA','CANCELADA') NOT NULL DEFAULT 'PENDENTE',
    FOREIGN KEY (id_cliente) REFERENCES cliente(id),
    FOREIGN KEY (id_pacote) REFERENCES pacote_viagem(id)
);

INSERT INTO cliente (nome, cpf, email, telefone) VALUES
('Maria Souza', '12345678901', 'maria@email.com', '(11) 91234-5678'),
('João Pereira', '98765432100', 'joao@email.com', '(11) 98888-7777'),
('Ana Lima', '11122233344', 'ana@email.com', '(21) 97777-6666');

INSERT INTO pacote_viagem (destino, descricao, duracao_dias, preco_por_pessoa, vagas_disponiveis) VALUES
('Gramado - RS', 'Hotel e café da manhã', 5, 2500.00, 18),
('Fortaleza - CE', 'Passagem aérea e resort', 7, 3800.00, 15),
('Bonito - MS', 'Ecoturismo com passeios', 4, 2100.00, 12);

INSERT INTO reserva (id_cliente, id_pacote, quantidade_pessoas, data_reserva, status) VALUES
(1, 1, 2, CURDATE(), 'CONFIRMADA');

-- SELECT * FROM cliente;