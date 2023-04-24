package top.easternday.graphviewer.pdfhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class PdfPageService {
    @Autowired
    private PdfPageRepository pdfPageRepository;

    public Optional<byte[]> getImageData(String pdfName, int pageNumber) {
        Optional<PdfPage> pdfPageOptional = pdfPageRepository.findById(new PdfPage.PdfPageKey(pdfName, pageNumber));
        return pdfPageOptional.map(PdfPage::getImageData);
    }

    public void putImageData(String pdfName, int pageNumber, byte[] imageData) {
        PdfPage pdfPage = new PdfPage(pdfName, pageNumber, imageData, new Timestamp(System.currentTimeMillis()));
        pdfPageRepository.save(pdfPage);
    }
}