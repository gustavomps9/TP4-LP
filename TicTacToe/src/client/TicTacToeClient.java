package src.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TicTacToeClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            // Recebe a mensagem inicial do servidor
            String response = in.readLine();
            System.out.println(response);

            // Loop principal para receber e enviar jogadas
            while (true) {
                response = in.readLine();
                if (response.startsWith("É a sua vez")) {
                    System.out.println(response);
                    String move = scanner.nextLine();
                    out.println("MOVE " + move); // Envia a jogada para o servidor
                } else if (response.startsWith("TABULEIRO")) {
                    System.out.println(response.substring(10)); // Imprime o tabuleiro
                } else {
                    System.out.println(response);
                    if (response.contains("venceu") || response.contains("Empate")) {
                        break; // Termina o loop se o jogo acabou
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}