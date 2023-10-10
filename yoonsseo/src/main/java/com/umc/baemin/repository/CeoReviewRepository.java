package com.umc.baemin.repository;

import com.umc.baemin.domain.CeoReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CeoReviewRepository extends JpaRepository<CeoReview, Long> {
}
