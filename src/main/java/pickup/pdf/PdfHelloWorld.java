package pickup.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.GreekList;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.RomanList;
import com.itextpdf.text.Section;
import com.itextpdf.text.ZapfDingbatsList;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by YongGang on 2017/1/24.
 */
public class PdfHelloWorld {

  public static void main(String[] args) {
    String filename = "HelloWorld.pdf";

    Document document = new Document();
    try {
      // can also write to httpResponse
//      response.setContentType("application/pdf");
//      PdfWriter.getInstance(document, response.getOutputStream());

      PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("HelloWorld.pdf"));
      document.open();

      document.add(new Paragraph("A Hello World PDF document."));

      document.add(new Paragraph("Some content here"));

      //Set attributes here
      document.addAuthor("Lokesh Gupta");
      document.addCreationDate();
      document.addCreator("HowToDoInJava.com");
      document.addTitle("Set Attribute Example");
      document.addSubject("An example to show how attributes can be added to pdf files.");

      //Add Image
      document.add(new Paragraph("Add Image: can overlay text"));
      Image image1 = Image.getInstance("AddImageExample.png");
      //Fixed Positioning
      image1.setAbsolutePosition(50f, 700f);
      //Scale to new height and new width of image
      image1.scaleAbsolute(100, 100);
      //Add to document
      document.add(image1);

      // add table
      document.add(new Paragraph("Table"));
      PdfPTable table = new PdfPTable(3); // 3 columns.
      table.setWidthPercentage(100); //Width 100%
      table.setSpacingBefore(10f); //Space before table
      table.setSpacingAfter(10f); //Space after table

      //Set Column widths
      float[] columnWidths = {1f, 1f, 1f};
      table.setWidths(columnWidths);

      PdfPCell cell1 = new PdfPCell(new Paragraph("Cell 1"));
      cell1.setBorderColor(BaseColor.BLUE);
      cell1.setPaddingLeft(10);
      cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
      cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

      PdfPCell cell2 = new PdfPCell(new Paragraph("Cell 2"));
      cell2.setBorderColor(BaseColor.GREEN);
      cell2.setPaddingLeft(10);
      cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
      cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

      PdfPCell cell3 = new PdfPCell(new Paragraph("Cell 3"));
      cell3.setBorderColor(BaseColor.RED);
      cell3.setPaddingLeft(10);
      cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
      cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

      //To avoid having the cell border and the content overlap, if you are having thick cell borders
      //cell1.setUserBorderPadding(true);
      //cell2.setUserBorderPadding(true);
      //cell3.setUserBorderPadding(true);

      table.addCell(cell1);
      table.addCell(cell2);
      table.addCell(cell3);

      document.add(table);

      // list
      document.add(new Paragraph("Listing"));
      //Add ordered list
      List orderedList = new List(List.ORDERED);
      orderedList.add(new ListItem("Item 1"));
      orderedList.add(new ListItem("Item 2"));
      orderedList.add(new ListItem("Item 3"));
      document.add(orderedList);

      //Add un-ordered list
      List unorderedList = new List(List.UNORDERED);
      unorderedList.add(new ListItem("Item 1"));
      unorderedList.add(new ListItem("Item 2"));
      unorderedList.add(new ListItem("Item 3"));
      document.add(unorderedList);

      //Add roman list
      RomanList romanList = new RomanList();
      romanList.add(new ListItem("Item 1"));
      romanList.add(new ListItem("Item 2"));
      romanList.add(new ListItem("Item 3"));
      document.add(romanList);

      //Add Greek list
      GreekList greekList = new GreekList();
      greekList.add(new ListItem("Item 1"));
      greekList.add(new ListItem("Item 2"));
      greekList.add(new ListItem("Item 3"));
      document.add(greekList);

      //ZapfDingbatsList List Example
      ZapfDingbatsList zapfDingbatsList = new ZapfDingbatsList(43, 30);
      zapfDingbatsList.add(new ListItem("Item 1"));
      zapfDingbatsList.add(new ListItem("Item 2"));
      zapfDingbatsList.add(new ListItem("Item 3"));
      document.add(zapfDingbatsList);

      //List and Sublist Examples
      List nestedList = new List(List.UNORDERED);
      nestedList.add(new ListItem("Item 1"));

      List sublist = new List(true, false, 30);
      sublist.setListSymbol(new Chunk("", FontFactory.getFont(FontFactory.HELVETICA, 6)));
      sublist.add("A");
      sublist.add("B");
      nestedList.add(sublist);

      nestedList.add(new ListItem("Item 2"));

      sublist = new List(true, false, 30);
      sublist.setListSymbol(new Chunk("", FontFactory.getFont(FontFactory.HELVETICA, 6)));
      sublist.add("C");
      sublist.add("D");
      nestedList.add(sublist);

      document.add(nestedList);

      //Paragraph with color and font styles
      document.add(new Paragraph("Styling Example"));

      Font blueFont = FontFactory
          .getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new CMYKColor(255, 0, 0, 0));
      Font redFont = FontFactory
          .getFont(FontFactory.COURIER, 12, Font.BOLD, new CMYKColor(0, 255, 0, 0));
      Font yellowFont = FontFactory
          .getFont(FontFactory.COURIER, 14, Font.BOLD, new CMYKColor(0, 0, 255, 0));

      Paragraph paragraphOne = new Paragraph("Some colored paragraph text", redFont);
      document.add(paragraphOne);

      //Create chapter and sections
      Paragraph chapterTitle = new Paragraph("Chapter Title", yellowFont);
      Chapter chapter1 = new Chapter(chapterTitle, 1);
      chapter1.setNumberDepth(0);

      Paragraph sectionTitle = new Paragraph("Section Title", redFont);
      Section section1 = chapter1.addSection(sectionTitle);

      Paragraph sectionContent = new Paragraph("Section Text content", blueFont);
      section1.add(sectionContent);

      document.add(chapter1);

      document.close();
      writer.close();

      System.out.println("Output: " + filename);
    } catch (DocumentException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void genFromHtml() throws DocumentException, IOException {

    // PDFKit is a javascript solution. worth trying.

    // step 1
    Document document = new Document();
    // step 2
    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("pdf.pdf"));
    // step 3
    document.open();
    // step 4

    XMLWorkerHelper.getInstance().parseXHtml(writer, document,
        new FileInputStream("index.html"));
    //step 5
    document.close();

    System.out.println("PDF Created!");
  }

}
