package br.com.ramires.dojomaster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ramires.dojomaster.entity.GameRankScore;

@Repository
public interface GameRankScoreRepository extends JpaRepository<GameRankScore, Integer> {

	public List<GameRankScore> findTop1PlayerByMatchIdOrderByKillsDesc(Long matchId);
	public List<GameRankScore> findAllByMatchIdOrderByKillsDesc(Long matchId);
}