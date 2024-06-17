package src.server;

import src.game.TicTacToeJogo; // Importa a classe do jogo do galo

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que implementa o servidor para o jogo do galo.
 */
public class TicTacToeServer {
    private static final int PORT = 1234; // Porta do servidor
    private TicTacToeJogo jogo; // Instância do jogo do galo
    private List<ClientHandler> clients; // Lista de handlers de clientes conectados

    /**
     * Construtor que inicializa o jogo e a lista de clientes.
     */
    public TicTacToeServer() {
        jogo = new TicTacToeJogo(); // Inicializa o jogo
        clients = new ArrayList<>(); // Inicializa a lista de clientes
    }

    /**
     * Método principal que inicia o servidor.
     * @throws IOException se ocorrer um erro de I/O ao abrir o socket do servidor.
     */
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT); // Cria um socket do servidor
        System.out.println("Servidor iniciado na porta " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept(); // Aceita conexões dos clientes
            ClientHandler clientHandler = new ClientHandler(clientSocket, clients.size() == 0 ? 'X' : 'O');
            clients.add(clientHandler); // Adiciona o handler do cliente à lista
            clientHandler.start(); // Inicia a thread para lidar com o cliente

            if (clients.size() == 2) {
                broadcast("Jogo iniciado. Jogador X começa.");
                broadcastBoard();
            }
        }
    }

    /**
     * Método para enviar uma mensagem para todos os clientes conectados.
     * @param message a mensagem a ser enviada.
     */
    private void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    /**
     * Método para enviar o estado atual do tabuleiro para todos os clientes conectados.
     */
    private void broadcastBoard() {
        String board = jogo.getTabuleiroString();
        String playerInfo = "(Jogador X) " + clients.get(0).getPlayerName() + " vs " + clients.get(1).getPlayerName() + " (Jogador O)";
        broadcast(playerInfo + "\n" + board);
    }

    /**
     * Classe interna que representa um handler para cada cliente conectado.
     */
    private class ClientHandler extends Thread {
        private Socket clientSocket; // Socket do cliente
        private BufferedReader in; // Leitor de entrada
        private PrintWriter out; // Escritor de saída
        private char player; // Identificador do jogador ('X' ou 'O')
        private String playerName; // Nome do jogador

        /**
         * Construtor do handler do cliente.
         * @param socket o socket do cliente.
         * @param player o identificador do jogador ('X' ou 'O').
         */
        public ClientHandler(Socket socket, char player) {
            this.clientSocket = socket;
            this.player = player;
        }

        /**
         * Método que executa a thread para cada cliente conectado.
         */
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // Inicializa o leitor de entrada
                out = new PrintWriter(clientSocket.getOutputStream(), true); // Inicializa o escritor de saída

                out.println("Bem-vindo ao Jogo do Galo! É o jogador " + player); // Mensagem inicial para o jogador
                out.println("Por favor, escreva o seu nome:"); // Solicitação para o nome do jogador
                playerName = in.readLine(); // Lê o nome enviado pelo cliente

                if (clients.size() < 2) {
                    out.println("A aguardar a conexão de outro jogador..."); // Informa ao cliente que está aguardando o outro jogador
                }

                while (clients.size() < 2) {
                    Thread.sleep(1000); // Espera um segundo antes de verificar novamente
                }

                while (true) {
                    if (jogo.getCurrentPlayer() == player) {
                        out.println("É a sua vez. Escreva a sua jogada no formato 'linha coluna' (Ex: 1 1):"); // Informa ao jogador que é sua vez

                        String input = in.readLine(); // Lê a jogada do jogador
                        if (input == null) {
                            break; // Se não houver entrada, sai do loop
                        }

                        String[] parts = input.split(" ");
                        if (parts.length == 2) {
                            try {
                                int row = Integer.parseInt(parts[0]) - 1; // Converte a linha para índice
                                int col = Integer.parseInt(parts[1]) - 1; // Converte a coluna para índice

                                if (row >= 0 && row < 3 && col >= 0 && col < 3) {
                                    synchronized (jogo) {
                                        if (jogo.jogada(row, col)) { // Realiza a jogada no jogo do galo
                                            broadcastBoard(); // Atualiza o tabuleiro para todos os clientes

                                            if (jogo.checkWin()) { // Verifica se houve um vencedor
                                                broadcast("Jogador " + playerName + " ganhou!\n");
                                                jogo.resetJogo(); // Reinicia o jogo
                                                broadcast("Novo jogo iniciado. Jogador X começa.");
                                                broadcastBoard();
                                            } else if (jogo.isFull()) { // Verifica se houve empate
                                                broadcast("Empate!\n");
                                                jogo.resetJogo(); // Reinicia o jogo
                                                broadcast("Novo jogo iniciado. Jogador X começa.");
                                                broadcastBoard();
                                            } else {
                                                jogo.trocaJogador(); // Troca o jogador atual
                                                broadcast("Aguarde a vez do jogador " + jogo.getCurrentPlayer() + ".");
                                            }
                                        } else {
                                            out.println(jogo.getTabuleiroString()); // Informa ao jogador o estado atual do tabuleiro
                                        }
                                    }
                                } else {
                                    out.println(jogo.getTabuleiroString()); // Informa ao jogador o estado atual do tabuleiro
                                }
                            } catch (NumberFormatException e) {
                                out.println(jogo.getTabuleiroString()); // Informa ao jogador o estado atual do tabuleiro
                            }
                        } else {
                            out.println(jogo.getTabuleiroString()); // Informa ao jogador o estado atual do tabuleiro
                        }
                    } else {
                        out.println("Aguarde a vez do jogador " + jogo.getCurrentPlayer() + "."); // Informa ao jogador que é necessário esperar
                        Thread.sleep(2000); // Espera 2 segundos antes de verificar novamente
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace(); // Trata exceções de I/O ou de interrupção
            } finally {
                try {
                    clientSocket.close(); // Fecha o socket do cliente
                } catch (IOException e) {
                    e.printStackTrace(); // Trata exceção de fechamento do socket
                }
            }
        }

        /**
         * Método para enviar uma mensagem para o cliente.
         * @param message a mensagem a ser enviada.
         */
        public void sendMessage(String message) {
            out.println(message); // Envia a mensagem para o cliente
        }

        /**
         * Método para obter o nome do jogador associado a este handler.
         * @return o nome do jogador.
         */
        public String getPlayerName() {
            return playerName; // Retorna o nome do jogador
        }
    }

    /**
     * Método principal que inicia o servidor do jogo do galo.
     * @param args argumentos da linha de comando (não utilizados).
     * @throws IOException se ocorrer um erro de I/O ao abrir o socket do servidor.
     */
    public static void main(String[] args) throws IOException {
        TicTacToeServer server = new TicTacToeServer(); // Cria uma instância do servidor
        server.start(); // Inicia o servidor
    }
}
