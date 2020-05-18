package edu.isu.xrese.server.base.db

import org.javalite.activejdbc.LazyList
import org.javalite.activejdbc.Model

import java.sql.Date

/**
 * Singleton class to export the contents of a database into a JSON document
 *
 * @author Isaac Griffith
 * @version 1.0.0
 */
abstract class DataExporter {

    private Map<String, Closure<LazyList<? extends Model>>> modelMap = [:]

    private Map<String, Closure<LazyList<? extends Model>>> modelMapAll = [:]

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

    abstract void initMaps()
}
