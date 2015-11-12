package br.com.ramires.dojomaster.report;

import br.com.ramires.dojomaster.domain.GameLogGateway;
import br.com.ramires.dojomaster.domain.GameRankGateway;

public interface GameRankReport {

	public GameRankGateway report(final GameLogGateway gameLogGateway);
}
