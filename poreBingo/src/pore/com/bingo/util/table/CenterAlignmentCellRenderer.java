package pore.com.bingo.util.table;

import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class CenterAlignmentCellRenderer extends DefaultTableCellRenderer {

	@Override
	protected void setValue(Object value) {
		
		setHorizontalAlignment(CENTER);
		
		if(value instanceof CellType)
		{
			CellType celula = (CellType) value;
			setBackground(celula.getCor());
			if(celula.getFonte() != null)
			{
				setFont(celula.getFonte());
			}
		    super.setValue(celula.getDado());
		}
		else
		{
			super.setValue(value);
		}
	}

}
