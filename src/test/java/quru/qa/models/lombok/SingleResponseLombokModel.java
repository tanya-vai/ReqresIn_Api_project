package quru.qa.models.lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SingleResponseLombokModel {
    private UserData data;
    private UserSupport support;
}
