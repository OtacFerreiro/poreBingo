package pore.com.bingo.util.table;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class HeaderChkBoxCellRenderer implements TableCellRenderer {
	
	private final JCheckBox check = new JCheckBox();
	
	public HeaderChkBoxCellRenderer(JTableHeader header) {
		header.addMouseListener(new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
            JTable table = ((JTableHeader) e.getSource()).getTable();
            TableColumnModel columnModel = table.getColumnModel();
            int viewColumn = columnModel.getColumnIndexAtX(e.getX());
            int modelColumn = table.convertColumnIndexToModel(viewColumn);
            if (modelColumn == 0) {
                check.setSelected(!check.isSelected());
                TableModel m = table.getModel();
                Boolean f = check.isSelected();
                for (int i = 0; i < m.getRowCount(); i++) {
                    m.setValueAt(f, i, 0);
                }
                ((JTableHeader) e.getSource()).repaint();
            }
        }
    });
	}

	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		check.setEnabled(true);
		check.setHorizontalAlignment(JLabel.CENTER);
		
        return check;
    }

	public JCheckBox getCheck() {
		return check;
	}
	
}
