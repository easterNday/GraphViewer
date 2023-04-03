package top.easternday.graphviewer.pdfhandler;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

@RestController
@CrossOrigin
public class PdfController {

    @GetMapping(value = "/pdf/{name}")
    public byte[] getPdf(@PathVariable String name) throws IOException {
        // 读取 PDF 文件并将其转换为字节数组
        InputStream pdfInputStream = getClass().getResourceAsStream("/pdf/" + name + ".pdf");
        assert pdfInputStream != null;
        byte[] pdfBytes = IOUtils.toByteArray(pdfInputStream);

        // 读取 JSON 文件并将其转换为字节数组
        InputStream jsonInputStream = getClass().getResourceAsStream("/pdf/" + name + ".json");
        assert jsonInputStream != null;
        byte[] jsonBytes = IOUtils.toByteArray(jsonInputStream);

        // 合并字节数组
        ByteBuffer buffer = ByteBuffer.allocate(4 + jsonBytes.length + pdfBytes.length);
        buffer.putInt(jsonBytes.length);
        buffer.put(jsonBytes);
        buffer.put(pdfBytes);

        // 返回字节数组
        return buffer.array();
    }
}