package net.wattmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import net.wattmarket.entity.ConsumptionsEntity;

@Repository
public interface ConsumptionsRepository extends JpaRepository<ConsumptionsEntity,Long> {

    @Query("SELECT c.consDate, c.consElectricity FROM ConsumptionsEntity c WHERE c.memberId =:memberId ORDER BY c.consDate DESC")
    List<Object[]> findAllConsumptionsByMemberId(@Param("memberId") String memberId);

}
