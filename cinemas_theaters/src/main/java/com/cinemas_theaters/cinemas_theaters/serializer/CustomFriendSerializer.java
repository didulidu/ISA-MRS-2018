package com.cinemas_theaters.cinemas_theaters.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;

import java.io.IOException;

public class CustomFriendSerializer extends StdSerializer<RegisteredUser> {
    public CustomFriendSerializer() {
        this(null);
    }

    public CustomFriendSerializer(Class<RegisteredUser> t) {
        super(t);
    }

    @Override
    public void serialize(
            RegisteredUser registeredUser,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException {

        generator.writeObject(registeredUser.getUsername());
    }
}
