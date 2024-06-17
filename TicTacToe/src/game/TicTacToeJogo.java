package src.game;

/**
 * Classe que representa a lógica do jogo do galo.
 */
public class TicTacToeJogo {
    private TicTacToeTabuleiro tabuleiro; // Instância do tabuleiro do jogo
    private char currentPlayer; // Jogador atual ('X' ou 'O')

    /**
     * Construtor que inicializa o jogo do galo.
     */
    public TicTacToeJogo() {
        tabuleiro = new TicTacToeTabuleiro(); // Inicializa o tabuleiro do jogo
        currentPlayer = 'X'; // Define que o jogador inicial é 'X'
    }

    /**
     * Realiza uma jogada no tabuleiro para o jogador atual.
     * @param row linha onde será feita a jogada (0 a 2)
     * @param col coluna onde será feita a jogada (0 a 2)
     * @return true se a jogada foi válida e realizada, false caso contrário.
     */
    public boolean jogada(int row, int col) {
        if (tabuleiro.jogada(row, col, currentPlayer)) { // Chama o método de jogada do tabuleiro
            return true; // Retorna true se a jogada foi válida
        }
        return false; // Retorna false se a jogada não foi válida
    }

    /**
     * Troca o jogador atual entre 'X' e 'O'.
     */
    public void trocaJogador() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X'; // Alterna entre 'X' e 'O'
    }

    /**
     * Obtém o jogador atual ('X' ou 'O').
     * @return o jogador atual.
     */
    public char getCurrentPlayer() {
        return currentPlayer; // Retorna o jogador atual
    }

    /**
     * Verifica se o jogador atual venceu o jogo.
     * @return true se o jogador atual venceu, false caso contrário.
     */
    public boolean checkWin() {
        return tabuleiro.checkWin(currentPlayer); // Chama o método de verificação de vitória do tabuleiro
    }

    /**
     * Verifica se o tabuleiro está completamente preenchido.
     * @return true se o tabuleiro estiver cheio, false caso contrário.
     */
    public boolean isFull() {
        return tabuleiro.isFull(); // Chama o método que verifica se o tabuleiro está completo
    }

    /**
     * Reinicia o jogo, dá reset ao tabuleiro e define 'X' como jogador inicial.
     */
    public void resetJogo() {
        tabuleiro.reset(); // Reseta o tabuleiro
        currentPlayer = 'X'; // Define 'X' como jogador inicial
    }

    /**
     * Obtém uma representação em forma de string do tabuleiro atual do jogo.
     * @return uma string representando o estado atual do tabuleiro.
     */
    public String getTabuleiroString() {
        return tabuleiro.toString(); // Chama o método toString do tabuleiro para obter a representação em string
    }
}
