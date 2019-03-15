package com.jp.trade.exchange.io;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.jp.trade.exchange.jms.dto.BuySellIndicator;
import com.jp.trade.exchange.jms.dto.TradeDTO;

/**
 * Asynchronously reads the excel file having trade instructions data.
 *
 * @author apoorva.ubana
 */
@Component
public class ExcelFileReader {

	private static final Logger logger = LogManager.getLogger();

	/**
	 * Asynchronously Read the EOD excel file containing the trade instructions data
	 * .It uses Apache POI to read the Excel file
	 *
	 * @param excelFilePath
	 *            location of EOD file
	 * @return CompletableFuture list of trade data
	 */
	@Async("threadPoolTaskExecutor")
	public CompletableFuture<List<TradeDTO>> readStockDataFromExcelFile(String excelFilePath) {

		logger.traceEntry();
		List<TradeDTO> listTrade = null;
		try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(excelFilePath)) {
			logger.info("Reading Exchange File");
			listTrade = new ArrayList<>();

			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator();
			// Iterating over each row
			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();

				// Leave the first row containing labels
				if (nextRow.getRowNum() == 0) {
					continue;
				}
				TradeDTO trade = new TradeDTO();
				// Iterating over cell and set the value in stock object
				while (cellIterator.hasNext()) {
					Cell nextCell = cellIterator.next();
					int columnIndex = nextCell.getColumnIndex();

					switch (columnIndex) {
					case 0:
						trade.setEntityName((String) getCellValue(nextCell));
						break;

					case 1:
						trade.setBuySellIndicator(
								BuySellIndicator.getByValue(getCellValue(nextCell).toString().trim()));
						break;
					case 2:
						trade.setAgreedFxRate(new BigDecimal((Double) getCellValue(nextCell)));
						break;

					case 3:
						trade.setCurrency((String) getCellValue(nextCell));
						break;

					case 4:
						trade.setInstructionDate(
								nextCell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
						break;

					case 5:
						trade.setSettlementDate(
								nextCell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
						break;

					case 6:
						Double units = (Double) getCellValue(nextCell);
						trade.setUnits(units.intValue());
						break;

					case 7:
						trade.setPrice(new BigDecimal((Double) getCellValue(nextCell)));
						break;
					}
				}
				listTrade.add(trade);
				trade = null;
			}

		} catch (IOException e) {
			logger.error(e);
		}
		logger.debug("Loaded trade data" + listTrade.size());
		return CompletableFuture.completedFuture(listTrade);
	}

	/**
	 * @param cell
	 *            cell of the each row in excel file
	 * @return object value depending on the type of cell value
	 */
	private Object getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();

		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();

		case Cell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue();

		default:
			return null;
		}
	}
}
