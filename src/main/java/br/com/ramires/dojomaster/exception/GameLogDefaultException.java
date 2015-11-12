package br.com.ramires.dojomaster.exception;

public class GameLogDefaultException extends RuntimeException {

	private static final long serialVersionUID = -7519569370929342918L;

	public GameLogDefaultException(final String message) {
        super(message);
    }
}
