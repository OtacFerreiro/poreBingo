package pore.com.bingo.controllers;

import java.awt.Window;
import java.util.ArrayList;

import pore.com.bingo.model.cartela.Cartela;
import pore.com.bingo.views.src.panels.EditarCartela_VW;
import pore.com.bingo.views.src.panels.ListarCartela_VW;
import pore.com.bingo.views.src.panels.PaginaInicial_VW;
import pore.com.bingo.views.src.panels.Sorteio_VW;

public class PaginaInicial_Controller extends ControllerSwing {

	private static PaginaInicial_VW tela;

	public PaginaInicial_Controller(final PaginaInicial_VW paginaInicial_VW) {
		tela = paginaInicial_VW;
		
		cartelas = new ArrayList<Cartela>();
		qdadeBolasPorCartela = 0;
	}

	public Window getTela() {
		return tela;
	}

	public void listarCartela() {
		ListarCartela_VW listarCartela = new ListarCartela_VW(tela, true);
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
