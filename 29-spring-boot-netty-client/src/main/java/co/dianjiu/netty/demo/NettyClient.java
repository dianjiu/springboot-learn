package co.dianjiu.netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * 客户端
 * 1.为初始化客户端，创建一个Bootstrap实例
 * 2.为进行事件处理分配了一个NioEventLoopGroup实例，其中事件处理包括创建新的连接以及处理入站和出站数据；
 * 3.当连接被建立时，一个EchoClientHandler实例会被安装到（该Channel的一个ChannelPipeline中；
 * 4.在一切都设置完成后，调用Bootstrap.connect()方法连接到远程节点。
 */
public class NettyClient {

    private final String host;
    private final int port;


    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    /**
     * 运行流程：
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        new NettyClient("127.0.0.1",20020).sendMessage("哈哈哈");
    }

    private void sendMessage(String content) throws Exception {

        /**
         * Netty用于接收客户端请求的线程池职责如下。
         * （1）接收客户端TCP连接，初始化Channel参数；
         * （2）将链路状态变更事件通知给ChannelPipeline
         */
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .remoteAddress(new InetSocketAddress(host,port))
                    /*..handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast("decoder", new StringDecoder());
                            p.addLast("encoder", new StringEncoder());
                            p.addLast(new ClientChannelInitializer());
                        }
                    })*/
                    .handler(new ClientChannelInitializer())
                    ;
            //绑定端口
            ChannelFuture f = b.connect().sync();
            f.channel().writeAndFlush(content);
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            group.shutdownGracefully().sync();
        }
    }

}