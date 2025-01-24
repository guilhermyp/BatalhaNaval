import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Mapa {
    private final int tamanho = 16;
    private final char[][] matriz = new char[tamanho][tamanho];
    private final List<Navio> navios = new ArrayList<>();

    public Mapa() {
        // Inicializar a matriz com espaços em branco
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                matriz[i][j] = 'O'; // Inicialmente, todos os espaços estão vazios
            }
        }
    }

    public void exibirMapa() {
        // Exibir números das colunas
        System.out.print(" ");
        for (int i = 0; i < tamanho; i++) {
            System.out.printf("%2d ", i+1); // Utilizando printf para formatar o número da coluna
        }
        System.out.println();
    
        // Exibir linhas com letras e conteúdo da matriz
        for (int i = 0; i < tamanho; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < tamanho; j++) {
                System.out.print(matriz[i][j] + "  "); // Adicionando espaçamento extra
            }
            System.out.println();
        }
    }

    public void exibirMapaOculto() {
        // Exibir números das colunas
        System.out.print(" ");
        for (int i = 0; i < tamanho; i++) {
            System.out.printf("%2d ", i + 1); // Utilizando printf para formatar o número da coluna
        }
        System.out.println();
    
        // Exibir linhas com letras e conteúdo da matriz
        for (int i = 0; i < tamanho; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < tamanho; j++) {
                char conteudo = matriz[i][j];
                if (conteudo == 'X' || conteudo == '*') {
                    System.out.print(conteudo + "  "); // Mostrar apenas as posições atingidas
                } else {
                    System.out.print("-  "); // Ocultar a localização dos navios não atingidos
                }
            }
            System.out.println();
        }
    }

    public boolean posicionarNavio(Navio navio, Posicao posicao, char orientacao) {
        int linha = posicao.getLinha();
        int coluna = posicao.getColuna();
        int tamanhoNavio = navio.getTamanho();

        // Verificar se o navio cabe na posição especificada
        if (orientacao == 'H') {
            if (coluna + tamanhoNavio > tamanho) {
                System.out.println("\nNavio ultrapassa os limites do mapa");
                return false; // Navio ultrapassa os limites do mapa
            }
            for (int i = coluna; i < coluna + tamanhoNavio; i++) {
                if (matriz[linha][i] != 'O') {
                    System.out.println("\nPosição está ocupada por outro navio");
                    return false; // Posição ocupada por outro navio
                }
            }
            // Posicionar o navio na horizontal
            for (int i = coluna; i < coluna + tamanhoNavio; i++) {
                matriz[linha][i] = navio.getTipo().charAt(0);
            }
        } else if (orientacao == 'V') {
            if (linha + tamanhoNavio > tamanho) {
                System.out.println("\nNavio ultrapassa os limites do mapa");
                return false; // Navio ultrapassa os limites do mapa
            }
            for (int i = linha; i < linha + tamanhoNavio; i++) {
                if (matriz[i][coluna] != 'O') {
                    System.out.println("\nPosição está ocupada por outro navio");
                    return false; // Posição ocupada por outro navio
                }
            }
            // Posicionar o navio na vertical
            for (int i = linha; i < linha + tamanhoNavio; i++) {
                matriz[i][coluna] = navio.getTipo().charAt(0);
            }
        } else {
            return false; // Orientação inválida
        }
        return true; // Navio posicionado com sucesso
    }

    public void registrarTiro(char jogador, Posicao posicao) {
        int linha = posicao.getLinha();
        int coluna = posicao.getColuna();
    
        // Verificar se a posição já foi atacada anteriormente
        if (matriz[linha][coluna] == 'X' || matriz[linha][coluna] == '*') {
            System.out.println("Esta posição já foi atacada anteriormente.");
            return;
        }
    
        // Verificar se houve acerto em algum navio
        if (matriz[linha][coluna] != 'O') {
            // Acerto!
            matriz[linha][coluna] = 'X'; // Marcar como acerto
            System.out.println("Jogador " + jogador + ": Acertou um navio!");
        } else {
            // Água...
            matriz[linha][coluna] = '*'; // Marcar como água
            System.out.println("Jogador " + jogador + ": Água...");
        }
    }

    public List<Navio> getNavios() {
        return navios;
    }

    public void exibirMapaParcial(char jogador) {
        System.out.println("Mapa do jogador " + jogador + ":");
        System.out.print("  ");
        for (int i = 1; i <= tamanho; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
    
        for (int i = 0; i < tamanho; i++) {
            char linhaLetra = (char) ('A' + i);
            System.out.print(linhaLetra + " ");
            for (int j = 0; j < tamanho; j++) {
                char conteudo = matriz[i][j];
                if (conteudo == 'X' || conteudo == '*') {
                    System.out.print(conteudo + " ");
                } else if (conteudo == 'O') {
                    System.out.print("- ");
                } else {
                    System.out.print(conteudo + " ");
                }
            }
            System.out.println();
        }
    }
    
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