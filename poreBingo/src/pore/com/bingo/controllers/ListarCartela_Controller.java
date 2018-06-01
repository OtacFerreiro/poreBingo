package pore.com.bingo.controllers;

import java.awt.Color;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

import pore.com.bingo.model.cartela.Cartela;
import pore.com.bingo.model.cartela.NumeroCartela;
import pore.com.bingo.util.ValidadorUniversal;
import pore.com.bingo.util.funcoes.FuncoesData;
import pore.com.bingo.util.funcoes.FuncoesSwing;
import pore.com.bingo.util.table.CellType;
import pore.com.bingo.util.table.CenterAlignmentCellRenderer;
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
		if(!ValidadorUniversal.isIntegerPositivo(tela.jTextFieldNumero.getText())) {
			System.out.println("[" + FuncoesData.formatarDataComHoraMinutoSegundo(new Date()) + "] - Numero de cartela invalido.");

			FuncoesSwing.mostrarMensagemSucesso(tela, "Numero de cartela invalido.");

			return;
		}


	}

	/*
	 * FORMATO DO ARQUIVO TXT
	 * CODIGOCARTELA  -  NUMERO NUMERO NUMERO NUMERO [...] NUMERO  
	 *  */
	public void importarCartelas() {

		qdadeBolasPorCartela = FuncoesSwing.getMensagemInt(tela, "Configura��o das Cartelas", "Quantas bolas cada cartela possui?");

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
									System.out.println("[" + FuncoesData.formatarDataComHoraMinutoSegundo(new Date()) + "] - Cartela N�" + cartela.getNumeroCartela() + ": numero de bolas corresponde a quantidade informada informado.");

								} else {
									System.out.println("[" + FuncoesData.formatarDataComHoraMinutoSegundo(new Date()) + "] - Cartela N�" + cartela.getNumeroCartela() + ": numero de bolas nao corresponde a quantidade informada informado.");
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
			
			preencherTabela(cartelas);
		}		
	}

	@SuppressWarnings("serial")
	public void preencherTabela(List<Cartela> cartelas) {

		if(ValidadorUniversal.isListaPreenchida(cartelas)) {
			int quantidadeItens = cartelas.size();

			Object dados[][] = new Object[quantidadeItens][7];
			String colunas[] = new String[]{
					"N� da Cartela", "Portador", "Editar", "Remover"
			};
			
			for(int i = 0 ; i < quantidadeItens; i++)
			{
				dados[i][2] = Boolean.FALSE;
				dados[i][3] = Boolean.FALSE;
			}

			DefaultTableModel modelo = new DefaultTableModel(dados, colunas){
				boolean[] canEdit = new boolean [] {
						false, false, false, false
				};
				@SuppressWarnings("rawtypes")
				Class[] types = new Class [] {
						Integer.class, String.class, Boolean.class, Boolean.class
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

			tela.jTableListaCartelas.setModel(modelo);
			
			tela.jTableListaCartelas.getColumnModel().getColumn(0).setPreferredWidth(50);
			tela.jTableListaCartelas.getColumnModel().getColumn(1).setCellRenderer(new CenterAlignmentCellRenderer());
			tela.jTableListaCartelas.getColumnModel().getColumn(1).setPreferredWidth(120);
			tela.jTableListaCartelas.getColumnModel().getColumn(1).setCellRenderer(new CenterAlignmentCellRenderer());
			tela.jTableListaCartelas.getColumnModel().getColumn(2).setPreferredWidth(30);
			tela.jTableListaCartelas.getColumnModel().getColumn(2).setCellRenderer(new CenterAlignmentCellRenderer());
			tela.jTableListaCartelas.getColumnModel().getColumn(3).setPreferredWidth(30);
			tela.jTableListaCartelas.getColumnModel().getColumn(3).setCellRenderer(new CenterAlignmentCellRenderer());
			
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
				
				CellType celula3 = new CellType();
				celula3.setCor(cor);
				modelo.setValueAt(celula3, i, 2);
				
				CellType celula4 = new CellType();
				celula4.setCor(cor);
				modelo.setValueAt(celula4, i, 3);
			}
		}
	}

}
