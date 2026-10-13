/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package visao;

import dao.ClienteDAO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;

public class TelaClientes extends JFrame {
    private final JTextField txtNome = new JTextField(25);
    private final JTextField txtCpf = new JTextField(11);
    private final JTextField txtEmail = new JTextField(25);
    private final JTextField txtTelefone = new JTextField(15);
    private final DefaultTableModel modelo =
            Estilo.modeloTabela("ID", "Nome", "CPF", "E-mail", "Telefone");
    private final JTable tabela = new JTable(modelo);
    private final ClienteDAO dao = new ClienteDAO();
    private java.util.List<Cliente> lista = new java.util.ArrayList<>();
    private int idSelecionado = 0;

    public TelaClientes() {
        setTitle("ViagemFácil - Clientes");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 540);
        setLocationRelativeTo(null);

        JLabel titulo = new JLabel("Cadastro de Clientes");
        titulo.setFont(Estilo.FONTE_TITULO);
        titulo.setForeground(Estilo.PRIMARIA);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        Estilo.linha(form, "Nome:", txtNome, 0);
        Estilo.linha(form, "CPF (11 dígitos):", txtCpf, 1);
        Estilo.linha(form, "E-mail:", txtEmail, 2);
        Estilo.linha(form, "Telefone:", txtTelefone, 3);

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
            for (Cliente c : lista) {
                modelo.addRow(new Object[]{c.getId(), c.getNome(), c.getCpf(), c.getEmail(), c.getTelefone()});
            }
        } catch (SQLException ex) {
            Estilo.aviso(this, "Erro ao acessar o banco: " + ex.getMessage());
        }
    }

    private void selecionar() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) return;
        Cliente c = lista.get(linha);
        idSelecionado = c.getId();
        txtNome.setText(c.getNome());
        txtCpf.setText(c.getCpf());
        txtEmail.setText(c.getEmail());
        txtTelefone.setText(c.getTelefone());
        txtCpf.setEnabled(false); // CPF não muda na edição
    }

    private void limpar() {
        idSelecionado = 0;
        txtNome.setText("");
        txtCpf.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtCpf.setEnabled(true);
        tabela.clearSelection();
    }

    private void salvar() {
        try {
            String nome = txtNome.getText().trim();
            String cpf = txtCpf.getText().trim();
            String email = txtEmail.getText().trim();
            String telefone = txtTelefone.getText().trim();
            if (idSelecionado == 0) {
                dao.inserir(new Cliente(0, nome, cpf, email, telefone));
            } else {
                if (nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
                dao.atualizar(new Cliente(idSelecionado, nome, cpf, email, telefone));
            }
            carregar();
            limpar();
        } catch (IllegalArgumentException ex) {
            Estilo.aviso(this, ex.getMessage());
        } catch (SQLIntegrityConstraintViolationException ex) {
            Estilo.aviso(this, "CPF já cadastrado.");
        } catch (SQLException ex) {
            Estilo.aviso(this, "Erro ao acessar o banco: " + ex.getMessage());
        }
    }

    private void excluir() {
        if (idSelecionado == 0) {
            Estilo.aviso(this, "Selecione um cliente na tabela.");
            return;
        }
        int op = JOptionPane.showConfirmDialog(this, "Excluir o cliente selecionado?",
                "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (op != JOptionPane.YES_OPTION) return;
        try {
            dao.excluir(idSelecionado);
            carregar();
            limpar();
        } catch (SQLIntegrityConstraintViolationException ex) {
            Estilo.aviso(this, "Este cliente possui reservas e não pode ser excluído.");
        } catch (SQLException ex) {
            Estilo.aviso(this, "Erro ao acessar o banco: " + ex.getMessage());
        }
    }
}