package com.hulkStore.application.Exception;

public class ProductOrIdNotFound extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8807423021557013436L;
	
	public ProductOrIdNotFound() {
		super("Producto o Id no encontrado");
	}

	public ProductOrIdNotFound(String message) {
		super(message);
	}

}
