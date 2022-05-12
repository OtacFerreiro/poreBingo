package pore.com.bingo.util;

import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

public class ImpressaoArquivos {
	
	public static void imprimirPDF(String filePath) throws IOException {
		PDDocument documento = null;
		
		try {
			documento = PDDocument.load(new File(filePath));
			PrintService servico = PrintServiceLookup.lookupDefaultPrintService();

			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPageable(new PDFPageable(documento));
			job.setPrintService(servico);
			job.print();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if(documento != null) {
				documento.close();
			}
		}
	}

}
