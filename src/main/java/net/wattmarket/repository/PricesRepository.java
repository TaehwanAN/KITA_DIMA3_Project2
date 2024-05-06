package net.wattmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.wattmarket.entity.PricesEntity;

@Repository
public interface PricesRepository extends JpaRepository<PricesEntity,Long> {
    @Query("SELECT p FROM PricesEntity p WHERE p.yearMonth = :yearMonth")
    PricesEntity findHousePricesByType(@Param("yearMonth") Long yearMonth);
}
