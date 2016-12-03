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
public class ControlaListas {

    private LinkedList<Processo> listaProcessos = new LinkedList<Processo>(); // Armazena todos os processos prontos
    private LinkedList<Processo> filaProntos = new LinkedList<Processo>(); // Armazena os processos prontos para execução 
    private LinkedList<Processo> filaBloqueados = new LinkedList<Processo>(); // Armazena os processos bloqueados para execução
    private Processo executando;
    private int qtdProcessos;
    private int maxFilaProntos = 0;
    private int maxFilaBloqueados = 0;
    private Processo processoSistema = new Processo(-1, 1, -5, -1, "S");
    private LinkedList<Integer> listaTempoEspera = new LinkedList<Integer>(); // Armazena os tempos de espera de cada processo
    private LinkedList<Integer> listaTempoEsperaId = new LinkedList<Integer>(); // Armazena os tempos de espera de cada processo

    public ControlaListas() {
        executando = processoSistema;
    }

    private boolean verificaItemOrdenar(Processo p1, Processo p2, int parametro) { // verifica para qual item a lista de prontos vai ser ordenada
        if (parametro == 0) {// Prioridade
            if (p1.getPrioridade() <= p2.getPrioridade()) {
                return true;
            }
        } else if (parametro == 1) {// SJF
            if ((p1.getFase() - p1.getPc()) <= (p2.getFase() - p2.getPc())) {
                return true;
            }
        }
        return false;
    }

    private int partition(List<Processo> lista, int start, int end, int parametro) {
        Processo aux;
        Processo pivot = lista.get(end);
        int pIndex = start;
        boolean b;
        for (int i = start; i < end; i++) {
            b = verificaItemOrdenar(lista.get(i), pivot, parametro); // verifica qual o tipo de ordenação e faz a comparação
            if (b) { // if true
                aux = lista.get(i);
                lista.set(i, lista.get(pIndex));
                lista.set(pIndex, aux);
                pIndex++;
            }
        }
        aux = lista.get(pIndex);
        lista.set(pIndex, lista.get(end));
        lista.set(end, aux);
        return pIndex;
    }

    public List<Processo> quickSort(List<Processo> lista, int start, int end, int paramentro) { //Paramentro = tipo de ordenação
        if (start < end) {
            int particionIndex = partition(lista, start, end, paramentro);
            quickSort(lista, start, particionIndex - 1, paramentro);
            quickSort(lista, particionIndex + 1, end, paramentro);
        }
        return lista;
    }

    public void addListaProcessos(Processo processo) {
        qtdProcessos++;
        listaProcessos.add(processo);
    }

    public void removeListaProcessos(Processo processo) {
        listaProcessos.remove(processo.getId());
    }

    public void addFilaProntos(Processo processo) {
        filaProntos.add(processo);
    }

    public void removeFilaProntos(Processo processo) {
        filaProntos.remove(processo.getId());
    }

    public void addFilaBloqueados(Processo processo) {
        filaBloqueados.add(processo);
    }

    public void removeFilaBloqueados(Processo processo) {
        filaBloqueados.remove(processo.getId());
    }

    public void addListaTempoEspera(Processo processo) {
        listaTempoEspera.add(processo.getTempoEspera());
        listaTempoEsperaId.add(processo.getId());
    }

    public void imprimeListaTempoEsperaTotal() {
        int media = 0;
        for (int i = 0; i < listaTempoEspera.size(); i++) {
            media += listaTempoEspera.get(i);
        }
        media /= qtdProcessos;
        System.out.println("Tempo Médio de Espera : " + media);
    }

    public void tamanhoMaximoFilas() {
        if (filaProntos.size() > maxFilaProntos) {
            maxFilaProntos = filaProntos.size();
        }
        if (filaBloqueados.size() > maxFilaBloqueados) {
            maxFilaBloqueados = filaBloqueados.size();
        }
    }

    public void imprimeListaTempoEspera() {
        for (int i = 0; i < listaTempoEspera.size(); i++) {
            System.out.println("id : " + listaTempoEsperaId.get(i) + "  Tempo de Espera : " + listaTempoEspera.get(i));

        }
    }

    public int getQtdProcessos() {
        return qtdProcessos;
    }

    public void setQtdProcessos(int qtdProcessos) {
        this.qtdProcessos = qtdProcessos;
    }

    public int getMaxFilaProntos() {
        return maxFilaProntos;
    }

    public void setMaxFilaProntos(int maxFilaProntos) {
        this.maxFilaProntos = maxFilaProntos;
    }

    public int getMaxFilaBloqueados() {
        return maxFilaBloqueados;
    }

    public void setMaxFilaBloqueados(int maxFilaBloqueados) {
        this.maxFilaBloqueados = maxFilaBloqueados;
    }
    
    public LinkedList<Integer> getListaTempoEspera() {
        return listaTempoEspera;
    }

    public void setListaTempoEspera(LinkedList<Integer> listaTempoEspera) {
        this.listaTempoEspera = listaTempoEspera;
    }

    public LinkedList<Integer> getListaTempoEsperaId() {
        return listaTempoEsperaId;
    }

    public void setListaTempoEsperaId(LinkedList<Integer> listaTempoEsperaId) {
        this.listaTempoEsperaId = listaTempoEsperaId;
    }

    public LinkedList<Processo> getFilaProntos() {
        return filaProntos;
    }

    public void setFilaProntos(LinkedList<Processo> filaProntos) {
        this.filaProntos = filaProntos;
    }

    public LinkedList<Processo> getFilaBloqueados() {
        return filaBloqueados;
    }

    public void setFilaBloqueados(LinkedList<Processo> filaBloqueados) {
        this.filaBloqueados = filaBloqueados;
    }

    public LinkedList<Processo> getListaProcessos() {
        return listaProcessos;
    }

    public void setListaProcessos(LinkedList<Processo> listaProcessos) {
        this.listaProcessos = listaProcessos;
    }

    public Processo getExecutando() {
        return executando;
    }

    public void setExecutando(Processo executando) {
        this.executando = executando;
    }

    public Processo getProcessoSistema() {
        return processoSistema;
    }

    public void setProcessoSistema(Processo processoSistema) {
        this.processoSistema = processoSistema;
    }

}
