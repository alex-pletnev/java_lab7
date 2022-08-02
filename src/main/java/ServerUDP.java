import SQLutil.SQLManager;
import collection.ParseFromSQL;
import util.Manager;
import util.RequestReader;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.logging.Logger;

public class ServerUDP {
    // Серверный UDP-сокет запущен на этом порту
    public final static int SERVICE_PORT = 44991;
    private static final Logger log = Logger.getLogger("Server");
    private static final Manager manager = new Manager();
    public static void main(String[] args) throws IOException {
        log.info("Server on");
        DatagramChannel datagramChannel = DatagramChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(SERVICE_PORT);
        datagramChannel.bind(inetSocketAddress);
        SQLManager.init();
        ParseFromSQL parseFromSQL = new ParseFromSQL();
        parseFromSQL.parseSQLToSet(manager.getCollectionManager());

        Thread read = new Thread(new RequestReader(datagramChannel, manager));
        read.start();
        Thread haltedHook = new Thread(SQLManager::close);
        Runtime.getRuntime().addShutdownHook(haltedHook);

    }


}

