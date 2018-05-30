package pore.com.bingo.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidadorUniversal {
	@SuppressWarnings("rawtypes")
	public static boolean isListaPreenchida(List lista)
    {
    	if(lista != null && lista.size() > 0)
    	{
    		return true;
    	}
    	
    	return false;
    }
	
	public static <T> boolean isArrayPreenchido(T[] array) {
		if(array != null && array.length > 0)
    	{
    		return true;
    	}
    	
    	return false;
    }

	
    /**
     * Verifica se a string pode ser convertida em um inteiro positivo
     */
    public static boolean isIntegerPositivo(String s)
    {
        boolean isInteger = isInteger(s);
        boolean isIntegerPositivo = isInteger;
        
        if(isInteger)
        {
            int valorInt = Integer.parseInt(s);
            isIntegerPositivo = (valorInt > 0) ;
        }
        
        return isIntegerPositivo;
        
    }
    
     /**
     * Verifica se a string pode ser convertida em um float positivo
     */
    public static boolean isFloatPositivo(String s, int n)
    {
        boolean isFloat = isFloat(s, n);
        boolean isFloatPositivo = isFloat;
        
        try 
        {
            if(isFloat)
            {
            	NumberFormat nf = NumberFormat.getNumberInstance(Locale.ITALY);
                nf.setMaximumFractionDigits(n);
        		nf.setMinimumFractionDigits(n);
            	
            	float valorFloat = nf.parse(s).floatValue();
                isFloatPositivo = (valorFloat > 0);
            }
        }
        catch(ParseException pe)
        {
            isFloatPositivo = false;
        }
        
        return isFloatPositivo;
    }
    
    /**
     * Verifica se a string eh um valor de porcentagem valido,
     *  ou seja, se esta entre 0 e 100
     */
    public static boolean isValorPorcentagem(String s, int n)
    {
        boolean isFloatPositivo = isFloat(s,n);
        boolean isValorPorcentagem = isFloatPositivo;
        
        try
        {
            if(isFloatPositivo)
            {
            	NumberFormat nf = NumberFormat.getNumberInstance(Locale.ITALY);
                nf.setMaximumFractionDigits(n);
        		nf.setMinimumFractionDigits(n);
            	
            	float valorFloat = nf.parse(s).floatValue();
                isValorPorcentagem = ((valorFloat >= 0) && (valorFloat <= 100)) ;
            }
        }
        catch(ParseException pe)
        {
            isFloatPositivo =false;
        }
        
        return isValorPorcentagem;
    }

    public static boolean contemLetra(String valor)
    {
    	boolean contemString = false;
    	
    	if(valor.toUpperCase().contains("A") ||
    			valor.toUpperCase().contains("B") ||
    			valor.toUpperCase().contains("C") ||
    			valor.toUpperCase().contains("D") ||
    			valor.toUpperCase().contains("E") ||
    			valor.toUpperCase().contains("F") ||
    			valor.toUpperCase().contains("G") ||
    			valor.toUpperCase().contains("H") ||
    			valor.toUpperCase().contains("I") ||
    			valor.toUpperCase().contains("J") ||
    			valor.toUpperCase().contains("K") ||
    			valor.toUpperCase().contains("L") ||
    			valor.toUpperCase().contains("M") ||
    			valor.toUpperCase().contains("N") ||
    			valor.toUpperCase().contains("O") ||
    			valor.toUpperCase().contains("P") ||
    			valor.toUpperCase().contains("Q") ||
    			valor.toUpperCase().contains("R") ||
    			valor.toUpperCase().contains("S") ||
    			valor.toUpperCase().contains("T") ||
    			valor.toUpperCase().contains("U") ||
    			valor.toUpperCase().contains("V") ||
    			valor.toUpperCase().contains("X") ||
    			valor.toUpperCase().contains("W") ||
    			valor.toUpperCase().contains("Z") 
    			)
    		
    	{
    		return true;
    	}
    	
    	
    	return contemString;
    }
    
    public static boolean isValorPositivoValido(String valor)
    {
    	if(!check(valor))
    	{
    		return false;
    	}
    	double valorDouble = 0; 
    	try {
			valorDouble = FuncoesNumeros.getNumeroNDigitosDecimais(valor, 2);
		} catch (ParseException e) {
			return false;
		}
		
		if(valorDouble == 0)
		{
			return false;
		}
		
		return true;
    }
    
    public static boolean checkEmail(String s){
    	boolean validou = false;
    	
    	//Set the email pattern string  
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");  
    
        //Match the given string with the pattern  
		if (s != null) {
			Matcher m = p.matcher(s);
			// check whether match is found
			boolean matchFound = m.matches();
			
			if (matchFound)
				validou = true;
		}
    	
    	return validou;
    }
    
    public static boolean checkAll(List<String> ss) {
    	boolean validou = true;
    	for (String s : ss) {
			if(!check(s))
			{
				validou  = false;
				break;
			}
		}
    	return validou;
	}
    
    public static boolean check(List<String> ss)
    {
    	boolean validou = false;
    	for (String s : ss) {
			validou = check(s);
		}
    	return validou;
    }
    
    public static boolean check(String s)
    {
        boolean validou = false;
        if (s != null)
        {
            s = s.trim();
            if (!s.equals(""))
            {
                validou = true;
            }
        }
        return validou;
    }
    
    public static boolean check(char[] ss)
    {
    	boolean validou = false;
    	if (ss != null)
    	{
    		char[] password = ss;
            String senha = "";
            for (int i = 0; i < password.length; i++) 
            {
            	senha += password[i];
    		}
    		senha = senha.trim();
    		if (!senha.equals(""))
    		{
    			validou = true;
    		}
    	}
    	return validou;
    }
    
    /**
     * Verifica se o array de bytes é não-nulo e não-vazio
     */
    public static boolean check(byte[] s)
    {
        return (s != null) && (s.length != 0);
    }    
       
    /**
     * Verifica se a string pode ser convertida em um inteiro
     */
    public static boolean isInteger(String s)
    {
        try 
        {
            if (!ValidadorUniversal.check(s)) return false;
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException error)
        {
            return false;
        }        
    }
    
    /**
     * Verifica se a string pode ser convertida em um float
     */
    public static boolean isFloat(String s)
    {
        try 
        {
            if (s == null) return false;
            Float.parseFloat(s);
            return true;
        }
        catch (NumberFormatException error)
        {
            return false;
        }        
    }
    
    /**
     * Verifica se a string pode ser convertida em um long
     */
    public static boolean isLong(String s)
    {
       try 
        {
            if (s == null) return false;
            Long.parseLong(s);
            return true;
        }
        catch (NumberFormatException error)
        {
            return false;
        }        
    }

    
    public static boolean isLongPositivo(String s)
    {
    	boolean isLong = isLong(s);
        boolean isLongPositivo = isLong;
        
        if(isLong)
        {
            long valorLong = Long.parseLong(s);
            isLongPositivo = (valorLong > 0) ;
        }
        
        return isLongPositivo;
    }
    
    
    
    /**
     * Verifica se a string pode ser convertida em um long
     */
    public static boolean isDouble(String s)
    {
       try 
        {
            if (s == null) return false;
            Double.parseDouble(s);
            return true;
        }
        catch (NumberFormatException error)
        {
            return false;
        }        
    }
    
    /**
     * Verifica se a string pode ser convertida em um float
     */
    public static boolean isFloat(String s, int n)
    {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ITALY);
        nf.setMaximumFractionDigits(n);
		nf.setMinimumFractionDigits(n);
    	
    	try
        {
            if (s == null) return false;
            if(contemLetra(s)) return false;
            nf.parse(s);
            return true;
        }
        catch (ParseException error)
        {
            return false;
        }        
    }
    
    /**
     * Verifica se a data é válido
     */
    public static boolean isDate(String s, SimpleDateFormat sdf)
    {
        try
        {
            if (s != null)
            {
            	if (sdf.toPattern().replace("'", "").trim().length() == s.trim().length())
            	{
            		sdf.setLenient(false);
            		sdf.parse(s);
            		return true;
            	}
            }
            return false;
        }
        catch (ParseException error)
        {
            return false;
        }
    }
    
   

    /**
     * Verifica se a hora é válido
     */
    public static boolean isHour(String s, SimpleDateFormat sdf)
    {
        try
        {
            sdf.parse(s);
            return true;
        }
        catch (ParseException error)
        {
            return false;
        }
    }

	public static boolean check(Number number) {
		return number != null && number.intValue() > 0;
	}

	public static boolean isMapPreenchido(Map<?, ?> map)
    {
    	if(map != null && map.size() > 0)
    	{
    		return true;
    	}
    	
    	return false;
    }
}
