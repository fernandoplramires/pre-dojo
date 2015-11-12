package br.com.ramires.dojomaster.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.ramires.dojomaster.convert.GameRankConvert;
import br.com.ramires.dojomaster.domain.GameLogGateway;
import br.com.ramires.dojomaster.domain.GameRankGateway;
import br.com.ramires.dojomaster.dto.GameRanksDTO;
import br.com.ramires.dojomaster.parser.GameLogParser;
import br.com.ramires.dojomaster.report.GameRankReport;
import br.com.ramires.dojomaster.validator.GameLogValidator;

@Service
public class GameLogServiceImpl implements GameLogService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameLogServiceImpl.class);

	@Autowired
	private GameLogValidator gameLogValidator;

	@Autowired
	private GameLogParser gameLogParser;

	@Autowired
	private GameRankReport gameRankReport;

	@Autowired
	private GameRankConvert gameRankGatewayConvert;

	public GameRanksDTO run(final MultipartFile fileInput) {

		LOGGER.debug("GameLogServiceImpl->run->begin()");

		GameLogGateway  gameLogGateway 	= before(fileInput);
		GameRankGateway gameRankGateway = execute(gameLogGateway);
		GameRanksDTO 	gameRanskDTO 	= after(gameRankGateway);

		LOGGER.debug("GameLogServiceImpl->run->end()");
		return gameRanskDTO;
	}

	private GameLogGateway before(final MultipartFile fileInput) {

		LOGGER.debug("GameLogServiceImpl->before->begin()");

		LOGGER.warn("GameLogServiceImpl->before-> Validate the game file log");
		gameLogValidator.validate(fileInput);

		LOGGER.warn("GameLogServiceImpl->before-> Parser the gamelog receveid to <GameLogGateway>");
		GameLogGateway gameLogGateway = gameLogParser.parser(fileInput).getGameLogs();

		LOGGER.debug("GameLogServiceImpl->before->end()");
		return gameLogGateway;
	}

	private GameRankGateway execute(GameLogGateway gameLogGateway) {

		LOGGER.debug("GameLogServiceImpl->execute->begin()");

		LOGGER.warn("GameLogServiceImpl->execute-> Generate reports");
		GameRankGateway gameRankGateway = gameRankReport.report(gameLogGateway);

		LOGGER.debug("GameLogService->execute->end()");
		return gameRankGateway;
	}

	private GameRanksDTO after(final GameRankGateway gameRankGateway) {

		LOGGER.debug("GameLogServiceImpl->after->begin()");

		LOGGER.warn("GameLogServiceImpl->after-> Convert Domain Object <GameRankGateway> to Data Transfer Object <GameRanksDTO>");
		GameRanksDTO gameRanksDTO = gameRankGatewayConvert.convertToDTO(gameRankGateway);

		LOGGER.debug("GameLogServiceImpl->after->end()");
		return gameRanksDTO;
	}
}
