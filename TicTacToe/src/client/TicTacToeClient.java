package src.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TicTacToeClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            String response = in.readLine();
            System.out.println(response);

            while (true) {
                response = in.readLine();
                if (response == null) {
                    break;
                }

                if (response.startsWith("UPDATE")) {
                    continue; // Ignore this line, the board state will follow
                } else if (response.startsWith("É a sua vez") || response.startsWith("Movimento inválido") || response.startsWith("Formato inválido")) {
                    System.out.println(response);
                    String move = scanner.nextLine();
                    out.println(move);
                } else {
                    System.out.println(response);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
