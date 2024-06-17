package src.game;

/**
 * Representa o tabuleiro do jogo do galo.
 */
public class TicTacToeTabuleiro {
    private char[][] tabuleiro; // Matriz que representa o tabuleiro

    /**
     * Construtor que inicializa o tabuleiro e dá reset ao mesmo.
     */
    public TicTacToeTabuleiro() {
        tabuleiro = new char[3][3]; // Inicializa a matriz do tabuleiro com 3x3
        reset(); // Reset do tabuleiro (preenche com espaços em branco)
    }

    /**
     * Reinicia o tabuleiro, preenchendo todas as posições com espaços em branco.
     */
    public void reset() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro[i][j] = ' '; // Preenche cada posição do tabuleiro com espaço em branco
            }
        }
    }

    /**
     * Realiza uma jogada no tabuleiro.
     * @param row linha onde será feita a jogada (0 a 2)
     * @param col coluna onde será feita a jogada (0 a 2)
     * @param player jogador que faz a jogada ('X' ou 'O')
     * @return true se a jogada foi válida (posição estava vazia), false caso contrário.
     */
    public boolean jogada(int row, int col, char player) {
        if (tabuleiro[row][col] == ' ') { // Verifica se a posição está vazia
            tabuleiro[row][col] = player; // Realiza a jogada colocando o símbolo do jogador na posição
            return true; // Retorna true indicando que a jogada foi válida
        }
        return false; // Retorna false se a posição não estava vazia
    }

    /**
     * Verifica se um jogador específico venceu o jogo.
     * @param player jogador a ser verificado ('X' ou 'O')
     * @return true se o jogador venceu, false caso contrário.
     */
    public boolean checkWin(char player) {
        // Verifica todas as linhas e colunas para ver se há três símbolos do jogador em linha
        for (int i = 0; i < 3; i++) {
            if (tabuleiro[i][0] == player && tabuleiro[i][1] == player && tabuleiro[i][2] == player) {
                return true; // Linha completa com símbolos do jogador
            }
            if (tabuleiro[0][i] == player && tabuleiro[1][i] == player && tabuleiro[2][i] == player) {
                return true; // Coluna completa com símbolos do jogador
            }
        }
        // Verifica as diagonais principais
        if (tabuleiro[0][0] == player && tabuleiro[1][1] == player && tabuleiro[2][2] == player) {
            return true; // Diagonal principal completa com símbolos do jogador
        }
        // Verifica a diagonal secundária
        if (tabuleiro[0][2] == player && tabuleiro[1][1] == player && tabuleiro[2][0] == player) {
            return true; // Diagonal secundária completa com símbolos do jogador
        }
        return false; // Retorna false se nenhum dos casos acima ocorrer
    }

    /**
     * Verifica se o tabuleiro está completamente preenchido.
     * @return true se o tabuleiro estiver cheio, false caso contrário.
     */
    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] == ' ') {
                    return false; // Ainda há pelo menos uma posição vazia no tabuleiro
                }
            }
        }
        return true; // Todas as posições do tabuleiro estão preenchidas
    }

    /**
     * Retorna uma representação do tabuleiro em forma de string.
     * @return uma string representando o tabuleiro.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(tabuleiro[i][j]);
                if (j < 2) sb.append(" | "); // Separador entre colunas
            }
            sb.append("\n");
            if (i < 2) sb.append("---------\n"); // Linha separadora entre linhas
        }
        return sb.toString(); // Retorna a representação do tabuleiro em forma de string
    }
}
