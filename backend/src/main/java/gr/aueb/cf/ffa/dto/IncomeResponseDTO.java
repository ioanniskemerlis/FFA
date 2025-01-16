package gr.aueb.cf.ffa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "DTO for income data sent in responses.")
public class IncomeResponseDTO {

    @Schema(description = "The ID of the income entry.", example = "64b7cfe2c3d2a12345678901")
    private String id;

    @Schema(description = "The ID of the user associated with the income entry.", example = "user123")
    private String userId;

    @Schema(description = "The type of income.", example = "Gross Pay")
    private String type;

    @Schema(description = "The amount of income.", example = "1500.00")
    private double amount;

    @Schema(description = "The date when the income was received.", example = "2025-01-01")
    private LocalDate date;

    @Schema(description = "Optional notes for the income entry.", example = "Salary for January.")
    private String notes;
}
