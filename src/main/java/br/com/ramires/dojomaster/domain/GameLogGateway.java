package br.com.ramires.dojomaster.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import br.com.ramires.dojomaster.entity.GameLog;

public class GameLogGateway {

	private HashMap<Long, HashSet<GameLog>> gameLogs;

	public GameLogGateway() {
		this.gameLogs = new HashMap<Long, HashSet<GameLog>>();
	}

	public HashSet<GameLog> getGameLogsByMatchId(Long matchId) {
		return this.gameLogs.get(matchId);
	}
	public HashSet<Long> getMatchsId() {

		HashSet<Long> matchsIds = new HashSet<Long>();
		for (Long matchId : this.gameLogs.keySet()) {
			matchsIds.add(matchId);
		}
		return matchsIds;
	}

	public void addGameLog(final Long matchIdCursor, final Date dateTime, final int type) {

		GameLog gameLog = new GameLog();
		gameLog.setMatchId(matchIdCursor);
		gameLog.setDateTime(dateTime);
		gameLog.setType(type);

		HashSet<GameLog> gameLogRegisters = gameLogs.get(matchIdCursor);
		if (gameLogRegisters == null) {
			gameLogRegisters = new HashSet<GameLog>();
		}
		gameLogRegisters.add(gameLog);
		this.gameLogs.put(matchIdCursor, gameLogRegisters);
	}

	public void addGameLog(final Long matchIdCursor, final Date dateTime, final int type, final String player, final String frag, final String weapon) {

		GameLog gameLog = new GameLog();
		gameLog.setMatchId(matchIdCursor);
		gameLog.setDateTime(dateTime);
		gameLog.setType(type);
		gameLog.setPlayer(player);
		gameLog.setFrag(frag);
		gameLog.setWeapon(weapon);

		HashSet<GameLog> gameLogRegisters = gameLogs.get(matchIdCursor);
		if (gameLogRegisters == null) {
			gameLogRegisters = new HashSet<GameLog>();
		}
		gameLogRegisters.add(gameLog);
		this.gameLogs.put(matchIdCursor, gameLogRegisters);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GameLogGateway [gameLogs=").append(gameLogs).append("]");
		return builder.toString();
	}
}