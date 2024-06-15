package game;

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
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                System.out.print(tabuleiro[i][j]);
                if (j < tamanho - 1) {
                    System.out.print(" | "); // Separador entre colunas
                }
            }
            System.out.println();
            if (i < tamanho - 1) {
                System.out.println("---------"); // Linha horizontal entre linhas
            }
        }
        System.out.println();
    }

    // Retorna uma representação em forma de String do tabuleiro
    public String getTabuleiroString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                sb.append(tabuleiro[i][j]);
                if (j < tamanho - 1) {
                    sb.append(" | ");
                }
            }
            if (i < tamanho - 1) {
                sb.append("\n---------\n"); // Linha horizontal entre linhas
            } else {
                sb.append("\n");
            }
        }
        return sb.toString();
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
