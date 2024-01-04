package th.co.cdgs.mapper;

import java.text.SimpleDateFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.jackson.ObjectMapperCustomizer;
import jakarta.inject.Singleton;

@Singleton
public class RegisterCustomModuleCustomizer 
        implements ObjectMapperCustomizer 
{

    public static final String OBJECT_MAPPER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    public void customize(ObjectMapper mapper) {
        mapper.setDateFormat(new SimpleDateFormat(OBJECT_MAPPER_DATE_FORMAT));
    }


}
