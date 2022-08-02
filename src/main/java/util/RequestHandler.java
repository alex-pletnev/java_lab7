package util;

import commands.CommandAbstract;
import commands.CommandParser;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.logging.Logger;

public class RequestHandler implements Runnable {
    private CommandAbstract command;
    private Manager manager;
    private DatagramChannel datagramChannel;
    private static final CommandParser parser = new CommandParser();
    private SocketAddress address;
    private static final Logger log = Logger.getLogger("Server");

    public RequestHandler(CommandAbstract command, Manager manager, DatagramChannel datagramChannel, SocketAddress address) {
        this.command = command;
        this.manager = manager;
        this.datagramChannel = datagramChannel;
        this.address = address;
    }

    @Override
    public void run() {
        Reply reply = parser.executeCommand(manager, command, command.getUser());
        log.info("we are in requestHandler");
        Thread send = new Thread(new RequestSender(reply, datagramChannel, address));
        send.start();
    }
}
