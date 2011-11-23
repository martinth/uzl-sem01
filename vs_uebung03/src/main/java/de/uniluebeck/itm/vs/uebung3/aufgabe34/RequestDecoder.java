package de.uniluebeck.itm.vs.uebung3.aufgabe34;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import de.uniluebeck.itm.vs.uebung3.serialization.elementwise.SpeiseplanRequestDecoder;
import de.uniluebeck.itm.vs.uebung3.types.SpeiseplanRequest;

/**
 * Decodes a frame to a POJO
 * 
 * @author martin
 */
public class RequestDecoder extends FrameDecoder {

    private final SpeiseplanRequestDecoder decoder = new SpeiseplanRequestDecoder();

    @Override
    protected SpeiseplanRequest decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer)
            throws Exception {

        // only decode if 3 bytes are readable
        if (buffer.readableBytes() < 3) {
            return null;
        }
        byte[] msg = new byte[buffer.readableBytes()];
        buffer.readBytes(msg);
        return decoder.decode(msg);
    }

}
