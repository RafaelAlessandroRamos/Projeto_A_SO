/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

/**
 *
 * @author Rafael
 */
public class RoundRobin {

    private ControlaListas controlaListas = new ControlaListas();
    private int tempoAtual = 0, flag = 0, quantum = 0, quantumMax = 4; // tempoAtual serve para saber o tempo atual no tempo de execução para saber se existe processos a serem executados naquele tempo

    public RoundRobin(ControlaListas controlaListas) {
        this.controlaListas = controlaListas;
        executar();
    }

    private void verificaTempo(int tempoAtual) {
        for (int i = 0; i < controlaListas.getListaProcessos().size(); i++) {
            if (tempoAtual == controlaListas.getListaProcessos().get(i).getTempoChegada()) {
                controlaListas.addFilaProntos(controlaListas.getListaProcessos().get(i)); // Adiciona um processo na lista de prontos
            }
        }
    }

    private void verificaQuantum() {
        if (quantum == quantumMax) {
            controlaListas.addFilaProntos(controlaListas.getExecutando());
            controlaListas.setExecutando(controlaListas.getFilaProntos().getFirst());
            controlaListas.getFilaProntos().removeFirst();
        }
    }

    public void executar() {
        do {
            verificaTempo(this.tempoAtual);
            for (int i = 0; i < controlaListas.getFilaProntos().size(); i++) {
                controlaListas.setExecutando(controlaListas.getFilaProntos().getFirst());
                controlaListas.getFilaProntos().removeFirst();
                this.flag++; // flag para chamar o processo do sistema
                if (this.flag == 3) { // a cada tres iterações o processo do sistema é chamado
                    controlaListas.setExecutando(procuraProcessoSistema(controlaListas.getExecutando()));
                    this.flag = 0;
                }
                processar(controlaListas.getExecutando());
            }
            this.tempoAtual++; // incrementa o tempo atual
        } while (true);
    }

    public void processar(Processo processo) {
        quantum++;
                verificaQuantum();
        if (processo.getTipo().equals("S")) { // Se o processo é do tipo Sistema(S)
            quantum = 0;
            if (!controlaListas.getFilaBloqueados().isEmpty()) {
                atenderBloqueado();
            }
        } else {
            if (processo.getFilaEntradaSaida().get(processo.getPc()) == 1) { // Se na posição pc estiver 1 o processo é bloqueado
                processo.setPc(processo.getPc() + 1); // pc+1 no processo
                bloqueado();
                quantum = 0;
            } else {
                processo.setPc(processo.getPc() + 1); // pc+1 no processo
            }
            if ((processo.getPc() == processo.getFase()-1) && (processo.getTipo().equals("U"))) { // Se acabou a lista de IO o processo encerra
                controlaListas.setExecutando(null);
                quantum = 0;
            } else if (processo.getPc() < processo.getFase()) {
                controlaListas.addFilaProntos(processo);
            }
            System.out.println(processo.toString());
        }
    }

    private void atenderBloqueado() {
        controlaListas.addFilaProntos(controlaListas.getFilaBloqueados().getFirst());
        controlaListas.getFilaBloqueados().removeFirst();
        controlaListas.addFilaProntos(controlaListas.getExecutando());
    }

    private void bloqueado() {
        controlaListas.addFilaBloqueados(controlaListas.getExecutando());
        controlaListas.setExecutando(null);
    }

    private Processo procuraProcessoSistema(Processo processo) {
        for (int i = 0; i < controlaListas.getFilaProntos().size(); i++) {
            if (controlaListas.getFilaProntos().get(i).getTipo().equals("S")) {
                processo = controlaListas.getFilaProntos().get(i);
                controlaListas.getFilaProntos().remove(i);
            }
        }
        return processo;
    }

    public ControlaListas getControlaListas() {
        return controlaListas;
    }

    public void setControlaListas(ControlaListas controlaListas) {
        this.controlaListas = controlaListas;
    }
}
