package com.henc.cdrs;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * Mutil module 프로젝트의 경우 공통에서 정의한 환경설정을 그대로
 * 상속받지만 경우에 따라서는 공통의 환경설정을 Override할 필요가있고
 * 또는 공통에는 없는 자신만의 환경설정이 필요한 경우가 있다.
 * 본 클래스는 *-config.yml 형태의 환경설정 파일을 읽어 공통에서 정의한
 * 환경설정 속성과 Merge 한다.
 *
 * @author YongSang
 */
public class PropertyFileRegisteringListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    public static final String PROPERTY_PATTERN = "classpath*:/*-config.yml";

    private ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

    private YamlPropertySourceLoader sourceLoader = new YamlPropertySourceLoader();

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
        try {
            loadProperties(environment);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load configuration files", e);
        }
    }

    /**
     * classpath:/*-config.yml 패턴의 설정파일을 로드
     * @param environment 환경설정
     * @throws IOException I/O 오류의 경우
     */
    private void loadProperties(ConfigurableEnvironment environment) throws IOException {
        Resource[] properties = resourceResolver.getResources(PROPERTY_PATTERN);
        configureEnvironment(environment, properties);
    }

    /**
     * 현재 활성화된 프로파일을 기준으로 설정파일을 분리
     * @param environment 환경설정
     * @param properties 환경설정 파일
     * @throws IOException I/O 오류의 경우
     */
    private void configureEnvironment(ConfigurableEnvironment environment, Resource[] properties) throws IOException {
        for (Resource property : properties) {
            if (property.exists()) {
                String name = property.getFilename();
                configurePropertySources(environment, property, name, null);
                for (String activeProfile : environment.getActiveProfiles()) {
                    configurePropertySources(environment, property, name + "#" + activeProfile, activeProfile);
                }
            }
        }
    }

    /**
     * 활성화된 프로파일을 가장 최우선으로 환경설정 객체에 추가
     * @param environment 환경설정
     * @param property 환경설정 파일
     * @param name 환경설정 파일명
     * @throws IOException I/O 오류의 경우
     */
    private void configurePropertySources(ConfigurableEnvironment environment, Resource property, String name, String profile) throws IOException {
        List<PropertySource<?>> propertySources = sourceLoader.load(name, property);
        if (propertySources != null) {
            if (profile == null) {
                for (PropertySource<?> propertySource : propertySources) {
                    environment.getPropertySources().addFirst(propertySource);
                }
            } else {
                for (PropertySource<?> propertySource : propertySources) {
                    Object profileName = propertySource.getProperty("spring.profiles");
                    if (profileName != null && profileName.equals(profile)) {
                        environment.getPropertySources().addFirst(propertySource);
                    }
                }
            }
        }
    }
}