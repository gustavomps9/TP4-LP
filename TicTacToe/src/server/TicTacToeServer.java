package server;

import game.TicTacToeTabuleiro;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TicTacToeServer {
    private static final int PORT = 8080; // Porta do servidor
    private TicTacToeTabuleiro game; // Instância do tabuleiro do jogo
    private List<ClientHandler> clients; // Lista de clientes conectados
    private char currentPlayer; // Jogador atual

    public TicTacToeServer() {
        game = new TicTacToeTabuleiro(); // Inicializa o tabuleiro do jogo
        clients = new ArrayList<>(); // Inicializa a lista de clientes
        currentPlayer = 'X'; // Define o jogador inicial como 'X'
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT); // Cria o socket do servidor
        System.out.println("Servidor iniciado na porta " + PORT);

        // Espera até que dois clientes se conectem
        while (clients.size() < 2) {
            Socket clientSocket = serverSocket.accept(); // Aceita a conexão do cliente
            ClientHandler clientHandler = new ClientHandler(clientSocket, clients.size() == 0 ? 'X' : 'O');
            clients.add(clientHandler); // Adiciona o cliente à lista de clientes
            clientHandler.start(); // Inicia a thread do cliente
        }

        // Inicia o jogo quando dois clientes estão conectados
        broadcast("Jogo iniciado. Jogador X começa.");
        broadcast(game.getTabuleiroString()); // Envia o tabuleiro inicial para ambos os clientes
    }

    // Método para enviar mensagens a todos os clientes
    private void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message); // Envia a mensagem para o cliente
        }
    }

    // Classe interna para manipular as conexões dos clientes
    private class ClientHandler extends Thread {
        private Socket clientSocket;
        private BufferedReader in;
        private PrintWriter out;
        private char player;

        public ClientHandler(Socket socket, char player) {
            this.clientSocket = socket;
            this.player = player;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Mensagem inicial para o jogador
                out.println("Bem-vindo ao Jogo do Galo! Você é o jogador " + player);

                // Loop para receber e processar as jogadas dos clientes
                while (true) {
                    if (currentPlayer == player) {
                        out.println("É a sua vez. Digite sua jogada no formato 'linha coluna':");
                    }

                    String input = in.readLine(); // Lê a entrada do cliente
                    if (input == null) {
                        break; // Encerra o loop se a entrada for nula
                    }

                    String[] parts = input.split(" ");
                    if (parts.length == 2) {
                        try {
                            int row = Integer.parseInt(parts[0]);
                            int col = Integer.parseInt(parts[1]);

                            // Verifica e faz a jogada
                            if (game.jogada(row, col, player)) {
                                broadcast("UPDATE " + row + " " + col + " " + player);
                                broadcast(game.getTabuleiroString()); // Envia o tabuleiro atualizado para todos

                                // Verifica se o jogador atual venceu
                                if (game.checkWin(player)) {
                                    broadcast("Jogador " + player + " venceu!");
                                    game.resetTabuleiro(); // Reseta o tabuleiro do jogo
                                    broadcast("Novo jogo iniciado. Jogador X começa.");
                                    currentPlayer = 'X'; // Reinicia o jogo com o jogador 'X'
                                    broadcast(game.getTabuleiroString()); // Envia tabuleiro vazio para reiniciar
                                } else if (game.isFull()) {
                                    broadcast("Empate!"); // Verifica se houve empate
                                    game.resetTabuleiro(); // Reseta o tabuleiro do jogo
                                    broadcast("Novo jogo iniciado. Jogador X começa.");
                                    currentPlayer = 'X'; // Reinicia o jogo com o jogador 'X'
                                    broadcast(game.getTabuleiroString()); // Envia tabuleiro vazio para reiniciar
                                } else {
                                    trocaJogador(); // Troca o jogador atual
                                }
                            } else {
                                out.println("Movimento inválido. Tente novamente.");
                            }
                        } catch (NumberFormatException e) {
                            out.println("Formato inválido. Use números para linha e coluna.");
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close(); // Fecha o socket do cliente
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Método para enviar mensagem ao cliente
        public void sendMessage(String message) {
            out.println(message);
        }

        // Método para trocar o jogador atual
        private void trocaJogador() {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
    }

    public static void main(String[] args) throws IOException {
        TicTacToeServer server = new TicTacToeServer(); // Cria uma instância do servidor
        server.start(); // Inicia o servidor
    }
}
