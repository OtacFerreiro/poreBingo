package pore.com.bingo.controllers;

import java.awt.Color;
import java.awt.Window;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

import pore.com.bingo.model.cartela.Cartela;
import pore.com.bingo.util.ValidadorUniversal;
import pore.com.bingo.util.funcoes.FuncoesData;
import pore.com.bingo.util.funcoes.FuncoesSwing;
import pore.com.bingo.util.table.CellType;
import pore.com.bingo.util.table.CenterAlignmentCellRenderer;
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
						if(cartela.getPortador().contains(portador)) {
							cartelasPesquisadas.add(cartela);

							break;								
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
					importarArquivoCartelasGenerico(file);

					if(ValidadorUniversal.isListaPreenchida(cartelas)) {
						gerarArquivoCartelasImportadas(CAMINHO_DIR_CARTELAS + File.separator + "cartelasImportadas.txt");

						System.out.println("[" + FuncoesData.formatarDataComHoraMinutoSegundo(new Date()) + "] - Cartelas importadas com sucesso.");
						FuncoesSwing.mostrarMensagemSucesso(tela, "Cartelas importadas com sucesso.");

						preencherTabela(cartelas);
					} else {
						System.out.println("[" + FuncoesData.formatarDataComHoraMinutoSegundo(new Date()) + "] - Cartelas nao foram importadas.");
						FuncoesSwing.mostrarMensagemErro(tela, "Erro", "Ocorreu um erro e o arquivo nao pode ser importador.");
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

	@SuppressWarnings("serial")
	public void preencherTabela(List<Cartela> cartelas) {

		int quantidadeItens = 0;

		if(ValidadorUniversal.isListaPreenchida(cartelas)) {
			quantidadeItens = cartelas.size();
		}

		tela.jLabelValorTotal.setText(String.valueOf(quantidadeItens));

		Object dados[][] = new Object[quantidadeItens][4];
		String colunas[] = new String[]{
				"Nº da Cartela", "Portador", "Editar", "Remover"
		};

		for(int i = 0 ; i < quantidadeItens; i++)
		{
			dados[i][2] = Boolean.FALSE;
			dados[i][3] = Boolean.FALSE;
		}

		DefaultTableModel modelo = new DefaultTableModel(dados, colunas){
			boolean[] canEdit = new boolean [] {
					false, true, true, true
			};
			@SuppressWarnings("rawtypes")
			Class[] types = new Class [] {
					String.class, String.class, Boolean.class, Boolean.class
			};

			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return types [columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit [columnIndex];
			}

			@SuppressWarnings("static-access")
			@Override
			public void setValueAt(Object aValue, int row, int column) {
				super.setValueAt(aValue, row, column);
				
				if(aValue instanceof String && ValidadorUniversal.check((String)aValue) && column == 1) {
					CellType celula = (CellType) tela.jTableListaCartelas.getValueAt(row, 0);
					
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
								cartela.setPortador((String)aValue);
								
								gerarArquivoCartelasImportadas(CAMINHO_DIR_CARTELAS + File.separator + "cartelas.txt");

								preencherTabela(cartelas);
							}
						}
					}						
				} else if(aValue instanceof Boolean && (boolean)aValue && (column == 2 || column == 3)) {
					if(column == 2) {
						CellType celula = (CellType) tela.jTableListaCartelas.getValueAt(row, 0);

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
									tela.jTableListaCartelas.setValueAt(false, row, column);

									EditarCartela_VW editarCartela = new EditarCartela_VW(tela, true);
									editarCartela.controller.setCartelaEditada(cartela);
									editarCartela.setVisible(true);

									while(editarCartela.isVisible()) {};

									preencherTabela(cartelas);
								}
							}		
						}
					} else if(column == 3) {						
						CellType celulaPortador = (CellType) tela.jTableListaCartelas.getValueAt(row, 1);
						
						String portador = celulaPortador.getDado();
						
						if(ValidadorUniversal.check(portador)) {
							int resp = FuncoesSwing.mostrarMensagemSimNao(tela, "Remover Cartela", "Realmente deseja remover o portador da cartela?");

							if(resp == FuncoesSwing.SIM) {
								CellType celula = (CellType) tela.jTableListaCartelas.getValueAt(row, 0);

								String numeroCartela = celula.getDado();

								if(ValidadorUniversal.isListaPreenchida(cartelas)) {
									for(Cartela cartela: cartelas) {
										if(cartela.getNumeroCartela() == Integer.parseInt(numeroCartela)) {
											cartela.setPortador("");

											tela.jTableListaCartelas.setValueAt(false, row, column);

											gerarArquivoCartelasImportadas(CAMINHO_DIR_CARTELAS + File.separator + "cartelas.txt");

											preencherTabela(cartelas);

											break;
										}
									}
								}
							}
						} else {
							tela.jTableListaCartelas.setValueAt(false, row, column);
						}						
					}
				}
			}
		};	 

		tela.jTableListaCartelas.setModel(modelo);

		tela.jTableListaCartelas.getColumnModel().getColumn(0).setPreferredWidth(50);
		tela.jTableListaCartelas.getColumnModel().getColumn(0).setCellRenderer(new CenterAlignmentCellRenderer());
		tela.jTableListaCartelas.getColumnModel().getColumn(1).setPreferredWidth(250);
		tela.jTableListaCartelas.getColumnModel().getColumn(1).setCellRenderer(new CenterAlignmentCellRenderer());
		tela.jTableListaCartelas.getColumnModel().getColumn(2).setPreferredWidth(30);
		tela.jTableListaCartelas.getColumnModel().getColumn(3).setPreferredWidth(30);

		for(int i = 0 ; i < quantidadeItens; i++)
		{
			Color cor = new Color(255,255,255);

			if(ValidadorUniversal.check(cartelas.get(i).getPortador())){
				cor = new Color(160,214,189);
			} else {
				cor = new Color(255,255,120);
			}

			CellType celula1 = new CellType();
			celula1.setCor(cor);
			celula1.setDado(String.valueOf(cartelas.get(i).getNumeroCartela()));
			modelo.setValueAt(celula1, i, 0);

			CellType celula2 = new CellType();
			celula2.setCor(cor);
			celula2.setDado(cartelas.get(i).getPortador());
			modelo.setValueAt(celula2, i, 1);
		}
	}
}
