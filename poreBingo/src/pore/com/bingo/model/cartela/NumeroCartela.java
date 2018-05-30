package pore.com.bingo.model.cartela;

import pore.com.bingo.model.NumeroGenerico;

public class NumeroCartela extends NumeroGenerico {

	private Cartela cartela;

	public Cartela getCartela() {
		return cartela;
	}

	public void setCartela(Cartela cartela) {
		this.cartela = cartela;
	}
}
