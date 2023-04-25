package cn.edu.zut.mfs.utils;

import io.rsocket.DuplexConnection;
import io.rsocket.RSocket;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.net.SocketAddress;

public class RSocketUtil {
    public static DuplexConnection getConnectionFromRequester(RSocketRequester requester) {
        RSocket rsocket = requester.rsocket();
        Field connectionField = ReflectionUtils.findField(rsocket.getClass(), "connection");
        connectionField.setAccessible(true);
        return (DuplexConnection) ReflectionUtils.getField(connectionField, rsocket);
    }

    public static SocketAddress getRemoteAddressFromRequester(RSocketRequester requester) {
        DuplexConnection connection = getConnectionFromRequester(requester);
        return connection.remoteAddress();
    }
}
