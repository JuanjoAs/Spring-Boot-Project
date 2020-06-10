package com.juanjo.proyecto.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.google.gson.Gson;
import com.juanjo.proyecto.component.PdfGenaratorUtil;
import com.juanjo.proyecto.model.Alquiler;
import com.juanjo.proyecto.model.Casa;
import com.juanjo.proyecto.model.Inquilino;
import com.juanjo.proyecto.model.User;
import com.juanjo.proyecto.service.AlquilerService;
import com.juanjo.proyecto.service.CasaService;
import com.juanjo.proyecto.service.InquilinoService;
import com.juanjo.proyecto.service.UserService;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.xml.messaging.saaj.util.ByteOutputStream;

@RestController
public class ApiController {
	@Autowired
	private UserService userService;
	@Autowired
	private CasaService casaService;
	@Autowired
	private AlquilerService alquilerService;
	@Autowired
	private InquilinoService inquilinoService;
	@Autowired
	PdfGenaratorUtil pdfGenaratorUtil;

	/*//CONTRATOS
	 * @GetMapping("/api/pdf/{id}") public ResponseEntity<InputStreamResource>
	 * greeting(@RequestParam(value = "id") String id) { Authentication auth =
	 * SecurityContextHolder.getContext().getAuthentication(); User user=null; if
	 * (!(auth instanceof AnonymousAuthenticationToken)) { user =
	 * userService.findUserByEmail(auth.getName()); } for(Casa c:user.getCasas()) {
	 * for(Alquiler a:c.getAlquilers()) { if(a.getId()==Integer.parseInt(id)) {
	 * ByteArrayInputStream bis = contratoReport(a,user);
	 * 
	 * HttpHeaders headers = new HttpHeaders(); headers.add("Content-Disposition",
	 * "inline; filename=citiesreport.pdf");
	 * 
	 * return
	 * ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
	 * .body(new InputStreamResource(bis)); } } } return null;
	 * 
	 * }
	 * 
	 * private ByteArrayInputStream contratoReport(Alquiler a, User user) {
	 * 
	 * Document document = new Document(); ByteArrayOutputStream out = new
	 * ByteArrayOutputStream();
	 * 
	 * 
	 * 
	 * document.close();
	 * 
	 * 
	 * return new ByteArrayInputStream(out.toByteArray()); }
	 */
	
	
	public void test() throws DocumentException, IOException {
	    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
	    templateResolver.setSuffix(".html");
	    templateResolver.setTemplateMode("XHTML");
	    templateResolver.setCharacterEncoding("UTF-8");

	    TemplateEngine templateEngine = new TemplateEngine();
	    templateEngine.setTemplateResolver(templateResolver);

	    Context ctx = new Context();
	    ctx.setVariable("message", "I don't want to live on this planet anymore");
	    String htmlContent = templateEngine.process("messageTpl", ctx);

	    ByteOutputStream os = new ByteOutputStream();
	    ITextRenderer renderer = new ITextRenderer();
	    ITextFontResolver fontResolver = renderer.getFontResolver();

	    ClassPathResource regular = new ClassPathResource("/META-INF/fonts/LiberationSerif-Regular.ttf");
	    fontResolver.addFont(regular.getURL().toString(), BaseFont.IDENTITY_H, true);

	    renderer.setDocumentFromString(htmlContent);
	    renderer.layout();
	    renderer.createPDF(os);

	    byte[] pdfAsBytes = os.getBytes();
	    os.close();

	    FileOutputStream fos = new FileOutputStream(new File("/tmp/message.pdf"));
	    fos.write(pdfAsBytes);
	    fos.close();
	  }
	
	@GetMapping("/api/data")
	public String getData() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			User user = userService.findUserByEmail(auth.getName());
			String data = "";
			List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();

			Map<String, String> obj = new HashMap<String, String>();

