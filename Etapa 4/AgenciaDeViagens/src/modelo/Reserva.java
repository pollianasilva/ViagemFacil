/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;

public class Reserva {

    private int id;
    private Cliente cliente;
    private PacoteViagem pacote;
    private int quantidadePessoas;
    private LocalDate dataReserva;
    private StatusReserva status;

    public Reserva(int id, Cliente cliente, PacoteViagem pacote, int quantidadePessoas) {
        if (!pacote.reservarVagas(quantidadePessoas)) {
            throw new IllegalStateException("Vagas insuficientes para este pacote.");
        }
        this.id = id;
        this.cliente = cliente;
        this.pacote = pacote;
        this.quantidadePessoas = quantidadePessoas;
        this.dataReserva = LocalDate.now();
        this.status = StatusReserva.PENDENTE; // RN05
    }

    public Reserva(int id, Cliente cliente, PacoteViagem pacote, int quantidadePessoas,
            LocalDate dataReserva, StatusReserva status) {
        this.id = id;
        this.cliente = cliente;
        this.pacote = pacote;
        this.quantidadePessoas = quantidadePessoas;
        this.dataReserva = dataReserva;
        this.status = status;
    }

    // RN03: 10% de desconto para 4 ou mais pessoas
    public double calcularTotal() {
        double total = pacote.getPrecoPorPessoa() * quantidadePessoas;
        if (quantidadePessoas >= 4) {
            total *= 0.90;
        }
        return total;
    }

    public void confirmar() {
        if (status == StatusReserva.PENDENTE) {
            status = StatusReserva.CONFIRMADA;
        }
    }

    public void cancelar() {
        if (status != StatusReserva.CANCELADA) {
            status = StatusReserva.CANCELADA;
            pacote.devolverVagas(quantidadePessoas);
        }
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public PacoteViagem getPacote() {
        return pacote;
    }

    public int getQuantidadePessoas() {
        return quantidadePessoas;
    }

    public LocalDate getDataReserva() {
        return dataReserva;
    }

    public StatusReserva getStatus() {
        return status;
    }

}
  

