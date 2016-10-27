/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.IOException;
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
        String[] string;
        for (String s : dadosArquivo) {
            string = s.split(" ");
            System.out.println(string[1]+"\t"+string[2]+"\t"+string[3]+"\t"+string[4]+"\n");
            //Processo processo = new Processo(Integer.parseInt(string[0]), Integer.parseInt(string[1]), Integer.parseInt(string[2]), Integer.parseInt(string[3]));
            //processo.toString();
        }

    }

}
