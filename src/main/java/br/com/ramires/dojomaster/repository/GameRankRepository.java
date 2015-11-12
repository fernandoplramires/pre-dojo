package br.com.ramires.dojomaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ramires.dojomaster.entity.GameRank;

@Repository
public interface GameRankRepository extends JpaRepository<GameRank, Integer> {


}