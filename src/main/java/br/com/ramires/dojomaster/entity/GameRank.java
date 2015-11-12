package br.com.ramires.dojomaster.entity;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "GameRank")
public class GameRank implements Serializable {

	private static final long serialVersionUID = 9163550099991040770L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(name = "mathId")
	private Long matchId;

	@OneToMany
	private Set<GameRankScore> scores;

	@Column(name = "winnerWeapon")
	private String winnerWeapon;

	@Column(name = "killStreak")
	private String killStreak;

	public GameRank() {
	}
	public GameRank(Long id, Long matchId, Set<GameRankScore> scores, String winnerWeapon, String killStreak) {
		this.id = id;
		this.matchId = matchId;
		this.scores = scores;
		this.winnerWeapon = winnerWeapon;
		this.killStreak = killStreak;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}

	public Long getMatchId() {
		return matchId;
	}
	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	public void setScores(Set<GameRankScore> scores) {
		this.scores = scores;
	}
	public Set<GameRankScore> getScores() {
		return Collections.unmodifiableSet(scores);
	}
	public void addGameRankScore(GameRankScore gameRankScore) {
		this.scores.add(gameRankScore);
	}

	public String getWinnerWeapon() {
		return this.winnerWeapon;
	}
	public void setWinnerWeapon(String winnerWeapon) {
		this.winnerWeapon = winnerWeapon;
	}

	public String getKillStreak() {
		return killStreak;
	}
	public void setKillStreak(String killStreak) {
		this.killStreak = killStreak;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GameRank [id=").append(id).append(", matchId=").append(matchId).append(", scores=")
				.append(scores).append(", winnerWeapon=").append(winnerWeapon).append(", killStreak=").append(killStreak).append("]");
		return builder.toString();
	}
}
