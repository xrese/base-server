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

import org.javalite.activejdbc.LazyList
import org.javalite.activejdbc.Model

/**
 * Class which imports the contents of a JSON document into the database
 *
 * @author Isaac Griffith
 * @version 1.0.1
 */
abstract class DataImporter {

    private Closure<Map[]> hydrate = { json ->
        JsonHelper.toMaps(json)
    }

    Map<String, Closure<LazyList<? extends Model>>> modelMap = [:]

    /**
     * Constructs a new DataImporter and initializes the maps
     */
    DataImporter() {
        initMaps()
    }

    /**
     * Imports the json data for a given table
     *
     * @param table The table into which the json data is to be imported
     * @param data The json data to be imported
     * @throws IllegalArgumentException if the value of table or data is null or the value of table is not a known table
     */
    final void importData(String table, String data) {
        if (!table || !data)
            throw new IllegalArgumentException()
        if (!modelMap[table])
            throw new IllegalArgumentException()

        modelMap[table](data)
    }

    /**
     * Initializes the map to process data for a given table
     *
     * An example of the modelMap would be:
     *
     * modelMap = [
     *     "company"          : { String json -> hydrate(json).each { map -> Company x = new Company(); x.fromMap(map); x.save() } },
     *     "company_industry" : { String json -> hydrate(json).each { map -> CompanyIndustry x = new CompanyIndustry(); x.fromMap(map); x.save() } }
     * ]
     */
    abstract void initMaps()
}
