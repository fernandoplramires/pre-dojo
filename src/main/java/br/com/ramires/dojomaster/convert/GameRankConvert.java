package br.com.ramires.dojomaster.convert;

import br.com.ramires.dojomaster.domain.GameRankGateway;
import br.com.ramires.dojomaster.dto.GameRanksDTO;

public interface GameRankConvert {

	public GameRanksDTO convertToDTO(final GameRankGateway gameRankGateway);
}
