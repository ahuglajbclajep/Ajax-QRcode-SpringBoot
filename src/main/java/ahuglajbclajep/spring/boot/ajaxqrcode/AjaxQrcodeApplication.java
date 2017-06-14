package ahuglajbclajep.spring.boot.ajaxqrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Objects;

@SpringBootApplication
@RestController
public class AjaxQrcodeApplication {

	@PostMapping("/ajax")
	public ResponseEntity<byte[]> getQRcode(@RequestBody String body) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			ImageIO.write(createQRcode(body), "jpg", baos);
			return ResponseEntity.ok(baos.toByteArray());
		} catch (IOException | WriterException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	private BufferedImage createQRcode(String contents) throws WriterException {
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
