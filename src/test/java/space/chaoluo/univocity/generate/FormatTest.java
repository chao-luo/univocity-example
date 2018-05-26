package space.chaoluo.univocity.generate;

import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.csv.CsvFormat;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import space.chaoluo.univocity.Student;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static space.chaoluo.univocity.Constants.HEADERS;

@Slf4j
public class FormatTest {

    @Test
    public void excludeFields() throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Student student = new Student("1111111111111111111111", "@testUser", 20);

            CsvFormat csvFormat = new CsvFormat();
            csvFormat.setQuote('@');
            csvFormat.setQuoteEscape('*');
            csvFormat.setDelimiter('|');

            final CsvWriterSettings csvWriterSettings = new CsvWriterSettings();
            csvWriterSettings.setHeaderWritingEnabled(Boolean.TRUE);
            csvWriterSettings.setQuoteAllFields(true);
            csvWriterSettings.setFormat(csvFormat);
            csvWriterSettings.setQuoteEscapingEnabled(true);
            csvWriterSettings.setHeaders(HEADERS);
            csvWriterSettings.setRowWriterProcessor(new BeanWriterProcessor<>(Student.class));
            CsvWriter writer = new CsvWriter(outputStream, csvWriterSettings);
            writer.processRecord(student);
            writer.close();

            final byte[] out = outputStream.toByteArray();
            log.info("output: {}", new String(out));
        }
    }
}
