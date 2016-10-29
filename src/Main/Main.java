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

        //imprime a lista da string de dados do arquivo
        for (String string : dadosArquivo) {
            String aux[] = string.split(" ");
            try {
                Processo processo = new Processo(Integer.parseInt(aux[0]), Integer.parseInt(aux[1]), Integer.parseInt(aux[2]), Integer.parseInt(aux[3]));

                LinkedList<Integer> entradaSaida = new LinkedList<>(); // Lista com as posições de entrada e saida do processo
                for (int j = 4; j < aux.length; j++) {
                    entradaSaida.add(Integer.parseInt(aux[j]));
                }
//            System.out.println("\n");
//            for (int i = 0; i < entradaSaida.size(); i++) {
//                System.out.println(entradaSaida.get(i));
//            }
                if (!entradaSaida.isEmpty()) {
                    processo.addFilaEntradaSaida(entradaSaida);
                }

                processo.imprimeFilaIO();
            } catch (Exception e) {
                System.out.println("Erro na criação do processo!!");
            }
        }
    }
}
