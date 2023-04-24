package top.easternday.graphviewer.pdfhandler;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
public class PdfFileInfoService {

    @Autowired
    private PdfFileInfoRepository pdfFileInfoRepository;

    public int getPagecount(String name) throws IOException {
        Optional<PdfFileInfo> optionalPagecount = pdfFileInfoRepository.findById(name);
        if (optionalPagecount.isPresent()) {
            return optionalPagecount.get().getPageCount();
        } else {
            try (InputStream inputStream = getClass().getResourceAsStream("/pdf/" + name + ".pdf")) {
                PDDocument document = PDDocument.load(inputStream);
                int pageCount = document.getNumberOfPages();
                PdfFileInfo pdfPagecount = new PdfFileInfo(name, pageCount);
                pdfFileInfoRepository.save(pdfPagecount);
                return pageCount;
            }
        }
    }

}
