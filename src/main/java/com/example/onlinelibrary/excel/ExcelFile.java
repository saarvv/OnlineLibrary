package com.example.onlinelibrary.excel;

import com.example.onlinelibrary.model.Book;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelFile {

    public static String TYPE = "text/book";
    static String[] HEADERs = {"Id", "Title", "AuthorName", "PublisherName"};
    static String SHEET = "Book";

    public static boolean hasBookFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static ByteArrayInputStream booksToExcel(List<Book> books) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            //Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (Book book : books) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(book.getId());
                row.createCell(1).setCellValue(book.getTitle());
                row.createCell(2).setCellValue(book.getAuthorName());
                row.createCell(3).setCellValue(book.getPublisherName());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel File: " + e.getMessage());
        }
    }

    public static List<Book> bookToExcels(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            List<Book> books = new ArrayList<Book>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                //skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Book book = new Book();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();


                    switch (cellIdx) {
                        case 0:
                            book.setId((long) currentCell.getNumericCellValue());
                            break;

                        case 1:
                            book.setTitle(currentCell.getStringCellValue());
                            break;

                        case 2:
                            book.setAuthorName(currentCell.getStringCellValue());
                            break;

                        case 3:
                            book.setPublisherName(currentCell.getStringCellValue());
                            break;
                    }
                    cellIdx++;
                }
                books.add(book);
            }

            workbook.close();
            return books;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse excel file:" + e.getMessage());
        }
    }
}
