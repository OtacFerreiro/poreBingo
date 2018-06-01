package pore.com.bingo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FuncoesData {
	
	public static SimpleDateFormat sdfDataHoraParaNomeArquivo = new SimpleDateFormat("yyyyMMdd_mmHH");
	
	public static SimpleDateFormat sdfDataHoraMinutoSegundo = new SimpleDateFormat("dd/MM/yyyy ',' HH:mm:ss");
	
	public static String formatarDataHoraParaNomeArquivo(Date data){
    	return sdfDataHoraParaNomeArquivo.format(data);
    }
	
	public static String formatarDataComHoraMinutoSegundo(Date data){
		return sdfDataHoraMinutoSegundo.format(data);
	}

}
