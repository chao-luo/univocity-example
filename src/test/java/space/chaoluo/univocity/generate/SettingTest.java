package space.chaoluo.univocity.generate;

import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import space.chaoluo.univocity.Student;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static space.chaoluo.univocity.Constants.HEADERS;

@Slf4j
public class SettingTest {

    @Test
    public void excludeFields() throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Student student = new Student("1111111111111111111111", "testUser", 20);
            final CsvWriterSettings csvWriterSettings = new CsvWriterSettings();
            csvWriterSettings.setHeaderWritingEnabled(Boolean.TRUE);
            csvWriterSettings.excludeFields("userName");
            csvWriterSettings.setHeaders(HEADERS);
            csvWriterSettings.setRowWriterProcessor(new BeanWriterProcessor<>(Student.class));
            CsvWriter writer = new CsvWriter(outputStream, csvWriterSettings);
            writer.processRecord(student);
            writer.close();

            final byte[] out = outputStream.toByteArray();
            log.info("output: {}", new String(out));
        }
    }

    @Test
    public void selectFields() throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Student student = new Student("1111111111111111111111", "testUser", 20);
            final CsvWriterSettings csvWriterSettings = new CsvWriterSettings();
            csvWriterSettings.setHeaderWritingEnabled(Boolean.TRUE);
            csvWriterSettings.selectFields("userName");
            csvWriterSettings.setHeaders(HEADERS);
            csvWriterSettings.setRowWriterProcessor(new BeanWriterProcessor<>(Student.class));
            CsvWriter writer = new CsvWriter(outputStream, csvWriterSettings);
            writer.processRecord(student);
            writer.close();

            final byte[] out = outputStream.toByteArray();
            log.info("output: {}", new String(out));
        }
    }

    @Test
    public void quoteEscapingEnabled() throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Student student = new Student("1111111111111111111111", "My \"precious\"", 20);
            final CsvWriterSettings csvWriterSettings = new CsvWriterSettings();
            csvWriterSettings.setHeaderWritingEnabled(Boolean.TRUE);
            csvWriterSettings.setHeaders(HEADERS);
            csvWriterSettings.setQuoteEscapingEnabled(true);
            csvWriterSettings.setRowWriterProcessor(new BeanWriterProcessor<>(Student.class));
            CsvWriter writer = new CsvWriter(outputStream, csvWriterSettings);
            writer.processRecord(student);
            writer.close();

            final byte[] out = outputStream.toByteArray();
            log.info("output: {}", new String(out));
        }
    }

    @Test
    public void quoteAllFields() throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Student student = new Student("1111111111111111111111", "test", 20);
            final CsvWriterSettings csvWriterSettings = new CsvWriterSettings();
            csvWriterSettings.setHeaderWritingEnabled(Boolean.TRUE);
            csvWriterSettings.setHeaders(HEADERS);
            csvWriterSettings.setQuoteAllFields(true);
            csvWriterSettings.setRowWriterProcessor(new BeanWriterProcessor<>(Student.class));
            CsvWriter writer = new CsvWriter(outputStream, csvWriterSettings);
            writer.processRecord(student);
            writer.close();

            final byte[] out = outputStream.toByteArray();
            log.info("output: {}", new String(out));
        }
    }
}
