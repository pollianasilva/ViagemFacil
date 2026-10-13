/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

public class PacoteViagem {
    private int id;
    private String destino;
    private String descricao;
    private int duracaoDias;
    private double precoPorPessoa;
    private int vagasDisponiveis;

    public PacoteViagem(int id, String destino, String descricao,
                        int duracaoDias, double precoPorPessoa, int vagasDisponiveis) {
        this.id = id;
        this.destino = destino;
        this.descricao = descricao;
        this.duracaoDias = duracaoDias;
        this.precoPorPessoa = precoPorPessoa;
        this.vagasDisponiveis = vagasDisponiveis;
    }

    // RN02: só reserva se houver vagas suficientes
    public boolean reservarVagas(int quantidade) {
        if (quantidade <= 0 || quantidade > vagasDisponiveis) {
            return false;
        }
        vagasDisponiveis -= quantidade;
        return true;
    }

    // RN04: devolve as vagas ao cancelar
    public void devolverVagas(int quantidade) {
        vagasDisponiveis += quantidade;
    }

    public int getId() { return id; }
    public String getDestino() { return destino; }
    public String getDescricao() { return descricao; }
    public int getDuracaoDias() { return duracaoDias; }
    public double getPrecoPorPessoa() { return precoPorPessoa; }
    public int getVagasDisponiveis() { return vagasDisponiveis; }

    @Override
    public String toString() {
        return destino + " - " + duracaoDias + " dias - R$ " + String.format("%.2f", precoPorPessoa)
                + "/pessoa (" + vagasDisponiveis + " vagas)";
    }
}
