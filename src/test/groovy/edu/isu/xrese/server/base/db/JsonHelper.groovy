package edu.isu.xrese.server.base.db

import com.fasterxml.jackson.databind.ObjectMapper

class JsonHelper {

    static Map toMap(String json) {
        ObjectMapper mapper = new ObjectMapper()
        try {
            return mapper.readValue(json, Map.class)
        } catch (IOException e) { throw new RuntimeException(e) }
    }

    static Map[] toMaps(String json) {
        ObjectMapper mapper = new ObjectMapper()
        try {
            return mapper.readValue(json, Map[].class)
        } catch (IOException e) { throw new RuntimeException(e) }
    }
}
