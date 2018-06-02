package pore.com.bingo.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import pore.com.bingo.model.cartela.Cartela;
import pore.com.bingo.model.cartela.NumeroCartela;
import pore.com.bingo.util.ValidadorUniversal;
import pore.com.bingo.util.funcoes.FuncoesData;

public abstract class ControllerSwing {

	public static String CAMINHO_DIR_PADRAO = File.separator + "poreBingo";
	public static String CAMINHO_DIR_LOG = CAMINHO_DIR_PADRAO + File.separator + "log";
	public static String CAMINHO_DIR_SORTEIO = CAMINHO_DIR_PADRAO + File.separator + "sorteio";
	public static String CAMINHO_DIR_CARTELAS = CAMINHO_DIR_PADRAO + File.separator + "cartelas";
	public static String CAMINHO_DIR_CONFIG = CAMINHO_DIR_PADRAO + File.separator + "conf";

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
		File dirConfig = new File(CAMINHO_DIR_CONFIG);

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

		if(!dirConfig.exists()) {
			dirConfig.mkdirs();
		}
	}

	//CRIA O ARQUIVO DAS CARTELAS IMPORTADAS NO FORMATO DO SISTEMA
	public void gerarArquivoCartelasImportadas(String caminho) {		
		String cartelasImportadasTxt = "";
		
		if(ValidadorUniversal.isListaPreenchida(cartelas)) {
			for(Cartela cartela: cartelas) {
				cartelasImportadasTxt += "|" + cartela.getNumeroCartela() + "|" + (ValidadorUniversal.check(cartela.getPortador()) ? cartela.getPortador() : "" ) + "|";
				
				if(ValidadorUniversal.isListaPreenchida(cartela.getNumeros())) {
					for(NumeroCartela numero: cartela.getNumeros()) {
						cartelasImportadasTxt += numero.getNumero() + "|";
					}					
				}				
				
				cartelasImportadasTxt += "\n";
			}
		}

		File cartelasImportadas = new File(caminho);
		
		try {
			escreverArquivoUsandoFileChannel(cartelasImportadas, cartelasImportadasTxt);
		} catch (Exception e) {
			System.out.println("[" + FuncoesData.formatarDataComHoraMinutoSegundo(new Date()) + "] - Arquivo da importacao nao foi salvo na pasta.");
			e.printStackTrace();
		}
	}
	
	//IMPORTA DO ARQUIVO EM FORMATO EXTERNO AO SISTEMA
	public void importarArquivoCartelasGenerico(File file) {
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

			if(ValidadorUniversal.isListaPreenchida(cartelas)) {
				gerarArquivoCartelasImportadas(CAMINHO_DIR_CARTELAS + File.separator + "cartelasImportadas.txt");
				
				File fileCartelas = new File(CAMINHO_DIR_CARTELAS + File.separator + "cartelasImportadas.txt");
				
				if(fileCartelas.exists()) {
					System.out.println("[" + FuncoesData.formatarDataComHoraMinutoSegundo(new Date()) + "] - Cartelas importadas com sucesso.");					
				}			
				
			} else {
				System.out.println("[" + FuncoesData.formatarDataComHoraMinutoSegundo(new Date()) + "] - Cartelas nao foram importadas.");
			}					
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	//IMPORTA AS CARTELAS PARA O SISTEMA PELO ARQUIVO NO FORMATO DO SISTEMA
	public void importarArquivoCartelasSistema(File file) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(file));

			String line = in.readLine();

			while(ValidadorUniversal.check(line)){
				line = line.trim();

				String [] cartelaArray = line.split("\\|");

				if(ValidadorUniversal.isArrayPreenchido(cartelaArray)) {
					Cartela cartela = new Cartela();
					
					if(ValidadorUniversal.isIntegerPositivo(cartelaArray[1])) {
						cartela.setNumeroCartela(Integer.parseInt(cartelaArray[1]));
					}
					
					if(cartelaArray.length > 1 && ValidadorUniversal.isIntegerPositivo(cartelaArray[2])) {
						cartela.setPortador(cartelaArray[2]);
					} else {
						cartela.setPortador("");
					}
					
					LinkedList<NumeroCartela> numeros = new LinkedList<NumeroCartela>();

					if(cartelaArray.length > 3) {						
						for(int i = 3; i < cartelaArray.length; i++) {
							NumeroCartela numero = new NumeroCartela();
							numero.setNumero(cartelaArray[i]);
							
							numeros.add(numero);
						}						
					}
					
					cartela.setNumeros(numeros);

					cartelas.add(cartela);
				}

				line = in.readLine();
			}

			if(ValidadorUniversal.isListaPreenchida(cartelas)) {
				qdadeBolasPorCartela = cartelas.get(0).getNumeros().size();
				
				System.out.println("[" + FuncoesData.formatarDataComHoraMinutoSegundo(new Date()) + "] - Cartelas importadas com sucesso.");
				
			} else {
				System.out.println("[" + FuncoesData.formatarDataComHoraMinutoSegundo(new Date()) + "] - Cartelas nao foram importadas.");
			}					
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	// CONVERTENDO PARA UTF-8 (PARA GARANTIR) POIS O ENCODING DIFERENTE DO SISTEMA NAO PERMITIRA QUE O ARQUIVO SEJA LIDO CORRETAMENTE PELO MFE, MESMO COLOCANDO O ENCODING NO PROPRIO XML
	@SuppressWarnings("resource")
	public static void escreverArquivoUsandoFileChannel(File file, String texto) throws Exception{
		try {
			FileChannel wChannel = new FileOutputStream(file, false).getChannel();
			ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(texto);
			wChannel.write(byteBuffer);
			wChannel.close();
			
		} catch (IOException e) {
			System.out.println("[" + FuncoesData.formatarDataComHoraMinutoSegundo(new Date()) + "] - Error.");
			e.printStackTrace();
			throw new Exception("Erro de processamento de Entrada e Sa\u00EDda!");
		}
	}
}
