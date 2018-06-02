package pore.com.bingo.controllers;

import java.awt.Color;
import java.awt.Window;

import javax.swing.table.DefaultTableModel;

import pore.com.bingo.model.cartela.Cartela;
import pore.com.bingo.model.cartela.NumeroCartela;
import pore.com.bingo.util.ValidadorUniversal;
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
		
		tela.jTextFieldNumero.setText("");
		tela.jTextFieldPortador.setText("");
		
		cartelaEditada = new Cartela();
	}
	
	public Window getTela() {
		return tela;
	}
	
	public void confirmar() {
		
	}
	
	@SuppressWarnings("serial")
	public static void preencherTabela(Cartela cartela) {

		if(cartela != null && ValidadorUniversal.isListaPreenchida(cartela.getNumeros())) {
			int totalNumeros = cartela.getNumeros().size();
			
			int numeroLinhas = 0;
			
			while(totalNumeros > 0) {
				totalNumeros -= 5;
				
				numeroLinhas++;
			}
			

			Object dados[][] = new Object[numeroLinhas][5];
			String colunas[] = new String[]{
					"B", "I", "N", "G", "O"
			};

			DefaultTableModel modelo = new DefaultTableModel(dados, colunas){
				boolean[] canEdit = new boolean [] {
						true, true, true, true, true
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
			
			int numeroColuna = -1;
			int numeroLinha = 0;
			
			for(NumeroCartela numero: cartela.getNumeros())
			{
				Color cor = new Color(255,255,255);

				CellType celula = new CellType();
				celula.setCor(cor);
				
				if(ValidadorUniversal.check(numero.getNumero())) {
					celula.setDado(String.valueOf(numero.getNumero()));
					
				} else {
					celula.setDado("");
				}
				
				if(numeroColuna == 4) {
					numeroColuna = 0;
					numeroLinha++;
				} else {
					numeroColuna++;
				}
				
				modelo.setValueAt(celula, numeroLinha, numeroColuna);
			}
		}
	}

}
