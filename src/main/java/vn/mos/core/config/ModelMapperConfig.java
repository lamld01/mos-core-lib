package vn.mos.core.config;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // ✅ Only map non-null fields (avoid overriding with null values)
        modelMapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull())
                .setMatchingStrategy(MatchingStrategies.STRICT) // Strict field matching
                .setFieldMatchingEnabled(true) // Enable field matching by name
                .setSkipNullEnabled(true); // Skip null values during mapping

        return modelMapper;
    }
}
