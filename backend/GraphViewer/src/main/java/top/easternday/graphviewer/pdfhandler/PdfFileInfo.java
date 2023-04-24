package top.easternday.graphviewer.pdfhandler;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pdf_fileinfo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdfFileInfo {

    @Id
    @Column(name = "pdf_name", nullable = false)
    private String pdfName;

    @Column(name = "page_count")
    private int pageCount;
}