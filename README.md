# sap-ci-html-to-pdf
Generate PDF from HTML in SAP CPI using Groovy + Apache PDFBox + OpenHTMLToPDF

Overview

SAP CPI doesn’t provide native HTML → PDF conversion.
This solution embeds required libraries as archive resources in your CPI iFlow and uses a Groovy script to convert HTML content into a PDF stream at runtime.

Tech Stack

- SAP Cloud Platform Integration (CPI / iFlow)
- Groovy Script
- Apache PDFBox
- OpenHTMLToPDF / PdfRendererBuilder
- FontBox
- Commons IO
- Graphics2D
- XmpBox

Setup Instructions

1. Download required JARs (latest stable versions recommended):

   - Apache PDFBox 2.0.27 → https://pdfbox.apache.org/download.html
   - FontBox 2.0.27 → included in PDFBox download (Can downoad separately as well)

   - XmpBox 2.0.27 → included in PDFBox download (Can downoad separately as well)

   - OpenHTMLToPDF Core 1.0.10 → https://github.com/danfickle/openhtmltopdf/releases/tag/1.0.10

   - OpenHTMLToPDF PDFBox 1.0.10 → same release page as above

   - Commons IO 2.11.0 → https://commons.apache.org/proper/commons-io/download_io.cgi

   - Graphics2D 0.40 → https://search.maven.org/artifact/com.github.jai-imageio/jai-imageio-core/1.4.0/jar
     
     Note : Links provided are for the latest stable releases. This project was tested with the versions listed above.

2. Upload JARs to CPI

   - Navigate to your iFlow → Resources → Archive

   - Upload all downloaded JARs

3. Add Groovy Script step

   - Copy the script below into a Groovy Script step

4. Pass HTML content

   - Message body should contain your HTML (e.g., sample.html)

   - Script outputs a PDF stream ready for downstream handling
  
  Groovy Script

```Groovy
import com.sap.gateway.ip.core.customdev.util.Message
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder
import java.io.ByteArrayOutputStream

def Message processData(Message message) {

    // Get HTML content from message body
    def html = message.getBody(String)
    
    // Convert HTML to PDF
    ByteArrayOutputStream pdfStream = new ByteArrayOutputStream()
    new PdfRendererBuilder()
        .useFastMode()
        .withHtmlContent(html, null)
        .toStream(pdfStream)
        .run()

    // Set PDF as message body
    message.setBody(pdfStream.toByteArray())

    // Set headers for downstream handling
    message.setHeader("Content-Type", "application/pdf")
    // Optional: message.setHeader("Content-Disposition", "attachment; filename=\"document.pdf\"")

    return message
}
```

Key Features

- Converts HTML → PDF fully inside CPI

- Supports inline CSS, fonts, and images

- No external API dependencies

- Can attach PDFs to emails, API responses, or storage

Example Usage

- Send an HTML payload to your iFlow

- Groovy script generates a PDF in the message body

- Downstream receiver (email adapter, SFTP, API, etc.) receives PDF as binary
- To view the file from trace/simulation, download the binary file and change extension to .pdf

Third-Party Libraries & Licenses

This project uses the following third-party libraries under their respective licenses:

- Apache PDFBox, FontBox, XmpBox → Apache License 2.0

- OpenHTMLToPDF → Apache License 2.0

- Commons IO → Apache License 2.0

- Graphics2D → Check library source for license


✨ Author

Developed by Manoj Adhithya

www.linkedin.com/in/manoj-adhithya

