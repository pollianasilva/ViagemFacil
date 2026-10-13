/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package visao;

import dao.PacoteDAO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.PacoteViagem;

public class TelaPacotes extends JFrame {
    private final JTextField txtDestino = new JTextField(25);
    private final JTextField txtDescricao = new JTextField(25);
    private final JSpinner spnDias = new JSpinner(new SpinnerNumberModel(5, 1, 90, 1));
    private final JTextField txtPreco = new JTextField(10);
    private final JSpinner spnVagas = new JSpinner(new SpinnerNumberModel(10, 0, 500, 1));
    private final DefaultTableModel modelo =
            Estilo.modeloTabela("ID", "Destino", "Descrição", "Dias", "Preço/pessoa", "Vagas");
    private final JTable tabela = new JTable(modelo);
    private final PacoteDAO dao = new PacoteDAO();
    private java.util.List<PacoteViagem> lista = new java.util.ArrayList<>();
    private int idSelecionado = 0;

    public TelaPacotes() {
        setTitle("ViagemFácil - Pacotes");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(860, 580);
        setLocationRelativeTo(null);

        JLabel titulo = new JLabel("Cadastro de Pacotes de Viagem");
        titulo.setFont(Estilo.FONTE_TITULO);
        titulo.setForeground(Estilo.PRIMARIA);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        Estilo.linha(form, "Destino:", txtDestino, 0);
        Estilo.linha(form, "Descrição:", txtDescricao, 1);
        Estilo.linha(form, "Duração (dias):", spnDias, 2);
        Estilo.linha(form, "Preço por pessoa (R$):", txtPreco, 3);
        Estilo.linha(form, "Vagas disponíveis:", spnVagas, 4);

        JButton btnSalvar = Estilo.botao("Salvar", Estilo.PRIMARIA, KeyEvent.VK_S);
        JButton btnExcluir = Estilo.botao("Excluir", Estilo.DESTAQUE, KeyEvent.VK_E);
        JButton btnLimpar = Estilo.botao("Limpar", Color.DARK_GRAY, KeyEvent.VK_L);
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoes.setOpaque(false);
        botoes.add(btnSalvar);
        botoes.add(btnExcluir);
        botoes.add(btnLimpar);

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

        btnSalvar.addActionListener(e -> salvar());
        btnExcluir.addActionListener(e -> excluir());
        btnLimpar.addActionListener(e -> limpar());
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) selecionar();
        });
        carregar();
    }

    private void carregar() {
        modelo.setRowCount(0);
        try {
            lista = dao.listar();
            for (PacoteViagem p : lista) {
                modelo.addRow(new Object[]{p.getId(), p.getDestino(), p.getDescricao(), p.getDuracaoDias(),
                    String.format("R$ %.2f", p.getPrecoPorPessoa()), p.getVagasDisponiveis()});
            }
        } catch (SQLException ex) {
            Estilo.aviso(this, "Erro ao acessar o banco: " + ex.getMessage());
        }
    }

    private void selecionar() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) return;
        PacoteViagem p = lista.get(linha);
        idSelecionado = p.getId();
        txtDestino.setText(p.getDestino());
        txtDescricao.setText(p.getDescricao());
        spnDias.setValue(p.getDuracaoDias());
        txtPreco.setText(String.valueOf(p.getPrecoPorPessoa()));
        spnVagas.setValue(p.getVagasDisponiveis());
    }

    private void limpar() {
        idSelecionado = 0;
        txtDestino.setText("");
        txtDescricao.setText("");
        spnDias.setValue(5);
        txtPreco.setText("");
        spnVagas.setValue(10);
        tabela.clearSelection();
    }

    private void salvar() {
        String destino = txtDestino.getText().trim();
        if (destino.isBlank()) {
            Estilo.aviso(this, "Destino é obrigatório.");
            return;
        }
        double preco;
        try {
            preco = Double.parseDouble(txtPreco.getText().trim().replace(",", "."));
        } catch (NumberFormatException ex) {
            Estilo.aviso(this, "Informe um preço válido (ex.: 2500,00).");
            return;
        }
        if (preco <= 0) {
            Estilo.aviso(this, "O preço deve ser maior que zero.");
            return;
        }
        String descricao = txtDescricao.getText().trim();
        int dias = (Integer) spnDias.getValue();
        int vagas = (Integer) spnVagas.getValue();

        try {
            PacoteViagem pacote = new PacoteViagem(idSelecionado, destino, descricao, dias, preco, vagas);
            if (idSelecionado == 0) {
                dao.inserir(pacote);
            } else {
                dao.atualizar(pacote);
            }
            carregar();
            limpar();
        } catch (SQLException ex) {
            Estilo.aviso(this, "Erro ao acessar o banco: " + ex.getMessage());
        }
    }

    private void excluir() {
        if (idSelecionado == 0) {
            Estilo.aviso(this, "Selecione um pacote na tabela.");
            return;
        }
        int op = JOptionPane.showConfirmDialog(this, "Excluir o pacote selecionado?",
                "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (op != JOptionPane.YES_OPTION) return;
        try {
            dao.excluir(idSelecionado);
            carregar();
            limpar();
        } catch (SQLIntegrityConstraintViolationException ex) {
            Estilo.aviso(this, "Este pacote possui reservas e não pode ser excluído.");
        } catch (SQLException ex) {
            Estilo.aviso(this, "Erro ao acessar o banco: " + ex.getMessage());
        }
    }
}