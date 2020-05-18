package edu.isu.xrese.server.base.db

import org.javalite.activejdbc.LazyList
import org.javalite.activejdbc.Model

/**
 * Singleton class which imports the contents of a JSON document into the database
 *
 * @author Isaac Griffith
 * @version 1.0.0
 */
abstract class DataImporter {

    private Closure<Map[]> hydrate = { json ->
        JsonHelper.toMaps(json)
    }

    Map<String, Closure<LazyList<? extends Model>>> modelMap = [:]

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

    abstract void initMaps()
}
