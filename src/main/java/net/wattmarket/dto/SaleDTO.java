package net.wattmarket.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.wattmarket.entity.SaleEntity;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SaleDTO {

    private Long saleNum;
    private String memberId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int tradeAmount;
    private int minPrice;
    private LocalDateTime registerDate;
    private Boolean isSelling; // 0: 판매완료, 1: 판매중 

    public static SaleDTO toDTO(SaleEntity saleEntity){
        SaleDTO saleDTO = SaleDTO.builder()
            .saleNum(saleEntity.getSaleNum()).memberId(saleEntity.getMemberId())
            .startDate(saleEntity.getStartDate()).endDate(saleEntity.getEndDate())
            .tradeAmount(saleEntity.getTradeAmount()).minPrice(saleEntity.getMinPrice())
            .registerDate(saleEntity.getRegisterDate()).isSelling(saleEntity.getIsSelling())
            .build();
        return saleDTO;
        }
}
