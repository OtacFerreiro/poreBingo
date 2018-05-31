package pore.com.bingo.controllers;

import java.io.File;

import javax.swing.JFileChooser;

import pore.com.bingo.views.src.panels.EditarCartela_VW;
import pore.com.bingo.views.src.panels.ListarCartela_VW;
import pore.com.bingo.views.src.panels.PaginaInicial_VW;
import pore.com.bingo.views.src.panels.Sorteio_VW;

public class PaginaInicial_Controller {
	
	public static String CAMINHO_DIR_PADRAO = File.separator + "poreBingo";
	public static String CAMINHO_DIR_LOG = File.separator + "log";
	public static String CAMINHO_DIR_SORTEIO = File.separator + "sorteio";
	public static String CAMINHO_DIR_CARTELAS = File.separator + "cartelas";
	
	private static PaginaInicial_VW tela;

	public PaginaInicial_Controller(final PaginaInicial_VW paginaInicial_VW) {
		tela = paginaInicial_VW;
	}
	
	public PaginaInicial_VW getTela() {
		return tela;
	}
	
	public void importarCartelas() {
		
		JFileChooser chooser = new JFileChooser();
		
		int returnVal = chooser.showOpenDialog(tela);
		
		File file = null;
		
		if(returnVal == JFileChooser.APPROVE_OPTION) {     
		  file = chooser.getSelectedFile();    
		}
	}
	
	public void listarCartela() {
		ListarCartela_VW listarCartela = new ListarCartela_VW();
		listarCartela.setVisible(true);
		
		while(listarCartela.isVisible()) {};
	}
	
	public void editarCartela() {
		EditarCartela_VW editarCartela = new EditarCartela_VW();
		editarCartela.setVisible(true);
		
		while(editarCartela.isVisible()) {};
	}
	
	public void realizarSorteio() {
		Sorteio_VW sorteio = new Sorteio_VW();
		sorteio.setVisible(true);
		
		while(sorteio.isVisible()) {};
	}

}
