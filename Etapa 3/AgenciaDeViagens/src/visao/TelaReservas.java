/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package visao;

import dados.Repositorio;
import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import modelo.PacoteViagem;
import modelo.Reserva;

public class TelaReservas extends JFrame {
    private final JComboBox<Cliente> cmbCliente = new JComboBox<>();
    private final JComboBox<PacoteViagem> cmbPacote = new JComboBox<>();
    private final JSpinner spnPessoas = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
    private final DefaultTableModel modelo =
            Estilo.modeloTabela("ID", "Cliente", "Destino", "Pessoas", "Total", "Status");
    private final JTable tabela = new JTable(modelo);
    private java.util.List<Reserva> lista = new java.util.ArrayList<>();

    public TelaReservas() {
        setTitle("ViagemFácil - Reservas");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(900, 560);
        setLocationRelativeTo(null);

        JLabel titulo = new JLabel("Reservas");
        titulo.setFont(Estilo.FONTE_TITULO);
        titulo.setForeground(Estilo.PRIMARIA);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        Estilo.linha(form, "Cliente:", cmbCliente, 0);
        Estilo.linha(form, "Pacote:", cmbPacote, 1);
        Estilo.linha(form, "Nº de pessoas:", spnPessoas, 2);

        JButton btnReservar = Estilo.botao("Reservar", Estilo.PRIMARIA, KeyEvent.VK_R);
        JButton btnConfirmar = Estilo.botao("Confirmar", Estilo.PRIMARIA, KeyEvent.VK_C);
        JButton btnCancelar = Estilo.botao("Cancelar reserva", Estilo.DESTAQUE, KeyEvent.VK_A);
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoes.setOpaque(false);
        botoes.add(btnReservar);
        botoes.add(btnConfirmar);
        botoes.add(btnCancelar);

        JPanel topo = new JPanel(new BorderLayout());
        topo.setOpaque(false);
        topo.add(titulo, BorderLayout.NORTH);
        topo.add(form, BorderLayout.CENTER);
        topo.add(botoes, BorderLayout.SOUTH);

        tabela.setFont(Estilo.FONTE);
        tabela.setRowHeight(26);
        JPanel raiz = Estilo.painelBase(new BorderLayout(10, 10));
        raiz.add(topo, BorderLayout.NORTH);
        raiz.add(new JScrollPane(tabela), BorderLayout.CENTER);
        setContentPane(raiz);

        btnReservar.addActionListener(e -> reservar());
        btnConfirmar.addActionListener(e -> confirmar());
        btnCancelar.addActionListener(e -> cancelar());
        carregarCombos();
        carregar();
    }

    private void carregarCombos() {
        cmbCliente.removeAllItems();
        for (Cliente c : Repositorio.clientes) cmbCliente.addItem(c);
        cmbPacote.removeAllItems();
        for (PacoteViagem p : Repositorio.pacotes) cmbPacote.addItem(p);
    }

    private void carregar() {
        lista = new java.util.ArrayList<>(Repositorio.reservas);
        modelo.setRowCount(0);
        for (Reserva r : lista) {
            modelo.addRow(new Object[]{r.getId(), r.getCliente().getNome(), r.getPacote().getDestino(),
                r.getQuantidadePessoas(), String.format("R$ %.2f", r.calcularTotal()), r.getStatus()});
        }
    }

    private void atualizar() {
        carregarCombos();
        carregar();
    }

    private void reservar() {
        Cliente c = (Cliente) cmbCliente.getSelectedItem();
        PacoteViagem p = (PacoteViagem) cmbPacote.getSelectedItem();
        if (c == null || p == null) {
            Estilo.aviso(this, "Cadastre ao menos um cliente e um pacote.");
            return;
        }
        try {
            Repositorio.reservas.add(new Reserva(Repositorio.novoIdReserva(), c, p,
                    (Integer) spnPessoas.getValue()));
            atualizar();
        } catch (IllegalStateException ex) {
            Estilo.aviso(this, ex.getMessage());
        }
    }

    private Reserva reservaSelecionada() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            Estilo.aviso(this, "Selecione uma reserva na tabela.");
            return null;
        }
        return lista.get(linha);
    }

    private void confirmar() {
        Reserva r = reservaSelecionada();
        if (r != null) {
            r.confirmar();
            atualizar();
        }
    }

    private void cancelar() {
        Reserva r = reservaSelecionada();
        if (r != null) {
            r.cancelar();
            atualizar();
        }
    }
}
