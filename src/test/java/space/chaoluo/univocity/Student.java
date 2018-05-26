package space.chaoluo.univocity;

import com.univocity.parsers.annotations.Parsed;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Parsed(field = "userNumber")
    private String userNumber;

    @Parsed(field = "userName")
    private String userName;

    @Parsed(field = "age")
    private Integer age;

}
