package net.wattmarket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

@Entity
@Table(name="PRODUCTIONS")
public class ProductionsEntity {
    @SequenceGenerator(
        name="PRODUCTION_SEQ",sequenceName = "PRODUCTION_SEQ",initialValue = 0,
        allocationSize = 1
    )

    @Id
    @GeneratedValue(generator = "PRODUCTION_SEQ")
    @Column(name="PRODUCTION_NUM")
    private Long productionNum;

    @Column(name="MEMBER_ID")
    private String memberId;

    @Column(name="PROD_DATE")
    private LocalDate prodDate;
    @Column(name="PROD_ELECTRICITY")
    private Double prodElectricity;
    @Column(name="PROD_DATE_MONTH")
    private int prodDateMonth;
}
