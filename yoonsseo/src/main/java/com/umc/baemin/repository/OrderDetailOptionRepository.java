package com.umc.baemin.repository;

import com.umc.baemin.domain.OrderDetailOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface OrderDetailOptionRepository extends JpaRepository<OrderDetailOption, Long> {
}
