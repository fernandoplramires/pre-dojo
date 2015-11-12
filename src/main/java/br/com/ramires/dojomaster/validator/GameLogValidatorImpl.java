package br.com.ramires.dojomaster.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.ramires.dojomaster.exception.GameLogValidatorException;

@Component
public class GameLogValidatorImpl implements GameLogValidator {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameLogValidatorImpl.class);

	public void validate(final MultipartFile fileInput) {

		LOGGER.debug("GameLogValidatorImpl->validate->begin()");

		LOGGER.debug("GameLogValidatorImpl->validate-> (Check) Game file input context validate");
		if (fileInput == null) {
			
			
			throw new GameLogValidatorException("Game file input are not correct upload");
		}

		LOGGER.debug("GameLogValidatorImpl->validate-> (Check) Game file size");
		if (fileInput.isEmpty() == true) {
			
			
			throw new GameLogValidatorException("Game file input is empty.");
		}

		LOGGER.debug("GameLogValidatorImpl->validate->end()");
	}
}
