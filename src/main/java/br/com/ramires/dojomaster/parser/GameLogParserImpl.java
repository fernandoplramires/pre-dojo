package br.com.ramires.dojomaster.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.ramires.dojomaster.domain.GameLogGateway;
import br.com.ramires.dojomaster.exception.GameLogParserException;

@Component
public class GameLogParserImpl implements GameLogParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameLogParserImpl.class);

	GameLogGateway 	gameLogGateway;

	public GameLogParser parser(MultipartFile fileInput) {

		LOGGER.debug("GameLogParserImpl->parser->begin()");
		LOGGER.warn("--------------------------------------------------------------------------------------------------------------");
		LOGGER.warn("GameLogParserImpl->parser-> For each line of game log ...");
		LOGGER.warn("--------------------------------------------------------------------------------------------------------------");

		Pattern dateHourNewMatchPattern 	= Pattern.compile(GameLogParserConfig.dateHourNewMatchRegexExpression);
		Pattern dateHourKillMatchPattern 	= Pattern.compile(GameLogParserConfig.dateHourKillMatchRegexExpression);
		Pattern dateHourEndMatchPattern 	= Pattern.compile(GameLogParserConfig.dateHourEndMatchRegexExpression);

		this.gameLogGateway 	= new GameLogGateway();

		BufferedReader reader 	= null;
		InputStream inputStream = null;
		Long matchIdCursor 		= null;

		try {

			inputStream = fileInput.getInputStream();
			reader = new BufferedReader(new InputStreamReader(inputStream));

			//Read line by line.
			String line = "";
			while ((line = reader.readLine()) != null) {

				LOGGER.debug("GameLogParserImpl->parser-> Try to parse the line of the log...");
				LOGGER.debug(String.format("GameLogParserImpl->parser-> *** %s ***", line));

				//Check if is the start of the match line and parser if is.
				Matcher matcher = dateHourNewMatchPattern.matcher(line);
				if (matcher.find()) {
					Long matchIdLine = new Long(matcher.group(GameLogParserConfig.MATCH_POS));
					if (matchIdCursor == null) {

						LOGGER.warn("GameLogParserImpl->parser-> (Parser) TYPE_NEW_MATCH line");
						matchIdCursor = matchIdLine;
						Date dateTime = getDateTimeFromGroupParser(matcher.group(GameLogParserConfig.DATE_TIME_POS));

						LOGGER.debug(String.format("GameLogParserImpl->parser-> (Parser) time: %s", dateTime));
						LOGGER.debug(String.format("GameLogParserImpl->parser-> (Parser) start: %d", matchIdCursor));

						this.gameLogGateway.addGameLog(matchIdCursor, dateTime, GameLogParserConfig.TYPE_NEW_MATCH);
					}
					else if (!matchIdCursor.equals(matchIdLine)) {

						LOGGER.warn("GameLogParserImpl->parser-> (Parser) Game log without matchid ended , raise exception!");
						throw new GameLogParserException("Game log without matchid ended");
					} else {

						LOGGER.warn("GameLogParserImpl->parser-> (Parser) Game log with repeat matchid started , raise exception!");
						throw new GameLogParserException("Game log with repeat matchid started");
					}
					continue;
				}

				//Check if action line and parser if is.
				matcher = dateHourKillMatchPattern.matcher(line);
				if (matcher.find()) {
					if (matchIdCursor != null) {

						LOGGER.warn("GameLogParserImpl->parser-> (Parser) TYPE_ACTION line");
						Date dateTime = getDateTimeFromGroupParser(matcher.group(GameLogParserConfig.DATE_TIME_POS));
						String player = new String(matcher.group(GameLogParserConfig.PLAYER_POS));
						String frag   = new String(matcher.group(GameLogParserConfig.FRAG_POS));
						String weapon = new String(matcher.group(GameLogParserConfig.WEAPON_POS));

						LOGGER.debug(String.format("GameLogParserImpl->parser-> (Parser) time: %s", dateTime.toString()));
						LOGGER.debug(String.format("GameLogParserImpl->parser-> (Parser) player: %s", player));
						LOGGER.debug(String.format("GameLogParserImpl->parser-> (Parser) frag: %s", frag));
						LOGGER.debug(String.format("GameLogParserImpl->parser-> (Parser) weapon: %s", weapon));

						this.gameLogGateway.addGameLog(matchIdCursor, dateTime, GameLogParserConfig.TYPE_ACTION, player, frag, weapon);
					} else {

						LOGGER.warn("GameLogParserImpl->parser-> (Parser) Game log without matchid started, raise exception!");
						throw new GameLogParserException("Game log without matchid started");
					}
					continue;
				}

				//Check if is the end of the match line and parser if is.
				matcher = dateHourEndMatchPattern.matcher(line);
				if (matcher.find()) {
					Long matchIdLine = new Long(matcher.group(GameLogParserConfig.MATCH_POS));
					if (matchIdCursor == null) {

						LOGGER.warn("GameLogParserImpl->parser-> (Parser) Game log without matchid started, raise exception!");
						LOGGER.warn("--------------------------------------------------------------------------------------------------------------");

						throw new GameLogParserException("Game log without matchid started");
					}
					else if (matchIdCursor.equals(matchIdLine)) {

						LOGGER.warn("GameLogParserImpl->parser-> (Parser) TYPE_END_MATCH line");
						Date dateTime = getDateTimeFromGroupParser(matcher.group(GameLogParserConfig.DATE_TIME_POS));

						LOGGER.debug(String.format("GameLogParserImpl->parser-> (Parser) time: %s", dateTime));
						LOGGER.debug(String.format("GameLogServiceImpl->parser-> (Parser) end: %s", matchIdCursor));

						this.gameLogGateway.addGameLog(matchIdCursor, dateTime, GameLogParserConfig.TYPE_END_MATCH);
						LOGGER.warn("--------------------------------------------------------------------------------------------------------------");

						matchIdCursor = null;
					}
					continue;
				}
			}
		} catch (FileNotFoundException e) {

			LOGGER.warn("GameLogParserImpl->parser-> (Parser) FileNotFoundException problem, raise exception!");
			throw new GameLogParserException(e.getMessage());
		} catch (IOException e) {

			LOGGER.warn("GameLogParserImpl->parser-> (Parser) IOException problem, raise exception!");
			throw new GameLogParserException(e.getMessage());
		}

		try {

			reader.close();
		} catch (IOException e) {

			LOGGER.warn("GameLogParserImpl->parser-> (Parser) IOException problem, raise exception!");
			throw new GameLogParserException(e.getMessage());
		}

		LOGGER.debug("GameLogParserImpl->parser->end()");
		return this;
	}

	public GameLogGateway getGameLogs() {
		return this.gameLogGateway;
	}

	private Date getDateTimeFromGroupParser(final String dateFromGroupParser) {

		Date dateTime;
		DateFormat formatter = new SimpleDateFormat(GameLogParserConfig.dateHourFormatInTheGameLogFile);
		try {
			dateTime = (Date) formatter.parse(dateFromGroupParser);
		} catch (ParseException e) {
			throw new GameLogParserException(e.toString());
		}
		return dateTime;
	}
}
