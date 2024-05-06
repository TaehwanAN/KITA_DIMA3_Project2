package net.wattmarket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.wattmarket.entity.MembersEntity;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ProductionsDTO {

    private Long productionNum;

    private MembersEntity prodEntity;
    private LocalDate prodDate;
    private Double prodElectricity;
    private int prodDateMonth;
}
