/*
 * Esta classse é responsáel por toda o escalonamento FIFO.
 */
package Main;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class Fifo {

    private ControlaListas controlaListas = new ControlaListas();
    private List<Integer> listaProcessosIO = new LinkedList<>();
    private int pc, tempoAtual = 0; // tempoAtual serve para saber o tempo atual no tempo de execução para saber se existe processos a serem executados naquele tempo

    public Fifo(ControlaListas controlaListas) {
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
            System.out.println(processo.toString());
            controlaListas.getFilaProntos().remove(0);
        }
    }

    public void atenderBloqueado() {
        controlaListas.addFilaProntos(controlaListas.getFilaBloqueados().get(0));
        controlaListas.getFilaBloqueados().remove(0);
    }

    public ControlaListas getControlaListas() {
        return controlaListas;
    }

    public void setControlaListas(ControlaListas controlaListas) {
        this.controlaListas = controlaListas;
    }

    public List<Integer> getListaProcessosIO() {
        return listaProcessosIO;
    }

    public void setListaProcessosIO(List<Integer> listaProcessosIO) {
        this.listaProcessosIO = listaProcessosIO;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }
}
