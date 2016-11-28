/*
 * Esta classse é responsáel por toda o escalonamento FIFO.
 */
package Main;

import java.util.concurrent.DelayQueue;

/**
 *
 * @author Rafael
 */
public class Fifo {

    private ControlaListas controlaListas = new ControlaListas();
    private int tempoAtual = 0; // tempoAtual serve para saber o tempo atual no tempo de execução para saber se existe processos a serem executados naquele tempo

    public Fifo(ControlaListas controlaListas) {
        this.controlaListas = controlaListas;
        executar();
    }

    public void verificaTempo(int tempoAtual) {
        for (int i = 0; i < controlaListas.getListaProcessos().size(); i++) {
            if (tempoAtual == controlaListas.getListaProcessos().get(i).getTempoChegada()) {
                controlaListas.addFilaProntos(controlaListas.getListaProcessos().get(i));
                controlaListas.getListaProcessos().remove(i);
            }
        }
        if (controlaListas.getListaProcessos().size() == 1) {
            controlaListas.getListaProcessos().removeFirst();
        }
    }

    public void executar() {
        do {
//            System.out.println("LISTA processo: " + controlaListas.getListaProcessos());
//            System.out.println("=======processo: " + controlaListas.getExecutando());
            if (controlaListas.getListaProcessos().isEmpty() && controlaListas.getExecutando() == null) {
                break;
            }
            verificaTempo(this.tempoAtual);
            this.tempoAtual++; // incrementa o tempo atual
            for (int i = 0; i < controlaListas.getFilaProntos().size(); i++) {
                controlaListas.setExecutando(controlaListas.getFilaProntos().getFirst());
                controlaListas.getFilaProntos().removeFirst();
                processar(controlaListas.getExecutando());
            }
        } while (true);
    }

    public void processar(Processo processo) {
        if (processo.getTipo().equals("S")) { // Se o processo é do tipo Sistema(S)
            if (!controlaListas.getFilaBloqueados().isEmpty()) {
                atenderBloqueado();
            }
        } else {
            if (processo.getFilaEntradaSaida().get(processo.getPc()) == 1) { // Se na posição pc estiver 1 o processo é bloqueado
                processo.setPc(processo.getPc() + 1); // pc+1 no processo
                bloqueado();
                atenderBloqueado();
            } else {
                processo.setPc(processo.getPc() + 1); // pc+1 no processo
            }
            if ((processo.getPc() <= processo.getFase()) && (processo.getTipo().equals("U"))) { // Se acabou a lista de IO o processo encerra
                controlaListas.setExecutando(null);
            }
            System.out.println(processo.toString());
        }
    }

    public void atenderBloqueado() {
        controlaListas.addFilaProntos(controlaListas.getFilaBloqueados().getFirst());
        controlaListas.getFilaBloqueados().removeFirst();
    }

    private void bloqueado() {
        controlaListas.addFilaBloqueados(controlaListas.getExecutando());
//        controlaListas.setExecutando(null);
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
}
