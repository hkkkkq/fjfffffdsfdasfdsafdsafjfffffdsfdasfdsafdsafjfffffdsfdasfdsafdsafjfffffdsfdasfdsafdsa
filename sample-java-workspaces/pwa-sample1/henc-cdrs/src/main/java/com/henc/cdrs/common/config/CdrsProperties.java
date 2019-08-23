package com.henc.cdrs.common.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "henc.cdrs")
public class CdrsProperties {

    @Getter
    @Setter
    private String callbackTelNo;

    @Getter
    @Setter
    private String reportUrl;

    @Getter
    @Setter
    private String signfilePath;
}