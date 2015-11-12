package br.com.ramires.dojomaster.report;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.ramires.dojomaster.domain.GameLogGateway;
import br.com.ramires.dojomaster.domain.GameRankGateway;
import br.com.ramires.dojomaster.entity.GameLog;
import br.com.ramires.dojomaster.entity.GameRankScore;
import br.com.ramires.dojomaster.parser.GameLogParserConfig;
import br.com.ramires.dojomaster.repository.GameLogRepository;
import br.com.ramires.dojomaster.repository.GameRankScoreRepository;

@Component
@Transactional
public class GameRankReportImpl implements GameRankReport {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameRankReportImpl.class);

	@Autowired
	private GameLogRepository gameLogRepository;

	@Autowired
	private GameRankScoreRepository gameRankScoreRepository;

	private GameRankGateway gameRankGateway;
	private GameLogGateway gameLogGateway;

	public GameRankGateway report(final GameLogGateway gameLogGateway) {

		LOGGER.debug("GameRankReportImpl->report->begin()");
		this.gameRankGateway = new GameRankGateway();
		this.gameLogGateway = gameLogGateway;

		LOGGER.warn("GameRankReportImpl->report-> Summarize Scores For kills and deaths");
		this.summarizeScoresForKillsandDeaths();

		LOGGER.warn("GameRankReportImpl->report-> Create a Ranking for the Matchs");
		this.createRankingForMatchs();

		LOGGER.warn("GameRankReportImpl->report-> Summarize scores with the best weapon for the winner and Humilhation Awards");
		this.summarizeScoresWithTheBestWeaponAndHumilhationAwards();

		//TODO: Sumarize Streaks in the matchs.
		//TODO: Summarize MonsterKill awards in the matchs.

		LOGGER.debug("GameRankReportImpl->report->end()");
		return gameRankGateway;
	}

	@Transactional
	public void summarizeScoresForKillsandDeaths() {

		LOGGER.debug("GameRankReportImpl->summarizeScoresForKillsandDeaths->begin()");
		HashSet<Long> matchsIds = this.gameLogGateway.getMatchsId();
		for (Long matchIdInterator : matchsIds) {

			HashSet<GameLog> gameLogs = this.gameLogGateway.getGameLogsByMatchId(matchIdInterator);
			for (GameLog gameLog : gameLogs) {

				if (gameLog.getType() == GameLogParserConfig.TYPE_ACTION) {

					LOGGER.debug(String.format("GameRankReportImpl->execsummarizeScoresForKillsandDeathsute-> to sumarize this register : %s", gameLog.toString()));
					gameRankGateway.summarizeKillAndDeaths(gameLog.getMatchId(), gameLog.getPlayer(), gameLog.getFrag());
				}

				LOGGER.debug(String.format("GameRankReportImpl->summarizeScoresForKillsandDeaths-> gameLogRepository.save: %s", gameLog.toString()));
				gameLogRepository.save(gameLog);
			}
		}
		LOGGER.debug("GameRankReportImpl->	public void summarizeScoresForKillsandDeaths->end()");
	}

	@Transactional
	public void createRankingForMatchs() {

		LOGGER.debug("GameRankReportImpl->createRankingForMatchs->begin()");
		HashSet<Long> matchsIds = this.gameLogGateway.getMatchsId();
		for (Long matchIdInterator : matchsIds) {

			LOGGER.debug("GameRankReportImpl->createRankingForMatchs-> Generate a a LinkedList for the Score of the Match");
			LinkedList<GameRankScore> listOfRanking = new LinkedList<GameRankScore>();
			Set<GameRankScore> scoresPlayers = this.gameRankGateway.getScoresByMatchId(matchIdInterator);
			for (GameRankScore score : scoresPlayers) {
				if (!score.getPlayerName().equals("<WORLD>")) {
					listOfRanking.add(score);
				}
			}

			LOGGER.debug("GameRankReportImpl->createRankingForMatchs-> Sort the Score of the Match");
			Collections.sort(listOfRanking, new Comparator<GameRankScore>() {
			    public int compare(GameRankScore o1, GameRankScore o2) {
			    	if (o1.getKills() == o2.getKills()) {
			    		if (o1.getDeaths() < o2.getDeaths()) { return -1;
			    		}
			    		else if (o1.getDeaths() == o2.getDeaths()) {
			    			return 0;
			    		}
			    	}
			    	else if (o1.getKills() > o2.getKills()) {
			    		return -1;
			    	}
			    	return 1;
			    }
			});

			LOGGER.warn("--------------------------------------------------------------------------------------------------------------");
			LOGGER.warn(String.format("GameRankReportImpl->createRankingForMatchs-> Start the set the rankings for this matchId [%s] ... ", matchIdInterator));
			LOGGER.warn("--------------------------------------------------------------------------------------------------------------");
			int positionRank = 0;
			long lastKillReference = 0;
			long lastDeathReference = 0;
			for (GameRankScore score : listOfRanking) {

				if (score.getKills() == lastKillReference && score.getDeaths() == lastDeathReference) {} else {
					positionRank++;
					lastKillReference = score.getKills();
					lastDeathReference = score.getDeaths();
				}

				LOGGER.warn(String.format("GameRankReportImpl->createRankingForMatchs-> Set the rank [%d] for [%s] in this Match", positionRank, score.getPlayerName()));
				score.setPositionRank(positionRank);
				gameRankGateway.setRankingForPlayerInMatchId(matchIdInterator, score.getPlayerName(), positionRank);

				LOGGER.debug("GameRankReportImpl->createRankingForMatchs-> Try to save this ranking score...");
				LOGGER.debug(String.format("GameRankReportImpl->createRankingForMatchs-> *** %s *** ", score.toString()));
				gameRankScoreRepository.save(score);
			}
		}
		LOGGER.warn("--------------------------------------------------------------------------------------------------------------");
		LOGGER.debug("GameRankReportImpl->createRankingForMatchs->end()");
	}

	@Transactional
	public void summarizeScoresWithTheBestWeaponAndHumilhationAwards(){

		LOGGER.debug("GameRankReportImpl->summarizeScoresWithTheBestWeaponAndHumilhationAwards->begin()");
		HashSet<Long> matchsIds = this.gameLogGateway.getMatchsId();
		for (Long matchIdInterator : matchsIds) {

			LOGGER.warn("--------------------------------------------------------------------------------------------------------------");
			LOGGER.warn(String.format("GameRankReportImpl->summarizeScoresWithTheBestWeaponAndHumilhationAwards-> BW/HA for [%s] ... ", matchIdInterator));
			LOGGER.warn("--------------------------------------------------------------------------------------------------------------");

			//Get winner of the match.
			LOGGER.debug(String.format("GameRankReportImpl->summarizeScoresWithTheBestWeaponAndHumilhationAwards-> Try to find the player winner for this match %d ...", matchIdInterator));
			List<GameRankScore> playerWinnerScore = gameRankScoreRepository.findTop1PlayerByMatchIdOrderByKillsDesc(matchIdInterator);

			String playerWinnerName = playerWinnerScore.get(0).getPlayerName();
			LOGGER.warn(String.format("GameRankReportImpl->summarizeScoresWithTheBestWeaponAndHumilhationAwards-> The player winner: %s", playerWinnerName));

			//Check and summarize Humilhation awards for the winner.
			LOGGER.debug("GameRankReportImpl->summarizeScoresWithTheBestWeaponAndHumilhationAwards-> Check for the Humilhation award...");
			long deathsOfTheWinner = playerWinnerScore.get(0).getDeaths();
			if (deathsOfTheWinner == 0) {

				LOGGER.warn("GameRankReportImpl->summarizeScoresWithTheBestWeaponAndHumilhationAwards-> Humilhation award receveid!");
				gameRankGateway.summarizeHumilhationAward(matchIdInterator, playerWinnerName);
			}

			//Summarize Scores with the best weapon for the winner.
			LOGGER.debug(String.format("GameRankReportImpl->summarizeScoresWithTheBestWeaponAndHumilhationAwards-> Try to find the best weapon for winner %s ...", playerWinnerName));
			List<Object[]> weapons = gameLogRepository.findBestWeaponForThePlayerWinner(matchIdInterator, playerWinnerName);
			Object[] objects = weapons.get(0);

			String bestWeaponForWinner = ((String) objects[0]).toString();
			LOGGER.warn(String.format("GameRankReportImpl->summarizeScoresWithTheBestWeaponAndHumilhationAwards-> Best weapon: %s", bestWeaponForWinner));
			gameRankGateway.summarizeBestWeaponForTheWinner(matchIdInterator, bestWeaponForWinner);
		}
		LOGGER.warn("--------------------------------------------------------------------------------------------------------------");
		LOGGER.debug("GameRankReportImpl->summarizeScoresWithTheBestWeaponAndHumilhationAwards->end()");
	}
}
