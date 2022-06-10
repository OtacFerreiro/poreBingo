package pore.com.bingo.util.funcoes;

/*
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class BigDecimalUtils {
	public static final BigDecimal	ZERO_VALUE	= new BigDecimal(0);
	public static final BigDecimal	CEM_VALUE	= BigDecimal.valueOf(100.0);
	public static final MathContext MATH_CTX = new MathContext(64, RoundingMode.HALF_UP);
	public static final DecimalFormat DEC_FORMAT_VLR = new DecimalFormat("0.00", new DecimalFormatSymbols(new Locale("en", "US")));

	public static BigDecimal buildFromDouble(double d) {
		int asInt = (int) d;
		BigDecimal result = null;

		if (asInt != d) { // evita o zero  esquerda.
			result = BigDecimal.valueOf(d);
		} else {
			result = new BigDecimal(asInt);
		}

		return result;
	}
	
	public static BigDecimal safetyDivision(BigDecimal value, BigDecimal dividend, MathContext ctx){
		if(isEmpty(value)){
			return value;
		}
		return value.divide(isEmpty(dividend) ? BigDecimal.ONE : dividend, ctx);
	}

	public static BigDecimal buildFromDouble(Number d) {
		BigDecimal result = null;

		if (d != null) {
			double value = d.doubleValue();
			int asInt = (int) value;

			if (asInt != value) { // evita o zero  esquerda.
				result = BigDecimal.valueOf(value);
			} else {
				result = new BigDecimal(asInt);
			}
		}

		return result;
	}

	public static BigDecimal getRounded(double value, int scale) {
		return BigDecimal.valueOf(value).setScale(scale, BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal getRounded(BigDecimal value, int scale) {
		return value.setScale(scale, BigDecimal.ROUND_HALF_UP);
	}

	public static double getRoundedDouble(double value, int scale) {
		BigDecimal b = BigDecimal.valueOf(value).setScale(scale, BigDecimal.ROUND_HALF_UP);

		return new Double(b.toString()).doubleValue();
	}

	public static int getValue(BigDecimal value, int vlrDefault) {
		if (value == null) {
			return vlrDefault;
		}

		return value.intValue();
	}

	public static BigDecimal getValue(BigDecimal value, BigDecimal vlrDefault) {
		if (value == null) {
			return vlrDefault;
		}

		return value;
	}

	public static BigDecimal getValueOrZero(BigDecimal value) {
		if (value == null) {
			return ZERO_VALUE;
		}

		return value;
	}

	public static boolean isEmpty(BigDecimal value) {
		if ((value == null) || (value.compareTo(BigDecimal.ZERO) == 0)) {
			return true;
		}

		return false;
	}
	
	public static boolean isNullOrZero(BigDecimal arg) {
		return getValueOrZero(arg).compareTo(BigDecimal.ZERO) == 0;
	}
	

	public static void main(String[] args) {
		BigDecimal b = new BigDecimal("190095.0566");
		System.out.println(formatCurrency(b, 3));
	}

	public static BigDecimal porcentagem(BigDecimal parte, BigDecimal todo) {
		MathContext mathCtx = new MathContext(2, RoundingMode.HALF_UP);

		return porcentagem(parte, todo, mathCtx);
	}

	public static BigDecimal porcentagem(BigDecimal parte, BigDecimal todo, MathContext mathCtx) {
		return parte.multiply(CEM_VALUE).divide(todo, mathCtx);
	}

	public static String toCurrency(BigDecimal value) {
		NumberFormat currencyFormt = new DecimalFormat("#,##0.00");

		return (value == null) ? "" : currencyFormt.format(value);
	}

	public static BigDecimal truncate(BigDecimal value, int scale) {
		return value.setScale(scale, BigDecimal.ROUND_DOWN);
	}

	public static BigDecimal truncate(double value, int scale) {
		return BigDecimal.valueOf(value).setScale(scale, BigDecimal.ROUND_DOWN);
	}

	public static BigDecimal truncateMGE(BigDecimal value, int precisao) {
		BigDecimal result = value;
		BigDecimal mult = BigDecimal.valueOf(Math.pow(10, precisao));

		result = result.multiply(mult, MATH_CTX);
		result = getRounded(result, 5);
		result = result.divide(mult, MATH_CTX);

		return truncate(result, precisao);
	}

	public static BigDecimal valueOf(double d) {
		return BigDecimal.valueOf(d);
	}

	public static BigDecimal valueOf(long l) {
		return BigDecimal.valueOf(l);
	}

	public static BigDecimal valueOf(String s) {
		return new BigDecimal(s);
	}
	
	public static BigDecimal getBigDecimal(Object value ) {
        BigDecimal ret = null;
        if( value != null ) {
        	try {
        		if( value instanceof BigDecimal ) {
        			ret = (BigDecimal) value;
        		} else if( value instanceof String ) {
        			if(StringUtils.getEmptyAsNull((String) value) == null){
        				return ret;
        			}
        			
        			ret = new BigDecimal( (String) value );
        		} else if( value instanceof Number ) {
        			ret = new BigDecimal( ((Number)value).doubleValue() );
        		} 
			} catch (NumberFormatException e) {
				throw new IllegalStateException("O valor " + value + " informado no pode ser convertido em BigDecimal");
	        }
        }
        
        return ret;
    }

	public static BigDecimal max(BigDecimal a, BigDecimal b) {
		if (a.compareTo(b) > 0) {
			return a;
		}
		return b;
	}

	public static BigDecimal min(BigDecimal a, BigDecimal b) {
		if (a.compareTo(b) < 0) {
			return a;
		}
		return b;
	}
	
	public static int min(int a, int b) {
		return (a > b ? b : a);
	}
	
	public static double subtractDouble(double value1, double value2){
		return BigDecimal.valueOf(value1).subtract(BigDecimal.valueOf(value2)).doubleValue();
	}

	public static double addDouble(double value1, double value2) {
		return BigDecimal.valueOf(value1).add(BigDecimal.valueOf(value2)).doubleValue();
	}

	public static BigDecimal divide(BigDecimal dividend, BigDecimal divider) {
		return divide(dividend, divider, 2);
	}
	
	public static BigDecimal divide(BigDecimal dividend, BigDecimal divider, int precision) {
		if (dividend == null) {
			throw new IllegalStateException("O valor informado como dividendo no pode ser nulo.");
		}

		if (divider == null) {
			throw new IllegalStateException("O valor informado como divisor no pode ser nulo.");
		}
		return dividend.divide(divider, new MathContext(precision, RoundingMode.HALF_EVEN));
	}

	public static String formatCurrency(BigDecimal value, int scale) {
		StringBuffer strFormat = new StringBuffer("#,##0");
		
		if(scale > 0) {
			strFormat.append(".");
		}

		for (int i = 0; i < scale; i++) {
			strFormat.append("0");
		}

		NumberFormat currencyFormt = new DecimalFormat(strFormat.toString());

		return (value == null) ? "" : currencyFormt.format(value);
	}

	/**
	 * Caso o valor da parte a ser descartada seja menor ou igual a 5 o arredondamento 
	 * deve ser feito para 'baixo', caso contrario para 'cima'.
	 */
	public static BigDecimal getRoundedHalfDown(BigDecimal value, int scale) {
		return value.setScale(scale, BigDecimal.ROUND_HALF_DOWN);
	}

	public static BigDecimal strToBigDecimalDef(String valor, BigDecimal valorDefault) {
		BigDecimal result = BigDecimal.ZERO;
		try {
			result = new BigDecimal(valor);
		} catch (Exception ignore) {
			result = valorDefault;
		}

		return result;
	}
}
