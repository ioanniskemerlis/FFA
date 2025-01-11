package gr.aueb.cf.ffa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "incomes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Income {
    @Id
    private String id;
    private String userId; // To associate income with a specific user
    private String type; // Income type (e.g., "Gross Pay",Net pay , "Tips")
    private double amount; // Income amount
    private LocalDate date; // Date of the income
    private String notes; // Optional notes
}