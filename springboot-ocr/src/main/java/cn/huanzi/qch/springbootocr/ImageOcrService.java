package cn.huanzi.qch.springbootocr;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

@Service
public class ImageOcrService {

    private final Tesseract tesseract;

    @Autowired
    public ImageOcrService(Tesseract tesseract) {
        this.tesseract = tesseract;
    }

    /**
     * 从本地文件路径读取图片并进行 OCR 处理
     * @param imagePath 图片文件路径
     * @return OCR 结果文本
     */
    public String ocrLocalImage(String imagePath) throws IOException, TesseractException {
        return tesseract.doOCR(new File(imagePath));
    }

    /**
     * 从远程 URL 获取图片并进行 OCR 处理
     * @param imageUrl 图片 URL
     * @return OCR 结果文本
     */
    public String ocrRemoteImage(String imageUrl) throws IOException, TesseractException, URISyntaxException {
        String[] suffixs = imageUrl.split("\\.");
        File file = getFileFromUrl(imageUrl,suffixs[suffixs.length-1]);
        return tesseract.doOCR(file);
    }

    private static File getFileFromUrl(String imageUrl,String suffix) throws IOException {
        URL url = new URL(imageUrl);
        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();

        File tempFile = File.createTempFile("tempImage", "."+suffix);
        FileOutputStream outputStream = new FileOutputStream(tempFile);

        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer))!= -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outputStream.close();

        return tempFile;
    }
}
