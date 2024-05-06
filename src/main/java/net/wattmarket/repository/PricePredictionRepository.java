package net.wattmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.wattmarket.entity.PricePredictionEntity;

@Repository
public interface PricePredictionRepository extends JpaRepository<PricePredictionEntity,String> {

}
