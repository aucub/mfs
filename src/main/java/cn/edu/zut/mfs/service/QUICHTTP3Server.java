package cn.edu.zut.mfs.service;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.incubator.codec.http3.*;
import io.netty.incubator.codec.quic.*;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class QUICHTTP3Server {
    static final int PORT = 9999;
    private static final byte[] CONTENT = "Hello World!\r\n".getBytes(CharsetUtil.US_ASCII);
    private static int port = PORT;
    private static SelfSignedCertificate cert;
    private static Bootstrap bs = new Bootstrap();
    private static Channel channel = null;
    private static NioEventLoopGroup group = new NioEventLoopGroup(1);
    private static ChannelHandler codec;
    private static QuicSslContext sslContext;

    public static void initServer() {
        try {
            cert = new SelfSignedCertificate();
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }
        sslContext = QuicSslContextBuilder.forServer(cert.key(), null, cert.cert())
                .applicationProtocols(Http3.supportedApplicationProtocols()).build();
        codec = Http3.newQuicServerCodecBuilder()
                .sslContext(sslContext)
                .maxIdleTimeout(5000, TimeUnit.MILLISECONDS)
                .initialMaxData(10000000)
                .initialMaxStreamDataBidirectionalLocal(1000000)
                .initialMaxStreamDataBidirectionalRemote(1000000)
                .initialMaxStreamsBidirectional(100)
                .tokenHandler(InsecureQuicTokenHandler.INSTANCE)
                .handler(new ChannelInitializer<QuicChannel>() {
                    @Override
                    protected void initChannel(QuicChannel ch) {
                        ch.pipeline().addLast(new Http3ServerConnectionHandler(
                                new ChannelInitializer<QuicStreamChannel>() {
                                    @Override
                                    protected void initChannel(QuicStreamChannel ch) {
                                        ch.pipeline().addLast(new Http3RequestStreamInboundHandler() {
                                            @Override
                                            protected void channelRead(ChannelHandlerContext ctx,
                                                                       Http3HeadersFrame frame, boolean isLast) {
                                                if (isLast) {
                                                    writeResponse(ctx);
                                                }
                                                ReferenceCountUtil.release(frame);
                                            }

                                            @Override
                                            protected void channelRead(ChannelHandlerContext ctx,
                                                                       Http3DataFrame frame, boolean isLast) {
                                                if (isLast) {
                                                    writeResponse(ctx);
                                                }
                                                ReferenceCountUtil.release(frame);
                                            }

                                            private void writeResponse(ChannelHandlerContext ctx) {
                                                Http3HeadersFrame headersFrame = new DefaultHttp3HeadersFrame();
                                                headersFrame.headers().status("200");
                                                headersFrame.headers().add("server", "netty");
                                                headersFrame.headers().addInt("content-length", CONTENT.length);
                                                ctx.write(headersFrame);
                                                ctx.writeAndFlush(new DefaultHttp3DataFrame(
                                                                Unpooled.wrappedBuffer(CONTENT)))
                                                        .addListener(QuicStreamChannel.SHUTDOWN_OUTPUT);
                                            }
                                        });
                                    }
                                }));
                    }
                }).build();
    }

    public static void Server() {
        try {
            channel = bs.group(group)
                    .channel(NioDatagramChannel.class)
                    .handler(codec)
                    .bind(new InetSocketAddress(port)).sync().channel();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Netty QUIC HTTP3 started on port(s): " + port);
    }

    public static void closeServer() {
        try {
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        group.shutdownGracefully();
    }
}