/**
 * PdfController类，处理PDF文件相关的请求
 */
package top.easternday.graphviewer.pdfhandler;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

@RestController
@CrossOrigin
public class PdfController {

    // 自动注入PdfPageService和PdfFileInfoService
    @Autowired
    private PdfPageService pdfPageService;

    @Autowired
    private PdfFileInfoService pdfFileInfoService;

    /**
     * 获取PDF文件的页数
     * @param name PDF文件名
     * @return 包含页数的ResponseEntity对象
     * @throws IOException IO读取报错
     */
    @GetMapping("/pdf/{name}/pagecount")
    @Cacheable(value = "pdfPageCount", key = "#name")
    public ResponseEntity<String> getPdfPageCount(@PathVariable String name) throws IOException {
        // 调用PdfFileInfoService的getPagecount方法获取页数
        int pageCount = pdfFileInfoService.getPagecount(name);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        // 返回包含页数的ResponseEntity对象
        return new ResponseEntity<>(String.valueOf(pageCount), headers, HttpStatus.OK);
    }

    /**
     * 获取PDF文件的某一页的图片
     * @param name PDF文件名
     * @param pageNum 页码
     * @return 包含图片的ResponseEntity对象
     * @throws IOException IO读取报错
     */
    @GetMapping("/pdf/{name}_{pageNum}")
    @Cacheable(value = "pdfPageImage", key = "#name + #pageNum")
    public ResponseEntity<ByteArrayResource> getPdfPageImage(@PathVariable String name, @PathVariable int pageNum) throws IOException {
        // 调用PdfPageService的getImageData方法获取图片数据
        Optional<byte[]> imageDataOptional = pdfPageService.getImageData(name, pageNum);
        if (imageDataOptional.isPresent()) {
            // 如果缓存中存在图片数据，则直接返回包含图片数据的ResponseEntity对象
            byte[] imageData = imageDataOptional.get();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(new ByteArrayResource(imageData), headers, HttpStatus.OK);
        } else {
            // 如果缓存中不存在图片数据，则从PDF文件中读取并生成图片
            try (InputStream inputStream = getClass().getResourceAsStream("/pdf/" + name + ".pdf")) {
                PDDocument document = PDDocument.load(inputStream);
                PDFRenderer renderer = new PDFRenderer(document);
                BufferedImage image = renderer.renderImage(pageNum - 1, 1.0f, ImageType.RGB);
                document.close();

                // 压缩图片
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                compressImage(image, baos, "jpeg", 0.5f);
                byte[] imageData = baos.toByteArray();
                // 将图片数据存入缓存
                pdfPageService.putImageData(name, pageNum, imageData);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_PNG);
                // 返回包含图片数据的ResponseEntity对象
                return new ResponseEntity<>(new ByteArrayResource(imageData), headers, HttpStatus.OK);
            }
        }
    }

    /**
     * 压缩图片
     * @param image 待压缩的图片
     * @param outputStream 压缩后的输出流
     * @param formatName 图片格式
     * @param quality 压缩质量
     * @throws IOException IO读取报错
     */
    public static void compressImage(BufferedImage image, OutputStream outputStream, String formatName, float quality) throws IOException {
        // 获取 ImageWriter 对象
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(formatName);
        ImageWriter writer = writers.next();

        // 设置压缩参数
        ImageWriteParam writeParam = writer.getDefaultWriteParam();
        if (writeParam.canWriteCompressed()) {
            writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            writeParam.setCompressionQuality(quality);
        }

        // 将压缩后的数据写入输出流中
        writer.setOutput(ImageIO.createImageOutputStream(outputStream));
        IIOImage iioImage = new IIOImage(image, null, null);
        writer.write(null, iioImage, writeParam);
    }
}
