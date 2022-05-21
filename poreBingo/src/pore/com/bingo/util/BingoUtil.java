package pore.com.bingo.util;

import java.util.LinkedList;
import java.util.List;

import pore.com.bingo.model.cartela.NumeroCartela;

public class BingoUtil {
	
	public static LinkedList<String> convertNumeroCartelaToString(List<NumeroCartela> numeros) {
		LinkedList<String> numerosNew = new LinkedList<String>();
		
		if(numeros != null) {
			for(NumeroCartela numeroC: numeros) {
				numerosNew.add(numeroC.getNumero());
			}
		}
		
		return numerosNew;
	}

}
