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
public class Prioridade {

    private ControlaListas controlaListas = new ControlaListas();
    private int tempoAtual = 0; // tempoAtual serve para saber o tempo atual no tempo de execução para saber se existe processos a serem executados naquele tempo

    public Prioridade(ControlaListas controlaListas) {
        this.controlaListas = controlaListas;
        System.out.println("PROCESSOS : " + controlaListas.getListaProcessos() + "\n");
        executar();
    }

    public void verificaTempo(int tempoAtual) {
        for (int i = 0; i < controlaListas.getListaProcessos().size(); i++) {
            if (tempoAtual == controlaListas.getListaProcessos().get(i).getTempoChegada()) { // verifica se o tempo atual na linha de tempo é igual ao tempo de chegada de algum processo na lista de processos
                controlaListas.addFilaProntos(controlaListas.getListaProcessos().get(i)); // Adiciona um processo na lista de prontos
                controlaListas.getListaProcessos().remove(i); // remove da lista de processos o processo escolhido
                controlaListas.quickSort(controlaListas.getFilaProntos(), 0, controlaListas.getFilaProntos().size() - 1, 0); // ordena a lista de prontos por prioridade
            }
        }
    }

    public void menorPcMesmaPrioridade() {
        Processo aux = new Processo();
        if (controlaListas.getFilaProntos().getFirst().getPrioridade() == controlaListas.getFilaProntos().get(1).getPrioridade()) {
            if (controlaListas.getFilaProntos().getFirst().getTempoChegada() <= controlaListas.getFilaProntos().get(1).getTempoChegada()) {
                aux = controlaListas.getFilaProntos().getFirst();
                controlaListas.getFilaProntos().set(0, controlaListas.getFilaProntos().get(1));
                controlaListas.getFilaProntos().set(1, aux);
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
                controlaListas.quickSort(controlaListas.getFilaProntos(), 0, controlaListas.getFilaProntos().size() - 1, 0); // ordena a lista de prontos por prioridade
                controlaListas.setExecutando(controlaListas.getFilaProntos().getFirst()); // pega o primeiro processo da lista de pronto para executar
                controlaListas.getFilaProntos().removeFirst();
            }
            if (this.tempoAtual % 3 == 0) { // a cada tres iterações o processo do sistema é chamado
                controlaListas.addFilaProntos(controlaListas.getProcessoSistema()); // adiciona processo do sistema na lista de prontos
                //controlaListas.getProcessoSistema().setPc(0); // seta o pc do processo de sistema para 0
                controlaListas.quickSort(controlaListas.getFilaProntos(), 0, controlaListas.getFilaProntos().size() - 1, 0); // ordena a lista de prontos por prioridade
                //controlaListas.setExecutando(controlaListas.getFilaProntos().getFirst()); // o primeiro da fila de prontos vai executar
                //controlaListas.getFilaProntos().removeFirst();
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
            if (processo.getFilaEntradaSaida().get(processo.getPc()) == 0) { // Se na posição pc estiver 0, pc + 1
                processo.setPc(processo.getPc() + 1); // pc+1 no processo
            } else if (processo.getFilaEntradaSaida().get(processo.getPc()) == 1) { // Se na posição pc estiver 0, pc + 1
                processo.setPc(processo.getPc() + 1); // pc+1 no processo
                bloqueado();
                controlaListas.setExecutando(null);
            }
            controlaListas.addFilaProntos(processo);
            System.out.println(processo.toString());

        } else if ((processo.getPc() == processo.getFase())) { // Se acabou a lista de IO o processo encerra
            controlaListas.setExecutando(null);
        }
    }

    private void atenderBloqueado() {
        controlaListas.addFilaProntos(controlaListas.getFilaBloqueados().getFirst());
        controlaListas.getFilaBloqueados().removeFirst();
    }

    private void bloqueado() {
        controlaListas.addFilaBloqueados(controlaListas.getExecutando());
    }
}
