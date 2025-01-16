package gr.aueb.cf.ffa.dto;

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
public class ExpenseResponseDTO {
    private String id;       // Expense ID
    private String userId;   // User ID
    private String type;     // Expense type
    private double amount;   // Expense amount
    private LocalDate date;  // Expense date
    private String notes;    // Optional notes
}
