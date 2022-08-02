package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.logging.Logger;

public class RequestSender implements Runnable {
    private Reply reply;
    private final ByteBuffer outputBuffer = ByteBuffer.allocate(16384);
    private DatagramChannel datagramChannel;
    private SocketAddress address;
    private static final Logger log = Logger.getLogger("Server");

    public RequestSender(Reply reply, DatagramChannel datagramChannel, SocketAddress address) {
        this.reply = reply;
        this.datagramChannel = datagramChannel;
        this.address = address;
    }

    @Override
    public void run() {
        try {
            ((Buffer)outputBuffer).clear();
            outputBuffer.put(serialize(reply));
            ((Buffer)outputBuffer).flip();
            datagramChannel.send(outputBuffer, address);
            log.info("we are in requestSender");
        } catch (IOException ex) {
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

}
