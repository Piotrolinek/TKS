package pl.lodz.p.soap.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentDTO {
    @NotNull(message = "Client UUID cannot be null")
    private String username;
    @NotNull(message = "VM UUID cannot be null")
    private UUID vmId;
    private LocalDateTime startTime;
}
