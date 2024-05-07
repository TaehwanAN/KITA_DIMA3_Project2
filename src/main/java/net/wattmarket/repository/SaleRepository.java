package net.wattmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.wattmarket.entity.SaleEntity;

@Repository
public interface SaleRepository extends JpaRepository<SaleEntity,Long> {

}
