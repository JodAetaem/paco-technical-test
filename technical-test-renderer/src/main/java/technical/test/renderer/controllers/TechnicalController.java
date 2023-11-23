package technical.test.renderer.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;
import technical.test.renderer.facades.FlightFacade;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class TechnicalController {

    @Autowired
    private FlightFacade flightFacade;

    @GetMapping
    public Mono<String> getMarketPlaceReturnCouponPage(final Model model) {
        model.addAttribute("flights", this.flightFacade.getFlights());
        return Mono.just("pages/index");
    }

    @GetMapping("/filtrage")
    public Mono<String> getMarketPlaceReturnCouponPageWithFilter(final Model model,
                                                                 @RequestParam Optional<Integer> minPrice,
                                                                 @RequestParam Optional<Integer> maxPrice,
                                                                 @RequestParam Optional<String> originIata,
                                                                 @RequestParam Optional<LocalDateTime> earliestDepartureDate) {
        model.addAttribute("flights", this.flightFacade.getFlights()
                .filter((ff) -> minPrice.isEmpty() || ff.getPrice() >= minPrice.get())
                .filter((ff) -> maxPrice.isEmpty() || ff.getPrice() <= maxPrice.get())
                .filter((ff) -> (originIata.isEmpty() || originIata.get().isEmpty())  ? true : originIata.get().equals(ff.getOrigin().getIata()))
                .filter((ff) -> earliestDepartureDate.isEmpty() || ff.getDeparture().isAfter(earliestDepartureDate.get()))
        );
        return Mono.just("pages/index");
    }
}
