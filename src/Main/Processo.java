/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.util.LinkedList;

/**
 *
 * @author Rafael
 */
public class Processo {

    private int id, fase, prioridade, tempoChegada, pc, tempoEspera = 0, tempoPreempcao = 0;
    private String tipo, io = ""; // Usuario (U) ou sistema(S)
    private LinkedList<Integer> filaEntradaSaida = new LinkedList<>(); // mapa do processo

    public Processo() {
    }
    
    public Processo(int id, int fase, int prioridade, int tempoChegada, String tipo) {
        this.id = id;
        this.fase = fase;
        this.prioridade = prioridade;
        this.tempoChegada = tempoChegada;
        this.pc = 0;
        for (int i = 0; i < fase; i++) {
            filaEntradaSaida.add(0);
        }
        this.tipo = tipo;
    }

    public Processo(int id, int fase, int prioridade, int tempoChegada) {
        this.id = id;
        this.fase = fase;
        this.prioridade = prioridade;
        this.tempoChegada = tempoChegada;
        this.pc = 0;
        for (int i = 0; i < fase; i++) {
            filaEntradaSaida.add(0);
        }
        this.tipo = "U";
    }

    public void addFilaEntradaSaida(LinkedList<Integer> io) { // lista com as posições de entrada e saida como parametro
        for (int i = 0, j = 0; (i < fase) && (j < io.size()); i++) {
            if (i == io.get(j)) {
                filaEntradaSaida.set(i, 1);
                j++;
            }
        }
    }

    public int getTempoEspera() {
        return tempoEspera;
    }

    public void setTempoEspera(int tempoEspera) {
        this.tempoEspera = tempoEspera;
    }

    public String getIo() {
        return io;
    }

    public void setIo(String io) {
        this.io = io;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFase() {
        return fase;
    }

    public void setFase(int fase) {
        this.fase = fase;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public int getTempoChegada() {
        return tempoChegada;
    }

    public void setTempoChegada(int tempoChegada) {
        this.tempoChegada = tempoChegada;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public String getTipo() {
        return tipo;
    }

    public int getTempoPreempcao() {
        return tempoPreempcao;
    }

    public void setTempoPreempcao(int tempoPreempcao) {
        this.tempoPreempcao = tempoPreempcao;
    }

    public void setTipo(char tipo) {
        this.tipo = String.valueOf(tipo).toUpperCase();
    }

    public LinkedList<Integer> getFilaEntradaSaida() {
        return filaEntradaSaida;
    }

    public void setFilaEntradaSaida(LinkedList<Integer> FilaEntradaSaida) {
        this.filaEntradaSaida = FilaEntradaSaida;
    }
    
    public void imprimeFilaIO() {
        io = "";
        for (int i = pc; i < fase; i++) {
            io += filaEntradaSaida.get(i);
//            System.out.print(filaEntradaSaida.get(i));
        }
//            System.out.print("\n");
    }
    
    @Override
    public String toString() {
        imprimeFilaIO();
        return "Processo{" + "id = " + id + ", fase = " + fase + ", prioridade = " + prioridade + ", tempoChegada = " + tempoChegada + ", PC= " + pc + ", tipo = " + tipo + ", listaIO = " + getFilaEntradaSaida() + ", io = " + io +'}';
    }

}
