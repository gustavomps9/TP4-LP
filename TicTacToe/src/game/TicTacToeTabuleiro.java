package src.game;

public class TicTacToeTabuleiro {
    private char[][] tabuleiro;

    public TicTacToeTabuleiro() {
        tabuleiro = new char[3][3];
        reset();
    }

    public void reset() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro[i][j] = ' ';
            }
        }
    }

    public boolean jogada(int row, int col, char player) {
        if (tabuleiro[row][col] == ' ') {
            tabuleiro[row][col] = player;
            return true;
        }
        return false;
    }

    public char getPosicao(int row, int col) {
        return tabuleiro[row][col];
    }

    public boolean checkWin(char player) {
        for (int i = 0; i < 3; i++) {
            if (tabuleiro[i][0] == player && tabuleiro[i][1] == player && tabuleiro[i][2] == player) {
                return true;
            }
            if (tabuleiro[0][i] == player && tabuleiro[1][i] == player && tabuleiro[2][i] == player) {
                return true;
            }
        }
        if (tabuleiro[0][0] == player && tabuleiro[1][1] == player && tabuleiro[2][2] == player) {
            return true;
        }
        if (tabuleiro[0][2] == player && tabuleiro[1][1] == player && tabuleiro[2][0] == player) {
            return true;
        }
        return false;
    }

    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(tabuleiro[i][j]);
                if (j < 2) sb.append(" | ");
            }
            sb.append("\n");
            if (i < 2) sb.append("---------\n");
        }
        return sb.toString();
    }
}
