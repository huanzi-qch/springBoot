package cn.huanzi.qch.springbootocr;

import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
public class ImageOcrController {

    private final ImageOcrService imageOcrService;

    @Autowired
    public ImageOcrController(ImageOcrService imageOcrService) {
        this.imageOcrService = imageOcrService;
    }

    // http://localhost:10093/ocr/local?path=D:\test.png
    @GetMapping("/ocr/local")
    public ResponseEntity<String> ocrLocalImage(@RequestParam("path") String imagePath) throws IOException, TesseractException {
        String result  = imageOcrService.ocrLocalImage(imagePath);
        System.out.println(result);
        return ResponseEntity.ok(result);
    }

    // http://localhost:10093/ocr/remote?url=https://i-blog.csdnimg.cn/blog_migrate/52d7bc4fc9ac8cf99e34421bac3b35a6.png
    @GetMapping("/ocr/remote")
    public ResponseEntity<String> ocrRemoteImage(@RequestParam("url") String imageUrl) throws IOException, TesseractException, URISyntaxException {
        String result = imageOcrService.ocrRemoteImage(imageUrl);
        System.out.println(result);
        return ResponseEntity.ok(result);
    }
}
