package gr.aueb.cf.ffa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

/**
 * Data Transfer Object for expense responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Data Transfer Object for expense response.")
public class ExpenseResponseDTO {

    @Schema(
            description = "Unique identifier for the expense.",
            example = "64b7cfe2c3d2a12345678901",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private String id; // Expense ID

    @Schema(
            description = "ID of the user associated with the expense.",
            example = "user123",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private String userId; // User ID

    @Schema(
            description = "Type of the expense (e.g., Rent, Utilities).",
            example = "Rent"
    )
    private String type; // Expense type

    @Schema(
            description = "Amount of the expense.",
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
