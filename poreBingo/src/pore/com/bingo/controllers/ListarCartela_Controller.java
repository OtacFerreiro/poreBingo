package pore.com.bingo.controllers;

import java.awt.Color;
import java.awt.Window;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

import pore.com.bingo.model.cartela.Cartela;
import pore.com.bingo.model.cartela.NumeroCartela;
import pore.com.bingo.util.BingoBoardPrinter;
import pore.com.bingo.util.ValidadorUniversal;
import pore.com.bingo.util.funcoes.FuncoesData;
import pore.com.bingo.util.funcoes.FuncoesSwing;
import pore.com.bingo.util.funcoes.StringUtils;
import pore.com.bingo.util.table.BooleanCellRenderer;
import pore.com.bingo.util.table.CellType;
import pore.com.bingo.util.table.CenterAlignmentCellRenderer;
import pore.com.bingo.util.table.HeaderChkBoxCellRenderer;
import pore.com.bingo.views.src.panels.EditarCartela_VW;
import pore.com.bingo.views.src.panels.ListarCartela_VW;

public class ListarCartela_Controller extends ControllerSwing {

	private static ListarCartela_VW tela;

	public ListarCartela_Controller(final ListarCartela_VW listar_VW) {
		tela = listar_VW;

		tela.jTextFieldNumero.setText("");
		tela.jTextFieldPortador.setText("");		
	}

	public Window getTela() {
		return tela;
	}

	public void pesquisarCartelas() {
		int numeroCartela = 0;
		String portador = "";

		if(ValidadorUniversal.check(tela.jTextFieldNumero.getText()) && ValidadorUniversal.isIntegerPositivo(tela.jTextFieldNumero.getText())) {
			numeroCartela = Integer.parseInt(tela.jTextFieldNumero.getText());
		}

		if(ValidadorUniversal.check(tela.jTextFieldPortador.getText())) {
			portador = tela.jTextFieldPortador.getText();
		}

		if(numeroCartela > 0 || ValidadorUniversal.check(portador)) {
			if(ValidadorUniversal.isListaPreenchida(cartelas)) {
				List<Cartela> cartelasPesquisadas = new ArrayList<Cartela>();

				for(Cartela cartela: cartelas) {
					if(numeroCartela > 0) {
						if(cartela.getNumeroCartela() == numeroCartela) {
							cartelasPesquisadas.add(cartela);

							break;						
						}
					} else if(ValidadorUniversal.check(portador) && ValidadorUniversal.check(cartela.getPortador())) {
						if(cartela.getPortador().toUpperCase().contains(portador.toUpperCase())) {
							cartelasPesquisadas.add(cartela);
						}
					}
				}

				if(!ValidadorUniversal.isListaPreenchida(cartelasPesquisadas)) {
					cartelasPesquisadas.addAll(cartelas);
				}

				preencherTabela(cartelasPesquisadas);			
			} else {
				FuncoesSwing.mostrarMensagemAtencao(tela, "O sistema nao possui cartelas importadas.");

				return;
			}
		} else {
			if(ValidadorUniversal.isListaPreenchida(cartelas)) {
				preencherTabela(cartelas);
			} else {
				FuncoesSwing.mostrarMensagemAtencao(tela, "O sistema nao possui cartelas importadas.");

				return;
			}
		}
	}

