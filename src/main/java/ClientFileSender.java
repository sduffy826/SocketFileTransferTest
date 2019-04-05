import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ClientFileSender {

  public static void main(String[] args) throws IOException {
    ClientFileSender clientFileSender = new ClientFileSender();
    SocketChannel socketChannel = clientFileSender.createChannel();
    clientFileSender.sendFile(socketChannel);
  }

  private void sendFile(SocketChannel socketChannel) throws IOException {
    // Read input file
    Path path = Paths.get("c:\\tmp\\inFile.txt");
    FileChannel fileChannel = FileChannel.open(path);
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    while (fileChannel.read(buffer) > 0 ) {
      buffer.flip();
      socketChannel.write(buffer);
      buffer.clear();
    }
    socketChannel.close();
    fileChannel.close();    
  }

  private SocketChannel createChannel() throws IOException {
    SocketChannel socketChannel = SocketChannel.open();
    SocketAddress socketAddress = new InetSocketAddress("localhost",6000);
    socketChannel.connect(socketAddress);    
    return socketChannel;
  }
}
