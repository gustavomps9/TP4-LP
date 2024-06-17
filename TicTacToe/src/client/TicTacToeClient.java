package src.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Classe que implementa o cliente para o jogo do galo.
 */
public class TicTacToeClient {
    private static final String SERVER_ADDRESS = "localhost"; // Endereço do servidor
    private static final int SERVER_PORT = 1234; // Porta do servidor

    /**
     * Método principal que inicia o cliente.
     * @param args argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); // Cria um socket para se conectar ao servidor
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Cria leitor de entrada do servidor
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // Cria escritor de saída para o servidor
             Scanner scanner = new Scanner(System.in)) { // Cria scanner para ler entrada do usuário

            String response = in.readLine(); // Lê a primeira mensagem do servidor
            System.out.println(response); // Exibe a mensagem inicial recebida do servidor

            // Loop principal do cliente para interagir com o servidor
            while (true) {
                response = in.readLine(); // Lê uma mensagem do servidor
                if (response == null) {
                    break; // Se a mensagem for nula, sai do loop
                }

                // Tratamento das diferentes mensagens recebidas do servidor
                if (response.startsWith("UPDATE")) {
                    continue; // Se a mensagem começar com "UPDATE", continua o loop para aguardar mais mensagens
                } else if (response.startsWith("Jogador X:")) {
                    System.out.println(response); // Exibe a mensagem indicando o turno do jogador X
                    continue; // Continua o loop para aguardar mais mensagens
                } else if (response.startsWith("Por favor, escreva o seu nome:")) {
                    System.out.println(response); // Exibe a mensagem solicitando ao jogador que escreva seu nome
                    String name = scanner.nextLine(); // Lê o nome inserido pelo jogador
                    out.println(name); // Envia o nome para o servidor
                } else if (response.startsWith("É a sua vez") || response.startsWith("Movimento inválido") || response.startsWith("Formato inválido")) {
                    System.out.println(response); // Exibe mensagens relacionadas ao turno do jogador ou movimento inválido
                    String move = scanner.nextLine(); // Lê a jogada inserida pelo jogador
                    out.println(move); // Envia a jogada para o servidor
                } else {
                    System.out.println(response); // Exibe outras mensagens recebidas do servidor
                }
            }

        } catch (IOException e) {
            e.printStackTrace(); // Trata exceções de I/O (por exemplo, falha na conexão com o servidor)
        }
    }
}
