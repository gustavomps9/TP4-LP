package src.game;

public class TicTacToeJogo{
    private TicTacToeTabuleiro tabuleiro;
    private char currentPlayer;

    public TicTacToeJogo() {
        tabuleiro = new TicTacToeTabuleiro();
        currentPlayer = 'X';
        printTabuleiro(); // Imprime o tabuleiro no início do jogo
    }

    public boolean jogada(int row, int col) {
        if (tabuleiro.jogada(row, col, currentPlayer)) {
            printTabuleiro(); // Imprime o tabuleiro após cada jogada
            return true;
        }
        return false;
    }

    public boolean checkWin() {
        return tabuleiro.checkWin(currentPlayer);
    }

    public boolean isFull() {
        return tabuleiro.isFull();
    }

    public void trocaJogador() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public void printTabuleiro() {
        tabuleiro.printTabuleiro();
    }

    public void resetJogo() {
        tabuleiro.resetTabuleiro();
        currentPlayer='X';
}
}