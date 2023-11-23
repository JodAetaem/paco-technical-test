package technical.test.renderer.viewmodels.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightDTO {
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private double price;
    private String originIata;
    private String destinationIata;
    private String image;
    
}
