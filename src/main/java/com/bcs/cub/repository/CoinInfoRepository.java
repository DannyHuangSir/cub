package com.bcs.cub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bcs.cub.model.CoinInfo;

@Repository
public interface CoinInfoRepository extends JpaRepository<CoinInfo, String> {

}
