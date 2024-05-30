package game;

public class TicTacToeTabuleiro {
    private char[][] tabuleiro;
    private static final int tamanho = 3;

    // Construtor que inicializa o tabuleiro
    public TicTacToeTabuleiro() {
        tabuleiro = new char[tamanho][tamanho];
        resetTabuleiro;
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
    public boolean jogada(int row, int col, char jogador){
        if (row >= 0 && row < tamanho && col >= 0 && col < tamanho && tabuleiro[row][col] == ' ') {
            tabuleiro[row][col] = jogador;
            return true;
        }
        // Return false se a jogada não for válida
        return false;
    }

    // Verifica se o jogador passado como parâmetro ganhou
    public boolean checkWin(char jogador) {
        // Verifica as linhas e colunas
        for (int i = 0; i < SIZE; i++) {
            if (tabuleiro[i][0] == jogador && tabuleiro[i][1] == jogador && tabuleiro[i][2] == jogador)
                return true;
            if (tabuleiro[0][i] == jogador && tabuleiro[1][i] == jogador && tabuleiro[2][i] == jogador)
                return true;
        }
        if (tabuleiro[0][0] == jogador && tabuleiro[1][1] == jogador && tabuleiro[2][2] == jogador)
            return true;
        if (tabuleiro[0][2] == jogador && tabuleiro[1][1] == jogador && tabuleiro[2][0] == jogador)
            return true;
        return false;
    }

    // Verifica se o tabuleiro está cheio
    public boolean isFull() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (tabuleiro[i][j] == ' ')
                    // Return false se encontrar uma posição vazia
                    return false;
            }
        }
        // Return true se todas posições estiverem preenchidas
        return true;
    }

    // Print do estado atual do tabuleira na consola
    public void printTabuleiro() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                System.out.print(tabuleiro[i][j]);
                if (j < tamanho - 1) System.out.print("|");
            }
            System.out.println();
            if (i < tamanho - 1) System.out.println("-----");
 }
}
}