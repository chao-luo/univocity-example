package space.chaoluo.univocity.generate;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import space.chaoluo.univocity.Student;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static space.chaoluo.univocity.Constants.HEADERS;

@Slf4j
public class HowToUseTest {




    @Test
    public void howToUse() throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            // 生成CSV内容
            Student student = new Student("1111111111111111111111", "testUser", 20);
            final CsvWriterSettings csvWriterSettings = new CsvWriterSettings();
            csvWriterSettings.setHeaderWritingEnabled(Boolean.TRUE);
            csvWriterSettings.setHeaders(HEADERS);
            csvWriterSettings.setRowWriterProcessor(new BeanWriterProcessor<>(Student.class));
            CsvWriter writer = new CsvWriter(outputStream, csvWriterSettings);
            writer.processRecord(student);
            writer.close();

            final byte[] out = outputStream.toByteArray();
            log.info("output: {}", new String(out));


            // 解析CSV内容
            CsvParserSettings csvParserSettings = new CsvParserSettings();
            final BeanListProcessor beanListProcessor = new BeanListProcessor(Student.class);
            csvParserSettings.setProcessor(beanListProcessor);
            CsvParser csvParser = new CsvParser(csvParserSettings);
            csvParser.parse(new ByteArrayInputStream(out));

            final List<Student> students = beanListProcessor.getBeans();
            final String[] headers = beanListProcessor.getHeaders();
            log.info("headers: {}", String.join(",", headers));
            log.info("students: {}", students.toString());

        }
    }
}
