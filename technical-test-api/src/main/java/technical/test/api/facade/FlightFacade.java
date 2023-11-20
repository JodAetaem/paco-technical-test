package technical.test.api.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.mapper.AirportMapper;
import technical.test.api.mapper.FlightMapper;
import technical.test.api.record.AirportRecord;
import technical.test.api.representation.FlightRepresentation;
import technical.test.api.representation.dto.FlightDTO;
import technical.test.api.services.AirportService;
import technical.test.api.services.FlightService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FlightFacade {
    private final FlightService flightService;
    private final AirportService airportService;
    private final FlightMapper flightMapper;
    private final AirportMapper airportMapper;

    public Flux<FlightRepresentation> getAllFlights(Optional<Long> priceMax, Optional<String> originMandatory) {
        return flightService.getAllFlights()
                .filter((fr) -> priceMax.isPresent() ? fr.getPrice() <= priceMax.get() : true )
                .filter((fr) -> originMandatory.isPresent() ?  originMandatory.get().equals(fr.getOrigin()) : true )
                .flatMap(flightRecord -> airportService.findByIataCode(flightRecord.getOrigin())
                        .zipWith(airportService.findByIataCode(flightRecord.getDestination()))
                        .flatMap(tuple -> {
                            AirportRecord origin = tuple.getT1();
                            AirportRecord destination = tuple.getT2();
                            FlightRepresentation flightRepresentation = this.flightMapper.convert(flightRecord);
                            flightRepresentation.setOrigin(this.airportMapper.convert(origin));
                            flightRepresentation.setDestination(this.airportMapper.convert(destination));
                            return Mono.just(flightRepresentation);
                        }));
    }

    public Mono<FlightRepresentation> saveFlight(FlightDTO flightDTO) {
        return flightService.saveFlight(flightDTO)
                .flatMap(flightRecord -> airportService.findByIataCode(flightRecord.getOrigin())
                    .zipWith(airportService.findByIataCode(flightRecord.getDestination()))
                    .flatMap(tuple -> {
                        AirportRecord origin = tuple.getT1();
                        AirportRecord destination = tuple.getT2();
                        FlightRepresentation flightRepresentation = this.flightMapper.convert(flightRecord);
                        flightRepresentation.setOrigin(this.airportMapper.convert(origin));
                        flightRepresentation.setDestination(this.airportMapper.convert(destination));
                        return Mono.just(flightRepresentation);
                    }));
    }
}
