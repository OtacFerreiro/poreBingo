package pore.com.bingo.util;

import java.io.File;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import pore.com.bingo.controllers.ControllerSwing;
import pore.com.bingo.util.funcoes.TimeUtils;

public class BingoBoardPrinter implements Runnable {
	
	private Map<String, Object> parameters;

	@Override
	public void run() {
		try {
			String fileName = TimeUtils.getNow("yyyyMMddHHmmss") + "_cartelas" + ".pdf";
			String filePath = ControllerSwing.CAMINHO_DIR_IMP + File.separator + fileName;
			
			JasperReport bingoBoardJR = JasperCompileManager.compileReport(getClass().getResourceAsStream(ControllerSwing.CAMINHO_BINGO_BOARD));
			JasperPrint print = JasperFillManager.fillReport(bingoBoardJR, parameters, new JREmptyDataSource());
			
			JasperExportManager.exportReportToPdfFile(print, filePath);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}
	
}
