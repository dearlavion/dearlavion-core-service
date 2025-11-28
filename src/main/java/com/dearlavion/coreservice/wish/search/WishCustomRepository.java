package com.dearlavion.coreservice.wish.search;

import com.dearlavion.coreservice.wish.Wish;
import org.springframework.data.domain.Page;

public interface WishCustomRepository {
    Page<Wish> searchWishes(WishSearchRequest request);
}