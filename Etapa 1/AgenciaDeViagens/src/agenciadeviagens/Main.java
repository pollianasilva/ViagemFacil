/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package agenciadeviagens;

import modelo.Cliente;
import modelo.PacoteViagem;
import modelo.Reserva;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ViagemFácil - Protótipo: Realizar Reserva ===\n");

        Cliente cliente = new Cliente(1, "Maria Souza", "12345678901",
                "maria@email.com", "(11) 91234-5678");
        PacoteViagem pacote = new PacoteViagem(1, "Gramado - RS",
                "Pacote com hotel e café da manhã", 5, 2500.00, 20);

        System.out.println("Cliente: " + cliente);
        System.out.println("Pacote:  " + pacote + "\n");

        // Reserva para 4 pessoas (aplica desconto)
        Reserva reserva = new Reserva(1, cliente, pacote, 4);
        System.out.println("Reserva criada. Status: " + reserva.getStatus());
        System.out.printf("Total com desconto: R$ %.2f%n", reserva.calcularTotal());
        System.out.println("Vagas restantes: " + pacote.getVagasDisponiveis());

        reserva.confirmar();
        System.out.println("Status após confirmar: " + reserva.getStatus());

        // Cancelamento devolve as vagas
        reserva.cancelar();
        System.out.println("Status após cancelar: " + reserva.getStatus());
        System.out.println("Vagas após cancelar: " + pacote.getVagasDisponiveis());
    }
}