	/*
	 * FORMATO DO ARQUIVO TXT
	 * CODIGOCARTELA  -  NUMERO NUMERO NUMERO NUMERO [...] NUMERO  
	 *  */
	public void importarCartelas() {
		boolean importarCartelas = true;

		if(ValidadorUniversal.isListaPreenchida(cartelas)) {
			int resp = FuncoesSwing.mostrarMensagemSimNao(tela, "Aviso", "Uma importacao ja foi realizada no sistema. Deseja adicionar novas cartelas?");

			if(resp == FuncoesSwing.NAO) {
				importarCartelas = false;

				return;
			}
			
			cartelas = new ArrayList<Cartela>();
		}

		if(importarCartelas) {
			if(qdadeBolasPorCartela > 0) {
				int resp = FuncoesSwing.mostrarMensagemSimNao(tela, "Configuracao das Cartelas", "As cartelas importadas ja possuem uma quantidade padrão de bolas (" + qdadeBolasPorCartela + "). Deseja continuar?");

				if(resp == FuncoesSwing.NAO) {
					return;
				}
			} else {
				qdadeBolasPorCartela = FuncoesSwing.getMensagemInt(tela, "Configuração das Cartelas", "Quantas bolas cada cartela possui?");				
			}

			if(qdadeBolasPorCartela > 0) {
				JFileChooser chooser = new JFileChooser();

				int returnVal = chooser.showOpenDialog(tela);

				File file = null;

				if(returnVal == JFileChooser.APPROVE_OPTION) {     
					file = chooser.getSelectedFile();    
				}

				if(file != null) {
					try {
						importarArquivoCartelasGenerico(file);
						
					} catch (Exception e) {
						FuncoesSwing.mostrarMensagemErro(tela, "Erro", "Nao foi possivel realizar a importacao das cartelas. Verifique o erro no arquivo de log.");
					}

					File fileCartelas = new File(CAMINHO_DIR_CARTELAS + File.separator + "cartelasImportadas.txt");
					
					if(!fileCartelas.exists()) {
						System.out.println("[" + FuncoesData.formatarDataComHoraMinutoSegundo(new Date()) + "] - Cartelas nao foram importadas.");
						FuncoesSwing.mostrarMensagemErro(tela, "Erro", "Ocorreu um erro e o arquivo nao pode ser importador.");

						return;
					}
					
					System.out.println("[" + FuncoesData.formatarDataComHoraMinutoSegundo(new Date()) + "] - Cartelas importadas com sucesso.");					
					FuncoesSwing.mostrarMensagemSucesso(tela, "Cartelas importadas com sucesso.");
					
					if(ValidadorUniversal.isListaPreenchida(cartelas)) {
						preencherTabela(cartelas);
					}
				}
			}
		}		
	}

	public void removerImportacao() {
		int resp = FuncoesSwing.mostrarMensagemSimNao(tela, "Aviso", "Deseja remover todas as importacoes do sistema?");

		if(resp == FuncoesSwing.SIM) {
			cartelas = new ArrayList<Cartela>();
			qdadeBolasPorCartela = 0;

			preencherTabela(cartelas);
		}
	}

	public void imprimirCartelas() {
		if(ValidadorUniversal.isListaPreenchida(cartelas)) {
			int firstIndex = 0;
			int lastIndex = tela.jTableListaCartelas.getRowCount();
			
			for(int index = firstIndex; index < lastIndex; index++) {
				boolean checked = (boolean) tela.jTableListaCartelas.getValueAt(index, 0);
				
				if(checked) {
					doImprimirCartela(index);
				}
			}
		}
	}
	
