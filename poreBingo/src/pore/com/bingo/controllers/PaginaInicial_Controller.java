package pore.com.bingo.controllers;

import java.awt.Window;
import java.io.File;
import java.util.ArrayList;

import pore.com.bingo.model.cartela.Cartela;
import pore.com.bingo.util.ValidadorUniversal;
import pore.com.bingo.util.funcoes.FuncoesSwing;
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
		
		File fileCartelas = new File(CAMINHO_DIR_CARTELAS + File.separator + "cartelas.txt");
		
		if(fileCartelas.exists()) {
			importarArquivoCartelasSistema(fileCartelas);
			
		} else {
			fileCartelas = new File(CAMINHO_DIR_CARTELAS + File.separator + "cartelasImportadas.txt");
			
			if(fileCartelas.exists()) {
				importarArquivoCartelasSistema(fileCartelas);
				
			}			
		}
		
	}

	public Window getTela() {
		return tela;
	}

	public void listarCartela() {
		ListarCartela_VW listarCartela = new ListarCartela_VW(tela, true);
		listarCartela.setVisible(true);

		while(listarCartela.isVisible()) {};
	}

	@SuppressWarnings("static-access")
	public void editarCartela() {
		
		int numeroCartela = FuncoesSwing.getMensagemInt(tela, "Selecionar Cartela", "Qual o numero da cartela que deseja editar?");
		
		if(numeroCartela > 0) {
			
			Cartela cartela = null;
			
			if(ValidadorUniversal.isListaPreenchida(cartelas)) {
				for(Cartela cartelaCadastrada: cartelas) {
					if(cartelaCadastrada.getNumeroCartela() == numeroCartela) {
						cartela = cartelaCadastrada;
					}
				}
				
				if(cartela != null) {
					EditarCartela_VW editarCartela = new EditarCartela_VW(tela, true);
					editarCartela.controller.setCartelaEditada(cartela);
					editarCartela.setVisible(true);
					
					while(editarCartela.isVisible()) {};
					
				} else {
					FuncoesSwing.mostrarMensagemErro(tela, "Erro Inesperado", "Não foi possível encontrar a cartela com a numeração informada.");
				}
			}			
		} else {
			FuncoesSwing.mostrarMensagemAtencao(tela, "Numero invalido.");
		}		
	}

	public void realizarSorteio() {
		Sorteio_VW sorteio = new Sorteio_VW();
		sorteio.setVisible(true);

		while(sorteio.isVisible()) {};
	}

}
