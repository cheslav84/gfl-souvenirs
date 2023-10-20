package gfl.havryliuk.souvenirs.util.json.schema;

import com.github.victools.jsonschema.generator.SchemaGenerator;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
import com.github.victools.jsonschema.module.jackson.JacksonModule;

import static com.github.victools.jsonschema.generator.Option.EXTRA_OPEN_API_FORMAT_VALUES;
import static com.github.victools.jsonschema.generator.OptionPreset.PLAIN_JSON;
import static com.github.victools.jsonschema.generator.SchemaVersion.DRAFT_2020_12;
import static com.github.victools.jsonschema.module.jackson.JacksonOption.RESPECT_JSONPROPERTY_REQUIRED;


/**
 * Singleton
 */
public class JsonSchemaGenerator {

    private static SchemaGenerator generator;

    private JsonSchemaGenerator() {}

    public static SchemaGenerator getSchemaGenerator() {
        if (generator == null) {
            JacksonModule module = new JacksonModule(RESPECT_JSONPROPERTY_REQUIRED);
            SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(DRAFT_2020_12, PLAIN_JSON)
                    .with(module)
                    .with(EXTRA_OPEN_API_FORMAT_VALUES);
            generator = new SchemaGenerator(configBuilder.build());
        }
        return generator;
    }

}
