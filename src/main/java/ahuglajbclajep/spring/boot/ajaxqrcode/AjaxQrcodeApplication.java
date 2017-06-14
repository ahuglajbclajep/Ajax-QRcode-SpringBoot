package ahuglajbclajep.spring.boot.ajaxqrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.EnumMap;
import java.util.Objects;

@SpringBootApplication
@RestController
public class AjaxQrcodeApplication {

	@PostMapping("/ajax")
	public void hello(@RequestBody String body, HttpServletResponse res) {
		try (OutputStream out = res.getOutputStream()) {
			ImageIO.write(create(body), "jpg", out);
		} catch (IOException | WriterException e) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private BufferedImage create(String contents) throws WriterException {
		// The width and height of the image are determined by first creating an image,
		// adding a margin (4*2 pixels), and comparing it with the specified length.
		final int SIZE = 200;

		if (Objects.equals(contents, "")) throw new WriterException();
		return MatrixToImageWriter.toBufferedImage(
				new com.google.zxing.qrcode.QRCodeWriter().encode(contents, BarcodeFormat.QR_CODE, SIZE, SIZE,
						new EnumMap<EncodeHintType, Object>(EncodeHintType.class) {
							{
								put(EncodeHintType.CHARACTER_SET, "UTF-8");
							}
						}
				)
		);
	}

	public static void main(String[] args) {
		SpringApplication.run(AjaxQrcodeApplication.class, args);
	}
}
