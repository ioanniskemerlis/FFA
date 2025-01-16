package gr.aueb.cf.ffa.controller;

import gr.aueb.cf.ffa.dto.IncomeRequestDTO;
import gr.aueb.cf.ffa.dto.IncomeResponseDTO;
import gr.aueb.cf.ffa.mapper.IncomeMapper;
import gr.aueb.cf.ffa.model.Income;
import gr.aueb.cf.ffa.service.IncomeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class IncomeControllerTest {

    @Mock
    private IncomeService incomeService;

    @Mock
    private IncomeMapper incomeMapper;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private IncomeController incomeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addIncome_ShouldReturnCreatedIncomeDTO() {
        IncomeRequestDTO requestDTO = new IncomeRequestDTO();
        requestDTO.setType("Salary");
        requestDTO.setAmount(5000.0);
        requestDTO.setDate(LocalDate.now());
        requestDTO.setNotes("Monthly salary");

        Income income = new Income(null, "user123", "Salary", 5000.0, LocalDate.now(), "Monthly salary");
        IncomeResponseDTO responseDTO = new IncomeResponseDTO();
        responseDTO.setId("1");
        responseDTO.setUserId("user123");
        responseDTO.setType("Salary");
        responseDTO.setAmount(5000.0);
        responseDTO.setDate(LocalDate.now());
        responseDTO.setNotes("Monthly salary");

        when(authentication.getName()).thenReturn("user123");
        when(incomeMapper.toEntity(requestDTO, "user123")).thenReturn(income);
        when(incomeService.addIncome(income)).thenReturn(income);
        when(incomeMapper.toResponseDTO(income)).thenReturn(responseDTO);

        IncomeResponseDTO result = incomeController.addIncome(requestDTO, authentication);

        assertEquals(responseDTO, result);
        verify(incomeMapper, times(1)).toEntity(requestDTO, "user123");
        verify(incomeService, times(1)).addIncome(income);
        verify(incomeMapper, times(1)).toResponseDTO(income);
    }

    @Test
    void getIncomes_ShouldReturnPagedIncomeDTOs() {
        List<Income> incomeList = List.of(
                new Income("1", "user123", "Freelancing", 1500.0, LocalDate.now(), "Project payment"),
                new Income("2", "user123", "Bonus", 500.0, LocalDate.now(), "Quarterly bonus")
        );
        Page<Income> incomePage = new PageImpl<>(incomeList);

        List<IncomeResponseDTO> responseDTOList = List.of(
                new IncomeResponseDTO("1", "user123", "Freelancing", 1500.0, LocalDate.now(), "Project payment"),
                new IncomeResponseDTO("2", "user123", "Bonus", 500.0, LocalDate.now(), "Quarterly bonus")
        );
        Page<IncomeResponseDTO> responsePage = new PageImpl<>(responseDTOList);

        when(authentication.getName()).thenReturn("user123");
        when(incomeService.getIncomesByUser("user123", 0, 10)).thenReturn(incomePage);
        when(incomeMapper.toResponseDTO(any(Income.class))).thenReturn(responseDTOList.get(0), responseDTOList.get(1));

        Page<IncomeResponseDTO> result = incomeController.getIncomes(0, 10, authentication);

        assertEquals(responsePage.getContent().size(), result.getContent().size());
        verify(incomeService, times(1)).getIncomesByUser("user123", 0, 10);
    }

    @Test
    void getAllIncomes_ShouldReturnAllIncomeDTOsForUser() {
        List<Income> incomeList = List.of(
                new Income("1", "user123", "Freelancing", 1500.0, LocalDate.now(), "Project payment"),
                new Income("2", "user123", "Bonus", 500.0, LocalDate.now(), "Quarterly bonus")
        );

        List<IncomeResponseDTO> responseDTOList = List.of(
                new IncomeResponseDTO("1", "user123", "Freelancing", 1500.0, LocalDate.now(), "Project payment"),
                new IncomeResponseDTO("2", "user123", "Bonus", 500.0, LocalDate.now(), "Quarterly bonus")
        );

        when(authentication.getName()).thenReturn("user123");
        when(incomeService.getAllIncomesByUser("user123")).thenReturn(incomeList);
        when(incomeMapper.toResponseDTO(any(Income.class))).thenReturn(responseDTOList.get(0), responseDTOList.get(1));

        List<IncomeResponseDTO> result = incomeController.getAllIncomes(authentication);

        assertEquals(responseDTOList.size(), result.size());
        verify(incomeService, times(1)).getAllIncomesByUser("user123");
    }
}
