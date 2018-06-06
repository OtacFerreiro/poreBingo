package pore.com.bingo.controllers;

import java.awt.Color;
import java.awt.Window;
import java.io.File;

import javax.swing.table.DefaultTableModel;

import pore.com.bingo.model.cartela.Cartela;
import pore.com.bingo.util.ValidadorUniversal;
import pore.com.bingo.util.funcoes.FuncoesSwing;
import pore.com.bingo.util.table.CellType;
import pore.com.bingo.util.table.CenterAlignmentCellRenderer;
import pore.com.bingo.views.src.panels.EditarCartela_VW;

public class EditarCartela_Controller extends ControllerSwing {

	private static EditarCartela_VW tela;

	public static Cartela cartelaEditada;

	public static Cartela getCartelaEditada() {
		return cartelaEditada;
	}

	public static void setCartelaEditada(Cartela cartelaEditada) {

		if(cartelaEditada != null) {
			EditarCartela_Controller.cartelaEditada = cartelaEditada;

			tela.jTextFieldNumero.setText(String.valueOf(cartelaEditada.getNumeroCartela()));
			tela.jTextFieldPortador.setText(cartelaEditada.getPortador());

			preencherTabela(getCartelaEditada());
		}		
	}

	public EditarCartela_Controller(final EditarCartela_VW editar_VW) {
		tela = editar_VW;

		limparCampos();
	}

	public Window getTela() {
		return tela;
	}

	public void limparCampos() {
		tela.jTextFieldNumero.setText("");
		tela.jTextFieldPortador.setText("");

		cartelaEditada = new Cartela();
	}

	public void confirmar() {
		if(!ValidadorUniversal.check(tela.jTextFieldNumero.getText())) {
			FuncoesSwing.mostrarMensagemErro(tela, "Erro", "O numero da cartela é um campo obrigatorio.");

			return;
		}

		if(ValidadorUniversal.isListaPreenchida(cartelas)) {
			boolean cartelaAlterada = false;

			for(Cartela cartela: cartelas) {

				if(cartela.getNumeroCartela() == Integer.parseInt(tela.jTextFieldNumero.getText())) {	
					cartelaAlterada = true;

					cartela.setPortador(tela.jTextFieldPortador.getText());

					gerarArquivoCartelasImportadas(CAMINHO_DIR_CARTELAS + File.separator + "cartelas.txt");

					FuncoesSwing.mostrarMensagemSucesso(tela, "Cartela alterada com sucesso");

					break;
				}			
			}

			if(!cartelaAlterada) {
				FuncoesSwing.mostrarMensagemErro(tela, "Erro", "Nao foi possivel encontrar a cartela com a numeracao selecionada.");

				return;
			}

			tela.dispose();
		}	
	}

