package net.wattmarket.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.wattmarket.dto.SaleDTO;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name="SALE")
@Builder
public class SaleEntity {

    @SequenceGenerator(name="sale_seq", sequenceName = "sale_seq", initialValue = 1, allocationSize = 1)
    @Id @Column(name="SALE_NUM") @GeneratedValue(generator = "sale_seq")
    private Long saleNum;
    @Column(name="MEMBER_ID")
    private String memberId;
    @Column(name="START_DATE")
    private LocalDate startDate;
    @Column(name="END_DATE")
    private LocalDate endDate;
    @Column(name="TRADE_AMOUNT")
    private int tradeAmount;
    @Column(name="MIN_PRICE")
    private int minPrice;
    @Column(name="REGISTER_DATE")
    @CreationTimestamp
    private LocalDateTime registerDate;
    @Column(name="IS_SELLING")
    private Boolean isSelling; // 0: 판매완료, 1: 판매중 

    public static SaleEntity toEntity(SaleDTO saleDTO){
        SaleEntity saleEntity = SaleEntity.builder()
            .saleNum(saleDTO.getSaleNum()).memberId(saleDTO.getMemberId())
            .startDate(saleDTO.getStartDate()).endDate(saleDTO.getEndDate())
            .tradeAmount(saleDTO.getTradeAmount()).minPrice(saleDTO.getMinPrice())
            .registerDate(saleDTO.getRegisterDate()).isSelling(saleDTO.getIsSelling())
            .build();
        return saleEntity;
    }
}
