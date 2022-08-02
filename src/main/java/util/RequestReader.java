package util;

import commands.CommandAbstract;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class RequestReader implements Runnable {
    private final DatagramChannel datagramChannel;
    private final ByteBuffer inputBuffer = ByteBuffer.allocate(16384);
    private SocketAddress socketAddress = null;
    private Manager manager;
    private static final Logger log = Logger.getLogger("Server");

    public RequestReader(DatagramChannel datagramChannel, Manager manager) {
        this.datagramChannel = datagramChannel;
        this.manager = manager;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ((Buffer)inputBuffer).clear();
                socketAddress = datagramChannel.receive(inputBuffer);
                ((Buffer) inputBuffer).flip();
                log.info("we are in requestReader");
                ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(inputBuffer.array()));
                CommandAbstract command = (CommandAbstract) objectInputStream.readObject();
                ExecutorService executorService = Executors.newCachedThreadPool();
                executorService.submit(new RequestHandler(command, manager, datagramChannel, socketAddress));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
