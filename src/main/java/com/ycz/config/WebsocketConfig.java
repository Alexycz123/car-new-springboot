package com.ycz.config;

import com.ycz.service.Impl.SysUserMsgServiceImpl;
import com.ycz.service.SysUserMsgService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Component
public class WebsocketConfig {

    @Bean
      public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
      }

    @Bean(name = "sysUserMsgServiceImpl2")
    public SysUserMsgService getSysUserMsgService(){
          return new SysUserMsgServiceImpl();
      }

}
