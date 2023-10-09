package com.umc.baemin.repository;

import com.umc.baemin.domain.Shop;
import com.umc.baemin.domain.User;
import com.umc.baemin.domain.Wish;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

//import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WishRepositoryTest {
    @Autowired private UserRepository userRepository;
    @Autowired private ShopRepository shopRepository;
    @Autowired private WishRepository wishRepository;

    @Test
    @DisplayName("찜 기능 테스트")
    public void wishlist() {
        //given
        User user1 = User.builder().nickname("쿠키").phone("01012345678").build();
        userRepository.save(user1);

        User user2 = User.builder().nickname("패트릭").phone("01012345679").build();
        userRepository.save(user2);

        Shop shop1 = Shop.builder().name("마루").phone("01012345689").build();
        shopRepository.save(shop1);

        Shop shop2 = Shop.builder().name("제티").phone("01012345789").build();
        shopRepository.save(shop2);

        //when
        Wish wish1 = Wish.builder().user(user1).shop(shop1).build();
        wishRepository.save(wish1);

        Wish wish2 = Wish.builder().user(user2).shop(shop1).build();
        wishRepository.save(wish2);

        Wish wish3 = Wish.builder().user(user1).shop(shop2).build();
        wishRepository.save(wish3);

        //then
        assertThat(wishRepository.findAll().size()).isEqualTo(3);

        assertThat(wishRepository.findByUser(user1).get(0).getShop().getName()).isEqualTo("마루");
        assertThat(wishRepository.findByShop(shop1).size()).isEqualTo(2);
        assertThat(wishRepository.findByUser(user1).size()).isEqualTo(2);


    }
}