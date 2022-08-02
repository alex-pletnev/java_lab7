import commands.CommandInterface;
import commands.CommandParser;
import util.AuthorizerManager;
import util.CommandManager;
import util.Reply;
import util.User;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class ClientUDP {
    /* Порт сервера, к которому собирается
  подключиться клиентский сокет */
    public final static int SERVICE_PORT = 44991;
    private static final ByteBuffer outputBuffer = ByteBuffer.allocate(16384);
    private static final ByteBuffer inputBuffer = ByteBuffer.allocate(16384);

    public static void main(String[] args) {
        try {
            User user = null;
            DatagramSocket datagramSocket = new DatagramSocket();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(SERVICE_PORT);
            CommandParser commandParser = new CommandParser();
            CommandManager commandManager = new CommandManager();
            AuthorizerManager authorizerManager = new AuthorizerManager();
            datagramSocket.setSoTimeout(4000);
            boolean power = true;
            boolean authorized = false;
            CommandInterface command;
            while (!authorized) {
                command = authorizerManager.initAuthorizer(new Scanner(System.in));

                DatagramPacket outPacket = makeOutPacket(command, inetSocketAddress);
                datagramSocket.send(outPacket);
                DatagramPacket inputPacket = makeInputPacket(inetSocketAddress);
                try {
                    datagramSocket.receive(inputPacket);
                } catch (SocketTimeoutException ex) {
                    System.err.println("Пакет утеренн!\nПовторите авторизацию...");
                    continue;
                }
                ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(inputPacket.getData()));
                Reply reply = (Reply) objectInputStream.readObject();
                String[] answer = reply.toString().split("   ");
                reply.setAnswer(answer[0]);
                System.out.println(reply);
                if (answer.length < 3) {
                    continue;
                }
                String username = answer[1];
                String password = answer[2];
                user = new User(username, password);
                authorized = true;
            }

            while (power) {
                command = commandParser.parseCommand(commandManager, new Scanner(System.in), user);
                if (command == null) {
                    continue;
                }

                DatagramPacket outPacket = makeOutPacket(command, inetSocketAddress);
                datagramSocket.send(outPacket);
                DatagramPacket inputPacket = makeInputPacket(inetSocketAddress);
                try {
                    datagramSocket.receive(inputPacket);
                } catch (SocketTimeoutException ex) {
                    System.err.println("Пакет утеренн!\nПовторите ввод...");
                    continue;
                }
                ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(inputPacket.getData()));
                Reply reply = (Reply) objectInputStream.readObject();
                System.out.println(reply);

            }


        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }


    private static byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
            try (ObjectOutputStream o = new ObjectOutputStream(b)) {
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }
    private static DatagramPacket makeOutPacket(Object data, InetSocketAddress inetSocketAddress) throws IOException{
        ((Buffer) outputBuffer).clear();
        outputBuffer.put(serialize(data));
        ((Buffer)outputBuffer).flip();

        return new DatagramPacket(outputBuffer.array(), outputBuffer.limit(), inetSocketAddress);

    }
    private static DatagramPacket makeInputPacket(InetSocketAddress inetSocketAddress) {
        ((Buffer)inputBuffer).clear();
        return new DatagramPacket(inputBuffer.array(), inputBuffer.limit(), inetSocketAddress);
    }
}
