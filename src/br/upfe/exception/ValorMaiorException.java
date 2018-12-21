package br.upfe.exception;

/**
 * Exception para lançar quando programa não entrado
 * @author samuel, eliandro
 *
 */
public class ValorMaiorException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ValorMaiorException(Integer valor) {
		super("Valor maior que o número de itens existentes, necessário ser menor que = " + valor);
	}

}
