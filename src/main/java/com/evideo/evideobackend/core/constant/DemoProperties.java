package com.evideo.evideobackend.core.constant;

import com.evideo.evideobackend.core.service.implement.DefaultAuthenticationProviderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.inject.Inject;

public class DemoProperties {
    static Logger log = Logger.getLogger(DemoProperties.class.getName());

    @Inject
    private Environment env;
    private String _secretKey = "";
    DemoProperties() {
        _secretKey = env.getProperty("security.key");
        log.info("_secretKey:"+_secretKey);
    }

    public static  String secretKey = "mustBe16BytesKey";
}
