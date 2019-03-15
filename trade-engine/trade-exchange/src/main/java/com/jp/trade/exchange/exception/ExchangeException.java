package com.jp.trade.exchange.exception;

/**
 * Custom exception for exchange
 *
 * @author apoorva.ubana
 */
public class ExchangeException extends RuntimeException {

	private static final long serialVersionUID = 13813201300224345L;

	/**
	 * Call Runtime exception with message and cause.
	 *
	 * @param message
	 * @param cause
	 */
	public ExchangeException(String message, Throwable cause) {
		super(message, cause);
	}
}
