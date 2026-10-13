/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package visao;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Estilo {
    public static final Color PRIMARIA = new Color(0x0D47A1);
    public static final Color DESTAQUE = new Color(0xBF360C);
    public static final Color FUNDO = new Color(0xF5F7FA);
    public static final Color TEXTO = new Color(0x212121);
    public static final Font FONTE = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONTE_TITULO = new Font("Segoe UI", Font.BOLD, 22);

    public static JButton botao(String texto, Color cor, int atalho) {
        JButton b = new JButton(texto);
        b.setBackground(cor);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setMnemonic(atalho);
        b.setPreferredSize(new Dimension(150, 40));
        return b;
    }

    public static JPanel painelBase(LayoutManager layout) {
        JPanel p = new JPanel(layout);
        p.setBackground(FUNDO);
        p.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        return p;
    }

    public static void linha(JPanel painel, String rotulo, JComponent campo, int y) {
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.gridy = y;
        g.gridx = 0;
        g.anchor = GridBagConstraints.WEST;
        JLabel l = new JLabel(rotulo);
        l.setFont(FONTE);
        l.setForeground(TEXTO);
        l.setLabelFor(campo);
        painel.add(l, g);
        campo.setFont(FONTE);
        g.gridx = 1;
        g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painel.add(campo, g);
    }

    public static DefaultTableModel modeloTabela(String... colunas) {
        return new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
    }

    public static void aviso(Component pai, String msg) {
        JOptionPane.showMessageDialog(pai, msg, "Atenção", JOptionPane.WARNING_MESSAGE);
    }
}
