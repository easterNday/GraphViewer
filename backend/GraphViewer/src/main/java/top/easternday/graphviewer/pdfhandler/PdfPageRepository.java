package top.easternday.graphviewer.pdfhandler;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdfPageRepository extends JpaRepository<PdfPage, PdfPage.PdfPageKey> {
}
