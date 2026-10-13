/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Cliente;
import modelo.PacoteViagem;
import modelo.Reserva;
import modelo.StatusReserva;

public class ReservaDAO {

    // RN02 + RN05: baixa as vagas e grava a reserva como PENDENTE, numa transação
    public void inserir(Reserva r) throws SQLException {
        try (Connection con = Conexao.getConexao()) {
            con.setAutoCommit(false);
            try {
                String baixa = "UPDATE pacote_viagem SET vagas_disponiveis = vagas_disponiveis - ? "
                        + "WHERE id = ? AND vagas_disponiveis >= ?";
                try (PreparedStatement ps = con.prepareStatement(baixa)) {
                    ps.setInt(1, r.getQuantidadePessoas());
                    ps.setInt(2, r.getPacote().getId());
                    ps.setInt(3, r.getQuantidadePessoas());
                    if (ps.executeUpdate() == 0) {
                        throw new SQLException("Vagas insuficientes para este pacote.");
                    }
                }
                String ins = "INSERT INTO reserva (id_cliente, id_pacote, quantidade_pessoas, "
                        + "data_reserva, status) VALUES (?, ?, ?, ?, 'PENDENTE')";
                try (PreparedStatement ps = con.prepareStatement(ins)) {
                    ps.setInt(1, r.getCliente().getId());
                    ps.setInt(2, r.getPacote().getId());
                    ps.setInt(3, r.getQuantidadePessoas());
                    ps.setDate(4, Date.valueOf(r.getDataReserva()));
                    ps.executeUpdate();
                }
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    public void confirmar(int idReserva) throws SQLException {
        String sql = "UPDATE reserva SET status = 'CONFIRMADA' WHERE id = ? AND status = 'PENDENTE'";
        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idReserva);
            ps.executeUpdate();
        }
    }

    // RN04: cancela e devolve as vagas, numa transação
    public void cancelar(Reserva r) throws SQLException {
        try (Connection con = Conexao.getConexao()) {
            con.setAutoCommit(false);
            try {
                int alteradas;
                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE reserva SET status = 'CANCELADA' WHERE id = ? AND status <> 'CANCELADA'")) {
                    ps.setInt(1, r.getId());
                    alteradas = ps.executeUpdate();
                }
                if (alteradas > 0) {
                    try (PreparedStatement ps = con.prepareStatement(
                            "UPDATE pacote_viagem SET vagas_disponiveis = vagas_disponiveis + ? WHERE id = ?")) {
                        ps.setInt(1, r.getQuantidadePessoas());
                        ps.setInt(2, r.getPacote().getId());
                        ps.executeUpdate();
                    }
                }
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    public List<Reserva> listar() throws SQLException {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT r.id, r.quantidade_pessoas, r.data_reserva, r.status, "
                + "c.id AS cid, c.nome, c.cpf, c.email, c.telefone, "
                + "p.id AS pid, p.destino, p.descricao, p.duracao_dias, p.preco_por_pessoa, p.vagas_disponiveis "
                + "FROM reserva r "
                + "JOIN cliente c ON c.id = r.id_cliente "
                + "JOIN pacote_viagem p ON p.id = r.id_pacote ORDER BY r.id";
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente c = new Cliente(rs.getInt("cid"), rs.getString("nome"), rs.getString("cpf"),
                        rs.getString("email"), rs.getString("telefone"));
                PacoteViagem p = new PacoteViagem(rs.getInt("pid"), rs.getString("destino"),
                        rs.getString("descricao"), rs.getInt("duracao_dias"),
                        rs.getDouble("preco_por_pessoa"), rs.getInt("vagas_disponiveis"));
                lista.add(new Reserva(rs.getInt("id"), c, p, rs.getInt("quantidade_pessoas"),
                        rs.getDate("data_reserva").toLocalDate(),
                        StatusReserva.valueOf(rs.getString("status"))));
            }
        }
        return lista;
    }
}
