package src.game;

public class TicTacToeJogo {
    private TicTacToeTabuleiro tabuleiro;
    private char currentPlayer;

    public TicTacToeJogo() {
        tabuleiro = new TicTacToeTabuleiro();
        currentPlayer = 'X';
    }

    public boolean jogada(int row, int col) {
        if (tabuleiro.jogada(row, col, currentPlayer)) {
            return true;
        }
        return false;
    }

    public void trocaJogador() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean checkWin() {
        return tabuleiro.checkWin(currentPlayer);
    }

    public boolean isFull() {
        return tabuleiro.isFull();
    }

    public void resetJogo() {
        tabuleiro.reset();
        currentPlayer = 'X';
    }

    public String getTabuleiroString() {
        return tabuleiro.toString();
    }
}
