/*
 * Esta classse é responsáel por toda o escalonamento FIFO.
 */
package Main;

/**
 *
 * @author Rafael
 */
public class Fifo {

    private ControlaListas controlaListas = new ControlaListas();
    private int tempoAtual = 0; // tempoAtual serve para saber o tempo atual no tempo de execução para saber se existe processos a serem executados naquele tempo

    public Fifo(ControlaListas controlaListas) {
        this.controlaListas = controlaListas;
        System.out.println("Processos : " + controlaListas.getListaProcessos());
        executar();
    }

    public void verificarTempo(int tempoAtual) {
        for (int i = 0; i < controlaListas.getListaProcessos().size(); i++) {
            if (tempoAtual == controlaListas.getListaProcessos().get(i).getTempoChegada()) {
                controlaListas.addFilaProntos(controlaListas.getListaProcessos().get(i));
                controlaListas.getListaProcessos().remove(i);
            }
        }
    }

    public void executar() {
        do {
            if (controlaListas.getListaProcessos().isEmpty() && controlaListas.getFilaBloqueados().isEmpty() && controlaListas.getFilaProntos().isEmpty() && controlaListas.getExecutando() == null) { // condição de parada: se tudo estiver vazio para o laço
                break;
            }
            verificarTempo(this.tempoAtual);
            if (!controlaListas.getFilaProntos().isEmpty()) { // se a fila de pronto nao estiver vazia
                controlaListas.setExecutando(controlaListas.getFilaProntos().getFirst()); // pega o primeiro processo da lista de pronto para executar
            }
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
        controlaListas.tamanhoMaximoFilas();
        if (processo.getTipo().equals("S")) { // Se o processo é do tipo Sistema(S)
            System.out.println("-------- SISTEMA ---------");
            if (!controlaListas.getFilaBloqueados().isEmpty()) {
                atenderBloqueado(); // desbloqueia o primeiro processo da fila de bloqueados
            }

        }
        if (processo.getPc() < processo.getFase() && processo.getTipo().equals("U")) { // se o pc é menor que o tamanho do processo
            if (processo.getPc() == 0) {
                processo.setTempoEspera(tempoAtual - processo.getTempoChegada());
                controlaListas.addListaTempoEspera(processo);
            }
            if (processo.getFilaEntradaSaida().get(processo.getPc()) == 0) { // Se na posição pc estiver 0, pc + 1
                processo.setPc(processo.getPc() + 1); // pc+1 no processo
            } else if (processo.getFilaEntradaSaida().get(processo.getPc()) == 1) { // Se na posição pc estiver 0, pc + 1
                processo.setPc(processo.getPc() + 1); // pc+1 no processo
                bloquear();
                controlaListas.setExecutando(controlaListas.getProcessoSistema());
                processar(controlaListas.getExecutando());
            }
            System.out.println(processo.toString());
        } else if ((processo.getPc() == processo.getFase())) { // Se acabou a lista de IO o processo encerra
            controlaListas.setExecutando(null);
            controlaListas.getFilaProntos().removeFirst();
            
        }
        controlaListas.tamanhoMaximoFilas();
    }

    public void atenderBloqueado() {
        controlaListas.setExecutando(controlaListas.getFilaBloqueados().getFirst());
        controlaListas.getFilaBloqueados().removeFirst();
    }

    private void bloquear() {
        controlaListas.addFilaBloqueados(controlaListas.getExecutando());
    }
}
