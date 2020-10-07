CREATE DATABASE sistema_assistencia_tecnica ENCODING = 'UTF8';	--utf8 aceita as acentuações do brasil

CREATE TABLE enderecos(
	id_endereco serial primary key,
	cep varchar(9),
	estado varchar not null,
	cidade varchar not null,
	logradouro varchar,
	bairro varchar,
	complemento varchar,
	numero_propriedade integer
);

CREATE TABLE cargos(
	id_cargo serial primary key,
	nome varchar not null,
	descricao varchar,
	salario numeric(7,2)
);

CREATE TABLE usuarios (
	id_usuario serial primary key,
	nome varchar not null,
	cpf varchar (14) not null UNIQUE,
	data_nascimento date not null, --https://stackoverflow.com/questions/409286/should-i-use-the-datetime-or-timestamp-data-type-in-mysql
	telefone_celular varchar(15),
	telefone_fixo varchar(14),
	email varchar not null,
	senha varchar not null,
	cargo_id integer REFERENCES cargos(id_cargo),
	endereco_id integer REFERENCES enderecos(id_endereco),
	data_cadastro date not null default now(), 	--https://stackoverflow.com/questions/5818423/set-now-as-default-value-for-datetime-datatype
	constraint chk_telefones_null check (telefone_celular is not null or telefone_fixo is not null) -- https://stackoverflow.com/questions/26102456/i-need-a-check-constraint-on-two-columns-at-least-one-must-be-not-null
);

CREATE TABLE clientes(
	id_cliente serial primary key,
	nome varchar not null,
	cpf varchar(14) UNIQUE,
	email varchar,
	telefone_celular varchar(15),
	telefone_fixo varchar(14),
	endereco_id integer REFERENCES enderecos (id_endereco),
	data_cadastro date not null default now(),
	constraint chk_telefones_null check (telefone_celular is not null or telefone_fixo is not null or email is not null) -- https://stackoverflow.com/questions/26102456/i-need-a-check-constraint-on-two-columns-at-least-one-must-be-not-null
);

CREATE TABLE fabricantes(
	id_fabricante serial primary key,
	nome varchar not null,
	email varchar,
	telefone varchar,
	site varchar
);

CREATE TABLE tipos_pecas(
	id_tipo_peca serial primary key,
	nome varchar NOT NULL,
	descricao varchar
);

CREATE TABLE pecas(
	id_peca serial primary key,
	nome varchar NOT NULL,
	preco numeric(7,2),
	descricao varchar,
	quantidade integer,
	tipo_peca_id integer REFERENCES tipos_pecas(id_tipo_peca),
	fabricante_id integer REFERENCES fabricantes(id_fabricante)
);

CREATE TABLE tipos_pagamento(
	id_tipo_pagamento serial primary key,
	nome varchar NOT NULL UNIQUE,
	descricao varchar
);

CREATE TABLE pagamentos(
	id_pagamento serial primary key,
	tipo_pagamento_id integer REFERENCES tipos_pagamento(id_tipo_pagamento) NOT NULL,
	parcelas integer not null DEFAULT 1,
	valor_parcelas numeric(7,2),
	valor_total numeric(7,2) NOT NULL,
	data_registro date not null default now()
);

CREATE TABLE ordens_servicos(
	id_ordem_servico serial primary key,
	status varchar,
	cliente_id integer REFERENCES clientes(id_cliente) NOT NULL,
	descricao_problema varchar NOT NULL,
	usuario_id integer REFERENCES usuarios(id_usuario),
	descricao_solucao varchar,
	pecas_usadas varchar,
	pagamento_id integer REFERENCES pagamentos(id_pagamento),
	data_registro date not null default now()
);

-- Inserts
INSERT INTO cargos (nome,descricao,salario) VALUES ('Administrador','Acesso a todas funcionalidades do sistema', 10000.00);
INSERT INTO cargos (nome,descricao,salario) VALUES ('Técnico','Acesso aos registros de ordem de serviço e cadastro de cliente', 3000.00);
INSERT INTO cargos (nome,descricao,salario) VALUES ('Atendente','Acesso aos registros de ordem de serviço e cadastro de cliente', 2000.00);

INSERT INTO enderecos (id_endereco, cep, estado, cidade, logradouro, bairro, complemento, numero_propriedade) VALUES (1,'57051-000','Alagoas','Maceió','Avenida tomás espíndola','Farol','Bem ali',20);
INSERT INTO usuarios (nome, cpf, data_nascimento, telefone_celular, telefone_fixo, email, senha, cargo_id, endereco_id) VALUES ('Administrador','123.123.123-12','01/01/1990','(82) 91234-1234','','admin@admin.com','admin',1,1);
SELECT setval('enderecos_id_endereco_seq', 2);	-- Por anteriormente ter feito um insert de um endereço incluindo o ID, ele não atualizou a sequência da primary key (id_endereco), então isso foi feito manualmente nessa linha de código.
