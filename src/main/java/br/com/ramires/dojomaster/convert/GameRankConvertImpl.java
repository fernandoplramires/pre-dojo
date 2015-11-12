package br.com.ramires.dojomaster.convert;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import br.com.ramires.dojomaster.domain.GameRankGateway;
import br.com.ramires.dojomaster.dto.GameRanksDTO;
import br.com.ramires.dojomaster.entity.GameRank;
import br.com.ramires.dojomaster.entity.GameRankScore;

@Component
public class GameRankConvertImpl implements GameRankConvert {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameRankConvertImpl.class);

	public GameRanksDTO convertToDTO(final GameRankGateway gameRankGateway) {

		LOGGER.debug("GameRankConvertImpl->convertToDto->begin()");

		GameRanksDTO gameRanksDTO = new GameRanksDTO();
		StringBuilder ranksReport = new StringBuilder();

		HashSet<Long> matchIds = gameRankGateway.getMatchsId();
		for(Long matchIdReference :  matchIds) {

			HashSet<GameRank> gameRanks = gameRankGateway.getGameRanksByMatchId(matchIdReference);
			for (GameRank gameRank : gameRanks ) {

				Long matchIdForThisMatch = gameRank.getMatchId();
				StringBuilder rankForThisMatch = new StringBuilder();

				//Header
				rankForThisMatch.append("").append("\n");
				rankForThisMatch.append("*=================================================================*").append("\n");
				rankForThisMatch.append(String.format("| MATCHID: %08d                                               |", matchIdForThisMatch)).append("\n");
				rankForThisMatch.append("|=================================================================|").append("\n");
				rankForThisMatch.append("| RANK | PLAYER                                   | KILLS | DEATH |").append("\n");
				rankForThisMatch.append("|=================================================================|").append("\n");

				//Sort the ranking
				LinkedList<GameRankScore> listOfRanking = new LinkedList<GameRankScore>();
				Set<GameRankScore> scores = gameRank.getScores();
				for (GameRankScore score : scores) {

					//Ignore player <WORLD>
					if (!score.getPlayerName().equals("<WORLD>")) {
						listOfRanking.add(score);
					}
				}
				Collections.sort(listOfRanking, new Comparator<GameRankScore>() {
				    public int compare(GameRankScore o1, GameRankScore o2) {
				    	if (o1.getPositionRank() == o2.getPositionRank()) { return 0; }
				    	else if (o1.getPositionRank() > o2.getPositionRank()) { return 1; }
				    	return -1;
				    }
				});

				//Generate the body
				boolean humilhationAward = false;
				for (GameRankScore score : listOfRanking) {

					//Set for Humilhation award.
					if (score.isHumilhationAward()) {
						humilhationAward = true;
					}

					//Body
					Long positionRanking = score.getPositionRank();
					String player = score.getPlayerName();
					Long kills = score.getKills();
					Long deaths = score.getDeaths();
					rankForThisMatch.append(String.format("| #%1$03d ", positionRanking));
					rankForThisMatch.append(String.format("| %1$-40s ", player));
					rankForThisMatch.append(String.format("| %5d | %5d |", kills, deaths));
					rankForThisMatch.append("\n");
				}

				//Trail
				rankForThisMatch.append("|=================================================================|").append("\n");
				rankForThisMatch.append(String.format("| Best Weapon used for the Winner:  %1$-29s |",  gameRank.getWinnerWeapon())).append("\n");;
				rankForThisMatch.append(String.format("| Humilhation Award for the Winner: %1$-29s |", humilhationAward)).append("\n");;
				rankForThisMatch.append("*=================================================================*").append("\n");

				//Append and go to next match.
				ranksReport.append(rankForThisMatch.toString());
			}
		}

		gameRanksDTO.setRanksReports(ranksReport.toString());
		LOGGER.debug("GameRankConvertImpl->convertToDto->end()");
		return gameRanksDTO;
	}
}
