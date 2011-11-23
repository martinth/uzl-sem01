package de.uniluebeck.itm.vs.uebung3.aufgabe34;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.LengthFieldPrepender;

public class Server implements Runnable {

    private int port;
    private ChannelFactory factory;
    static final ChannelGroup allChannels = new DefaultChannelGroup("speiseplan-server");

    public Server(int port) {
        this.port = port;
    }

    public void run() {

        factory = new NioServerSocketChannelFactory(
            Executors.newCachedThreadPool(),
            Executors.newCachedThreadPool()
        );

        ServerBootstrap bootstrap = new ServerBootstrap(factory);

        // setup pipeline - each task is done by a seperate "layer" of code
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() {
                return Channels.pipeline(
                    /* Downstream handler
                     *  FrameDecoder chops of length field and passes the rest to the next downstream hander */
                    new LengthFieldBasedFrameDecoder(10000, 0, 4, 0, 4),
                    
                    /* Upstream Handler
                     * Prepends length field */
                    new LengthFieldPrepender(4),
                    
                    /* Downstream handler
                     * decodes the request to a POJO */
                    new RequestDecoder(),
                    
                    /* Upstream Handler
                     * serializes POJO to bytes */
                    new RequestEncoder(),
                    
                    /* Up- and Downstream Handler
                     * Fetches the Response from Database */
                    new SpeiseplanHandler());
            }
        });

        // bind to server port
        Channel channel = bootstrap.bind(new InetSocketAddress(this.port));
        allChannels.add(channel);
        
        // shutdown hook for gracefull shutdown
        final Server serverproc = this;
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                serverproc.shutdown();
            }
        }, "shutdownHook"));        
    }

    /**
     * Shutdown all channels
     */
    protected void shutdown() {
        System.out.println("Gacefully shuttding down");
        ChannelGroupFuture future = allChannels.close();
        future.awaitUninterruptibly();
        factory.releaseExternalResources();
    }
}
