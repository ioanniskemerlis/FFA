package gr.aueb.cf.ffa.dto;

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
public class ExpenseRequestDTO {
    private String type;     // Expense type (e.g., "Rent", "Utilities")
    private double amount;   // Expense amount
    private LocalDate date;  // Expense date
    private String notes;    // Optional notes
}
