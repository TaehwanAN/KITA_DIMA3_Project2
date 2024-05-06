package net.wattmarket.dto;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.wattmarket.entity.MembersEntity;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ConsumptionsDTO {

    private Long consumptionNum;
	private MembersEntity consEntity;
	private int consDate;
	private double consElectricity;
}
