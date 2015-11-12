package br.com.ramires.dojomaster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ramires.dojomaster.entity.GameLog;

@Repository
public interface GameLogRepository extends JpaRepository<GameLog, Integer> {

	@Query("SELECT weapon, COUNT(weapon) FROM GameLog WHERE matchId = :matchId AND player = :player GROUP BY weapon ORDER BY COUNT(weapon) DESC")
	public List<Object[]> findBestWeaponForThePlayerWinner(@Param("matchId") Long matchId, @Param("player") String player);
}
