package br.com.ramires.dojomaster.parser;

import org.springframework.web.multipart.MultipartFile;

import br.com.ramires.dojomaster.domain.GameLogGateway;

public interface GameLogParser {

	public GameLogParser parser(MultipartFile fileInput);
	public GameLogGateway getGameLogs();
}
