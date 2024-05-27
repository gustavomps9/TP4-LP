package game;

public class TicTacToeJogo{
    private TicTacToeTabuleiro tabuleiro;
    private char currentPlayer;

    public TicTacToeJogo() {
        tabuleiro = new TicTacToeTabuleiro();
        currentPlayer = 'X';
    }

    public boolean jogada(int row, int col) {
        if (tabuleiro.jogada(row, col, currentJogador)) {
            return true;
        }
        return false;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public void printTabuleiro() {
        tabuleiro.printTabuleiro();
    }

    public void resetJogo() {
        tabuleiro.resetTabuleiro();
        currentPlayer = 'X';
    }
}
