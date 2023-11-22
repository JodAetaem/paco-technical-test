package technical.test.renderer.clients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.properties.TechnicalApiProperties;
import technical.test.renderer.viewmodels.AirportViewModel;
import technical.test.renderer.viewmodels.FlightViewModel;
import technical.test.renderer.viewmodels.dto.FlightDTO;

@Component
@Slf4j
public class TechnicalApiClient {

    private final TechnicalApiProperties technicalApiProperties;
    private final WebClient webClient;

    public TechnicalApiClient(TechnicalApiProperties technicalApiProperties, final WebClient.Builder webClientBuilder) {
        this.technicalApiProperties = technicalApiProperties;
        this.webClient = webClientBuilder.build();
    }

    public Flux<FlightViewModel> getFlights() {
        return webClient
                .get()
                .uri(technicalApiProperties.getUrl() + technicalApiProperties.getFlightPath())
                .retrieve()
                .bodyToFlux(FlightViewModel.class);
    }

    public Flux<FlightViewModel> createNewFlight(FlightDTO flightDTO) {
        return webClient
                .post()
                .uri(technicalApiProperties.getUrl() + technicalApiProperties.getFlightPath())
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(flightDTO), FlightDTO.class)
                .retrieve()
                .bodyToFlux(FlightViewModel.class);
    }
}
