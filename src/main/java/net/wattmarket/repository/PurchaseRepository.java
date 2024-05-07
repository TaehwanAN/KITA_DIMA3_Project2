package net.wattmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.wattmarket.entity.PurchaseEntity;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseEntity,Long> {

}
