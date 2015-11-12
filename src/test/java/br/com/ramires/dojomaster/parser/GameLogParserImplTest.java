package br.com.ramires.dojomaster.parser;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import br.com.ramires.dojomaster.exception.GameLogParserException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {GameLogParserImpl.class} )
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GameLogParserImplTest {

	@Autowired
	GameLogParser gameLogParser;

	@Before
	public void beforeTest() {
	}

	@After
	public void afterTest() {
	}

	@Test
	//Should pass;
	public void shouldPassForValidGameFileInput() {

		MultipartFile mockFile = null;
		String buffer = "";

		LinkedList<String> listOfLines = new LinkedList<String>();
		listOfLines.add("23/04/2013 15:34:22 - New match 11348965 has started");
		listOfLines.add("23/04/2013 15:36:04 - Roman killed Nick using M16");
		listOfLines.add("23/04/2013 15:36:06 - Roman killed Paul using M16");
		listOfLines.add("23/04/2013 15:36:12 - Roman killed Oliver using DEAGLE");
		listOfLines.add("23/04/2013 15:36:33 - <WORLD> killed Nick by DROWN");
		listOfLines.add("23/04/2013 15:39:22 - Match 11348965 has ended");
		for (String line : listOfLines) {
			buffer = buffer + line + "\r\n";
		}

		try {
			mockFile = new MockMultipartFile("mockFile", "buffer.log", "text/log", buffer.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		GameLogParser gamelogParser = gameLogParser.parser(mockFile);
		assertNotNull(gamelogParser);
		HashSet<Long> gameLogsMatchsIds = gamelogParser.getGameLogs().getMatchsId();
		assertEquals(true, gameLogsMatchsIds.contains(new Long(11348965)));
	}

	@Test(expected = GameLogParserException.class)
	//throw new GameLogParserException("Game log without matchid ended");
	public void shouldExceptionForGameLogWithoutMatchidEndedStarted() {

		MultipartFile mockFile = null;
		String buffer = "";

		LinkedList<String> listOfLines = new LinkedList<String>();
		listOfLines.add("23/04/2013 15:34:22 - New match 11348965 has started");
		listOfLines.add("23/04/2013 15:36:04 - Roman killed Nick using M16");
		listOfLines.add("23/04/2013 15:36:06 - Roman killed Paul using M16");
		listOfLines.add("23/04/2013 15:36:12 - Roman killed Oliver using DEAGLE");
		listOfLines.add("23/04/2013 15:36:33 - <WORLD> killed Nick by DROWN");
		//listOfLines.add("23/04/2013 15:39:22 - Match 11348965 has ended");
		listOfLines.add("23/04/2013 15:34:22 - New match 11348966 has started");
		for (String line : listOfLines) {
			buffer = buffer + line + "\r\n";
		}

		try {
			mockFile = new MockMultipartFile("mockFile", "buffer.log", "text/log", buffer.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		@SuppressWarnings("unused")
		GameLogParser gamelogParser = gameLogParser.parser(mockFile);
	}

	@Test(expected = GameLogParserException.class)
	//throw new GameLogParserException("Game log with repeat matchid started");
	public void shouldExceptionForGameLogWithRepeatMatchidStartedStarted() {

		MultipartFile mockFile = null;
		String buffer = "";

		LinkedList<String> listOfLines = new LinkedList<String>();
		listOfLines.add("23/04/2013 15:34:22 - New match 11348965 has started");
		listOfLines.add("23/04/2013 15:34:22 - New match 11348965 has started");
		listOfLines.add("23/04/2013 15:36:04 - Roman killed Nick using M16");
		listOfLines.add("23/04/2013 15:36:06 - Roman killed Paul using M16");
		listOfLines.add("23/04/2013 15:36:12 - Roman killed Oliver using DEAGLE");
		listOfLines.add("23/04/2013 15:36:33 - <WORLD> killed Nick by DROWN");
		listOfLines.add("23/04/2013 15:39:22 - Match 11348965 has ended");
		for (String line : listOfLines) {
			buffer = buffer + line + "\r\n";
		}

		try {
			mockFile = new MockMultipartFile("mockFile", "buffer.log", "text/log", buffer.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		@SuppressWarnings("unused")
		GameLogParser gamelogParser = gameLogParser.parser(mockFile);
	}

	@Test(expected = GameLogParserException.class)
	//throw new GameLogParserException("Game log without matchid started");
	public void shouldExceptionForGameLogWithoutMatchidStartedAction(){

		MultipartFile mockFile = null;
		String buffer = "";

		LinkedList<String> listOfLines = new LinkedList<String>();
		//listOfLines.add("23/04/2013 15:34:22 - New match 11348965 has started");
		listOfLines.add("23/04/2013 15:36:04 - Roman killed Nick using M16");
		listOfLines.add("23/04/2013 15:36:06 - Roman killed Paul using M16");
		listOfLines.add("23/04/2013 15:36:12 - Roman killed Oliver using DEAGLE");
		listOfLines.add("23/04/2013 15:36:33 - <WORLD> killed Nick by DROWN");
		listOfLines.add("23/04/2013 15:39:22 - Match 11348965 has ended");
		for (String line : listOfLines) {
			buffer = buffer + line + "\r\n";
		}

		try {
			mockFile = new MockMultipartFile("mockFile", "buffer.log", "text/log", buffer.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		@SuppressWarnings("unused")
		GameLogParser gamelogParser = gameLogParser.parser(mockFile);
	}

	@Test(expected = GameLogParserException.class)
	//throw new GameLogParserException("Game log without matchid started");
	public void shouldExceptionForGameLogWithoutMatchidStartedEnded (){

		MultipartFile mockFile = null;
		String buffer = "";

		LinkedList<String> listOfLines = new LinkedList<String>();
		//listOfLines.add("23/04/2013 15:34:22 - New match 11348965 has started");
		//listOfLines.add("23/04/2013 15:36:04 - Roman killed Nick using M16");
		//listOfLines.add("23/04/2013 15:36:06 - Roman killed Paul using M16");
		//listOfLines.add("23/04/2013 15:36:12 - Roman killed Oliver using DEAGLE");
		//listOfLines.add("23/04/2013 15:36:33 - <WORLD> killed Nick by DROWN");
		listOfLines.add("23/04/2013 15:39:22 - Match 11348965 has ended");
		for (String line : listOfLines) {
			buffer = buffer + line + "\r\n";
		}

		try {
			mockFile = new MockMultipartFile("mockFile", "buffer.log", "text/log", buffer.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		@SuppressWarnings("unused")
		GameLogParser gamelogParser = gameLogParser.parser(mockFile);
	}

	@Test(expected= GameLogParserException.class)
	//throw new GameLogParserException("Game log with repeat matchid started");
	public void shouldExceptionForGameLogWithRepeatMatchidStartedEnded(){

		MultipartFile mockFile = null;
		String buffer = "";

		LinkedList<String> listOfLines = new LinkedList<String>();
		listOfLines.add("23/04/2013 15:39:22 - Match 11348965 has ended");
		listOfLines.add("23/04/2013 15:34:22 - New match 11348965 has started");
		listOfLines.add("23/04/2013 15:36:04 - Roman killed Nick using M16");
		listOfLines.add("23/04/2013 15:36:06 - Roman killed Paul using M16");
		listOfLines.add("23/04/2013 15:36:12 - Roman killed Oliver using DEAGLE");
		listOfLines.add("23/04/2013 15:36:33 - <WORLD> killed Nick by DROWN");
		listOfLines.add("23/04/2013 15:39:22 - Match 11348965 has ended");
		for (String line : listOfLines) {
			buffer = buffer + line + "\r\n";
		}

		try {
			mockFile = new MockMultipartFile("mockFile", "buffer.log", "text/log", buffer.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		@SuppressWarnings("unused")
		GameLogParser gamelogParser = gameLogParser.parser(mockFile);
	}

	@Test//(expected= GameLogParserException.class)
	//throw new GameLogParserException(e.toString());
	public void shouldExceptionForGameLogWithBadDateFormat(){

		MultipartFile mockFile = null;
		String buffer = "";

		LinkedList<String> listOfLines = new LinkedList<String>();
		//listOfLines.add("23/04/2013 15:34:22 - New match 11348965 has started");
		listOfLines.add("2013/04/20 15:34:22 - New match 11348965 has started");
		listOfLines.add("2013/04/20 15:36:04 - Roman killed Nick using M16");
		listOfLines.add("2013/04/20 15:36:06 - Roman killed Paul using M16");
		listOfLines.add("2013/04/20 15:36:12 - Roman killed Oliver using DEAGLE");
		listOfLines.add("2013/04/20 15:36:33 - <WORLD> killed Nick by DROWN");
		listOfLines.add("2013/04/20 15:39:22 - Match 11348965 has ended");
		for (String line : listOfLines) {
			buffer = buffer + line + "\r\n";
		}

		try {
			mockFile = new MockMultipartFile("mockFile", "buffer.log", "text/log", buffer.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		@SuppressWarnings("unused")
		GameLogParser gamelogParser = gameLogParser.parser(mockFile);
	}

	//public GameLogGateway getGameLogs();
}