package com.juanjo.proyecto.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.juanjo.proyecto.model.Alquiler;
import com.juanjo.proyecto.model.Casa;
import com.juanjo.proyecto.model.Inquilino;
import com.juanjo.proyecto.model.User;
import com.juanjo.proyecto.service.AlquilerService;
import com.juanjo.proyecto.service.CasaService;
import com.juanjo.proyecto.service.InquilinoService;
import com.juanjo.proyecto.service.UserService;

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

	@GetMapping("/api/myData")
	public String greeting(@RequestParam(value = "casa") String casa,
			@RequestParam(value = "fechaEntrada") String fechaEntrada,
			@RequestParam(value = "fechaSalida") String fechaSalida, @RequestParam(value = "precio") String precio,
			@RequestParam(value = "inquilino") String inquilino) {
		if (casa != null && fechaEntrada != null && fechaSalida != null && precio != null && inquilino != null) {
			Casa c = casaService.findCasaByCodVivienda(casa);
			Alquiler a = new Alquiler();
			a.setCasa(c);
			a.setFechaEntrada(fechaEntrada);
			a.setFechaSalida(fechaSalida);
			a.setPrecio(Float.parseFloat(precio));
			if (inquilino != null) {
				a.setInquilino(inquilinoService.findById(Integer.parseInt(inquilino)));
			}
			alquilerService.saveAlquiler(a);
			return "succed";
		} else {
			return "error";
		}

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
	@RequestMapping(value = "/api/getPdfInquilinos", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> getPDF() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user=null;
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			user = userService.findUserByEmail(auth.getName());
		}

        ByteArrayInputStream bis = inquilinosReport(user.getInquilino());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

	public static ByteArrayInputStream inquilinosReport(List<Inquilino> inquilinos) {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(60);
            table.setWidths(new int[]{1, 3, 3});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Id", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Name", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Population", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            for (Inquilino inquilino : inquilinos) {

                PdfPCell cell;

                cell = new PdfPCell(new Phrase(inquilino.getId()+""));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(inquilino.getFirstname()+" "+inquilino.getLastname()));
                cell.setPaddingLeft(5);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(inquilino.getDni())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
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