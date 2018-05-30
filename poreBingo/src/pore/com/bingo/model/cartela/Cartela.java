package pore.com.bingo.model.cartela;

import java.util.List;

import pore.com.bingo.model.ObjetoGenerico;

public class Cartela extends ObjetoGenerico {

	private int numeroCartela;
	
	private String portador;
	
	public String getPortador() {
		return portador;
	}

	public void setPortador(String portador) {
		this.portador = portador;
	}

	private List<NumeroCartela> numeros;

	public int getNumeroCartela() {
		return numeroCartela;
	}

	public void setNumeroCartela(int numeroCartela) {
		this.numeroCartela = numeroCartela;
	}

	public List<NumeroCartela> getNumeros() {
		return numeros;
	}

	public void setNumeros(List<NumeroCartela> numeros) {
		this.numeros = numeros;
	}
}
