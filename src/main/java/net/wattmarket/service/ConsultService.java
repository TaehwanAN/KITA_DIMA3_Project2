package net.wattmarket.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import net.wattmarket.dto.PurchaseDTO;
import net.wattmarket.dto.SaleDTO;
import net.wattmarket.entity.PurchaseEntity;
import net.wattmarket.entity.SaleEntity;
import net.wattmarket.repository.PurchaseRepository;
import net.wattmarket.repository.SaleRepository;

@Service
@RequiredArgsConstructor
public class ConsultService {
    private final PurchaseRepository purchaseRepository;
    private final SaleRepository saleRepository;

    // 구매 등록
    public void registerPurchase(
        String memberId,
        LocalDate startDate,
        LocalDate endDate,
        int tradeAmount,
        int minPrice
    ){
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setMemberId(memberId); purchaseDTO.setStartDate(startDate); purchaseDTO.setEndDate(endDate);
        purchaseDTO.setTradeAmount(tradeAmount); purchaseDTO.setMinPrice(minPrice);
        purchaseDTO.setIsPurchasing(true);
        purchaseRepository.save(PurchaseEntity.toEntity(purchaseDTO));
    }
    public void registerSale(
        String memberId,
        LocalDate startDate,
        LocalDate endDate,
        int tradeAmount,
        int minPrice
    ){
        SaleDTO saleDTO = new SaleDTO();
        saleDTO.setMemberId(memberId); saleDTO.setStartDate(startDate); saleDTO.setEndDate(endDate);
        saleDTO.setTradeAmount(tradeAmount); saleDTO.setMinPrice(minPrice);
        saleDTO.setIsSelling(true);
        saleRepository.save(SaleEntity.toEntity(saleDTO));
    }

}
