package com.about280.ashleigh.sports.config;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "sports")
public class SportProperties {

    private Map<String, List<String>> sportPositions;

}
