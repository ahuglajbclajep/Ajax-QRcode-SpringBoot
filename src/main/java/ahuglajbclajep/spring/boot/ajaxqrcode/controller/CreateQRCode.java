package ahuglajbclajep.spring.boot.ajaxqrcode.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.EnumMap;

@RestController
public class CreateQRCode {
    private final static Logger logger = LoggerFactory.getLogger(CreateQRCode.class);

    @PostMapping("/create")
    public ResponseEntity<byte[]> sendImage(@RequestBody String body) {
        // If body is empty, Spring will display 'Failed to read HTTP message' and processing will be aborted.
        logger.info("Received request: body: [{}]", body);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(createQRCode(body), "jpg", baos);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(baos.toByteArray());
        } catch (IOException | WriterException e) {
            logger.info("Failed to create QRCode", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    private BufferedImage createQRCode(String contents) throws WriterException {
        // The width and height of the image are determined by first creating an image,
        // adding a margin (4*2 pixels), and comparing it with the specified length.
        final int SIZE = 200;

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
}
