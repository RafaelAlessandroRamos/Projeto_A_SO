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
public class SJF {
    private ControlaListas controlaListas = new ControlaListas();
    private int tempoAtual = 0, flag = 0; // tempoAtual serve para saber o tempo atual no tempo de execução para saber se existe processos a serem executados naquele tempo

    public SJF(ControlaListas controlaListas) {
        this.controlaListas = controlaListas;
        executar();
    }

    public void verificaTempo(int tempoAtual) {
        for (int i = 0; i < controlaListas.getListaProcessos().size(); i++) {
            if (tempoAtual == controlaListas.getListaProcessos().get(i).getTempoChegada()) {
                controlaListas.addFilaProntos(controlaListas.getListaProcessos().get(i)); // Adiciona um processo na lista de prontos
                controlaListas.quickSort(controlaListas.getFilaProntos(), 0, controlaListas.getFilaProntos().size() - 1, 1); // ordena a lista de prontos por prioridade
            }
        }
    }

    public void menorPcMesmoTamanho() {
        Processo aux = new Processo();
        if ((controlaListas.getFilaProntos().getFirst().getFase() - controlaListas.getFilaProntos().getFirst().getPc())  == (controlaListas.getFilaProntos().get(1).getFase() - controlaListas.getFilaProntos().get(1).getPc())) {
            if (controlaListas.getFilaProntos().getFirst().getTempoChegada() >= controlaListas.getFilaProntos().get(1).getTempoChegada()) {
                aux = controlaListas.getFilaProntos().getFirst();
                controlaListas.getFilaProntos().set(0, controlaListas.getFilaProntos().get(1));
                controlaListas.getFilaProntos().set(1, aux);
            }
        }
    }

    public void executar() {
        do {
            verificaTempo(this.tempoAtual);
            for (int i = 0; i < controlaListas.getFilaProntos().size(); i++) {
                controlaListas.quickSort(controlaListas.getFilaProntos(), 0, controlaListas.getFilaProntos().size() - 1, 1); // ordena a lista de prontos por prioridade
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
        if (processo.getTipo().equals("S")) { // Se o processo é do tipo Sistema(S)
            if (!controlaListas.getFilaBloqueados().isEmpty()) {
                atenderBloqueado();
            }
        } else {
            if (processo.getFilaEntradaSaida().get(processo.getPc()) == 1) { // Se na posição pc estiver 1 o processo é bloqueado
                processo.setPc(processo.getPc() + 1); // pc+1 no processo
                bloqueado();
            } else {
                processo.setPc(processo.getPc() + 1); // pc+1 no processo
            }
            if ((processo.getPc() == processo.getFase()) && (processo.getTipo().equals("U"))) { // Se acabou a lista de IO o processo encerra
                controlaListas.setExecutando(null);
            } else if (processo.getPc() != processo.getFase()) {
                controlaListas.addFilaProntos(processo);
            }
            System.out.println(processo.toString());
        }
    }

    private void atenderBloqueado() {
        controlaListas.addFilaProntos(controlaListas.getFilaBloqueados().getFirst());
        controlaListas.getFilaBloqueados().removeFirst();
        controlaListas.addFilaProntos(controlaListas.getExecutando());
        controlaListas.quickSort(controlaListas.getFilaProntos(), 0, controlaListas.getFilaProntos().size() - 1, 1); // ordena a lista de prontos por prioridade
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
