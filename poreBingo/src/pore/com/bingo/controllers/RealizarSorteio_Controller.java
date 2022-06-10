package pore.com.bingo.controllers;

import java.awt.Window;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
		
		carregarArquivosSorteio();
		atualizarEstatisticas();
		
		if(ValidadorUniversal.isListaPreenchida(getBolasChamadas())) {
			tela.jLabelNumeroChamado.setText(String.valueOf(getBolasChamadas().get(getBolasChamadas().size() - 1)));
		}
		
		tela.jLabelTotalCartelasSorteio.setText(String.valueOf(getCartelasSorteio().size()));
		
	}
	
	public void limparCampos() {
		tela.jTextFieldNumeroSorteado.setText("");
		tela.jTextFieldNumeroSorteado.requestFocus();
		tela.jTextAreaNumerosChamados.setText("");
		tela.jLabelNumeroChamado.setText("");
		
		cartelasSorteio = new LinkedList<Cartela>();
		bolasChamadas = new LinkedList<Integer>();
	}
	
	public Window getTela() {
		return tela;
	}
	
	public void inserirNumeroChamado() {
		if(ValidadorUniversal.check(tela.jTextFieldNumeroSorteado.getText()) && ValidadorUniversal.isIntegerPositivo(tela.jTextFieldNumeroSorteado.getText())
				&& Integer.parseInt(tela.jTextFieldNumeroSorteado.getText()) < (MAIOR_NUMERO_CARTELA + 1)	) {			
			for(Integer numeroChamado: getBolasChamadas()) {
				if(numeroChamado == Integer.parseInt(tela.jTextFieldNumeroSorteado.getText())) {
					tela.jTextFieldNumeroSorteado.setText("");
					
					return;
				}
			}
			
			tela.jTextFieldNumeroSorteado.setEnabled(false);			
			
			getBolasChamadas().add(Integer.parseInt(tela.jTextFieldNumeroSorteado.getText()));
			
			File fileBolasChamadas = new File(CAMINHO_DIR_SORTEIO + File.separator + "bolasChamadas.txt");
			gerarArquivoBolasChamadas(fileBolasChamadas);
			
			File fileBolasChamadasCartelas = new File(CAMINHO_DIR_SORTEIO + File.separator + "bolasChamadasPorCartela.txt");
			gerarArquivoBolasChamadasPorCartela(fileBolasChamadasCartelas);								
			
			atualizarEstatisticas();
			
			tela.jTextFieldNumeroSorteado.setText("");
			tela.jTextFieldNumeroSorteado.setEnabled(true);
			tela.jTextFieldNumeroSorteado.requestFocus();
		} else {
			tela.jTextFieldNumeroSorteado.setText("");
			tela.jTextFieldNumeroSorteado.requestFocus();
		}
	}
	
	public void voltarNumeroChamado() {
		String ultimaBolaChamada = tela.jLabelNumeroChamado.getText();
		
		if(ValidadorUniversal.isListaPreenchida(bolasChamadas)) {			
			for(int i = 0; i < bolasChamadas.size(); i++) {
				if(Integer.parseInt(ultimaBolaChamada) == bolasChamadas.get(i)) {
					bolasChamadas.remove(i);

					File fileBolasChamadasCartelas = new File(CAMINHO_DIR_SORTEIO + File.separator + "bolasChamadasPorCartela.txt");
					gerarArquivoBolasChamadasPorCartela(fileBolasChamadasCartelas);		
					
					File fileBolasChamadas = new File(CAMINHO_DIR_SORTEIO + File.separator + "bolasChamadas.txt");
					gerarArquivoBolasChamadas(fileBolasChamadas);

					atualizarEstatisticas();
					
					break;
				}
			}					
		}
	}
	
	public void gerarArquivoBolasChamadas(File file) {
		String bolasChamadasTxt = "";
		
		if(ValidadorUniversal.isListaPreenchida(bolasChamadas)) {
			for(int i = 0; i < bolasChamadas.size(); i++) {
				bolasChamadasTxt += String.valueOf(bolasChamadas.get(i));
				
				if(i < bolasChamadas.size() - 1) {
					bolasChamadasTxt += ",";
				}
			}
		}				
		
		try {
			escreverArquivoUsandoFileChannel(file, bolasChamadasTxt);
		} catch (Exception e) {
			System.out.println("[" + FuncoesData.formatarDataComHoraMinutoSegundo(new Date()) + "] - Arquivo com as cartelas do sorteio nao foi salvo na pasta.");
			e.printStackTrace();
		}
	}
	
	public void gerarArquivoBolasChamadasPorCartela(File file) {
		LinkedList<Cartela> cartelasSorteios = getCartelasSorteio();
		
		//SE TIVER BOLAS CHAMADAS
		if(ValidadorUniversal.isListaPreenchida(getBolasChamadas())) {
			
			//SE TIVER CARTELAS NO SORTEIO
			if(ValidadorUniversal.isListaPreenchida(cartelasSorteios)) {
				
				for(Cartela cartela: cartelasSorteios) {
					cartela.setNumerosChamados(new LinkedList<NumeroCartela>());
				}
				
				//PEGAR A BOLA CHAMADA
				for(Integer bolaChamada: getBolasChamadas()) {
					
					//PEGAR A CARTELA
					for(Cartela cartela: cartelasSorteios) {
						
						//SE A CARTELA TIVER BOLAS
						if(ValidadorUniversal.isListaPreenchida(cartela.getNumeros())) {
							
							//PEGAR A BOLA
							for(NumeroCartela numeroCartela: cartela.getNumeros()) {
								
								//SE A CARTELA TIVER A BOLA CHAMADA
								if(Integer.parseInt(numeroCartela.getNumero()) == bolaChamada) {									
									NumeroCartela numero = new NumeroCartela();
									numero.setNumero(String.valueOf(bolaChamada));
									
									cartela.getNumerosChamados().add(numero);
								}
							}
						}						
					}
				}				
			}
		} else {
			for(Cartela cartela: cartelasSorteios) {
				cartela.setNumerosChamados(new LinkedList<NumeroCartela>());
			}
		}
		
		String txt = "";
		
		int count = 1;
		
		for(Cartela cartela: cartelasSorteios) {
			if(ValidadorUniversal.check(cartela.getPortador())) {
				
				txt += "|" + cartela.getNumeroCartela() + "|" + cartela.getPortador() + "|";
				
				if(ValidadorUniversal.isListaPreenchida(cartela.getNumerosChamados())) {
					for(NumeroCartela numero: cartela.getNumerosChamados()) {
						txt += numero.getNumero() + "|";
					}
					
					if(cartela.getNumerosChamados().size() >= 25) {
						txt += " B I N G O | Nº" + count;
						
						count++;
					}
				}
				
				txt += "\n";
			}
		}				
		
		try {
			escreverArquivoUsandoFileChannel(file, txt);
		} catch (Exception e) {
			System.out.println("[" + FuncoesData.formatarDataComHoraMinutoSegundo(new Date()) + "] - Arquivo com as cartelas do sorteio nao foi salvo na pasta.");
			e.printStackTrace();
		}
	}
	
	public void carregarArquivosSorteio() {
		boolean temBolasChamadas = false;
		boolean temCartelasSorteio = false;
		
		File fileBolasChamadas = new File(CAMINHO_DIR_SORTEIO + File.separator + "bolasChamadas.txt");
		
		if(fileBolasChamadas.exists()) {
			temBolasChamadas = true;
			setSorteioIniciado(true);
			
			BufferedReader in = null;
			try {
				in = new BufferedReader(new FileReader(fileBolasChamadas));

				String line = in.readLine();

				while(ValidadorUniversal.check(line)){
					line = line.trim();

					String [] cartelaArray = line.split(",");

					if(ValidadorUniversal.isArrayPreenchido(cartelaArray)) {
						for(String numeroChamado: cartelaArray) {
							if(ValidadorUniversal.isListaPreenchida(getBolasChamadas())) {
								boolean bolaAdicionada = false;
								
								for(Integer bolaChamada: getBolasChamadas()) {
									if(bolaChamada == Integer.parseInt(numeroChamado)) {
										bolaAdicionada = true;
										
										break;
									}
								}
								
								if(!bolaAdicionada) {
									getBolasChamadas().add(Integer.parseInt(numeroChamado));
								}
							} else {
								getBolasChamadas().add(Integer.parseInt(numeroChamado));								
							}
						}
					}

					line = in.readLine();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		File cartelasSorteio = new File(CAMINHO_DIR_SORTEIO + File.separator + "cartelasSorteio.txt");
		
		if(cartelasSorteio.exists()) {
			setCartelasSorteio(importarArquivoCartelasSistema(cartelasSorteio));
			temCartelasSorteio = true;						
			
		} else {
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
		
		if(temBolasChamadas && temCartelasSorteio) {
			File bolasChamadasPorCartela = new File(CAMINHO_DIR_SORTEIO + File.separator + "bolasChamadasPorCartela.txt");
			gerarArquivoBolasChamadasPorCartela(bolasChamadasPorCartela);
		}
	}
	
	public void exibirNumerosChamados() {
		if(ValidadorUniversal.isListaPreenchida(getBolasChamadas())) {
			String bolasChamadas = "";
			
			for(int i = 0; i < getBolasChamadas().size(); i++) {
				bolasChamadas += String.valueOf(getBolasChamadas().get(i));
				
				if(i < getBolasChamadas().size() - 1) {
					bolasChamadas += ", ";
				}
			}
			
			tela.jTextAreaNumerosChamados.setText(bolasChamadas);
		} else {
			tela.jTextAreaNumerosChamados.setText("");
		}
	}
	
	public void atualizarEstatisticas() {
		if(ValidadorUniversal.isListaPreenchida(getBolasChamadas())) {
			tela.jLabelNumeroChamado.setText(String.valueOf(getBolasChamadas().get(getBolasChamadas().size() - 1)));
		} else {
			tela.jLabelNumeroChamado.setText("");
		}
		
		exibirNumerosChamados();
		
		tela.jLabelTotalBolasChamadas.setText(String.valueOf(getBolasChamadas().size()));
		tela.jLabelTotalRestante.setText(String.valueOf(MAIOR_NUMERO_CARTELA - getBolasChamadas().size()));		
	}

}
