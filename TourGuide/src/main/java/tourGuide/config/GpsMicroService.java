package tourGuide.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tourGuide.Dto.AttractionRequest;
import tourGuide.Dto.VisitedLocationRequest;
import java.util.List;
import java.util.UUID;

@FeignClient(value = "GpsMicroService", url = "${CLIENT_GPS_BASE_URL:http://localhost:8081/gps}")
public interface GpsMicroService {


    @GetMapping("/userLocation/{userId}")
    VisitedLocationRequest getLocation(@PathVariable("userId") final UUID userId);

    @GetMapping("/attractions")
    List<AttractionRequest> getAttractions();


}
