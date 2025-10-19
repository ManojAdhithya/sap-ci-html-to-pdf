import com.sap.gateway.ip.core.customdev.util.Message
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder

import java.io.ByteArrayOutputStream

def Message processData(Message message) {
    // Step 1: Get HTML from message body
    def html = message.getBody(String)
    
    // Step 2: Convert HTML to PDF
    ByteArrayOutputStream pdfStream = new ByteArrayOutputStream()

    PdfRendererBuilder builder = new PdfRendererBuilder()
    builder.useFastMode()
    builder.withHtmlContent(html, null)
    builder.toStream(pdfStream)
    builder.run()

    byte[] pdfBytes = pdfStream.toByteArray()

    // Step 3: Set the PDF as the new message body
    message.setBody(pdfBytes)

    // Step 4: Set headers for downstream handling
    message.setHeader("Content-Type", "application/pdf")
    // Optional: message.setHeader("Content-Disposition", "attachment; filename=\"document.pdf\"")

    return message
}
