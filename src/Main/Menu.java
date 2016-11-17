/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import com.sun.org.apache.bcel.internal.generic.SWITCH;
import java.util.Scanner;
import javax.swing.JOptionPane;

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
        opcao = scanner.nextInt();
        switch (opcao) {
            case 0:
                Fifo fifo = new Fifo(controlaListas);
                break;
            case 1:
                Prioridade prioridade = new Prioridade(controlaListas);
                break;
            case 2:
                SJF sjf = new SJF(controlaListas);
                break;
            case 3:
                RoundRobin roundRobin = new RoundRobin(controlaListas);
                break;
            default:
                System.out.println("Opção invalida!!");
        }
        
    }
    
    
}
