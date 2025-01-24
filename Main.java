import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Jogo {
    private Jogador jogador1;
    private Jogador jogador2;

    public Jogo(String nomeJogador1, String nomeJogador2) {
        this.jogador1 = new Jogador(nomeJogador1);
        this.jogador2 = new Jogador(nomeJogador2);
    }

    private void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception ex) {
            System.out.println("Erro ao limpar a tela: " + ex.getMessage());
        }
    }

    private void pressioneEnterParaContinuar() {
        System.out.println("Pressione Enter para continuar...");
        new Scanner(System.in).nextLine();
    }

    public void iniciar() {
        clearScreen();
    
        System.out.println("Vez do jogador " + jogador1.getNome());
        jogador1.posicionarNavios();
        clearScreen();
        System.out.println("Barcos posicionados. Aguarde a vez do jogador 2.");
        pressioneEnterParaContinuar();
    
        clearScreen();
        System.out.println("Vez do jogador " + jogador2.getNome());
        jogador2.posicionarNavios();
        clearScreen();
        System.out.println("Barcos posicionados. Iniciando o jogo.");
        pressioneEnterParaContinuar();
    
        Jogador jogadorInicial = jogador1;
        Jogador jogadorDefensor = jogador2;
    
        System.out.println("O jogador " + jogadorInicial.getNome() + " vai começar!");
    
        while (true) {
            realizarRodada(jogadorInicial, jogadorDefensor);
            clearScreen();
            System.out.println("Mapa do jogador " + jogadorInicial.getNome() + ":");
            jogadorInicial.getMapa().exibirMapaParcial('1');
    
            if (jogadorDefensor.todosOsNaviosAfundados()) {
                System.out.println("Todos os navios de " + jogadorDefensor.getNome() + " foram afundados. " + jogadorInicial.getNome() + " venceu!");
                break;
            }
    
            realizarRodada(jogadorDefensor, jogadorInicial);
            clearScreen();
            System.out.println("Mapa do jogador " + jogadorDefensor.getNome() + ":");
            jogadorDefensor.getMapa().exibirMapaParcial('2');
    
            if (jogadorInicial.todosOsNaviosAfundados()) {
                System.out.println("Todos os navios de " + jogadorInicial.getNome() + " foram afundados. " + jogadorDefensor.getNome() + " venceu!");
                break;
            }
        }
    }

    private void realizarRodada(Jogador atacante, Jogador defensor) {
        System.out.println("Vez do jogador " + atacante.getNome());
        System.out.println("Realize seu ataque.");

        // Exibir mapa do jogador atacante para que ele possa escolher onde atacar
        System.out.println("Mapa do jogador " + atacante.getNome() + ":");
        atacante.getMapa().exibirMapaParcial(atacante.getNome().charAt(0));

        // Realizar o ataque
        atacante.realizarAtaque(defensor);

        System.out.println("Vez do jogador " + atacante.getNome());
          System.out.println("Realize seu ataque.");

        // Exibir mapa oculto do oponente
           System.out.println("Mapa do jogador " + defensor.getNome() + ":");
        defensor.getMapa().exibirMapaOculto();

          // Realizar o ataque
       atacante.realizarAtaque(defensor);


        // Aguardar um pouco antes de limpar a tela para que o jogador possa ver o resultado do ataque
        try {
            Thread.sleep(5000); // 5 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

   // private void pressioneEnterParaContinuar() {
    //    System.out.println("Pressione Enter para continuar...");
   //     new Scanner(System.in).nextLine();
   // }
}
class Posicao {
    private int linha;
    private int coluna;

    public Posicao(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o nome do Jogador 1:");
        String nomeJogador1 = scanner.nextLine();

        System.out.println("Digite o nome do Jogador 2:");
        String nomeJogador2 = scanner.nextLine();

        Jogo jogo = new Jogo(nomeJogador1, nomeJogador2);
        jogo.iniciar();
    }
}
