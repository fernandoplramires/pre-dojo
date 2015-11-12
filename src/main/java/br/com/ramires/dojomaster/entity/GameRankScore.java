package br.com.ramires.dojomaster.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "GameRankScore")
public class GameRankScore implements Serializable {

	private static final long serialVersionUID = -6686435742550603845L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(name = "matchId")
	private Long matchId;

	@Column(name = "positionRank")
	private long positionRank;

	@NotNull
	@Column(name = "playerName")
	private String playerName;

	@NotNull
	@Column(name = "kills")
	private long kills;

	@NotNull
	@Column(name = "deaths")
	private long deaths;

	@Column(name = "humilhationAward")
	private boolean humilhationAward;

	@Column(name = "monsterKillAward")
	private boolean monsterKillAward;

	public GameRankScore() {

	}
	public GameRankScore(Long id, Long mathId, long positionRank, String playerName, long kills, long deaths, boolean humilhationAward, boolean monsterKillAward) {
		this.id = id;
		this.matchId = mathId;
		this.positionRank = positionRank;
		this.playerName = playerName;
		this.kills = kills;
		this.deaths = deaths;
		this.humilhationAward = humilhationAward;
		this.monsterKillAward = monsterKillAward;
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
	public void setMatchId(Long mathId) {
		this.matchId = mathId;
	}

	public long getPositionRank() {
		return positionRank;
	}
	public void setPositionRank(long positionRank) {
		this.positionRank = positionRank;
	}

	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public long getKills() {
		return kills;
	}
	public void setKills(long kills) {
		this.kills = kills;
	}

	public long getDeaths() {
		return deaths;
	}
	public void setDeaths(long deaths) {
		this.deaths = deaths;
	}

	public boolean isHumilhationAward() {
		return humilhationAward;
	}
	public void setHumilhationAward(boolean humilhationAward) {
		this.humilhationAward = humilhationAward;
	}

	public boolean isMonsterKillAward() {
		return monsterKillAward;
	}
	public void setMonsterKillAward(boolean monsterKillAward) {
		this.monsterKillAward = monsterKillAward;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GameRankScore [id=").append(id).append(", matchId=").append(matchId)
				.append(", positionRank=").append(positionRank)
				.append(", playerName=").append(playerName)
				.append(", kills=").append(kills)
				.append(", deaths=").append(deaths)
				.append(", humilhationAward=").append(humilhationAward)
				.append(", monsterKillAward=").append(monsterKillAward).append("]");
		return builder.toString();
	}
}
