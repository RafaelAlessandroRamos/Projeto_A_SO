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
    private int tempoAtual = 0, quantum = 0, quantumMax = 3; // tempoAtual serve para saber o tempo atual no tempo de execução para saber se existe processos a serem executados naquele tempo

    public RoundRobin(ControlaListas controlaListas) {
        this.controlaListas = controlaListas;
        executar();
    }

    private void verificaTempo(int tempoAtual) {
        for (int i = 0; i < controlaListas.getListaProcessos().size(); i++) {
            if (tempoAtual == controlaListas.getListaProcessos().get(i).getTempoChegada()) {
                controlaListas.addFilaProntos(controlaListas.getListaProcessos().get(i)); // Adiciona um processo na lista de prontos
                controlaListas.getListaProcessos().remove(i); // remove da lista de processos o processo escolhido
            }
        }
    }

    private void verificaQuantum() {
        if (quantum == quantumMax) {
            controlaListas.addFilaProntos(controlaListas.getExecutando());
            controlaListas.setExecutando(controlaListas.getFilaProntos().getFirst());
            controlaListas.getFilaProntos().removeFirst();
            quantum = 0;
        }
    }

    public void executar() {
        do {
            System.out.println(" ========LISTA PRONTOS     " + controlaListas.getFilaProntos());
            //System.out.println(" ---------LISTA BLOQUEADOS  " + controlaListas.getFilaBloqueados());
            //System.out.println(" **********EXECULTANDO  " + controlaListas.getExecutando());
            if (controlaListas.getListaProcessos().isEmpty() && controlaListas.getFilaBloqueados().isEmpty() && controlaListas.getFilaProntos().isEmpty() && controlaListas.getExecutando() == null) { // condição de parada: se tudo estiver vazio para o laço
                break;
            }
            verificaTempo(this.tempoAtual);
            if (!controlaListas.getFilaProntos().isEmpty()) { // se a fila de pronto nao estiver vazia
                controlaListas.setExecutando(controlaListas.getFilaProntos().getFirst()); // pega o primeiro processo da lista de pronto para executar
                controlaListas.getFilaProntos().removeFirst();
            }
            if (this.tempoAtual % 5 == 0) { // a cada tres iterações o processo do sistema é chamado
                controlaListas.addFilaProntos(controlaListas.getProcessoSistema()); // adiciona processo do sistema na lista de prontos
            }
            System.out.println("Quantum = " + quantum);
            verificaQuantum();
            processar(controlaListas.getExecutando());
            this.tempoAtual++; // incrementa o tempo atual
        } while (true);
        System.out.println("///////////////////////////////////////////");
        controlaListas.imprimeListaTempoEspera();
        System.out.println("////////////////////////////////////////////");
        controlaListas.imprimeListaTempoEsperaTotal();
        System.out.println("////////////////////////////////////////////");
        System.out.println("Tamanho maximo da fila de pronto : " + controlaListas.getMaxFilaProntos());
        System.out.println("Tamanho maximo da fila de bloqueados : " + controlaListas.getMaxFilaBloqueados());
        System.out.println("////////////////////////////////////////////");
    }

    public void processar(Processo processo) {
        processo.setTempoEspera(processo.getTempoEspera() + (tempoAtual - processo.getTempoPreempcao())); // soma cada intervalo de tempo de espera
        controlaListas.tamanhoMaximoFilas();
        if (processo.getTipo().equals("S")) { // Se o processo é do tipo Sistema(S)
            System.out.println("-------- SISTEMA ---------");
            if (!controlaListas.getFilaBloqueados().isEmpty()) {
                atenderBloqueado(); // desbloqueia o primeiro processo da fila de bloqueados
            }
            controlaListas.setExecutando(null);
            quantum = -1;
        }
        if (processo.getPc() < processo.getFase() && processo.getTipo().equals("U")) { // se o pc é menor que o tamanho do processo
            if (processo.getFilaEntradaSaida().get(processo.getPc()) == 0) { // Se na posição pc estiver 0, pc + 1
                processo.setPc(processo.getPc() + 1); // pc+1 no processo
            } else if (processo.getFilaEntradaSaida().get(processo.getPc()) == 1) { // Se na posição pc estiver 0, pc + 1
                processo.setPc(processo.getPc() + 1); // pc+1 no processo
                processo.setTempoPreempcao(tempoAtual);
                bloqueado();
                controlaListas.setExecutando(null);
                quantum = -1;
            }
            controlaListas.addFilaProntos(processo);
            System.out.println(processo.toString());
            if ((processo.getPc() == processo.getFase())) { // Se acabou a lista de IO o processo encerra
                controlaListas.setExecutando(null);
                quantum = -1;
                controlaListas.addListaTempoEspera(processo);
            }
        }
        quantum++;
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
}
