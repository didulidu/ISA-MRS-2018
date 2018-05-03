package com.cinemas_theaters.cinemas_theaters.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.cinemas_theaters.cinemas_theaters.domain.dto.FriendDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Friendship;
import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomFriendshipSerializer extends StdSerializer<Map<RegisteredUser, Friendship>>{
    public CustomFriendshipSerializer() {
        this(null);
    }

    public CustomFriendshipSerializer(Class<Map<RegisteredUser, Friendship>> t) {
        super(t);
    }

    @Override
    public void serialize(
            Map<RegisteredUser, Friendship> friendships,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException {

        List<FriendDTO> ids = new ArrayList<FriendDTO>();
        for (Friendship friendship : friendships.values()) {
            ids.add(new FriendDTO(friendship.getSecondUser().getName(), friendship.getSecondUser().getLastname(),
                    friendship.getSecondUser().getUsername(), friendship.getStatus()));
        }
        generator.writeObject(ids);
    }
}