			for (Inquilino i : user.getInquilino()) {
				obj = new HashMap<String, String>();
				data += "{";
				data += "id:" + i.getId() + ",";
				data += "name:" + i.getFirstname() + " " + i.getLastname() + ",";
				data += "dni:" + i.getDni() + ",";
				data += "pais:" + i.getPais() + ",";
				data += "vivienda:" + i.getVivienda() + ",";
				data += "email:" + i.getEmail() + ",";
				data += "telefono:" + i.getTelefono() + "";
				data += "}";
				obj.put("id", i.getId() + "");
				obj.put("name", i.getFirstname() + " " + i.getLastname() + "");
				obj.put("dni", i.getDni() + "");
				obj.put("pais", i.getPais() + "");
				obj.put("vivienda", i.getVivienda() + "");
				obj.put("email", i.getEmail() + "");
				obj.put("telefono", i.getTelefono() + "");
				dataList.add(obj);
				data = "";

			}
			Map<String, Object> ss = new HashMap<String, Object>();
			ss.put("data", dataList);
			String json = new Gson().toJson(ss);
			return json;
		} else {
			return "";
		}
	}

	@GetMapping(value = "/api/getPdfInquilinos/prueba")
	public ResponseEntity<byte[]> getImage() throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			User user = userService.findUserByEmail(auth.getName());

			List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();

			Map<String, String> obj = new HashMap<String, String>();

			for (Inquilino i : user.getInquilino()) {
				obj = new HashMap<String, String>();

				obj.put("id", i.getId() + "");
				obj.put("name", i.getFirstname() + " " + i.getLastname() + "");
				obj.put("dni", i.getDni() + "");
				obj.put("pais", i.getPais() + "");
				obj.put("vivienda", i.getVivienda() + "");
				obj.put("email", i.getEmail() + "");
				obj.put("telefono", i.getTelefono() + "");
				dataList.add(obj);

			}
			Map<String, Object> ss = new HashMap<String, Object>();
			ss.put("data", dataList);

			byte[] bytes = null;
			BufferedInputStream fileInputStream = null;
			try {
				File file = pdfGenaratorUtil.createPdf("lista", ss);
				fileInputStream = new BufferedInputStream(new FileInputStream(file));
				// fileInputStream =
				// Thread.currentThread().getContextClassLoader().getResourceAsStream(this.filePath);
				bytes = new byte[(int) file.length()];
				fileInputStream.read(bytes);

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_PDF);
				// Here you have to set the actual filename of your pdf
				headers.setContentDispositionFormData(file.getName(), file.getName());
				headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
				ResponseEntity<byte[]> response = new ResponseEntity<>(bytes, headers, HttpStatus.OK);
				return response;

			} catch (FileNotFoundException ex) {
				throw ex;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;

	}

	/*
	 * @RequestMapping("/api/getPdfInquilinos") public
	 * ResponseEntity<InputStreamResource> savePDF(@RequestBody Inquilino details)
	 * throws IOException, DocumentException { Authentication auth =
	 * SecurityContextHolder.getContext().getAuthentication(); User user=null; if
	 * (!(auth instanceof AnonymousAuthenticationToken)) { user =
	 * userService.findUserByEmail(auth.getName()); List<Map<String, String>>
	 * dataList = new ArrayList<Map<String, String>>();
	 * 
	 * Map<String, String> obj = new HashMap<String, String>();
	 * 
	 * for (Inquilino i : user.getInquilino()) { obj = new HashMap<String,
	 * String>();
	 * 
	 * obj.put("id", i.getId() + ""); obj.put("name", i.getFirstname() + " " +
	 * i.getLastname() + ""); obj.put("dni", i.getDni() + ""); obj.put("pais",
	 * i.getPais() + ""); obj.put("vivienda", i.getVivienda() + "");
	 * obj.put("email", i.getEmail() + ""); obj.put("telefono", i.getTelefono() +
	 * ""); dataList.add(obj);
	 * 
	 * } HttpHeaders headers = new HttpHeaders(); headers.add("Content-Disposition",
	 * "inline; filename=citiesreport.pdf");
	 * 
	 * Context context = new Context();
	 * 
	 * context.setVariable("listaInquilinos",dataList);
	 * 
	 * 
	 * String htmlContentToRender = templateEngine.process("pdf-template", context);
	 * String xHtml = xhtmlConvert(htmlContentToRender);
	 * 
	 * ITextRenderer renderer = new ITextRenderer();
	 * 
	 * String baseUrl = FileSystems .getDefault() .getPath("src", "main",
	 * "resources","templates") .toUri() .toURL() .toString();
	 * System.out.println("----------------"); System.out.println(baseUrl);
	 * System.out.println("----------------"); renderer.setDocumentFromString(xHtml,
	 * baseUrl); renderer.layout();
	 * 
	 * FileOutputStream outputStream = new FileOutputStream("src//test.pdf"); try {
	 * renderer.createPDF(outputStream); } catch (com.lowagie.text.DocumentException
	 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * outputStream.close(); ByteArrayInputStream bais=new
	 * ByteArrayInputStream(out.toByteArray()); return
	 * ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
	 * .body(new InputStreamResource(bais));
	 * 
	 * } else { return null; } }
	 * 
	 * private String xhtmlConvert(String html) throws UnsupportedEncodingException
	 * { Tidy tidy = new Tidy(); tidy.setInputEncoding("UTF-8");
	 * tidy.setOutputEncoding("UTF-8"); tidy.setXHTML(true); ByteArrayInputStream
	 * inputStream = new ByteArrayInputStream(html.getBytes("UTF-8"));
	 * ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	 * tidy.parseDOM(inputStream, outputStream); return
	 * outputStream.toString("UTF-8"); } // ANTIGUO PDF
	 * 
	 * @RequestMapping(value = "/api/getPdfInquilino", method = RequestMethod.GET,
	 * produces = MediaType.APPLICATION_PDF_VALUE) public
	 * ResponseEntity<InputStreamResource> getPDF() { Authentication auth =
	 * SecurityContextHolder.getContext().getAuthentication(); User user = null; if
	 * (!(auth instanceof AnonymousAuthenticationToken)) { user =
	 * userService.findUserByEmail(auth.getName()); } HttpHeaders headers = new
	 * HttpHeaders(); headers.add("Content-Disposition",
	 * "inline; filename=citiesreport.pdf"); Document document = new
	 * Document(PageSize.A4); ByteArrayOutputStream out = new
	 * ByteArrayOutputStream(); try {
	 * 
	 * PdfWriter pdfWriter = PdfWriter.getInstance(document,out); document.open();
	 * document.addAuthor("Real Gagnon"); document.addCreator("Real's HowTo");
	 * document.addSubject("Thanks for your support"); document.addCreationDate();
	 * document.addTitle("Please read this");
	 * 
	 * XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
	 * 
	 * String str = "<html><head></head><body>"+
	 * "<a href='http://www.rgagnon.com/howto.html'><b>Real's HowTo</b></a>" +
	 * "<h1>Show your support</h1>" +
	 * "<p>It DOES cost a lot to produce this site - in ISP storage and transfer fees, "
	 * +
	 * "in personal hardware and software costs to set up test environments, and above all,"
	 * +
	 * "the huge amounts of time it takes for one person to design and write the actual content.</p>"
	 * +
	 * "<p>If you feel that effort has been useful to you, perhaps you will consider giving something back?</p>"
	 * + "<p>Donate using PayPal� to real@rgagnon.com.</p>" +
	 * "<p>Contributions via PayPal are accepted in any amount</p>" +
	 * "<P><br/><table border='1'><tr><td>Java HowTo</td></tr><tr>" +
	 * "<td style='background-color:red;'>Javascript HowTo</td></tr>" +
	 * "<tr><td>Powerbuilder HowTo</td></tr></table></p>" + "</body></html>";
	 * worker.parseXHtml(pdfWriter, document, new StringReader(str));
	 * document.close(); System.out.println("Done."); } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * try { PdfWriter.getInstance(document, out); } catch (DocumentException e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); }
	 * ByteArrayInputStream bais=new ByteArrayInputStream(out.toByteArray()); return
	 * ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
	 * .body(new InputStreamResource(bais)); }
	 */

	// ANTIGUO PDF

	@RequestMapping(value = "/api/getPdfInquilinos", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> getPDF() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = null;
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			user = userService.findUserByEmail(auth.getName());
		}

		ByteArrayInputStream bis = inquilinosReport(user.getInquilino());

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	public static ByteArrayInputStream inquilinosReport(List<Inquilino> inquilinos) {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfPTable table = new PdfPTable(7);
			table.setWidthPercentage(100);

			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("DNI", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Name", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Apellidos", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Pais", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Vivienda", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Teléfono", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Correo", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			for (Inquilino inquilino : inquilinos) {

				PdfPCell cell;

				cell = new PdfPCell(new Phrase(inquilino.getDni() + ""));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(inquilino.getFirstname() + " "));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(" " + inquilino.getLastname()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(" " + inquilino.getPais()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(" " + inquilino.getVivienda()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(" " + inquilino.getTelefono()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(" " + inquilino.getEmail()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
			}

			PdfWriter.getInstance(document, out);
			document.open();
			document.add(table);

			document.close();

		} catch (DocumentException ex) {

			ex.printStackTrace();
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

}