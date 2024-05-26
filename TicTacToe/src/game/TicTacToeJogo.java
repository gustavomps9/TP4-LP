package game;

public class TicTacToeJogo{
    private TicTacToeTabuleiro tabuleiro;
    private char currentPlayer;

    public TicTacToeJogo() {
        tabuleiro = new TicTacToeTabuleiro();
        currentPlayer = 'X';
    }

