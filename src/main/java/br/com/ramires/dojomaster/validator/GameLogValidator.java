package br.com.ramires.dojomaster.validator;

import org.springframework.web.multipart.MultipartFile;

public interface GameLogValidator {

	public void validate(final MultipartFile fileInput);
}
