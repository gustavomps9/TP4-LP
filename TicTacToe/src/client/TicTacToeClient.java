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
                } else if (response.startsWith("UPDATE")) {
                    String[] parts = response.split(" ");
                    int row = Integer.parseInt(parts[1]);
                    int col = Integer.parseInt(parts[2]);
                    char player = parts[3].charAt(0);
                    System.out.println("Jogada realizada por jogador " + player + " na posição (" + row + ", " + col + ")");
                } else if (response.contains("|")) { // Assuming the board string contains '|'
                    System.out.println(response); // Print the board
                } else {
                    System.out.println(response);
                    break; // Termina o loop se o jogo acabou
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
