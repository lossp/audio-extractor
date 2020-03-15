package main.com.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ffmpeg")
public class ConvertEntity {
    private String filelocation;
    private String outputlocation;
    private String location;


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFilelocation() {
        return filelocation;
    }

    public void setFilelocation(String filelocation) {
        this.filelocation = filelocation;
    }

    public String getOutputlocation() {
        return outputlocation;
    }

    public void setOutputlocation(String outputlocation) {
        this.outputlocation = outputlocation;
    }

    @Override
    public String toString() {
        return "file location = " + filelocation + ", output location = " + outputlocation;
    }
}
