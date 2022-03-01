package tourGuide;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tourGuide.util.Initializer;
import tourGuide.util.InternalTestHelper;

@SpringBootApplication
@EnableFeignClients
@EnableSwagger2
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Initializer initializer = new Initializer(new InternalTestHelper());
        initializer.initialization();
    }
}
