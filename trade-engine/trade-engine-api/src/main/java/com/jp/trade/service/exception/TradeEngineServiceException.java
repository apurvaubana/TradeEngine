package com.jp.trade.service.exception;

/**
 * A business service exception.
 *
 * @author apoorva.ubana
 */
public class TradeEngineServiceException extends RuntimeException {

	private static final long serialVersionUID = 1381320130022416598L;

	/**
	 * Call Runtime exception with message and cause.
	 *
	 * @param message
	 * @param cause
	 */
	public TradeEngineServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Call Runtime exception with message and cause.
	 *
	 * @param message
	 */
	public TradeEngineServiceException(String message) {
		super(message);
	}
}
