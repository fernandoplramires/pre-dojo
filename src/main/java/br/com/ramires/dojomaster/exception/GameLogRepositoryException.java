package br.com.ramires.dojomaster.exception;

public class GameLogRepositoryException extends RuntimeException {

	private static final long serialVersionUID = -1073940395791143349L;

	public GameLogRepositoryException(final String message) {
        super(message);
    }
}
