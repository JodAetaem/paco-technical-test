package technical.test.renderer.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;
import technical.test.renderer.facades.FlightFacade;
import technical.test.renderer.viewmodels.dto.FlightDTO;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    @Autowired
    private FlightFacade flightFacade;

    @GetMapping
    public Mono<String> getAdminReturn(final Model model) {
        return Mono.just("pages/admin");
    }

    @PostMapping
    public  Mono<String> postAdminReturn(FlightDTO flightToSave, final Model model) {
        model.addAttribute("submitNewFlight", this.flightFacade.createNewFlight(flightToSave));
        return Mono.just("pages/admin");
    }
}
