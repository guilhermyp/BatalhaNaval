import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private String nome;
    private Mapa mapa;

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




    public Jogador(String nome) {
        this.nome = nome;
        this.mapa = new Mapa();
    }

    public String getNome() {
        return nome;
    }

    public Mapa getMapa() {
        return mapa;
    }

    public void posicionarNavios() {
        System.out.println("Vez do jogador " + nome);
        Scanner scanner = new Scanner(System.in);

        for (NavioType tipo : NavioType.values()) {
            int quantidadeMaxima = tipo.getQuantidadeMaxima();

            for (int i = 0; i < quantidadeMaxima; i++) {
                System.out.println("Posicione o " + tipo.name());
                mapa.exibirMapa(); // Exibir mapa antes de posicionar o navio

                // Solicitar linha
                System.out.println("Informe a linha (A-P):");
                int linha = -1;
                String linhaStr = scanner.nextLine();
                if (linhaStr.length() == 1 && linhaStr.charAt(0) >= 'A' && linhaStr.charAt(0) <= 'P') {
                    linha = linhaStr.charAt(0) - 'A';
                } else {
                    System.out.println("Linha inválida! Tente novamente.");
                    i--; // Reduzir o contador para que a mesma quantidade seja posicionada novamente
                    continue; // Reinicia o loop para posicionar o mesmo tipo de navio
                }

                // Solicitar coluna
                System.out.println("Informe a coluna (1-16):");
                int coluna = -1;
                try {
                    coluna = Integer.parseInt(scanner.nextLine()) - 1; // subtrai 1 para converter para índice de matriz
                    if (coluna < 0 || coluna >= 16) {
                        System.out.println("Coluna inválida! Tente novamente.");
                        i--; // Reduzir o contador para que a mesma quantidade seja posicionada novamente
                        continue; // Reinicia o loop para posicionar o mesmo tipo de navio
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida! Tente novamente.");
                    i--; // Reduzir o contador para que a mesma quantidade seja posicionada novamente
                    continue; // Reinicia o loop para posicionar o mesmo tipo de navio
                }

                // Solicitar orientação
                System.out.println("Informe a orientação (H para horizontal, V para vertical):");
                char orientacao;
                do {
                    orientacao = scanner.nextLine().toUpperCase().charAt(0);
                    if (orientacao != 'H' && orientacao != 'V') {
                        System.out.println("Orientação inválida! Tente novamente.");
                    }
                } while (orientacao != 'H' && orientacao != 'V');

                // Inicializar navio com base no tipo
                Navio navio;
                switch (tipo) {
                    case PortaAvioes:
                        navio = new PortaAvioes(tipo.name());
                        break;
                    case Destroyer:
                        navio = new Destroyer(tipo.name());
                        break;
                    case Submarino:
                        navio = new Submarino(tipo.name());
                        break;
                    case Fragata:
                        navio = new Fragata(tipo.name());
                        break;
                    case Bote:
                        navio = new Bote(tipo.name());
                        break;
                    default:
                        throw new IllegalArgumentException("Tipo de navio não reconhecido: " + tipo);
                }


                // Verificar se a posição é válida
                if (!mapa.posicionarNavio(navio, new Posicao(linha, coluna), orientacao)) {
                    System.out.println("Posição inválida! Tente novamente.");
                    i--; // Reduzir o contador para que a mesma quantidade seja posicionada novamente
                    continue; // Reinicia o loop para posicionar o mesmo tipo de navio
                }

                // Mostrar mapa após o posicionamento do navio
                System.out.println("Barco posicionado. Mapa do jogador " + nome + ":");
                mapa.exibirMapa();
            }
        }
    }

    public boolean todosOsNaviosAfundados() {
        for (Navio navio : mapa.getNavios()) {
            if (!navio.foiAfundado()) {
                return false;
            }
        }
        return true;
    }

    public void realizarAtaque(Jogador oponente) {
        Scanner scanner = new Scanner(System.in);
        clearScreen(); // Limpar a tela antes de exibir o mapa e solicitar o ataque
        System.out.println("Mapa do jogador " + oponente.getNome() + ":");
        oponente.getMapa().exibirMapaOculto();
    
        System.out.println("Vez de " + nome + " realizar o ataque.");
        System.out.println("Informe a coordenada do ataque (linha e coluna, ex: A1):");
        String coordenada = scanner.nextLine().toUpperCase(); // Garantir que a entrada seja em maiúsculas
    
        // Validar coordenada
        if (coordenada.length() != 2 || coordenada.charAt(0) < 'A' || coordenada.charAt(0) > 'P' ||
                coordenada.charAt(1) < '1' || coordenada.charAt(1) > '9') {
            System.out.println("Coordenada inválida! Tente novamente.");
            pressioneEnterParaContinuar(); // Aguardar confirmação antes de prosseguir
            realizarAtaque(oponente); // Chamar o método novamente para obter uma coordenada válida
            return;
        }
    
        int linha = coordenada.charAt(0) - 'A';
        int coluna = coordenada.charAt(1) - '1';
    
        // Realizar o ataque no mapa do oponente
        oponente.getMapa().registrarTiro(nome.charAt(0), new Posicao(linha, coluna));
    
        // Aguardar um pouco antes de limpar a tela para que o jogador possa ver o resultado do ataque
        try {
            Thread.sleep(5000); // 5 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    
        clearScreen();
}


    public enum NavioType {
        PortaAvioes(2),
        Destroyer(3),
        Submarino(4),
        Fragata(5),
        Bote(6);

        private int quantidadeMaxima;

        NavioType(int quantidadeMaxima) {
            this.quantidadeMaxima = quantidadeMaxima;
        }

        public int getQuantidadeMaxima() {
            return quantidadeMaxima;
        }
    }
}
