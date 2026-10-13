/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dados;

import java.util.ArrayList;
import java.util.List;
import modelo.Cliente;
import modelo.PacoteViagem;
import modelo.Reserva;

public class Repositorio {
    public static final List<Cliente> clientes = new ArrayList<>();
    public static final List<PacoteViagem> pacotes = new ArrayList<>();
    public static final List<Reserva> reservas = new ArrayList<>();

    private static int proxCliente = 1;
    private static int proxPacote = 1;
    private static int proxReserva = 1;

    static {
        clientes.add(new Cliente(proxCliente++, "Maria Souza", "12345678901", "maria@email.com", "(11) 91234-5678"));
        clientes.add(new Cliente(proxCliente++, "João Pereira", "98765432100", "joao@email.com", "(11) 98888-7777"));
        pacotes.add(new PacoteViagem(proxPacote++, "Gramado - RS", "Hotel e café da manhã", 5, 2500.00, 20));
        pacotes.add(new PacoteViagem(proxPacote++, "Fortaleza - CE", "Passagem aérea e resort", 7, 3800.00, 15));
    }

    public static int novoIdCliente() { return proxCliente++; }
    public static int novoIdPacote() { return proxPacote++; }
    public static int novoIdReserva() { return proxReserva++; }
}
