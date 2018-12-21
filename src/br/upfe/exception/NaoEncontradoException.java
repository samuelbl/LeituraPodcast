package br.upfe.exception;

/**
 * Exception para lançar quando programa não entrado
 * @author samuel, eliandro
 *
 */
public class NaoEncontradoException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public NaoEncontradoException() {
		super("Programa não encontrado");
	}

}
