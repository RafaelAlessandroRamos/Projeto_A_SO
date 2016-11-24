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
public class ControlaListas{
    private LinkedList<Processo> listaProcessos = new LinkedList<Processo>(); // Armazena todos os processos prontos
    private LinkedList<Processo> filaProntos = new LinkedList<Processo>(); // Armazena os processos prontos para execução 
    private LinkedList<Processo> filaBloqueados = new LinkedList<Processo>(); // Armazena os processos bloqueados para execução
    private Processo executando;
    private Processo processoSistema = new Processo(-1,1,-5,-1,"S");

    public ControlaListas() {
//        filaProntos.add(processoSistema);
        executando = processoSistema;
    }
    
    private boolean verificaItemOrdenar(Processo p1, Processo p2, int parametro){ // verifica para qual item a lista de prontos vai ser ordenada
        if(parametro == 0){// Prioridade
            if(p1.getPrioridade() <= p2.getPrioridade()){
                return true;
            }
        }
        else if(parametro == 1){// SJF
            if((p1.getFase() - p1.getPc()) <= (p2.getFase() - p2.getPc())){
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
    
    public void addListaProcessos(Processo processo){
        listaProcessos.add(processo);
    }
    
    public void removeListaProcessos(Processo processo){
        listaProcessos.remove(processo.getId());
    }
    
    public void addFilaProntos(Processo processo){
        filaProntos.add(processo);
    }
    
    public void removeFilaProntos(Processo processo){
        filaProntos.remove(processo.getId());
    }
    
    public void addFilaBloqueados(Processo processo){
        filaBloqueados.add(processo);
    }
    
    public void removeFilaBloqueados(Processo processo){
        filaBloqueados.remove(processo.getId());
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
