package space.chaoluo.univocity.generate;

import com.univocity.parsers.annotations.Convert;
import com.univocity.parsers.annotations.Parsed;
import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.conversions.Conversion;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import space.chaoluo.univocity.Student;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static space.chaoluo.univocity.Constants.HEADERS;

@Slf4j
public class AnnotationTest {

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Student {

        @Parsed(field = "userNumber")
        @Convert(conversionClass = HumanReadableStringOutputConvert.class)
        private String userNumber;

        @Parsed(field = "userName")
        private String userName;

        @Parsed(field = "age")
        private Integer age;
    }

    public static class HumanReadableStringOutputConvert implements Conversion<String, String> {

        private String prefix;

        private String suffix;

        public HumanReadableStringOutputConvert(String... args) {
            String defaultPrefix = "=\"";
            String defaultSuffix = "\"";
            final int length = args.length;
            if (length >= 1) {
                defaultPrefix = args[0];
            }

            if (length >= 2) {
                defaultSuffix = args[1];
            }

            this.prefix = defaultPrefix;
            this.suffix = defaultSuffix;

        }

        @Override
        public String execute(String input) {
            return null;
        }

        @Override
        public String revert(String input) {
            if (input == null) {
                return input;
            }
            return prefix + input + suffix;
        }
    }

    @Test
    public void name() throws IOException {
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
        }
    }
}
