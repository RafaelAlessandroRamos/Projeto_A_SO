/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class Prioridade {
    private ControlaListas controlaListas = new ControlaListas();
    private List<Integer> listaProcessosIO = new LinkedList<>();
    private int pc, tempoAtual = 0; // tempoAtual serve para saber o tempo atual no tempo de execução para saber se existe processos a serem executados naquele tempo

    public Prioridade(ControlaListas controlaListas) {
        this.controlaListas = controlaListas;
        executar();
    }

    public void verificaTempo(int tempoAtual) {
        for (int i = 0; i < controlaListas.getListaProcessos().size(); i++) {
            if (tempoAtual == controlaListas.getListaProcessos().get(i).getTempoChegada()) {
                controlaListas.addFilaProntos(controlaListas.getListaProcessos().get(i));
                
             
            }
        }
    }

    public void executar() {
        do {
            verificaTempo(this.tempoAtual);
            if (!controlaListas.getFilaProntos().isEmpty()) { // condição para verificar se existe processo pronto para executar
                processar(controlaListas.getFilaProntos().get(0));
            }
            this.tempoAtual++; // incrementa o tempo atual
        } while (true);
    }

    public void processar(Processo processo) {
        if (processo.getTipo().equals("S")) { // Se o processo é do tipo Sistema(S)
            atenderBloqueado();
        } else {
            for (int i = 0; i < processo.getFilaEntradaSaida().size(); i++) { // laço para transferir a lista de IO do processo para a classe
                pc = processo.getPc(); // recebe o pc do primeiro processo da lista de prontos
                listaProcessosIO.add(processo.getFilaEntradaSaida().get(i));
                processo.setPc(processo.getPc() + 1); // pc+1 no processo
            }
            System.out.println(processo.toString());
            controlaListas.getFilaProntos().remove(0);
//            if (!controlaListas.getFilaProntos().isEmpty()) {
//                bloquado();
//            }

        }
    }

    public void atenderBloqueado() {
        controlaListas.addFilaProntos(controlaListas.getFilaBloqueados().get(0));
        controlaListas.getFilaBloqueados().remove(0);
    }

    public void bloqueado() {

    }
}
