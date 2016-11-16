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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();

        String caminho = "Dados_Entrada.txt";

        List<String> dadosArquivo = manipulaArquivo.abrirArquivo(caminho); // armazena todas as linhas do arquivo
        ControlaListas controlaListas = new ControlaListas();

        //imprime a lista da string de dados do arquivo
        for (String string : dadosArquivo) {
            String aux[] = string.split(" ");
            try {
                Processo processo = new Processo(Integer.parseInt(aux[0]), Integer.parseInt(aux[1]), Integer.parseInt(aux[2]), Integer.parseInt(aux[3]));

                LinkedList<Integer> entradaSaida = new LinkedList<>(); // Lista com as posições de entrada e saida do processo

                for (int j = 4; j < aux.length; j++) { // laço para verificar se existe posições de IO do arquivo
                    entradaSaida.add(Integer.parseInt(aux[j])-1);
                }
                if (!entradaSaida.isEmpty()) { // Condição para verificar se existe IO na lista entradaSaida
                    processo.addFilaEntradaSaida(entradaSaida);
                }
                controlaListas.addListaProcessos(processo); // Adiciona o processo na lista de processos
            } catch (Exception e) {
                System.out.println("Erro na criação do processo!!");
            }
            
        }
            System.out.println(controlaListas.getListaProcessos().toString());
//            Fifo fifo = new Fifo(controlaListas);
//            Prioridade prioridade = new Prioridade(controlaListas);
            SJF sjf = new SJF(controlaListas);
            
            //fifo.getControlaListas().setListaProcessos(controlaListas.getListaProcessos()); // Passa a lista de processos para o controlador de listas de rocessos do fifo
    }
}
