package gr.aueb.cf.ffa.mapper;

import gr.aueb.cf.ffa.dto.IncomeRequestDTO;
import gr.aueb.cf.ffa.dto.IncomeResponseDTO;
import gr.aueb.cf.ffa.model.Income;
import org.springframework.stereotype.Component;

@Component
public class IncomeMapper {

    public Income toEntity(IncomeRequestDTO dto, String userId) {
        Income income = new Income();
        income.setUserId(userId);
        income.setType(dto.getType());
        income.setAmount(dto.getAmount());
        income.setDate(dto.getDate());
        income.setNotes(dto.getNotes());
        return income;
    }

    public IncomeResponseDTO toResponseDTO(Income income) {
        IncomeResponseDTO dto = new IncomeResponseDTO();
        dto.setId(income.getId());
        dto.setUserId(income.getUserId());
        dto.setType(income.getType());
        dto.setAmount(income.getAmount());
        dto.setDate(income.getDate());
        dto.setNotes(income.getNotes());
        return dto;
    }
}
