package pore.com.bingo.model.cartela;

import java.util.LinkedList;

import pore.com.bingo.model.ObjetoGenerico;
import pore.com.bingo.util.ValidadorUniversal;

public class Cartela extends ObjetoGenerico {

	private int numeroCartela;
	
	private String portador;
	
	private LinkedList<NumeroCartela> numeros;
	private LinkedList<NumeroCartela> numerosChamados;

	public String getPortador() {
		return portador;
	}

	public void setPortador(String portador) {
		this.portador = portador;
	}


	public int getNumeroCartela() {
		return numeroCartela;
	}

	public void setNumeroCartela(int numeroCartela) {
		this.numeroCartela = numeroCartela;
	}

	public LinkedList<NumeroCartela> getNumeros() {
		if(!ValidadorUniversal.isListaPreenchida(numeros)) {
			numeros = new LinkedList<NumeroCartela>();
		}
		
		return numeros;
	}

	public void setNumeros(LinkedList<NumeroCartela> numeros) {
		this.numeros = numeros;
	}

	public LinkedList<NumeroCartela> getNumerosChamados() {
		if(!ValidadorUniversal.isListaPreenchida(numerosChamados)) {
			numerosChamados = new LinkedList<NumeroCartela>();
		}
		
		return numerosChamados;
	}

	public void setNumerosChamados(LinkedList<NumeroCartela> numerosChamados) {
		this.numerosChamados = numerosChamados;
	}

}
