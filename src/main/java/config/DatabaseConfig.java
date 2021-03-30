package config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class DatabaseConfig {

    @Bean(name = "dataSource")
    public BasicDataSource basicDataSource(){
        BasicDataSource basicDataSource = new BasicDataSource();
        try {
            Properties properties = databaseProperties();
            basicDataSource.setDriverClassName(properties.getProperty("db.driverClassName"));
            basicDataSource.setUrl(properties.getProperty("db.url"));
            basicDataSource.setUsername(properties.getProperty("db.user"));
            basicDataSource.setPassword(properties.getProperty("db.password"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return basicDataSource;
    }


    private Properties databaseProperties() throws IOException {
        Properties out = new Properties();
        try(InputStream inputStream =
                    DatabaseConfig.class.getResourceAsStream("/data_base_properties.properties")){
            out.load(inputStream);
        }
        return out;
    }

}
