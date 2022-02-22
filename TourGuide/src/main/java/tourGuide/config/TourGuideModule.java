package tourGuide.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Configuration
public class TourGuideModule {

	@Bean
	public Locale getLocale() {
		Locale.setDefault(Locale.US);
		return Locale.getDefault();
	}

	@Bean
	public ExecutorService getExecutorService() {
		return Executors.newFixedThreadPool(1000);
	}

	@Bean
	public Retryer retryer() {
		return Retryer.NEVER_RETRY;
	}

}
