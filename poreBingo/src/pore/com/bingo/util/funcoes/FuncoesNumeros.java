package pore.com.bingo.util.funcoes;

import java.text.ParseException;

import pore.com.bingo.util.ValidadorUniversal;

public class FuncoesNumeros {
	public static double getNumeroNDigitosDecimais(String i, int n)
			throws ParseException
	{
		if(!ValidadorUniversal.check(i))
		{
			i = "0,00";
		}

		return truncate(new Double(i.replace(".", "").replace(",", ".")).doubleValue(), n);
	}
	
	public static float truncate(float d, int n) {
		String i = "1";
		for(int k = 0 ; k < n ; k++)
		{
			i += "0";
		}
		
		int fator = Integer.parseInt(i);
		
		d = new Float(Math.floor(d * fator)).floatValue();
		d = d / fator;
		
		return d;
	}
	
	public static double truncate(double d, int n) {
		double result = d;
	    String arg = String.valueOf(d);
	    
	    if(arg.contains("E"))
	    {
	    	arg = String.valueOf(Math.floor(d));
	    }
	    
	    int idx = arg.indexOf('.');
	    if (idx!=-1)
	    {
	        if (arg.length() > idx+n) 
	        {
	            arg = arg.substring(0,idx+n+1);
	            result  = Double.parseDouble(arg);
	        }
	    }
	    return result ;
	}
}
