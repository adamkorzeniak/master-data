package com.adamkorzeniak.masterdata.api.features.crypto.repository;

import com.adamkorzeniak.masterdata.api.features.crypto.model.CryptoDetails;
import com.adamkorzeniak.masterdata.api.features.crypto.model.CryptoShort;
import com.adamkorzeniak.masterdata.api.features.goal.model.entity.GoalGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CryptoRepository extends JpaRepository<CryptoDetails, Long> {

    List<CryptoDetails> save(List<CryptoDetails> cryptoShortList);

}
