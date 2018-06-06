package pore.com.bingo.controllers;

import java.awt.Window;
import java.io.File;
import java.util.Date;
import java.util.LinkedList;

import pore.com.bingo.model.cartela.Cartela;
import pore.com.bingo.model.cartela.NumeroCartela;
import pore.com.bingo.util.ValidadorUniversal;
import pore.com.bingo.util.funcoes.FuncoesData;
import pore.com.bingo.views.src.panels.RealizarSorteio_VW;

public class RealizarSorteio_Controller extends ControllerSwing {
	
	private static RealizarSorteio_VW tela;
	
	private static LinkedList<Cartela> cartelasSorteio;
	private static LinkedList<Integer> bolasChamadas;
	
	public static LinkedList<Integer> getBolasChamadas() {
		if(!ValidadorUniversal.isListaPreenchida(bolasChamadas)) {
			bolasChamadas = new LinkedList<Integer>();
		}
		
		return bolasChamadas;
	}

	public static void setBolasChamadas(LinkedList<Integer> bolasChamadas) {
		RealizarSorteio_Controller.bolasChamadas = bolasChamadas;
	}

	public static LinkedList<Cartela> getCartelasSorteio() {
		if(!ValidadorUniversal.isListaPreenchida(cartelasSorteio)) {
			cartelasSorteio = new LinkedList<Cartela>();
		}
		
		return cartelasSorteio;
	}

	public static void setCartelasSorteio(LinkedList<Cartela> cartelasSorteio) {
		RealizarSorteio_Controller.cartelasSorteio = cartelasSorteio;
	}

	public RealizarSorteio_Controller(final RealizarSorteio_VW sorteio) {
		tela = sorteio;
		
		limparCampos();	
		
		File cartelasSorteio = new File(CAMINHO_DIR_SORTEIO + File.separator + "cartelasSorteio.txt");
		
		String cartelasSorteioTxt = "";
		
		if(ValidadorUniversal.isListaPreenchida(cartelas)) {
			for(Cartela cartela: cartelas) {
				if(ValidadorUniversal.check(cartela.getPortador())) {
					getCartelasSorteio().add(cartela);
					
					cartelasSorteioTxt += "|" + cartela.getNumeroCartela() + "|" + cartela.getPortador() + "|";
					
					if(ValidadorUniversal.isListaPreenchida(cartela.getNumeros())) {
						for(NumeroCartela numero: cartela.getNumeros()) {
							cartelasSorteioTxt += numero.getNumero() + "|";
						}					
					}				
					
					cartelasSorteioTxt += "\n";
				}
			}
			
			try {
				escreverArquivoUsandoFileChannel(cartelasSorteio, cartelasSorteioTxt);
			} catch (Exception e) {
				System.out.println("[" + FuncoesData.formatarDataComHoraMinutoSegundo(new Date()) + "] - Arquivo com as cartelas do sorteio nao foi salvo na pasta.");
				e.printStackTrace();
			}
		}			
	}
	
	public void limparCampos() {
		tela.jTextFieldNumeroSorteado.setText("");
		tela.jLabelNumerosChamados.setText("");
		tela.jLabelNumeroChamado.setText("");
		
		cartelasSorteio = new LinkedList<Cartela>();
	}
	
	public Window getTela() {
		return tela;
	}
	
	public void inserirNumeroChamado() {
		if(ValidadorUniversal.check(tela.jLabelNumeroChamado.getText()) && ValidadorUniversal.isIntegerPositivo(tela.jLabelNumeroChamado.getText())) {
			tela.jLabelNumeroChamado.setEnabled(false);
			
			tela.jLabelNumeroChamado.setText(tela.jLabelNumeroChamado.getText());
			
			getBolasChamadas().add(Integer.parseInt(tela.jLabelNumeroChamado.getText()));
			
			if(ValidadorUniversal.isListaPreenchida(cartelasSorteio)) {
				for(Cartela cartela: cartelasSorteio) {
					if(ValidadorUniversal.isListaPreenchida(cartela.getNumeros())) {
						for(NumeroCartela numero: cartela.getNumeros()) {
							if(Integer.parseInt(numero.getNumero()) == Integer.parseInt(tela.jLabelNumeroChamado.getText())) {
								cartela.getNumerosChamados().add(numero);
								
								break;
							}
						}
					}
				}
				
				String txt = "";
				
				for(Cartela cartela: cartelasSorteio) {
					if(ValidadorUniversal.check(cartela.getPortador())) {
						
						txt += "|" + cartela.getNumeroCartela() + "|" + cartela.getPortador() + "|";
						
						if(ValidadorUniversal.isListaPreenchida(cartela.getNumerosChamados())) {
							for(NumeroCartela numero: cartela.getNumerosChamados()) {
								txt += numero.getNumero() + "|";
							}					
						}				
						
						txt += "\n";
					}
				}
				
				File fileBolasChamadasCartelas = new File(CAMINHO_DIR_SORTEIO + File.separator + "bolasChamadasPorCartela.txt");
				
				try {
					escreverArquivoUsandoFileChannel(fileBolasChamadasCartelas, txt);
				} catch (Exception e) {
					System.out.println("[" + FuncoesData.formatarDataComHoraMinutoSegundo(new Date()) + "] - Arquivo com as cartelas do sorteio nao foi salvo na pasta.");
					e.printStackTrace();
				}
				
				String bolasChamadasTxt = "";
				
				if(ValidadorUniversal.isListaPreenchida(bolasChamadas)) {
					for(Integer num: bolasChamadas) {
						bolasChamadasTxt += "|" + num + "|";
					}
				}
				
				File fileBolasChamadas = new File(CAMINHO_DIR_SORTEIO + File.separator + "bolasChamadas.txt");
				
				try {
					escreverArquivoUsandoFileChannel(fileBolasChamadas, bolasChamadasTxt);
				} catch (Exception e) {
					System.out.println("[" + FuncoesData.formatarDataComHoraMinutoSegundo(new Date()) + "] - Arquivo com as cartelas do sorteio nao foi salvo na pasta.");
					e.printStackTrace();
				}
				
			}		
			
			tela.jLabelNumeroChamado.setText("");
			tela.jLabelNumeroChamado.setEnabled(true);
		}
	}
	
	public void voltarNumeroChamado() {
		
	}

}
