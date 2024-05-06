package net.wattmarket.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.wattmarket.entity.ProductionsEntity;

@Repository
public interface ProductionsRepository extends JpaRepository<ProductionsEntity,Long> {

    @Query("SELECT p.prodElectricity,p.prodDateMonth FROM ProductionsEntity p WHERE p.memberId = :memberId")
    List<Object[]> findProdElectricityAndMonthsByMemberId(@Param("memberId") String memberId);
}
