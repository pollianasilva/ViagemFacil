/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package visao;

import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class TelaPrincipal extends JFrame {
    public TelaPrincipal() {
        setTitle("ViagemFácil");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(480, 380);
        setLocationRelativeTo(null);

        JLabel titulo = new JLabel("ViagemFácil - Agência de Viagens", SwingConstants.CENTER);
        titulo.setFont(Estilo.FONTE_TITULO);
        titulo.setForeground(Estilo.PRIMARIA);

        JButton btnClientes = Estilo.botao("Clientes", Estilo.PRIMARIA, KeyEvent.VK_C);
        JButton btnPacotes = Estilo.botao("Pacotes", Estilo.PRIMARIA, KeyEvent.VK_P);
        JButton btnReservas = Estilo.botao("Reservas", Estilo.PRIMARIA, KeyEvent.VK_R);
        btnClientes.addActionListener(e -> new TelaClientes().setVisible(true));
        btnPacotes.addActionListener(e -> new TelaPacotes().setVisible(true));
        btnReservas.addActionListener(e -> new TelaReservas().setVisible(true));

        JPanel botoes = new JPanel(new GridLayout(3, 1, 10, 10));
        botoes.setOpaque(false);
        botoes.setBorder(BorderFactory.createEmptyBorder(20, 80, 20, 80));
        botoes.add(btnClientes);
        botoes.add(btnPacotes);
        botoes.add(btnReservas);

        JPanel raiz = Estilo.painelBase(new BorderLayout(10, 10));
        raiz.add(titulo, BorderLayout.NORTH);
        raiz.add(botoes, BorderLayout.CENTER);
        setContentPane(raiz);
    }
}