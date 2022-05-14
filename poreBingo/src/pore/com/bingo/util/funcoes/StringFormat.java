package pore.com.bingo.util.funcoes;

/*
 * Created on 28/08/2003
 *
 */

import java.util.HashMap;

public class StringFormat {
	private static HashMap compiledPatterns = new HashMap();
	private CompiledPattern currentPattern; 
	private char defaultChar = ' ';


	public void setPattern(String pattern){
		currentPattern = (CompiledPattern) compiledPatterns.get( pattern );
		if( currentPattern != null )
		   return;
		   
		try {
			currentPattern = new CompiledPattern();
			StringBuffer buf = new StringBuffer(pattern);
			int directionParamIdx = 0, deleteParamIdx = 0;
			char charToDel = 0;

			directionParamIdx = buf.indexOf("%rev%");

			if (directionParamIdx > -1) {
				buf.delete(directionParamIdx, directionParamIdx + 5);
				currentPattern.setReverse( true );
			}

			deleteParamIdx = buf.indexOf("%del[");

			if (deleteParamIdx > -1) {
				int delParamEnd = buf.indexOf("]%",deleteParamIdx);
				currentPattern.setCharsToDelete( buf.substring(deleteParamIdx + 5, delParamEnd) );
				buf.delete( deleteParamIdx , delParamEnd +2 );
			}

			if (currentPattern.isReverse() ) {
				currentPattern.setPatternStartIdx( buf.length() - 1 );
				currentPattern.setEndOfJob( -1 );
				currentPattern.setIncrement( -1 );
			} else {
				currentPattern.setPatternStartIdx( 0 );
				currentPattern.setEndOfJob( buf.length() );
				currentPattern.setIncrement( 1 );
			}
			currentPattern.setPattern( buf.toString());
			compiledPatterns.put( pattern , currentPattern);
		} catch (Throwable e) {
			throw new IllegalArgumentException("Formato de argumento invlido : " + pattern);
		}
	}

	public String format(String strToFormat, String pattern ){
		setPattern(pattern);
		return format(strToFormat);
	}
	
	public String format(String strToFormat){
		
		if( currentPattern == null )
		   throw new IllegalStateException("Nenhum padro de formato foi definido, use StringFormat.setPattern()");
		   
		String pattern       = currentPattern.getPattern(),
		       charsToDelete = currentPattern.getCharsToDelete();
		boolean      reverse = currentPattern.isReverse();
		StringBuffer buf     = new StringBuffer( pattern);
		
		int patternStartIdx  = currentPattern.getPatternStartIdx(),
		    endOfJob         = currentPattern.getEndOfJob(),
		    increment        = currentPattern.getIncrement(),
		    strIndex         = 0,
		    strSize          = 0,
		    insertPointStart = (!reverse) ? 0 : buf.length() - 1;
		
		if(strToFormat == null ) {
			strToFormat = "";
		}
		
		try {
			if( charsToDelete != null ){
				StringBuffer strbuf = new StringBuffer();
				for( int i = 0 ; i < strToFormat.length() ; i++ ){
					char c = strToFormat.charAt(i);
					if( charsToDelete.indexOf( c ) == -1 ){
						strbuf.append( c );
					}
				}
				strToFormat = strbuf.toString();
			}
			
			strIndex = (reverse) ? strToFormat.length() - 1 : 0;
			strSize  = strToFormat.length();
			
			for (int curIndex = patternStartIdx; curIndex != endOfJob; curIndex += increment) {
				char c = pattern.charAt(curIndex);
				if (c == '#') {
					if ((!reverse) ? strIndex < strSize : strIndex > -1) {
						buf.setCharAt(curIndex, strToFormat.charAt(strIndex));
						strIndex += increment;
					} else {
						buf.setCharAt(curIndex, defaultChar);
					}
				} else if (c == '|') {
					int insertPoint = (!reverse) ? buf.indexOf("|", insertPointStart) : buf.lastIndexOf("|", insertPointStart);
					buf.deleteCharAt(insertPoint);
					buf.insert(insertPoint, strToFormat);
					insertPointStart = (!reverse) ? insertPoint + strToFormat.length() : insertPoint - 1;
				}
			}
		} catch ( Exception e) {
			e.printStackTrace();
		}
		return buf.toString();
	}
	
	private class CompiledPattern{
		private String pattern;
		private boolean reverse;
		private int patternStartIdx;
		private int endOfJob;
		private int increment;
		private String charsToDelete;
		
		public String getCharsToDelete() {
			return charsToDelete;
		}

		public int getEndOfJob() {
			return endOfJob;
		}

		public int getIncrement() {
			return increment;
		}

		public String getPattern() {
			return pattern;
		}

		public int getPatternStartIdx() {
			return patternStartIdx;
		}

		public boolean isReverse() {
			return reverse;
		}

		public void setCharsToDelete(String string) {
			charsToDelete = string;
		}

		public void setEndOfJob(int i) {
			endOfJob = i;
		}

		public void setIncrement(int i) {
			increment = i;
		}

		public void setPattern(String string) {
			pattern = string;
		}

		public void setPatternStartIdx(int i) {
			patternStartIdx = i;
		}

		public void setReverse(boolean b) {
			reverse = b;
		}
	}

	public static void main(String[] args) throws Exception { // teste
		StringFormat form = new StringFormat();
		form.setPattern(  "###.###/###.###" );
		System.out.println("["+form.format("123456789012")+"]");
	}

	public void setDefaultChar(char defaultChar) {
		this.defaultChar = defaultChar;
	}
}

