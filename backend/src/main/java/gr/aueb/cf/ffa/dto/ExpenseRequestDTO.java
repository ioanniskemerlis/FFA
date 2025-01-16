package gr.aueb.cf.ffa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

/**
 * Data Transfer Object for expense requests.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Data Transfer Object for creating a new expense.")
public class ExpenseRequestDTO {

    @Schema(
            description = "Type of the expense (e.g., Rent, Utilities).",
            example = "Rent"
    )
    private String type; // Expense type (e.g., "Rent", "Utilities")

    @Schema(
            description = "Amount of the expense. Must be a positive value.",
            example = "1200.50"
    )
    private double amount; // Expense amount

    @Schema(
            description = "Date of the expense in ISO format (yyyy-MM-dd).",
            example = "2025-01-01"

    )
    private LocalDate date; // Expense date

    @Schema(
            description = "Additional notes or comments for the expense.",
            example = "Monthly house rent."
    )
    private String notes; // Optional notes
}
