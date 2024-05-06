package net.wattmarket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name="CONSUMPTIONS")
public class ConsumptionsEntity {

	@SequenceGenerator(
			name = "consumption_seq",
			sequenceName = "CONSUMPTION_SEQ",
			initialValue = 0,
			allocationSize = 1
			)
	
	
	@Id
	@GeneratedValue(generator = "consumption_seq")
	@Column(name = "CONSUMPTION_NUM")
	private Long consumptionNum;
	
    @Column(name = "MEMBER_ID") // FK
    private String memberId;
	
	@Column(name = "CONS_DATE")
	private int consDate;
	
	@Column(name = "CONS_ELECTRICITY")
	private double consElectricity;
	
}
