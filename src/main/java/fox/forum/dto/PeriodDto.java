package fox.forum.dto;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class PeriodDto {
	LocalDate dateFrom;
	LocalDate dateTo;
}
