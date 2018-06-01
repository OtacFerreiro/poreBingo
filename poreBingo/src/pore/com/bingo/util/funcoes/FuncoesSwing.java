package pore.com.bingo.util.funcoes;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import pore.com.bingo.util.ValidadorUniversal;

public class FuncoesSwing {
	
	public static final String ATENCAO = "Aten\u00e7\u00e3o";
	
	public static final int SIM = 0;
	public static final int NAO = 1;
	
	public static void mostrarMensagem(Component frame, String titulo, String mensagem, int tipo){
        JOptionPane.showMessageDialog(frame, mensagem, titulo, tipo);        
	}

	public static void mostrarMensagem(Component frame, String titulo, String mensagem){
		mostrarMensagem(frame, titulo, mensagem, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void mostrarMensagemAtencao(Component frame, String mensagem){
		mostrarMensagem(frame, ATENCAO, mensagem, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void mostrarMensagemSucesso(Component frame, String mensagem){
		mostrarMensagem(frame, "Sucesso", mensagem, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void mostrarMensagemComIcone(Component frame, String titulo, String mensagem, Icon icone){
		JOptionPane.showMessageDialog(frame, mensagem, titulo, JOptionPane.INFORMATION_MESSAGE, icone);
	}

	public static void mostrarMensagemErro(Component frame, String titulo, String mensagem){
		mostrarMensagem(frame, titulo, mensagem, JOptionPane.ERROR_MESSAGE);
	}

	public static int mostrarMensagemSimNao(Component frame, String titulo, String mensagem){
		UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
		return JOptionPane.showConfirmDialog(frame, mensagem, titulo, JOptionPane.YES_NO_OPTION);
	}
	
	public static int getMensagemInt(Component frame, String titulo, String mensagem){
		String retorno = JOptionPane.showInputDialog(frame, mensagem, titulo, JOptionPane.QUESTION_MESSAGE);
		
		if(ValidadorUniversal.isIntegerPositivo(retorno.trim())){
			return Integer.parseInt(retorno.trim());
			
		} else{
			FuncoesSwing.mostrarMensagemErro(frame, titulo, "Numero n\u00e3o \u00e9 um inteiro.\nTente novamente!");
			return 0;
		}
	}

	public static void esperarJanela(JFrame janela){
		while(janela.isVisible()){}
	}
	
	public static void esperarJanela(JDialog janela){
		while(janela.isVisible()){}
	}

}
