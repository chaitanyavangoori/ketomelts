package com.ketomelts.main.repository;

import com.ketomelts.main.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Chaitanya on 12/6/16.
 */
@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
}
