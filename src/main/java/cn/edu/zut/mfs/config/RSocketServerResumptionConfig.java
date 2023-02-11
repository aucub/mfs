package cn.edu.zut.mfs.config;

import io.rsocket.core.RSocketServer;
import io.rsocket.core.Resume;
import org.springframework.boot.rsocket.server.RSocketServerCustomizer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


/**
 * 使套接字能够恢复。
 * 默认情况下，Resume Session 的持续时间为 120 秒，超时时间为 10 秒，
 * 并使用 In Memory（易失性、非持久性）会话存储。
 */
@Profile("resumption")
@Component
public class RSocketServerResumptionConfig implements RSocketServerCustomizer {

    @Override
    public void customize(RSocketServer rSocketServer) {
        rSocketServer.resume(new Resume());
    }

}
