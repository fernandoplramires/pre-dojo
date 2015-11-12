package br.com.ramires.dojomaster.validator;

import java.io.UnsupportedEncodingException;
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

import br.com.ramires.dojomaster.exception.GameLogValidatorException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {GameLogValidatorImpl.class} )
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GameLogValidatorImplTest {

	@Autowired
	GameLogValidator gameLogValidator;

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

		gameLogValidator.validate(mockFile);
	}

	@Test(expected= GameLogValidatorException.class)
	//throw new GameLogValidatorException("Game file input is empty.");
	public void shouldExceptionForGameFileInputIsEmpty() {

		MultipartFile mockFile = null;
		String bufferEmpty = "";

		try {
			mockFile = new MockMultipartFile("mockFile", "bufferEmpty.log", "text/log", bufferEmpty.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		gameLogValidator.validate(mockFile);
	}

	@Test(expected= GameLogValidatorException.class)
	//throw new GameLogValidatorException("Game file input are not correct upload");
	public void shouldExceptionForGameFileInputAreNotCorrectUpload() {

		gameLogValidator.validate(null);
	}

}