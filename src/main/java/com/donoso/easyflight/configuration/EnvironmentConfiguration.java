package com.donoso.easyflight.configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

public class EnvironmentConfiguration {

    public String loadPropertie(String key) {
        Properties properties = new Properties();

        try {
            properties.load(Files.newInputStream(new File(this.getClass().getResource("/configuracion.properties").getFile()).toPath()));
            return (String) properties.get(key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
