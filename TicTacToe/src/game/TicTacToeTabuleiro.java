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


