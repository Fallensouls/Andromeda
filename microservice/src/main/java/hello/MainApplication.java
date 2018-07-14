package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import hello.init.JxCustomConfigure;
import org.springframework.boot.web.servlet.ServletComponentScan;

// springboot 的程序主入口
@SpringBootApplication
@EnableConfigurationProperties(JxCustomConfigure.class)
@ServletComponentScan
public class MainApplication {

  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
  }


}
