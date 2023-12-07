package fi.ishtech.happeo.codingexercise.mapper;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 *
 * @author Muneer Ahmed Syed
 */
@JsonComponent
public class PageJsonSerializer<T> extends JsonSerializer<Page<T>> {

	@Override
	public void serialize(Page<T> page, JsonGenerator gen, SerializerProvider serializers) throws IOException {
	    gen.writeStartObject();

	    gen.writeObjectField("content", page.getContent());

	    gen.writeBooleanField("empty", page.isEmpty());
	    gen.writeBooleanField("first", page.isFirst());
	    gen.writeBooleanField("last", page.isLast());
	    gen.writeNumberField("number", page.getNumber());
	    gen.writeNumberField("numberOfElements", page.getNumberOfElements());
	    gen.writeNumberField("size", page.getSize());
	    gen.writeNumberField("totalPages", page.getTotalPages());
	    gen.writeNumberField("totalElements", page.getTotalElements());

	    // mostly duplicate data (pageable.pageSize -> size, pageable.offset -> number, etc.)
	    // need special care in `Spring Boot 3.2.0` if `pageable` object is `unpaged`; otherwise error
	    if (page.getPageable().isUnpaged()) {
	      gen.writeStringField("pageable", "INSTANCE");
	    } else {
	      gen.writeObjectField("pageable", page.getPageable());
	    }

	    gen.writeObjectField("sort", page.getSort());

	    gen.writeEndObject();
	}

}
