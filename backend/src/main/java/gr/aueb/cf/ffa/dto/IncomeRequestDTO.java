package gr.aueb.cf.ffa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "DTO for income data sent in requests.")
public class IncomeRequestDTO {

    @Schema(description = "The type of income.", example = "Gross Pay")
    @NotNull(message = "Income type cannot be null.")
    private String type;

    @Schema(description = "The amount of income.", example = "1500.00")
    @Positive(message = "Income amount must be positive.")
    private double amount;

    @Schema(description = "The date when the income was received.", example = "2025-01-01")
    @NotNull(message = "Income date cannot be null.")
    private LocalDate date;

    @Schema(description = "Optional notes for the income entry.", example = "Salary for January.")
    private String notes;
}
