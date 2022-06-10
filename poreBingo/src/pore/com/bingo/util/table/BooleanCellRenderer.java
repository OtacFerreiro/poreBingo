package pore.com.bingo.util.table;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class BooleanCellRenderer extends JCheckBox implements TableCellRenderer {

	public BooleanCellRenderer() {
        setHorizontalAlignment(JLabel.CENTER);
    }

	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof Boolean) {
            setSelected((Boolean) value);
        }
        
        CellType celulaPortador = (CellType) table.getValueAt(row, 1);
        setBackground(celulaPortador.getCor());
		
        return this;
    }
}
