package space.chaoluo.univocity.example000;

import com.univocity.parsers.annotations.Parsed;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.List;

@Slf4j
public class SettingTest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Student {

        @Parsed(field = "userNumber")
        private String userNumber;

        @Parsed(field = "userName")
        private String userName;

        @Parsed(field = "age")
        private Integer age;

    }

    public static final String[] HEADERS = new String[]{"userNumber", "userName", "age"};

    @Test
    public void testSetting() throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            // 生成
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


            // 解析
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
