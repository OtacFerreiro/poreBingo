package pore.com.bingo.controllers;

import java.awt.Color;
import java.awt.Window;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import pore.com.bingo.model.cartela.Cartela;
import pore.com.bingo.util.ImpressaoArquivos;
import pore.com.bingo.util.ValidadorUniversal;
import pore.com.bingo.util.funcoes.FuncoesData;
import pore.com.bingo.util.funcoes.FuncoesSwing;
import pore.com.bingo.util.funcoes.TimeUtils;
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
			Map<String, Object> parameters = new HashMap<>();
			
			for(int index = 0; index < tela.jTableListaCartelas.getRowCount(); index++) {
				boolean checked = (boolean) tela.jTableListaCartelas.getValueAt(index, 0);
				
				if(checked) {
					CellType celula = (CellType) tela.jTableListaCartelas.getValueAt(index, 1);
					
					Optional<Cartela> cartelaOpt = cartelas.stream().
						filter(cartela -> String.valueOf(cartela.getNumeroCartela()).equals(celula.getDado())).
						findFirst();
					
					if(cartelaOpt.isPresent()) {
						parameters.put(String.valueOf(celula.getDado()), cartelaOpt.get());
					}
				}
			}
			
			if(!ValidadorUniversal.isMapPreenchido(parameters)) {
				FuncoesSwing.mostrarMensagemErro(tela, "Erro", "Nao foi possivel encontrar os parametros para impressao das cartelas. Verifique se as cartelas foram importadas corretamente");
				
				return;
			}
			
			try {
				String fileName = TimeUtils.getNow("yyyy-MM-dd'T'HH:mm:ss") + "_cartelas" + ".pdf";
				String filePath = CAMINHO_DIR_IMP + File.separator + fileName;
				
				JasperReport bingoBoardJR = JasperCompileManager.compileReport(getClass().getResourceAsStream(CAMINHO_BINGO_BOARD));
				JasperPrint print = JasperFillManager.fillReport(bingoBoardJR, parameters);
				
				JasperExportManager.exportReportToPdfFile(print, filePath);
				
				ImpressaoArquivos.imprimirPDF(filePath);
				
			} catch (Exception e) {
				e.printStackTrace();
				
				FuncoesSwing.mostrarMensagemErro(tela, "Erro", "Nao foi possivel imprimir as cartelas. Verifique o log de erro.");
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

		Object dados[][] = new Object[quantidadeItens][5];
		String colunas[] = new String[]{
				"", "Nº da Cartela", "Portador", "Editar", "Remover"
		};

		for(int i = 0 ; i < quantidadeItens; i++)
		{
			dados[i][0] = Boolean.FALSE;
			dados[i][3] = Boolean.FALSE;
			dados[i][4] = Boolean.FALSE;
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
				
				if(aValue instanceof String && column == 2) {
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
				} else if(aValue instanceof Boolean && (boolean)aValue && (column == 4 || column == 5)) {
					if(column == 4) {
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
					} else if(column == 5) {						
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

		tela.jTableListaCartelas.getColumnModel().getColumn(0).setPreferredWidth(30);
		tela.jTableListaCartelas.getColumnModel().getColumn(0).setCellRenderer(new CenterAlignmentCellRenderer());
		tela.jTableListaCartelas.getColumnModel().getColumn(1).setPreferredWidth(50);
		tela.jTableListaCartelas.getColumnModel().getColumn(1).setCellRenderer(new CenterAlignmentCellRenderer());
		tela.jTableListaCartelas.getColumnModel().getColumn(2).setPreferredWidth(250);
		tela.jTableListaCartelas.getColumnModel().getColumn(2).setCellRenderer(new CenterAlignmentCellRenderer());
		tela.jTableListaCartelas.getColumnModel().getColumn(3).setPreferredWidth(30);
		tela.jTableListaCartelas.getColumnModel().getColumn(4).setPreferredWidth(30);

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
			modelo.setValueAt(celula1, i, 1);

			CellType celula2 = new CellType();
			celula2.setCor(cor);
			celula2.setDado(cartelas.get(i).getPortador());
			modelo.setValueAt(celula2, i, 2);
		}
	}
}
