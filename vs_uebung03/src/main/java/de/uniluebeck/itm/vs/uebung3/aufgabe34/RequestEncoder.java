package de.uniluebeck.itm.vs.uebung3.aufgabe34;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import de.uniluebeck.itm.vs.uebung3.serialization.EncodingException;
import de.uniluebeck.itm.vs.uebung3.serialization.elementwise.SpeiseplanResponseEncoder;
import de.uniluebeck.itm.vs.uebung3.types.SpeiseplanResponse;

/**
 * Encodes a POJO and writes the bytes to channel
 * 
 * @author martin
 */
public class RequestEncoder extends SimpleChannelHandler {

    private final SpeiseplanResponseEncoder encoder = new SpeiseplanResponseEncoder();
    private ChannelBuffer buf = ChannelBuffers.dynamicBuffer();

    public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws EncodingException {
        SpeiseplanResponse response = (SpeiseplanResponse) e.getMessage();
        buf.writeBytes(encoder.encode(response));
        Channels.write(ctx, e.getFuture(), buf);
    }
}
