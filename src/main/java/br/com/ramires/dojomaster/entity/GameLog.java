package br.com.ramires.dojomaster.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "GameLog")
public class GameLog implements Serializable {

	private static final long serialVersionUID = 108237068307740638L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(name = "matchId")
	private Long matchId;

	@NotNull
	@Column(name = "dateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;

	@NotNull
	@Column(name = "type")
	private int type;

	@Column(name = "player")
	private String player;

	@Column(name = "frag")
	private String frag;

	@Column(name = "weapon")
	private String weapon;

	public GameLog() {
	}
	public GameLog(Long id, Long matchId, Date dateTime, int type, String player, String frag, String weapon) {
		this.id = id;
		this.matchId = matchId;
		this.dateTime = dateTime;
		this.type = type;
		this.player = player;
		this.frag = frag;
		this.weapon = weapon;
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

	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}

	public String getFrag() {
		return frag;
	}
	public void setFrag(String frag) {
		this.frag = frag;
	}

	public String getWeapon() {
		return weapon;
	}
	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GameLog [id=").append(id).append(", matchId=").append(matchId).append(", dateTime=")
				.append(dateTime).append(", type=").append(type).append(", player=").append(player).append(", frag=")
				.append(frag).append(", weapon=").append(weapon).append("]");
		return builder.toString();
	}
}
