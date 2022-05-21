package pore.com.bingo.model;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class CartelaDS implements JRDataSource {
	
	private static String[] numeros = new String[] {"1","12","31","341","14","15","61","16","71","18","01","111","41","17","81","51","154","11","0","0","0","0","0","0","0"};
	
	private int counter = -1;
	
	public static JRDataSource getDataSource(){
        return new CartelaDS();
    }

	@Override
	public Object getFieldValue(JRField field) throws JRException {
		if (field.getName().equals("numeros")) {
			return numeros;
		}
		
        return "";
	}

	@Override
	public boolean next() throws JRException {
		if (numeros != null && counter < numeros.length - 1) {
            counter++;
            
            return true;
        }
        return false;
	}

	public static String[] getNumeros() {
		return numeros;
	}

	public static void setNumeros(String[] numeros) {
		CartelaDS.numeros = numeros;
	}
	
}
