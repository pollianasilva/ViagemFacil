/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.PacoteViagem;

public class PacoteDAO {

    public void inserir(PacoteViagem p) throws SQLException {
        String sql = "INSERT INTO pacote_viagem (destino, descricao, duracao_dias, "
                + "preco_por_pessoa, vagas_disponiveis) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getDestino());
            ps.setString(2, p.getDescricao());
            ps.setInt(3, p.getDuracaoDias());
            ps.setDouble(4, p.getPrecoPorPessoa());
            ps.setInt(5, p.getVagasDisponiveis());
            ps.executeUpdate();
        }
    }

    public void atualizar(PacoteViagem p) throws SQLException {
        String sql = "UPDATE pacote_viagem SET destino = ?, descricao = ?, duracao_dias = ?, "
                + "preco_por_pessoa = ?, vagas_disponiveis = ? WHERE id = ?";
        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getDestino());
            ps.setString(2, p.getDescricao());
            ps.setInt(3, p.getDuracaoDias());
            ps.setDouble(4, p.getPrecoPorPessoa());
            ps.setInt(5, p.getVagasDisponiveis());
            ps.setInt(6, p.getId());
            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement("DELETE FROM pacote_viagem WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<PacoteViagem> listar() throws SQLException {
        List<PacoteViagem> lista = new ArrayList<>();
        String sql = "SELECT id, destino, descricao, duracao_dias, preco_por_pessoa, "
                + "vagas_disponiveis FROM pacote_viagem ORDER BY destino";
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new PacoteViagem(rs.getInt("id"), rs.getString("destino"),
                        rs.getString("descricao"), rs.getInt("duracao_dias"),
                        rs.getDouble("preco_por_pessoa"), rs.getInt("vagas_disponiveis")));
            }
        }
        return lista;
    }
}

