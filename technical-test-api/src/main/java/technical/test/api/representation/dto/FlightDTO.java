package technical.test.api.representation.dto;

import lombok.*;
import technical.test.api.record.FlightRecord;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightDTO {
    //Je rajouterais plus que le @NonNull pour la vérification, par exemple @NotBlank/@NotNull
    //https://www.baeldung.com/java-bean-validation-not-null-empty-blank
    @NonNull
    private LocalDateTime departure;
    @NonNull
    private LocalDateTime arrival;
    @NonNull
    private double price;
    @NonNull
    private String originIata;
    @NonNull
    private String destinationIata;
    @NonNull
    private String image;

    public FlightRecord toFlightRecord() {
        return FlightRecord.builder()
                .id(UUID.randomUUID()) //Idéalemebnt ça, on laisse la DB le gérer automatiquement
                .departure(departure)
                .arrival(arrival)
                .price(price)
                .origin(originIata)
                .destination(destinationIata)
                .image(image)
                .build();
    }
}
