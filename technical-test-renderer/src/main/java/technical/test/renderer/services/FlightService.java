package technical.test.renderer.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import technical.test.renderer.clients.TechnicalApiClient;
import technical.test.renderer.viewmodels.FlightViewModel;
import technical.test.renderer.viewmodels.dto.FlightDTO;

@Service
public class FlightService {
    private final TechnicalApiClient technicalApiClient;

    public FlightService(TechnicalApiClient technicalApiClient) {
        this.technicalApiClient = technicalApiClient;
    }

    public Flux<FlightViewModel> getFlights() {
        return this.technicalApiClient.getFlights();
    }

    public Flux<FlightViewModel> createNewFlight(FlightDTO flightDTO) {
        return this.technicalApiClient.createNewFlight(flightDTO);
    }
}
