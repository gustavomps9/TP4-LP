package src.game;

public class TicTacToeTabuleiro {
    private char[][] tabuleiro;
    private static final int tamanho = 3;

    // Construtor que inicializa o tabuleiro
    public TicTacToeTabuleiro() {
        tabuleiro = new char[tamanho][tamanho];
        resetTabuleiro();
    }

    // Reset no tabuleiro, preenchendo todas as posições com espaços em branco
    public void resetTabuleiro() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                tabuleiro[i][j] = ' ';
            }
        }
    }

    // Realiza uma jogada no tabuleiro
    public boolean jogada(int row, int col, char jogador) {
        if (row >= 0 && row < tamanho && col >= 0 && col < tamanho && tabuleiro[row][col] == ' ') {
            tabuleiro[row][col] = jogador;
            return true;
        }
        // Return false se a jogada não for válida
        return false;
    }

    // Verifica se o jogador venceu
    public boolean checkWin(char jogador) {
        // Verifica linhas
        for (int i = 0; i < tamanho; i++) {
            if (tabuleiro[i][0] == jogador && tabuleiro[i][1] == jogador && tabuleiro[i][2] == jogador) {
                return true;
            }
        }

        // Verifica colunas
        for (int j = 0; j < tamanho; j++) {
            if (tabuleiro[0][j] == jogador && tabuleiro[1][j] == jogador && tabuleiro[2][j] == jogador) {
                return true;
            }
        }

        // Verifica diagonal principal
        if (tabuleiro[0][0] == jogador && tabuleiro[1][1] == jogador && tabuleiro[2][2] == jogador) {
            return true;
        }

        // Verifica diagonal secundária
        if (tabuleiro[0][2] == jogador && tabuleiro[1][1] == jogador && tabuleiro[2][0] == jogador) {
            return true;
        }

        return false;
    }

    // Print do tabuleiro na consola
    public void printTabuleiro() {
        System.out.print("  0 1 2\n"); // Imprime os números das colunas

        // Itera pelas linhas do tabuleiro
        for (int i = 0; i < tamanho; i++) {
            System.out.print(i + " "); // Imprime o número da linha

            // Itera pelas colunas do tabuleiro
            for (int j = 0; j < tamanho; j++) {
                System.out.print(tabuleiro[i][j]); // Imprime o conteúdo da célula (X, O ou vazio)

                // Adiciona um separador entre as células, exceto na última coluna
                if (j < tamanho - 1) {
                    System.out.print(" | ");
                }
            }

            System.out.println(); // Move para a próxima linha após imprimir todas as colunas

            // Adiciona uma linha horizontal entre as linhas, exceto na última linha
            if (i < tamanho - 1) {
                System.out.println("  -------");
            }
        }

        System.out.println(); // Imprime uma linha em branco no final para separar visualmente
    }

    // Retorna uma representação em forma de String do tabuleiro
    public String getTabuleiroString() {
        StringBuilder builder = new StringBuilder();
        builder.append("  0 1 2\n"); // Imprime os números das colunas
        for (int i = 0; i < 3; i++) {
            builder.append(i + " "); // Imprime o número da linha
            for (int j = 0; j < 3; j++) {
                builder.append(tabuleiro[i][j]);
                if (j < 2) {
                    builder.append("|");
                }
            }
            builder.append("\n");
            if (i < 2) {
                builder.append("  -------\n");
            }
        }
        return builder.toString();
    }

    // Verifica se o tabuleiro está cheio
    public boolean isFull() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (tabuleiro[i][j] == ' ') {
                    return false; // Encontrou espaço vazio, tabuleiro não está cheio
                }
            }
        }
        return true; // Tabuleiro está cheio
    }
}
