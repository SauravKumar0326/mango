package com.mango.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mango.dao.ResellerDao;
import com.mango.dto.ListResponse;
import com.mango.dto.ResellerDto;
import com.mango.exception.BadRequestException;
import com.mango.exception.DAOException;
import com.mango.exception.MangoException;
import com.mango.manager.ResellerManager;

@Component
public class ResellerManagerImpl implements ResellerManager {

	private static final Logger log = LoggerFactory.getLogger(ResellerManagerImpl.class);

	@Autowired
	private ResellerDao resellerDao;

	@Override
	public ResellerDto create(long companyId, ResellerDto resellerDto) throws DAOException {
		try {
			resellerDto = resellerDao.create(companyId, resellerDto);
		} catch (DAOException e) {
			log.error("Failed to create Reseller for company {} with filter {}", companyId,
					resellerDto.getResellerName());
			throw e;
		}
		return resellerDto;
	}

	@Override
	public ListResponse<ResellerDto> getResellers(long companyId, int start, int count, String sortBy, String sortOrder,
			String filter) throws MangoException {
		ListResponse<ResellerDto> resellerList = null;
		try {
			resellerList = resellerDao.get(companyId, start, count, sortBy, sortOrder, filter);
		} catch (BadRequestException | DAOException e) {
			log.error("Failed to get Resellers for company {} with filter {}", companyId, filter);
			throw e;
		}
		return resellerList;
	}

	@Override
	public ResellerDto update(long companyId, long resellerId, ResellerDto resellerDto) throws DAOException {
		try {
			resellerDto = resellerDao.update(companyId, resellerId, resellerDto);
		} catch (DAOException e) {
			log.error("Failed to update Reseller for company {} with filter {}", companyId,
					resellerDto.getResellerName());
			throw e;
		}
		return resellerDto;
	}

	@Override
	public ResellerDto get(long resellerId) throws DAOException {
		ResellerDto resellerDto = null;
		try {
			resellerDto = resellerDao.get(resellerId);
		} catch (DAOException e) {
			log.error("Failed to get Reseller {}", resellerId);
			throw e;
		}
		return resellerDto;
	}

	@Override
	public void delete(long resellerId) throws DAOException {
		try {
			resellerDao.delete(resellerId);
		} catch (DAOException e) {
			log.error("Failed to get Reseller {}", resellerId);
			throw e;
		}
	}

	@Override
	public byte[] getPdf(long companyId) throws Exception {
		byte[] byteArray = null;
		try {
			Document doc = new Document();
			List<ResellerDto> resellerDtoList = resellerDao.getPDF(companyId);
			File file = File.createTempFile("report", ".pdf");
			PdfWriter.getInstance(doc, new FileOutputStream(file));
			doc.open();
			PdfPTable table = new PdfPTable(5);
			table.setWidthPercentage(100.0f);
			table.setWidths(new float[] { 3.0f, 2.0f, 2.0f, 2.0f, 3.0f });
			table.setSpacingBefore(10);
			// define font for table header row
			Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
			Font font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);
			doc.add(new Paragraph("Reseller Information:", font));
			// define table header cell
			PdfPCell cell = new PdfPCell();
			cell.setPadding(5);
			// write table header
			cell.setPhrase(new Phrase("Reseller Name", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Phone", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Opening Balance (Rs)", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Closing Balance (Rs)", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Address", font));
			table.addCell(cell);
			// write table row data
			for (ResellerDto reseller : resellerDtoList) {
				table.addCell(new PdfPCell(new Phrase(reseller.getResellerName(), font2)));
				table.addCell(new PdfPCell(new Phrase(reseller.getResellerPhone(), font2)));
				table.addCell(new PdfPCell(new Phrase("" + reseller.getResellerOpeningBalance(), font2)));
				table.addCell(new PdfPCell(new Phrase("" + reseller.getResellerClosingBalance(), font2)));
				table.addCell(new PdfPCell(new Phrase(reseller.getResellerAddress(), font2)));
			}
			doc.add(table);
			doc.close();
			byteArray = FileUtils.readFileToByteArray(file);
			log.info("File is deleted:" + file.delete());
		} catch (DAOException | BadRequestException | DocumentException e) {
			log.error("Failed to generate PDF for Reseller");
			throw e;
		}
		return byteArray;
	}
}
