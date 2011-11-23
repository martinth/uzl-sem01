package de.uniluebeck.itm.vs.uebung3.aufgabe34;


import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import de.uniluebeck.itm.vs.uebung3.apps.SpeiseplanDatabase;
import de.uniluebeck.itm.vs.uebung3.types.Speiseplan;
import de.uniluebeck.itm.vs.uebung3.types.SpeiseplanRequest;
import de.uniluebeck.itm.vs.uebung3.types.SpeiseplanResponse;

public class SpeiseplanHandler extends SimpleChannelHandler {
    

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        SpeiseplanRequest request = (SpeiseplanRequest) e.getMessage();        
        Speiseplan plan = SpeiseplanDatabase.getSpeiseplan(request.getDayOfYear());
        SpeiseplanResponse response = new SpeiseplanResponse(request.getDayOfYear(), plan);
        ChannelFuture f = e.getChannel().write(response);
        
        // close channel when data is send
        f.addListener(ChannelFutureListener.CLOSE);        
    }
    
    @Override
    public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) {
        Server.allChannels.add(e.getChannel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        e.getCause().printStackTrace();
        Channel ch = e.getChannel();
        ch.close();
    }
}
