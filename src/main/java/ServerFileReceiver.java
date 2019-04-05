import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

public class ServerFileReceiver {

  public static void main(String[] args) throws IOException {
     ServerFileReceiver serverFileReceiver = new ServerFileReceiver();
     SocketChannel socketChannel = serverFileReceiver.createServerSocketChannel();
     serverFileReceiver.readFileFromSocketChannel(socketChannel);
  }

  private void readFileFromSocketChannel(SocketChannel socketChannel) throws IOException {
    Path path = Paths.get("c:\\tmp\\outFile.txt");
    FileChannel fileChannel = FileChannel.open(path, EnumSet.of(StandardOpenOption.CREATE,
                                                                StandardOpenOption.TRUNCATE_EXISTING,
                                                                StandardOpenOption.WRITE));
    // Allocate buffer
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    while (socketChannel.read(buffer) > 0) {
      buffer.flip();
      fileChannel.write(buffer);
      buffer.clear();
    }
    fileChannel.close();
    System.out.println("Received file from client");
    socketChannel.close();                  
  }

  private SocketChannel createServerSocketChannel() throws IOException {
    ServerSocketChannel serverSocket = null;
    SocketChannel client = null;
    serverSocket = ServerSocketChannel.open();
    serverSocket.socket().bind(new InetSocketAddress(6000));
    client = serverSocket.accept();
    System.out.println("Connection established in ServerFileReceiver.createServerSocketChannel" + client.getRemoteAddress());
    return client;
  }

}
