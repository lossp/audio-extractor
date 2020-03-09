package main.com.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Entity represents the ftp connect
 */
@Component
@ConfigurationProperties(prefix = "spring.ftp")
public class FtpEntity {
    private String host;
    private int port;
    private String username;
    private String password;
    private String baseUrl;
    private String httpUrl;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    @Override
    public String toString() {
        return "host: " + host + ", port:" + port + ", username:" + username + ", password:" + password + ", baseUrl:" + baseUrl + ", httpUrl:" + httpUrl;
    }
}