	public void doImprimirCartela(int index) {
		CellType celula = (CellType) tela.jTableListaCartelas.getValueAt(index, 1);
		
		Optional<Cartela> cartelaOpt = cartelas.stream().
			filter(cartela -> String.valueOf(cartela.getNumeroCartela()).equals(celula.getDado())).
			findFirst();
		
		if(cartelaOpt.isPresent()) {
			Map<String, Object> parameters = new HashMap<>();
			
			Cartela cartela = cartelaOpt.get();
			parameters.put("portador", cartela.getPortador());
			parameters.put("numeroCartela", cartela.getNumeroCartela());
			
			LinkedList<NumeroCartela> numeros = cartela.getNumeros();
			
			for(int i = 0; i < 25; i++) {
				if(numeros.size() < i) {
					parameters.put("numero" + (i+1), "");
					
				} else {
					NumeroCartela numCart = numeros.get(i);
					
					String numeroValue = "";
					
					if(numCart != null) {
						numeroValue = StringUtils.getNullAsEmpty(numCart.getNumero());
					}
					
					parameters.put("numero" + (i+1), numeroValue);
				}
			}
			
			String nomeArquivo = "Cart_" + cartela.getNumeroCartela();
			
			if(StringUtils.isNotEmpty(cartela.getPortador())) {
				nomeArquivo += "_";
				nomeArquivo += cartela.getPortador().length() > 25 ? cartela.getPortador().substring(0, 25) : cartela.getPortador();
			}
			
			nomeArquivo += ".pdf";
			
			BingoBoardPrinter printer = new BingoBoardPrinter();
			printer.setParameters(parameters);
			printer.setNomeArquivo(nomeArquivo);
			
			Thread t = new Thread(printer);
			t.start();
			
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void removerPortador() {
		boolean stopFirst = false;
		
		List<Integer> cartelasChecked = getCartelasChecked(stopFirst);
		
		if(ValidadorUniversal.isListaPreenchida(cartelasChecked)) {
			String msg = "Realmente deseja remover o portador da cartela selecionada?";
			
			if(cartelasChecked.size() > 1) {
				msg = "Realmente deseja remover o portador das cartelas selecionadas?";
			}
			
			int resp = FuncoesSwing.mostrarMensagemSimNao(tela, "Remover Cartela", msg);
			
			if(resp == FuncoesSwing.SIM) {
				for(Integer row: cartelasChecked) {
					CellType celulaPortador = (CellType) tela.jTableListaCartelas.getValueAt(row, 2);

					String portador = celulaPortador.getDado();

					if(ValidadorUniversal.check(portador)) {
						CellType celulaNumCart = (CellType) tela.jTableListaCartelas.getValueAt(row, 1);

						String numeroCartela = celulaNumCart.getDado();

						if(ValidadorUniversal.isListaPreenchida(cartelas)) {
							for(Cartela cartela: cartelas) {
								if(cartela.getNumeroCartela() == Integer.parseInt(numeroCartela)) {
									cartela.setPortador("");

									break;
								}
							}
						}
					}
				}
				
				desmarcarCartelas();
				
				gerarArquivoCartelasImportadas(CAMINHO_DIR_CARTELAS + File.separator + "cartelas.txt");

				preencherTabela(cartelas);
			}
		}
	}
	
	public List<Integer> getCartelasChecked(boolean stopFirst) {
		List<Integer> cartelasChecked = new LinkedList<Integer>();
		
		for(int index = 0; index < tela.jTableListaCartelas.getRowCount(); index++) {
			boolean checked = (boolean) tela.jTableListaCartelas.getValueAt(index, 0);
			
			if(checked) {
				cartelasChecked.add(index);
				
				if(stopFirst) {
					break;
				}
			}
		}
		
		return cartelasChecked;
	}
	
	public void desmarcarCartelas() {
		for(int index = 0; index < tela.jTableListaCartelas.getRowCount(); index++) {
			tela.jTableListaCartelas.setValueAt(false, index, 0);
		}
	}
	
	public void editarCartela() {
		boolean stopFirst = true;
		
		List<Integer> cartelasChecked = getCartelasChecked(stopFirst);
		
		for(Integer row: cartelasChecked) {
			CellType celula = (CellType) tela.jTableListaCartelas.getValueAt(row, 1);

			String numeroCartela = celula.getDado();

			if(ValidadorUniversal.check(numeroCartela)) {								
				Cartela cartela = null;

				if(ValidadorUniversal.isListaPreenchida(cartelas)) {
					for(Cartela cartelaCadastrada: cartelas) {
						if(cartelaCadastrada.getNumeroCartela() == Integer.parseInt(numeroCartela)) {
							cartela = cartelaCadastrada;
							break;
						}
					}

					if(cartela != null) {
						EditarCartela_VW editarCartela = new EditarCartela_VW(tela, true);
						editarCartela.controller.setCartelaEditada(cartela);
						editarCartela.setVisible(true);

						while(editarCartela.isVisible()) {};

						preencherTabela(cartelas);
					}
				}		
			}
		}
	}
	
	@SuppressWarnings("serial")
	public void preencherTabela(List<Cartela> cartelas) {

		int quantidadeItens = 0;
		int quantidadePortador = 0;

		if(ValidadorUniversal.isListaPreenchida(cartelas)) {
			quantidadeItens = cartelas.size();
			
			for(Cartela cartela: cartelas) {
				if(ValidadorUniversal.check(cartela.getPortador())) {
					quantidadePortador++;
				}
			}
		}

		tela.jLabelValorTotal.setText(String.valueOf(quantidadeItens));
		tela.jLabelNumeroPortador.setText(String.valueOf(quantidadePortador));
		
		Object dados[][] = new Object[quantidadeItens][3];
		Object colunas[] = new Object[]{
				"", "Nº da Cartela", "Portador"
		};

		for(int i = 0 ; i < quantidadeItens; i++)
		{
			dados[i][0] = Boolean.FALSE;
		}

		DefaultTableModel modelo = new DefaultTableModel(dados, colunas){
			boolean[] canEdit = new boolean [] {
					true, false, true, true, true
			};
			@SuppressWarnings("rawtypes")
			Class[] types = new Class [] {
					Boolean.class, String.class, String.class, Boolean.class, Boolean.class
			};

			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return types [columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit [columnIndex];
			}

			@Override
			public void setValueAt(Object aValue, int row, int column) {
				super.setValueAt(aValue, row, column);
				
				try {
					if(aValue instanceof String && column == 2) {
						CellType celula = (CellType) tela.jTableListaCartelas.getValueAt(row, 1);

						String numeroCartela = celula.getDado();

						if(ValidadorUniversal.check(numeroCartela)) {								
							Cartela cartela = null;

							if(ValidadorUniversal.isListaPreenchida(cartelas)) {
								for(Cartela cartelaCadastrada: cartelas) {
									if(cartelaCadastrada.getNumeroCartela() == Integer.parseInt(numeroCartela)) {
										cartela = cartelaCadastrada;
										break;
									}
								}

								if(cartela != null) {
									if(ValidadorUniversal.check((String)aValue)) {
										cartela.setPortador((String)aValue);
									} else {
										cartela.setPortador("");									
									}

									gerarArquivoCartelasImportadas(CAMINHO_DIR_CARTELAS + File.separator + "cartelas.txt");

									preencherTabela(cartelas);
								}
							}
						}						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};	 

		tela.jTableListaCartelas.setModel(modelo);
		tela.jTableListaCartelas.setRowHeight(50);
		
		tela.jTableListaCartelas.getTableHeader().setResizingAllowed(true);
		
		tela.jTableListaCartelas.getColumnModel().getColumn(0).setHeaderRenderer(new HeaderChkBoxCellRenderer(tela.jTableListaCartelas.getTableHeader()));
		
		tela.jTableListaCartelas.getColumnModel().getColumn(0).setPreferredWidth(50);
		tela.jTableListaCartelas.getColumnModel().getColumn(0).setMaxWidth(50);
		tela.jTableListaCartelas.getColumnModel().getColumn(0).setCellRenderer(new BooleanCellRenderer());
		tela.jTableListaCartelas.getColumnModel().getColumn(1).setPreferredWidth(80);
		tela.jTableListaCartelas.getColumnModel().getColumn(1).setMaxWidth(80);
		tela.jTableListaCartelas.getColumnModel().getColumn(1).setCellRenderer(new CenterAlignmentCellRenderer());
		tela.jTableListaCartelas.getColumnModel().getColumn(2).setPreferredWidth(250);
		tela.jTableListaCartelas.getColumnModel().getColumn(2).setCellRenderer(new CenterAlignmentCellRenderer());

		CellType celula = new CellType();
		
		for(int i = 0 ; i < quantidadeItens; i++)
		{
			Color cor = new Color(255,255,255);

			if(ValidadorUniversal.check(cartelas.get(i).getPortador())){
				cor = new Color(160,214,189);
			} else {
				cor = new Color(255,255,120);
			}
			
			celula = new CellType();
			celula.setCor(cor);
			celula.setDado(String.valueOf(cartelas.get(i).getNumeroCartela()));
			modelo.setValueAt(celula, i, 1);

			celula = new CellType();
			celula.setCor(cor);
			celula.setDado(cartelas.get(i).getPortador());
			modelo.setValueAt(celula, i, 2);
		}
	}
	
}
