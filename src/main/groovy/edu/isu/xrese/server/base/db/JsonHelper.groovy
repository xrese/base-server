/*
 * MIT License
 *
 * Copyright (c) 2020 Isaac Griffith and Idaho State University
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package edu.isu.xrese.server.base.db

import com.fasterxml.jackson.databind.ObjectMapper

/**
 * A Simple Utility class used to convert JSON to maps used to load data by ActiveJDBC
 */
class JsonHelper {

    /**
     * Loads a single instance of a Model from json
     * @param json Json representation of the Model
     * @return Map that can be processed by ActiveJDBC
     */
    static Map toMap(String json) {
        ObjectMapper mapper = new ObjectMapper()
        try {
            return mapper.readValue(json, Map.class)
        } catch (IOException e) { throw new RuntimeException(e) }
    }

    /**
     * Loads multiple instances of a Model from json
     * @param json json representation of the model instances
     * @return an array of maps to be converted to a LazyList<> of the model type
     */
    static Map[] toMaps(String json) {
        ObjectMapper mapper = new ObjectMapper()
        try {
            return mapper.readValue(json, Map[].class)
        } catch (IOException e) { throw new RuntimeException(e) }
    }
}
