package src.server;


import src.game.TicTacToeJogo;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TicTacToeServer {
    private static final int PORT = 8080;
    private TicTacToeJogo jogo;
    private List<ClientHandler> clients;

    public TicTacToeServer() {
        jogo = new TicTacToeJogo();
        clients = new ArrayList<>();
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor iniciado na porta " + PORT);

        while (clients.size() < 2) {
            Socket clientSocket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(clientSocket, clients.size() == 0 ? 'X' : 'O');
            clients.add(clientHandler);
            clientHandler.start();
        }

        broadcast("Jogo iniciado. Jogador X começa.");
        broadcast(jogo.getTabuleiroString());
    }

    private void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

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

                out.println("Bem-vindo ao Jogo do Galo! Você é o jogador " + player);

                while (true) {
                    if (jogo.getCurrentPlayer() == player) {
                        out.println("É a sua vez. Digite sua jogada no formato 'linha coluna':");

                        String input = in.readLine();
                        if (input == null) {
                            break;
                        }

                        String[] parts = input.split(" ");
                        if (parts.length == 2) {
                            try {
                                int row = Integer.parseInt(parts[0]) - 1;
                                int col = Integer.parseInt(parts[1]) - 1;

                                // Verifica se a jogada está dentro dos limites do tabuleiro
                                if (row >= 0 && row < 3 && col >= 0 && col < 3) {
                                    synchronized (jogo) {
                                        if (jogo.jogada(row, col)) {
                                            broadcast("UPDATE " + row + " " + col + " " + player);
                                            broadcast(jogo.getTabuleiroString());

                                            if (jogo.checkWin()) {
                                                broadcast("Jogador " + player + " venceu!");
                                                jogo.resetJogo();
                                                broadcast("Novo jogo iniciado. Jogador X começa.");
                                                broadcast(jogo.getTabuleiroString());
                                            } else if (jogo.isFull()) {
                                                broadcast("Empate!");
                                                jogo.resetJogo();
                                                broadcast("Novo jogo iniciado. Jogador X começa.");
                                                broadcast(jogo.getTabuleiroString());
                                            } else {
                                                jogo.trocaJogador();
                                            }
                                        } else {
                                            out.println("Movimento inválido. Tente novamente.");
                                        }
                                    }
                                } else {
                                    out.println("Jogada fora dos limites do tabuleiro. Tente novamente.");
                                }
                            } catch (NumberFormatException e) {
                                out.println("Formato inválido. Use números para linha e coluna.");
                            }
                        } else {
                            out.println("Formato inválido. Use números para linha e coluna.");
                        }
                    } else {
                        out.println("Aguarde a vez do jogador " + jogo.getCurrentPlayer() + ".");
                        Thread.sleep(2000); // Aguarda 2 segundos antes de verificar novamente
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }
    }

    public static void main(String[] args) throws IOException {
        TicTacToeServer server = new TicTacToeServer();
        server.start();
    }
}