	@SuppressWarnings("serial")
	public static void preencherTabela(Cartela cartela) {

		int numeroLinhas = 0;

		if(cartela != null && ValidadorUniversal.isListaPreenchida(cartela.getNumeros())) {
			int totalNumeros = cartela.getNumeros().size();			

			while(totalNumeros > 0) {
				totalNumeros -= 5;

				numeroLinhas++;
			}
		}

		Object dados[][] = new Object[numeroLinhas][5];
		String colunas[] = new String[]{
				"B", "I", "N", "G", "O"
		};

		DefaultTableModel modelo = new DefaultTableModel(dados, colunas){
			boolean[] canEdit = new boolean [] {
					false, false, false, false, false
			};
			@SuppressWarnings("rawtypes")
			Class[] types = new Class [] {
					String.class, String.class, String.class, String.class, String.class
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

				//EDITAR AS BOLAS DA CARTELA
				/*if(aValue instanceof CellType) {
					CellType cell = (CellType)aValue;

					if(ValidadorUniversal.isIntegerPositivo(cell.getDado())) {
						super.setValueAt(aValue, row, column);						

					} else {
						FuncoesSwing.mostrarMensagemAtencao(tela, "Numero inserido nao é valido.");

						super.setValueAt("", row, column);
					}						
				} else if(aValue instanceof String) {
					if(ValidadorUniversal.isIntegerPositivo((String)aValue)) {
						super.setValueAt(aValue, row, column);						

					} else {
						FuncoesSwing.mostrarMensagemAtencao(tela, "Numero inserido nao é valido.");

						super.setValueAt("", row, column);
					}
				}*/
			}
		};	 

		tela.jTableNumeros.setModel(modelo);

		tela.jTableNumeros.getColumnModel().getColumn(0).setPreferredWidth(100);
		tela.jTableNumeros.getColumnModel().getColumn(0).setCellRenderer(new CenterAlignmentCellRenderer());
		tela.jTableNumeros.getColumnModel().getColumn(1).setPreferredWidth(100);
		tela.jTableNumeros.getColumnModel().getColumn(1).setCellRenderer(new CenterAlignmentCellRenderer());
		tela.jTableNumeros.getColumnModel().getColumn(2).setPreferredWidth(100);
		tela.jTableNumeros.getColumnModel().getColumn(2).setCellRenderer(new CenterAlignmentCellRenderer());
		tela.jTableNumeros.getColumnModel().getColumn(3).setPreferredWidth(100);
		tela.jTableNumeros.getColumnModel().getColumn(3).setCellRenderer(new CenterAlignmentCellRenderer());
		tela.jTableNumeros.getColumnModel().getColumn(4).setPreferredWidth(100);
		tela.jTableNumeros.getColumnModel().getColumn(4).setCellRenderer(new CenterAlignmentCellRenderer());

		if(cartela != null && ValidadorUniversal.isListaPreenchida(cartela.getNumeros())) {
			int qdadeBolasB = 0;
			int qdadeBolasI = 0;
			int qdadeBolasN = 0;
			int qdadeBolasG = 0;
			int qdadeBolasO = 0;
			
			int numeroLinhaB = 0;
			int numeroLinhaI = 0;
			int numeroLinhaN = 0;
			int numeroLinhaG = 0;
			int numeroLinhaO = 0;
			
			CellType celula = null;
			
			for(int i = 0; i < cartela.getNumeros().size(); i++)
			{
				Color cor = new Color(160,214,189);

				celula = new CellType();
				celula.setCor(cor);

				if(ValidadorUniversal.check(cartela.getNumeros().get(i).getNumero())) {
					celula.setDado(cartela.getNumeros().get(i).getNumero());

				} else {
					celula.setDado("");
				}				

				if(Integer.parseInt(cartela.getNumeros().get(i).getNumero()) < 16) {					
					if(qdadeBolasB < 5) {
						qdadeBolasB++;
						
						modelo.setValueAt(celula, numeroLinhaB++, COLUMN_B);
					}
				} else if(Integer.parseInt(cartela.getNumeros().get(i).getNumero()) > 15 && Integer.parseInt(cartela.getNumeros().get(i).getNumero()) < 31) {
					if(qdadeBolasI < 5) {
						qdadeBolasI++;
						
						modelo.setValueAt(celula, numeroLinhaI++, COLUMN_I);
					}
				} else if(Integer.parseInt(cartela.getNumeros().get(i).getNumero()) > 30 && Integer.parseInt(cartela.getNumeros().get(i).getNumero()) < 46) {					
					if(qdadeBolasN < 5) {
						qdadeBolasN++;
						
						modelo.setValueAt(celula, numeroLinhaN++, COLUMN_N);
					}
				} else if(Integer.parseInt(cartela.getNumeros().get(i).getNumero()) > 45 && Integer.parseInt(cartela.getNumeros().get(i).getNumero()) < 61) {					
					if(qdadeBolasG < 5) {
						qdadeBolasG++;
						
						modelo.setValueAt(celula, numeroLinhaG++, COLUMN_G);
					}
				} else if(Integer.parseInt(cartela.getNumeros().get(i).getNumero()) > 60 && Integer.parseInt(cartela.getNumeros().get(i).getNumero()) < 76) {
					if(qdadeBolasO < 5) {
						qdadeBolasO++;
						
						modelo.setValueAt(celula, numeroLinhaO++, COLUMN_O);
					}
				}
			}
		}
	}

}
