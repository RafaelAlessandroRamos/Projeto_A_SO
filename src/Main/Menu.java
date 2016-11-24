/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.util.Scanner;
/**
 *
 * @author Rafael
 */
public class Menu {
    private int opcao;
    private ControlaListas controlaListas = new ControlaListas();
    Scanner scanner = new Scanner(System.in);

    public Menu(ControlaListas controlaListas) {
        this.controlaListas = controlaListas;
        estruturaMenu();
    }
    
    public void estruturaMenu(){
        System.out.println(" 1 - FIFO \n 2 - Prioridade \n 3 - SJF \n 4 - Round Robin");
        System.out.print(" Escolha um escalonador de processos: ");
        opcao = scanner.nextInt();
        switch (opcao) {
            case 1:
                Fifo fifo = new Fifo(controlaListas);
                break;
            case 2:
                Prioridade prioridade = new Prioridade(controlaListas);
                break;
            case 3:
                SJF sjf = new SJF(controlaListas);
                break;
            case 4:
                RoundRobin roundRobin = new RoundRobin(controlaListas);
                break;
            default:
                System.out.println("Opção invalida!!");
        }
        
    }
    
    
}
