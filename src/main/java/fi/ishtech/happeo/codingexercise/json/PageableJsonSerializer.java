package fi.ishtech.happeo.codingexercise.json;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Custom {@link JsonSerializer} for {@link Pageable}<br>
 * In Spring Boot 3.2.0, serializing Unpaged causes UnsupportedOperationException for certain fields.
 *
 * @author Muneer Ahmed Syed
 */
@JsonComponent
public class PageableJsonSerializer extends JsonSerializer<Pageable> {

	@Override
	public void serialize(Pageable pageable, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeStartObject();

		gen.writeBooleanField("paged", pageable.isPaged());
		gen.writeBooleanField("unpaged", pageable.isUnpaged());

		if (pageable.isUnpaged()) {
			// skip pageSize, pageNumber, offset as they throw exception
		} else {
			gen.writeNumberField("pageSize", pageable.getPageSize());
			gen.writeNumberField("pageNumber", pageable.getPageNumber());
			gen.writeNumberField("offset", pageable.getOffset());
		}

		gen.writeObjectField("sort", pageable.getSort());

		gen.writeEndObject();
	}

}
