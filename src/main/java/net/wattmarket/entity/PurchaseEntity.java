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
import net.wattmarket.dto.PurchaseDTO;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name="PURCHASE")
public class PurchaseEntity {
    @SequenceGenerator(name="purchase_seq", sequenceName = "purchase_seq", initialValue = 1, allocationSize = 1)
    @Id @Column(name="PURCHASE_NUM") @GeneratedValue(generator = "purchase_seq")
    private Long purchaseNum;
    @Column(name="MEMBER_ID")
    private String memberId;
    @Column(name="START_DATE")
    private LocalDate startDate;
    @Column(name = "END_DATE")
    private LocalDate endDate;
    @Column(name="TRADE_AMOUNT")
    private int tradeAmount;
    @Column(name="MIN_PRICE")
    private int minPrice;
    @Column(name="REGISTER_DATE")
    @CreationTimestamp
    private LocalDateTime registerDate;
    @Column(name="IS_PURCHASING")
    private Boolean isPurchasing; // 0: 구매완료, 1: 구매중  

    public static PurchaseEntity toEntity(PurchaseDTO purchaseDTO){
        PurchaseEntity purchaseEntity = PurchaseEntity.builder()
            .purchaseNum(purchaseDTO.getPurchaseNum()).memberId(purchaseDTO.getMemberId())
            .startDate(purchaseDTO.getStartDate()).endDate(purchaseDTO.getEndDate())
            .tradeAmount(purchaseDTO.getTradeAmount()).minPrice(purchaseDTO.getMinPrice())
            .registerDate(purchaseDTO.getRegisterDate()).isPurchasing(purchaseDTO.getIsPurchasing())
            .build();
        return purchaseEntity;
    }
}
