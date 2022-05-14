package pore.com.bingo.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
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
import pore.com.bingo.util.funcoes.BigDecimalUtils;
import pore.com.bingo.util.funcoes.FuncoesData;

public abstract class ControllerSwing {
	
	public static final int COLUMN_B = 0;
	public static final int COLUMN_I = 1;
	public static final int COLUMN_N = 2;
	public static final int COLUMN_G = 3;
	public static final int COLUMN_O = 4;
	
	public static final int NUMERO_MAX_B = 0;
	public static final int NUMERO_MAX_I = 1;
	public static final int NUMERO_MAX_N = 2;
	public static final int NUMERO_MAX_G = 3;
	public static final int NUMERO_MAX_O = 4;
	
	public static final int MAIOR_NUMERO_CARTELA = 75;

	public static String CAMINHO_DIR_PADRAO = File.separator + "poreBingo";
	public static String CAMINHO_DIR_LOG = CAMINHO_DIR_PADRAO + File.separator + "log";
	public static String CAMINHO_DIR_SORTEIO = CAMINHO_DIR_PADRAO + File.separator + "sorteio";
	public static String CAMINHO_DIR_CARTELAS = CAMINHO_DIR_PADRAO + File.separator + "cartelas";
	public static String CAMINHO_DIR_CONFIG = CAMINHO_DIR_PADRAO + File.separator + "conf";
	public static String CAMINHO_DIR_IMP = CAMINHO_DIR_PADRAO + File.separator + "impressoes";
	
	public static String CAMINHO_BINGO_BOARD = File.separator + "assets" + File.separator + "reports" + File.separator + "bingoBoard.jrxml";

	public static int qdadeBolasPorCartela;
	
	public static boolean sorteioIniciado = false;

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

	public static boolean isSorteioIniciado() {
		return sorteioIniciado;
	}

	public static void setSorteioIniciado(boolean sorteioIniciado) {
		ControllerSwing.sorteioIniciado = sorteioIniciado;
	}

	public void inicializarPastas() {
		File dirBingo = new File(CAMINHO_DIR_PADRAO);
		File dirLog = new File(CAMINHO_DIR_LOG);
		File dirCartelas = new File(CAMINHO_DIR_CARTELAS);
		File dirSorteio = new File(CAMINHO_DIR_SORTEIO);
		File dirConfig = new File(CAMINHO_DIR_CONFIG);
		File dirImp = new File(CAMINHO_DIR_IMP);

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
		
		if(!dirImp.exists()) {
			dirImp.mkdirs();
		}
	}

	//CRIA O ARQUIVO DAS CARTELAS IMPORTADAS NO FORMATO DO SISTEMA
	public void gerarArquivoCartelasImportadas(String caminho) {		
		File cartelasImportadas = new File(caminho);

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
		
		try {
			escreverArquivoUsandoFileChannel(cartelasImportadas, cartelasImportadasTxt);
		} catch (Exception e) {
			System.out.println("[" + FuncoesData.formatarDataComHoraMinutoSegundo(new Date()) + "] - Arquivo da importacao nao foi salvo na pasta.");
			e.printStackTrace();
		}
	}
	
	//IMPORTA DO ARQUIVO EM FORMATO EXTERNO AO SISTEMA
	public void importarArquivoCartelasGenerico(File file) throws IOException {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(file));

			String line = in.readLine();

			while(ValidadorUniversal.check(line)){
				line = line.trim();

				String [] cartelaArray = line.split("  -  ");

				if(ValidadorUniversal.isArrayPreenchido(cartelaArray) && cartelaArray.length > 1) {							
					String [] numeros = cartelaArray[1].split(" ");

					if(ValidadorUniversal.isArrayPreenchido(numeros)) {
						if(BigDecimalUtils.getBigDecimal(cartelaArray[0]).compareTo(BigDecimal.ZERO) <= 0) {
							System.out.println("[" + FuncoesData.formatarDataComHoraMinutoSegundo(new Date()) + "] - Nº da cartela invalido.");
							
							continue;
						}
						
						Cartela cartela = new Cartela();
						cartela.setNumeroCartela(Integer.parseInt(cartelaArray[0]));

						for(int i = 0; i < numeros.length; i++) {
							NumeroCartela numCartela = new NumeroCartela();
							numCartela.setCartela(cartela);
							numCartela.setNumero(numeros[i]);

							cartela.getNumeros().add(numCartela);
						}

						if(cartela.getNumeros().size() == qdadeBolasPorCartela) {
							System.out.println("[" + FuncoesData.formatarDataComHoraMinutoSegundo(new Date()) + "] - Cartela Nº" + cartela.getNumeroCartela() + ": numero de bolas corresponde a quantidade informada.");

							cartelas.add(cartela);
							
						} else {
							System.out.println("[" + FuncoesData.formatarDataComHoraMinutoSegundo(new Date()) + "] - Cartela Nº" + cartela.getNumeroCartela() + ": numero de bolas nao corresponde a quantidade informada.");
						}
					}
				}

				line = in.readLine();
			}

			if(ValidadorUniversal.isListaPreenchida(cartelas)) {
				gerarArquivoCartelasImportadas(CAMINHO_DIR_CARTELAS + File.separator + "cartelasImportadas.txt");
			}
		} catch (IOException e) {
			throw e;
		}		
	}
	
	//IMPORTA AS CARTELAS PARA O SISTEMA PELO ARQUIVO NO FORMATO DO SISTEMA
	public LinkedList<Cartela> importarArquivoCartelasSistema(File file) {
		LinkedList<Cartela> cartelas = new LinkedList<Cartela>();
		
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(file));

			String line = in.readLine();

			while(ValidadorUniversal.check(line)){
				line = line.trim();

				String [] cartelaArray = line.split("\\|");

				if(ValidadorUniversal.isArrayPreenchido(cartelaArray)) {
					Cartela cartela = new Cartela();
					
					if(cartelaArray.length > 1 && ValidadorUniversal.isIntegerPositivo(cartelaArray[1])) {
						cartela.setNumeroCartela(Integer.parseInt(cartelaArray[1]));
					}
					
					if(ValidadorUniversal.check(cartelaArray[2])) {
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
		
		return cartelas;
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
