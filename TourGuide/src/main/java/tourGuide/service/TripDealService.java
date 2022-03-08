package tourGuide.service;

import tourGuide.Dto.ProviderRequest;
import tourGuide.model.Provider;


import java.util.List;

public interface TripDealService {

  List<ProviderRequest> getTripDeals(String userName);

}
