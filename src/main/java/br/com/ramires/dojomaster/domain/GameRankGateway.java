package br.com.ramires.dojomaster.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.ramires.dojomaster.entity.GameRank;
import br.com.ramires.dojomaster.entity.GameRankScore;

public class GameRankGateway {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameRankGateway.class);

	private HashMap<Long, HashSet<GameRank>> gameRanks;

	public GameRankGateway() {
		this.gameRanks = new HashMap<Long, HashSet<GameRank>>();
	}

	public HashSet<GameRank> getGameRanksByMatchId(Long matchId) {
		return this.gameRanks.get(matchId);
	}
	public HashSet<Long> getMatchsId() {

		HashSet<Long> matchsIds = new HashSet<Long>();
		for (Long matchId : this.gameRanks.keySet()) {
			matchsIds.add(matchId);
		}
		return matchsIds;
	}

	public void summarizeKillAndDeaths(final Long matchIdCursor, final String player, final String frag) {

		LOGGER.debug("GameRankGateway->summarizeKillAndDeaths->begin()");

		HashSet<GameRank> gameRanksRegister = this.gameRanks.get(matchIdCursor);
		if (gameRanksRegister == null) {

			LOGGER.debug("GameRankGateway->summarizeKillAndDeaths-> Create a new HashSet for GameRank");
			gameRanksRegister = new HashSet<GameRank>();
		} else {

			LOGGER.debug("GameRankGateway->summarizeKillAndDeaths-> Recover a HashSet for GameRank");
		}

		GameRank gameRankRegister = null;
		for (GameRank gameRankInterator : gameRanksRegister) {
			if (gameRankInterator != null) {
				if (gameRankInterator.getMatchId().equals(matchIdCursor)) {

					LOGGER.debug(String.format("GameRankGateway->summarizeKillAndDeaths-> Recover a GameRank for this matchId: %d", matchIdCursor));
					gameRankRegister = gameRankInterator;
				}
			}
		}
		if (gameRankRegister == null) {

			LOGGER.debug(String.format("GameRankGateway->summarizeKillAndDeaths-> Create a new GameRank for this matchId: %d", matchIdCursor));
			gameRankRegister = new GameRank();
			gameRankRegister.setMatchId(matchIdCursor);
			HashSet<GameRankScore> scores = new HashSet<GameRankScore>();
			gameRankRegister.setScores(scores);
			gameRanksRegister.add(gameRankRegister);
		}

		GameRankScore scoresForPlayer = null;
		for (GameRankScore gameRankScoreInterator : gameRankRegister.getScores()) {
			if (gameRankScoreInterator != null) {
				if (gameRankScoreInterator.getPlayerName().equals(player)) {

					LOGGER.debug(String.format("GameRankGateway->summarizeKillAndDeaths-> Recover a Score for this player: %s", player));
					scoresForPlayer = gameRankScoreInterator;
				}
			}
		}
		if (scoresForPlayer == null) {

			LOGGER.debug(String.format("GameRankGateway->summarizeKillAndDeaths-> Create a Score for this player: %s", player));
			scoresForPlayer = new GameRankScore();
			scoresForPlayer.setPlayerName(player);
			scoresForPlayer.setMatchId(matchIdCursor);
		}

		GameRankScore scoresForFrag = null;
		for (GameRankScore gameRankScoreInterator : gameRankRegister.getScores()) {
			if (gameRankScoreInterator != null) {
				if (gameRankScoreInterator.getPlayerName().equals(frag)) {

					LOGGER.debug(String.format("GameRankGateway->summarizeKillAndDeaths-> Recover a Score for this player/frag: %s", frag));
					scoresForFrag = gameRankScoreInterator;
				}
			}
		}
		if (scoresForFrag == null) {

			LOGGER.debug(String.format("GameRankGateway->summarizeKillAndDeaths-> Create a Score for this player/frag: %s", frag));
			scoresForFrag = new GameRankScore();
			scoresForFrag.setPlayerName(frag);
			scoresForFrag.setMatchId(matchIdCursor);
		}

		LOGGER.debug("GameRankGateway->summarizeKillAndDeaths-> Sumarize deaths and kills");
		scoresForPlayer.setKills(scoresForPlayer.getKills()+1);
		scoresForFrag.setDeaths(scoresForFrag.getDeaths()+1);

		LOGGER.debug("GameRankGateway->summarizeKillAndDeaths-> Store the scores for this players");
		gameRankRegister.addGameRankScore(scoresForPlayer);
		gameRankRegister.addGameRankScore(scoresForFrag);

		LOGGER.debug("GameRankGateway->summarizeKillAndDeaths-> Store the all scores for this matchId");
		this.gameRanks.put(matchIdCursor, gameRanksRegister);

		LOGGER.debug("GameRankGateway->summarizeKillAndDeaths->end()");
	}

	public void summarizeHumilhationAward(final Long matchIdCursor, final String playerWinnerName) {

		LOGGER.debug("GameRankGateway->summarizeHumilhationAward->begin()");

		HashSet<GameRank> gameRanksRegister = this.gameRanks.get(matchIdCursor);
		if (gameRanksRegister == null) {

			LOGGER.debug("GameRankGateway->summarizeHumilhationAward-> Create a new HashSet for GameRank");
			gameRanksRegister = new HashSet<GameRank>();
		} else {

			LOGGER.debug("GameRankGateway->summarizeHumilhationAward-> Recover a HashSet for GameRank");
		}

		GameRank gameRankRegister = null;
		for (GameRank gameRankInterator : gameRanksRegister) {
			if (gameRankInterator != null) {
				if (gameRankInterator.getMatchId().equals(matchIdCursor)) {

					LOGGER.debug(String.format("GameRankGateway->summarizeHumilhationAward-> Recover a GameRank for this matchId: %d", matchIdCursor));
					gameRankRegister = gameRankInterator;
				}
			}
		}
		if (gameRankRegister == null) {

			LOGGER.debug(String.format("GameRankGateway->summarizeHumilhationAward-> Create a new GameRank for this matchId: %d", matchIdCursor));
			gameRankRegister = new GameRank();
			gameRankRegister.setMatchId(matchIdCursor);
			HashSet<GameRankScore> scores = new HashSet<GameRankScore>();
			gameRankRegister.setScores(scores);
			gameRanksRegister.add(gameRankRegister);
		}

		GameRankScore scoresForPlayer = null;
		for (GameRankScore gameRankScoreInterator : gameRankRegister.getScores()) {
			if (gameRankScoreInterator != null) {
				if (gameRankScoreInterator.getPlayerName().equals(playerWinnerName)) {

					LOGGER.debug(String.format("GameRankGateway->summarizeHumilhationAward-> Recover a Score for this player: %s", playerWinnerName));
					scoresForPlayer = gameRankScoreInterator;
				}
			}
		}
		if (scoresForPlayer == null) {

			LOGGER.debug(String.format("GameRankGateway->summarizeHumilhationAward-> Create a Score for this player: %s", playerWinnerName));
			scoresForPlayer = new GameRankScore();
			scoresForPlayer.setPlayerName(playerWinnerName);
			scoresForPlayer.setMatchId(matchIdCursor);
		}

		scoresForPlayer.setHumilhationAward(true);

		LOGGER.debug("GameRankGateway->summarizeHumilhationAward-> Store the scores for this player");
		gameRankRegister.addGameRankScore(scoresForPlayer);

		LOGGER.debug("GameRankGateway->summarizeHumilhationAward-> Store the all scores for this matchId");
		this.gameRanks.put(matchIdCursor, gameRanksRegister);

		LOGGER.debug("GameRankGateway->summarizeHumilhationAward->end()");
	}

	public void summarizeBestWeaponForTheWinner(final Long matchIdCursor, final String bestWeaponForWinner) {

		LOGGER.debug("GameRankGateway->summarizeBestWeaponForTheWinner->begin()");

		HashSet<GameRank> gameRanksRegister = this.gameRanks.get(matchIdCursor);
		if (gameRanksRegister == null) {

			LOGGER.debug("GameRankGateway->summarizeBestWeaponForTheWinner-> Create a new HashSet for GameRank");
			gameRanksRegister = new HashSet<GameRank>();
		} else {

			LOGGER.debug("GameRankGateway->summarizeBestWeaponForTheWinner-> Recover a HashSet for GameRank");
		}

		GameRank gameRankRegister = null;
		for (GameRank gameRankInterator : gameRanksRegister) {
			if (gameRankInterator != null) {
				if (gameRankInterator.getMatchId().equals(matchIdCursor)) {

					LOGGER.debug(String.format("GameRankGateway->summarizeBestWeaponForTheWinner-> Recover a GameRank for this matchId: %d", matchIdCursor));
					gameRankRegister = gameRankInterator;
				}
			}
		}
		if (gameRankRegister == null) {

			LOGGER.debug(String.format("GameRankGateway->summarizeBestWeaponForTheWinner-> Create a new GameRank for this matchId: %d", matchIdCursor));
			gameRankRegister = new GameRank();
			gameRankRegister.setMatchId(matchIdCursor);
			HashSet<GameRankScore> scores = new HashSet<GameRankScore>();
			gameRankRegister.setScores(scores);
			gameRanksRegister.add(gameRankRegister);
		}

		LOGGER.debug("GameRankGateway->summarizeBestWeaponForTheWinner-> Store the best weapon for the winner for this matchId");
		gameRankRegister.setWinnerWeapon(bestWeaponForWinner);
		this.gameRanks.put(matchIdCursor, gameRanksRegister);

		LOGGER.debug("GameRankGateway->summarizeBestWeaponForTheWinner->end()");
	}

	public Set<GameRankScore> getScoresByMatchId(final Long matchIdCursor) {

		LOGGER.debug("GameRankGateway->getScoresByMatchId->begin()");
		HashSet<GameRank> gameRanksRegister = this.gameRanks.get(matchIdCursor);
		if (gameRanksRegister != null) {

			GameRank gameRankRegister = null;
			for (GameRank gameRankInterator : gameRanksRegister) {
				if (gameRankInterator != null) {
					if (gameRankInterator.getMatchId().equals(matchIdCursor)) {

						LOGGER.debug(String.format("GameRankGateway->getScoresByMatchId-> Recover a GameRank for this matchId: %d", matchIdCursor));
						gameRankRegister = gameRankInterator;
					}
				}
			}

			LOGGER.debug("GameRankGateway->getScoresByMatchId->end()");
			Set<GameRankScore> scores = gameRankRegister.getScores();
			return scores;
		} else {

			LOGGER.debug("GameRankGateway->getScoresByMatchId->end()");
			return null;
		}
	}

	public void setRankingForPlayerInMatchId(final Long matchIdCursor, final String player, final int rankingPosition) {

		LOGGER.debug("GameRankGateway->setRankingForPlayerInMatchId->begin()");

		HashSet<GameRank> gameRanksRegister = this.gameRanks.get(matchIdCursor);
		if (gameRanksRegister == null) {

			LOGGER.debug("GameRankGateway->setRankingForPlayerInMatchId-> Create a new HashSet for GameRank");
			gameRanksRegister = new HashSet<GameRank>();
		} else {

			LOGGER.debug("GameRankGateway->setRankingForPlayerInMatchId-> Recover a HashSet for GameRank");
		}

		GameRank gameRankRegister = null;
		for (GameRank gameRankInterator : gameRanksRegister) {
			if (gameRankInterator != null) {
				if (gameRankInterator.getMatchId().equals(matchIdCursor)) {

					LOGGER.debug(String.format("GameRankGateway->setRankingForPlayerInMatchId-> Recover a GameRank for this matchId: %d", matchIdCursor));
					gameRankRegister = gameRankInterator;
				}
			}
		}
		if (gameRankRegister == null) {

			LOGGER.debug(String.format("GameRankGateway->setRankingForPlayerInMatchId-> Create a new GameRank for this matchId: %d", matchIdCursor));
			gameRankRegister = new GameRank();
			gameRankRegister.setMatchId(matchIdCursor);
			HashSet<GameRankScore> scores = new HashSet<GameRankScore>();
			gameRankRegister.setScores(scores);
			gameRanksRegister.add(gameRankRegister);
		}

		GameRankScore scoresForPlayer = null;
		for (GameRankScore gameRankScoreInterator : gameRankRegister.getScores()) {
			if (gameRankScoreInterator != null) {
				if (gameRankScoreInterator.getPlayerName().equals(player)) {

					LOGGER.debug(String.format("GameRankGateway->setRankingForPlayerInMatchId-> Recover a Score for this player: %s", player));
					scoresForPlayer = gameRankScoreInterator;
				}
			}
		}
		if (scoresForPlayer == null) {

			LOGGER.debug(String.format("GameRankGateway->setRankingForPlayerInMatchId-> Create a Score for this player: %s", player));
			scoresForPlayer = new GameRankScore();
			scoresForPlayer.setPlayerName(player);
			scoresForPlayer.setMatchId(matchIdCursor);
		}

		scoresForPlayer.setPositionRank(rankingPosition);

		LOGGER.debug("GameRankGateway->setRankingForPlayerInMatchId-> Store the scores for this player");
		gameRankRegister.addGameRankScore(scoresForPlayer);

		LOGGER.debug("GameRankGateway->setRankingForPlayerInMatchId-> Store the all scores for this matchId");
		this.gameRanks.put(matchIdCursor, gameRanksRegister);

		LOGGER.debug("GameRankGateway->setRankingForPlayerInMatchId->end()");
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GameRankGateway [gameRanks=").append(gameRanks).append("]");
		return builder.toString();
	}
}