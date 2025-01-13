package gr.aueb.cf.ffa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "expenses")
public class Expense {
    @Id
    private String id;
    private String userId;
    private String type;
    private double amount;
    private LocalDate date;
    private String category;
    private String notes;
}
