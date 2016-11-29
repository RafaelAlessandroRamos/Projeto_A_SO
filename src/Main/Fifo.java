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
    }

    public void executar() {
        do {
            //System.out.println(" ========LISTA PRONTOS     " + controlaListas.getFilaProntos());
            //System.out.println(" ---------LISTA BLOQUEADOS  " + controlaListas.getFilaBloqueados());
            //System.out.println(" **********EXECULTANDO  " + controlaListas.getExecutando());
            if (controlaListas.getListaProcessos().isEmpty() && controlaListas.getFilaBloqueados().isEmpty() && controlaListas.getFilaProntos().isEmpty() && controlaListas.getExecutando() == null) { // condição de parada: se tudo estiver vazio para o laço
                break;
            }
            verificaTempo(this.tempoAtual);
            if (!controlaListas.getFilaProntos().isEmpty()) { // se a fila de pronto nao estiver vazia
                controlaListas.setExecutando(controlaListas.getFilaProntos().getFirst()); // pega o primeiro processo da lista de pronto para executar
            }
            processar(controlaListas.getExecutando());
            this.tempoAtual++; // incrementa o tempo atual
        } while (true);
    }

    public void processar(Processo processo) {
        if (processo.getTipo().equals("S")) { // Se o processo é do tipo Sistema(S)
            System.out.println("-------- SISTEMA ---------");
            if (!controlaListas.getFilaBloqueados().isEmpty()) {
                atenderBloqueado(); // desbloqueia o primeiro processo da fila de bloqueados
            }
            controlaListas.setExecutando(null);
        }
        if (processo.getPc() < processo.getFase() && processo.getTipo().equals("U")) { // se o pc é menor que o tamanho do processo
            processo.setPc(processo.getPc()+1);
            System.out.println(processo.toString());

        } else if ((processo.getPc() == processo.getFase())) { // Se acabou a lista de IO o processo encerra
            controlaListas.setExecutando(null);
            controlaListas.getFilaProntos().removeFirst();
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
