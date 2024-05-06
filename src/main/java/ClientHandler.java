import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            OutputStream writer = clientSocket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String input;
            while ((input = reader.readLine()) != null) {
                if (!input.equals("PING")) {
                    // System.out.println("Received message from client: " + input);
                    continue;
                }
                writer.write("+PONG\r\n".getBytes());
                writer.flush();
            }

        } catch (Exception e) {
            System.out.println("IOExeptin " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }
    }
}
