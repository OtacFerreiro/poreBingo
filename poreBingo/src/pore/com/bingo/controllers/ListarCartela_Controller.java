package pore.com.bingo.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import javax.swing.JFileChooser;

import pore.com.bingo.model.cartela.Cartela;
import pore.com.bingo.model.cartela.NumeroCartela;
import pore.com.bingo.util.FuncoesData;
import pore.com.bingo.util.FuncoesSwing;
import pore.com.bingo.util.ValidadorUniversal;
import pore.com.bingo.views.src.panels.ListarCartela_VW;

public class ListarCartela_Controller extends ControllerSwing {
	
	private static ListarCartela_VW tela;
	
	public ListarCartela_Controller(final ListarCartela_VW listar_VW) {
		tela = listar_VW;
		
		importarCartelas();
	}

	public ListarCartela_VW getTela() {
		return tela;
	}

	/*
	 * FORMATO DO ARQUIVO TXT
	 * CODIGOCARTELA  -  NUMERO NUMERO NUMERO NUMERO [...] NUMERO  
	 *  */
	public void importarCartelas() {

		qdadeBolasPorCartela = FuncoesSwing.getMensagemInt(tela, "Configuração das Cartelas", "Quantas bolas cada cartela possui?");

		if(qdadeBolasPorCartela > 0) {
			JFileChooser chooser = new JFileChooser();

			int returnVal = chooser.showOpenDialog(tela);

			File file = null;

			if(returnVal == JFileChooser.APPROVE_OPTION) {     
				file = chooser.getSelectedFile();    
			}

			if(file != null) {
				BufferedReader in = null;
				try {
					in = new BufferedReader(new FileReader(file));
					
					String line = in.readLine();

					while(ValidadorUniversal.check(line)){
						line = line.trim();
						
						String [] cartelaArray = line.split("  -  ");
						
						if(ValidadorUniversal.isArrayPreenchido(cartelaArray)) {							
							String [] numeros = cartelaArray[1].split(" ");
							
							if(ValidadorUniversal.isArrayPreenchido(numeros)) {
								Cartela cartela = new Cartela();
								cartela.setNumeroCartela(Integer.parseInt(cartelaArray[0]));
								
								for(int i = 0; i < numeros.length; i++) {
									NumeroCartela numCartela = new NumeroCartela();
									numCartela.setCartela(cartela);
									numCartela.setNumero(numeros[i]);
									
									cartela.getNumeros().add(numCartela);
								}
								
								if(cartela.getNumeros().size() == qdadeBolasPorCartela) {
									System.out.println("[" + FuncoesData.formatarDataComHoraMinutoSegundo(new Date()) + "] - Cartela Nº" + cartela.getNumeroCartela() + ": numero de bolas corresponde a quantidade informada informado.");
									
								} else {
									System.out.println("[" + FuncoesData.formatarDataComHoraMinutoSegundo(new Date()) + "] - Cartela Nº" + cartela.getNumeroCartela() + ": numero de bolas nao corresponde a quantidade informada informado.");
								}
								
								cartelas.add(cartela);								
							}
						}
						
						line = in.readLine();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}				
			}
			
			System.out.println("[" + FuncoesData.formatarDataComHoraMinutoSegundo(new Date()) + "] - Cartelas importadas com sucesso.");
			FuncoesSwing.mostrarMensagemSucesso(tela, "Cartelas importadas com sucesso.");
		}		
	}
}
