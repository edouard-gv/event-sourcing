package com.gomezvaez.eventsourcing.eventstore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EventConverter implements AttributeConverter<Event, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Event event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert event to JSON", e);
        }
    }

    @Override
    public Event convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, Event.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON to event", e);
        }
    }
}