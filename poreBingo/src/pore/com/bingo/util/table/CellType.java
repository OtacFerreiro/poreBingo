package pore.com.bingo.util.table;

import java.awt.Color;
import java.awt.Font;

public class CellType {
	
	private String dado;
	private int valor;
	private Color cor;
	private Font fonte;
	
	public Font getFonte() {
		return fonte;
	}
	
	public void setFonte(Font fonte) {
		this.fonte = fonte;
	}
	
	public String getDado() {
		return dado;
	}
	
	public void setDado(String dado) {
		this.dado = dado;
	}
	
	public Color getCor() {
		return cor;
	}
	
	public void setCor(Color cor) {
		this.cor = cor;
	}
	
	public int getValor() {
		return valor;
	}
	
	public void setValor(int valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "";
	}

}
