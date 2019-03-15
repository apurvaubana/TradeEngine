package com.jp.trade.api.model.response;

import java.io.Serializable;
import java.util.List;

import lombok.ToString;

/**
 * Error message class to be used for API response.
 *
 * @author apoorva.ubana
 */
@ToString
public class ErrorMessage implements Serializable {

	private static final long serialVersionUID = -1L;

	private List<String> errors;
	private String msg;

	/**
	 * Constructor to populate the message.
	 *
	 * @param message
	 */
	public ErrorMessage(String message) {
		this.msg = message;
	}

	/**
	 * Constructor to populate the message and errors.
	 *
	 * @param errors
	 * @param message
	 */
	public ErrorMessage(String msg, List<String> errors) {
		super();
		this.errors = errors;
		this.msg = msg;
	}

	/**
	 * Constructor to populate the errors.
	 *
	 * @param errors
	 */
	public ErrorMessage(List<String> errors) {
		super();
		this.errors = errors;
	}

}
