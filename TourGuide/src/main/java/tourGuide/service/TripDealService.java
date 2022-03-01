package tourGuide.service;

import tourGuide.model.Provider;


import java.util.List;

public interface TripDealService {

  List<Provider> getTripDeals(String userName);

}
