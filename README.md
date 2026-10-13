# ViagemFácil - Sistema de Agência de Viagens

## Status do projeto
🚧 Em desenvolvimento

## Objetivo do software
Sistema desktop para agências de viagens, que organiza o cadastro de clientes
e de pacotes de viagem e controla reservas e vagas, substituindo planilhas e
anotações em papel e evitando overbooking.

## Tecnologias utilizadas
- Java (aplicação desktop com Swing)
- NetBeans IDE
- MySQL e MySQL Workbench
- JDBC (MySQL Connector/J)
- Figma (projeto de interfaces)
- UML (diagrama de classes)
- Git e GitHub

## Time de desenvolvedores
- Polliana Silva

## Funcionalidades do sistema
- **RF01:** cadastrar, editar, listar e excluir clientes
- **RF02:** cadastrar, editar, listar e excluir pacotes de viagem
- **RF03:** realizar reserva de um pacote para um cliente
- **RF04:** confirmar e cancelar reservas
- **RF05:** consultar reservas por cliente

## Regras de negócio
- CPF com 11 dígitos e único por cliente
- Não é possível reservar mais pessoas que as vagas disponíveis
- Reservas para 4 ou mais pessoas têm 10% de desconto
- Ao cancelar uma reserva, as vagas voltam ao pacote

## Como executar
1. Execute o script `banco.sql` no MySQL Workbench para criar o banco `viagemfacil`.
2. Ajuste usuário e senha em `src/dao/Conexao.java`.
3. Confira se o driver `mysql-connector-j` (pasta `lib`) está em Libraries no NetBeans.
4. Abra o projeto no NetBeans e execute a classe `Main`.
