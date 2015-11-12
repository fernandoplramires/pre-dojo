package br.com.ramires.dojomaster.exception;

public class GameLogValidatorException extends RuntimeException {

	private static final long serialVersionUID = 7791029161747603710L;

	public GameLogValidatorException(final String message) {
        super(message);
    }
}
