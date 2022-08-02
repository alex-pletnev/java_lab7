package commands.com;

import commands.CommandAbstract;
import commands.CommandInterface;
import commands.CommandParser;
import exception.ArgumentException;
import exception.CommandException;
import exception.UnknownCommandException;
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
import java.util.*;

public class ExecuteScript {
    private static final int SERVICE_PORT = 44991;
    private final ByteBuffer outputBuffer = ByteBuffer.allocate(16384);
    private final ByteBuffer inputBuffer = ByteBuffer.allocate(16384);
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private static final List<String> scriptList = new ArrayList<>();
    private final List<Object> argList = new ArrayList<>();

    public List<Object> checkArguments() throws ArgumentException {
        if (getArgList().size() != 1) {
            getArgList().forEach(System.out::println);
            throw new ArgumentException("Неверное количество аргументов повторите ввод команды!");
        }
        return getArgList();
    }

    public Reply execute(User user) throws CommandException{
        for (String lScr: scriptList) {
            if (lScr.equals(getArgList().get(0))) {
                    throw new CommandException("Рекурсия!(Скрипт завершен досрочно)");
            }
        }
        scriptList.add((String) getArgList().get(0));
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream((String) getArgList().get(0)))){
            Scanner scanner = new Scanner(inputStream);
            DatagramSocket datagramSocket = new DatagramSocket();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(SERVICE_PORT);
            CommandParser commandParser = new CommandParser();
            CommandManager commandManager = new CommandManager();
            CommandAbstract command = null;
            datagramSocket.setSoTimeout(90000);
            boolean power = true;
            while (power) {
                try {
                    command = (CommandAbstract) parseCommand(commandManager, scanner, user);
                } catch (NoSuchElementException ex) {
                    scriptList.remove((String) getArgList().get(0));
                    return new Reply("Скрипт выполнен");
                }

                if (command == null) {
                    scriptList.remove((String) getArgList().get(0));
                    throw new CommandException("Ошибка в файле, скрипт завершен досрочно");
                }
                DatagramPacket outPacket = makeOutPacket(command, inetSocketAddress);
                datagramSocket.send(outPacket);
                DatagramPacket inputPacket = makeInputPacket(inetSocketAddress);
                try {
                    datagramSocket.receive(inputPacket);
                } catch (SocketTimeoutException ex) {
                    System.err.println("Пакет утеренн!\nПовторите ввод...");
                    power = false;
                    continue;
                }
                ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(inputPacket.getData()));
                Reply reply = (Reply) objectInputStream.readObject();
                System.out.println(reply);

            }


        }
        catch (IOException | ClassNotFoundException ex) {
            scriptList.remove((String) getArgList().get(0));
            throw new CommandException("Ошибка при чтении файла!");
        }
        scriptList.remove((String) getArgList().get(0));
        return new Reply("Скрипт выполнен");
    }

    private byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
            try (ObjectOutputStream o = new ObjectOutputStream(b)) {
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }
    private DatagramPacket makeOutPacket(Object data, InetSocketAddress inetSocketAddress) throws IOException{
        ((Buffer) outputBuffer).clear();
        outputBuffer.put(serialize(data));
        ((Buffer)outputBuffer).flip();

        return new DatagramPacket(outputBuffer.array(), outputBuffer.limit(), inetSocketAddress);

    }
    private DatagramPacket makeInputPacket(InetSocketAddress inetSocketAddress) {
        ((Buffer)inputBuffer).clear();
        return new DatagramPacket(inputBuffer.array(), inputBuffer.limit(), inetSocketAddress);
    }

    public List<Object> getArgList() {
        return argList;
    }

    public void setArgList(String[] argArr) {
        argList.addAll(Arrays.asList(argArr));
        argList.remove(0);
    }
    private CommandInterface parseCommand(CommandManager manager, Scanner input, User user) throws NoSuchElementException{
        try {
            String str = input.nextLine();
            if (!str.equals("")) {
                String[] splitStr = str.split(" ");

                if (splitStr[0].equals("execute_script")) {

                    ExecuteScript executeScript = new ExecuteScript();
                    try {
                        executeScript.setArgList(splitStr);
                        executeScript.checkArguments();
                        System.out.println(executeScript.execute(user));
                    } catch (ArgumentException | CommandException ex) {
                        ex.getMessage();
                    }

                    return null;
                }

                if (manager.getCommandMap().get(splitStr[0]) == null) {
                    try {
                        throw new UnknownCommandException("Введена неизвестная команда!");
                    } catch (UnknownCommandException ex) {
                        System.err.println(ex.getMessage());
                        return null;
                    }
                }
                if (splitStr[0].equals("exit")) {
                    System.out.println("Сеанс завершенн");
                    System.exit(1);
                }

                manager.getCommandMap().get(splitStr[0]).setArgList(splitStr);
                CommandInterface command = manager.getCommandMap().get(splitStr[0]);
                command.setUser(user);
                try {
                    command.checkArguments(input, 1);
                } catch (ArgumentException ex) {
                    System.err.println(ex.getMessage());
                    return null;
                }


                return command;

            } else {
                System.err.print("Enter your command...(plz)\n");
                return null;
            }
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException();
        }

    }
}

