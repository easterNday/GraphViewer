package top.easternday.graphviewer.pdfhandler;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "pdf_pages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdfPage {
    @EmbeddedId
    private PdfPageKey id;

    @Lob
    @Column(name = "image_data", nullable = false)
    private byte[] imageData;

    @Column(name = "last_updated", nullable = false)
    private Timestamp lastUpdated;

    public PdfPage(String pdfName, int pageNumber, byte[] imageData, Timestamp lastUpdated) {
        this.id = new PdfPageKey(pdfName, pageNumber);
        this.imageData = imageData;
        this.lastUpdated = lastUpdated;
    }

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PdfPageKey implements Serializable {
        @Column(name = "pdf_name", nullable = false)
        private String pdfName;

        @Column(name = "page_number", nullable = false)
        private int pageNumber;
    }
}