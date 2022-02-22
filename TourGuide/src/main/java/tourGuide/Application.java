package tourGuide;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tourGuide.helper.Initializer;
import tourGuide.helper.InternalTestHelper;

@SpringBootApplication
@EnableFeignClients
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
