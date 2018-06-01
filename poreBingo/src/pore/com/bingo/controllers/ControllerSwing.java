package pore.com.bingo.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pore.com.bingo.model.cartela.Cartela;
import pore.com.bingo.util.ValidadorUniversal;

public abstract class ControllerSwing {
	
	public static String CAMINHO_DIR_PADRAO = File.separator + "poreBingo";
	public static String CAMINHO_DIR_LOG = CAMINHO_DIR_PADRAO + File.separator + "log";
	public static String CAMINHO_DIR_SORTEIO = CAMINHO_DIR_PADRAO + File.separator + "sorteio";
	public static String CAMINHO_DIR_CARTELAS = CAMINHO_DIR_PADRAO + File.separator + "cartelas";

	public static int qdadeBolasPorCartela;
	
	public static List<Cartela> cartelas;

	public static int getQdadeBolasPorCartela() {
		return qdadeBolasPorCartela;
	}

	public static void setQdadeBolasPorCartela(int qdadeBolasPorCartela) {
		ControllerSwing.qdadeBolasPorCartela = qdadeBolasPorCartela;
	}

	public static List<Cartela> getCartelas() {
		if(!ValidadorUniversal.isListaPreenchida(cartelas)) {
			cartelas = new ArrayList<Cartela>();
		}
		
		return cartelas;
	}

	public static void setCartelas(List<Cartela> cartelas) {
		ControllerSwing.cartelas = cartelas;
	}
	
	public void inicializarPastas() {
		File dirBingo = new File(CAMINHO_DIR_PADRAO);
        File dirLog = new File(CAMINHO_DIR_LOG);
        File dirCartelas = new File(CAMINHO_DIR_CARTELAS);
        File dirSorteio = new File(CAMINHO_DIR_SORTEIO);
        
        if(!dirBingo.exists()) {
        	dirBingo.mkdirs();
        }
        
        if(!dirLog.exists()) {
        	dirLog.mkdirs();
        }
        
        if(!dirCartelas.exists()) {
        	dirCartelas.mkdirs();
        }
        
        if(!dirSorteio.exists()) {
        	dirSorteio.mkdirs();
        }
	}
}
