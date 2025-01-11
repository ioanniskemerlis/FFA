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
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "goals")
public class FinancialGoal {
    @Id
    private String id;
    private String userId;
    private String name;
    private double targetAmount;
    private double currentAmount;
    private LocalDate deadline;

}
