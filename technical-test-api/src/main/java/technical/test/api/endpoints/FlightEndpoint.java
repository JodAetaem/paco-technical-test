package technical.test.api.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.facade.FlightFacade;
import technical.test.api.representation.FlightRepresentation;
import technical.test.api.representation.dto.FlightDTO;

import java.util.Optional;

@RestController
@RequestMapping("/flight")
@RequiredArgsConstructor
public class FlightEndpoint {
    private final FlightFacade flightFacade;

    @GetMapping
    public Flux<FlightRepresentation> getAllFlights(@RequestParam(value = "priceMax", required = false) Optional<Long> priceMax, @RequestParam(value = "origin", required = false) Optional<String> origin) {
        Flux<FlightRepresentation> result =  flightFacade.getAllFlights(priceMax,origin);
        return result;
    }

    @PostMapping
    public Mono<FlightRepresentation> createFlight(@RequestBody FlightDTO flightToSave) {
        return flightFacade.saveFlight(flightToSave);
    }
}
