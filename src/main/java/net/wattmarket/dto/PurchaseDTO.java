package net.wattmarket.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.wattmarket.entity.PurchaseEntity;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PurchaseDTO {

    private Long purchaseNum;
    private String memberId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int tradeAmount;
    private int minPrice;
    private LocalDateTime registerDate;
    private Boolean isPurchasing; // 0: 구매완료, 1: 구매중  

    public static PurchaseDTO purchaseDTO(PurchaseEntity purchaseEntity){
        PurchaseDTO purchaseDTO = PurchaseDTO.builder()
            .purchaseNum(purchaseEntity.getPurchaseNum()).memberId(purchaseEntity.getMemberId())
            .startDate(purchaseEntity.getStartDate()).endDate(purchaseEntity.getEndDate())
            .tradeAmount(purchaseEntity.getTradeAmount()).minPrice(purchaseEntity.getMinPrice())
            .registerDate(purchaseEntity.getRegisterDate()).isPurchasing(purchaseEntity.getIsPurchasing())
            .build();
        return purchaseDTO;
    }

}
