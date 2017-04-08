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
import com.mango.dao.SupplierDao;
import com.mango.dto.ListResponse;
import com.mango.dto.SupplierDto;
import com.mango.exception.BadRequestException;
import com.mango.exception.DAOException;
import com.mango.exception.MangoException;
import com.mango.manager.SupplierManager;

@Component
public class SupplierManagerImpl implements SupplierManager {

	private static final Logger log = LoggerFactory.getLogger(ResellerManagerImpl.class);

	@Autowired
	private SupplierDao supplierDao;

	@Override
	public SupplierDto create(long companyId, SupplierDto supplierDto) throws DAOException {

		try {
			supplierDto = supplierDao.create(companyId, supplierDto);
		} catch (DAOException e) {
			log.error("Failed to create Supplier for company {} for Name {}", companyId, supplierDto.getSupplierName());
			throw e;
		}
		return supplierDto;
	}

	@Override
	public ListResponse<SupplierDto> getSuppliers(long companyId, int start, int count, String sortBy, String sortOrder,
			String filter) throws MangoException {
		ListResponse<SupplierDto> supplierList = null;
		try {
			supplierList = supplierDao.get(companyId, start, count, sortBy, sortOrder, filter);
		} catch (DAOException | BadRequestException e) {
			log.error("Failed to get Suppliers for company {} with filter {}", companyId, filter);
			throw e;
		}
		return supplierList;
	}

	@Override
	public SupplierDto get(long supplierId) throws DAOException {
		SupplierDto supplierDto = null;
		try {
			supplierDto = supplierDao.get(supplierId);
		} catch (DAOException e) {
			log.error("Failed to get Supplier {}", supplierId);
			throw e;
		}
		return supplierDto;
	}

	@Override
	public void delete(long supplierId) throws DAOException {
		try {
			supplierDao.delete(supplierId);
		} catch (DAOException e) {
			log.error("Failed to delete Supplier {}", supplierId);
			throw e;
		}
	}

	@Override
	public SupplierDto update(long companyId, long supplierId, SupplierDto supplierDto) throws DAOException {
		try {
			supplierDto = supplierDao.update(companyId, supplierId, supplierDto);
		} catch (DAOException e) {
			log.error("Failed to Upadate Supplier for company {} for Name {}", companyId,
					supplierDto.getSupplierName());
			throw e;
		}
		return supplierDto;
	}

	@Override
	public byte[] getPdf(long companyId) throws Exception {
		byte[] byteArray = null;
		try {
			Document doc = new Document();
			List<SupplierDto> supplierDtoList = supplierDao.getPDF(companyId);
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
			doc.add(new Paragraph("Supplier Information:", font));
			// define table header cell
			PdfPCell cell = new PdfPCell();
			cell.setPadding(5);
			// write table header
			cell.setPhrase(new Phrase("Supplier Name", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Phone", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Opening Balance (Rs)", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Advance Amount (Rs)", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Address", font));
			table.addCell(cell);
			// write table row data
			for (SupplierDto supplier : supplierDtoList) {
				table.addCell(new PdfPCell(new Phrase(supplier.getSupplierName(), font2)));
				table.addCell(new PdfPCell(new Phrase(supplier.getSupplierPhone(), font2)));
				table.addCell(new PdfPCell(new Phrase("" + supplier.getSupplierOpeningBalance(), font2)));
				table.addCell(new PdfPCell(new Phrase("" + supplier.getSupplierAdvanceAmount(), font2)));
				table.addCell(new PdfPCell(new Phrase(supplier.getSupplierAddress(), font2)));
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
