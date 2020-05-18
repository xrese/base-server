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

import java.sql.Date

/**
 * Class to export the contents of a database into a JSON document
 *
 * @author Isaac Griffith
 * @version 1.0.1
 */
abstract class DataExporter {

    private Map<String, Closure<LazyList<? extends Model>>> modelMap = [:]

    private Map<String, Closure<LazyList<? extends Model>>> modelMapAll = [:]

    /**
     * Constructs a new DataExporter and initializes the maps
     */
    DataExporter() {
        initMaps()
    }

    /**
     * Generates the json of the instances of a given model named by its table name.
     *
     * @param table The name of the table from which to select items
     * @param since Date representing time after which we will select items from the table
     * @throws IllegalArgumentException if the provided table name is not a known table name
     * @return The json representing the selected items from the table
     */
    def exportData(String table, Date since) {
        if (table) {
            if (!modelMap.keySet().contains(table))
                throw new IllegalArgumentException()

            if (since) modelMap[table](since)
            else modelMapAll[table]()
        }
    }

    /**
     * Initializes the modelmap and modelmapall
     *
     * An example of modelMapAll would be:
     *
     * modelMapAll = [
     *     "company"          : { Company.findAll() },
     *     "company_industry" : { CompanyIndustry.findAll() }
     * ]
     *
     * An example of modelMap would be:
     *
     * modelMap = [
     *     "company"          : {Date since -> Company.where("updated_at > ?", since)},
     *     "company_industry" : {Date since -> CompanyIndustry.where("updated_at > ?", since)},
     * }
     */
    abstract void initMaps()
}
