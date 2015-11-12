package br.com.ramires.dojomaster.exception;

public class GameLogParserException extends RuntimeException {

	private static final long serialVersionUID = 8696038809260795915L;

	public GameLogParserException(final String message) {
        super(message);
    }
}
