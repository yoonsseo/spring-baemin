package com.umc.baemin.repository;

import com.umc.baemin.domain.Shop;
import com.umc.baemin.domain.User;
import com.umc.baemin.domain.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
    List<Wish> findByUser(User user);

    List<Wish> findByShop(Shop shop);
}
