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

import edu.isu.xrese.server.base.config.ServerContext
import org.javalite.activejdbc.Base

/**
 * Utility class which sets up, opens, and closes a DB connection.\
 *
 * @author Isaac Griffith
 * @version 1.0.0
 */
class DbManager {

    private boolean open
    private final def drivers = [
            "mysql"      : "com.mysql.jdbc.Driver",
            "mariadb"    : "org.mariadb.jdbc.Driver",
            "postgresql" : "org.postgresql.Driver",
            "mssql"      : "com.microsoft.sqlserver.jdbc.SQLServerDrive",
            "sqlite"     : "org.sqlite.JDBC",
            "derby"      : "org.apache.derby.jdbc.EmbeddedDriver",
            "h2"         : "org.h2.Driver",
            "default"    : "sun.jdbc.odbc.JdbcOdbcDriver"
    ]

    DataImporter importer
    DataExporter exporter

    /**
     * Constructs a new DbManager which uses the provided data importer and exporter
     * @param importer The data importer for a given table
     * @param exporter The data exporter for a given table
     */
    DbManager(DataImporter importer, DataExporter exporter) {
        this.importer = importer
        this.exporter = exporter
    }

    /**
     * Opens a connection to the database specified in the given Model Context, if not already open
     * @param context ModelContext containing the specification for the database connection
     */
    void open(ServerContext context) {
        if (!open) {
            String driver = drivers[context.db.type] ? drivers[context.db.type] : drivers["default"]
            Base.open(driver, context.db.url, context.db.user, context.db.pass)
        }
        open = true
    }

    /**
     * Test whether the connection to the database is currently open
     * @return true is open, false otherwise
     */
    boolean isOpen() {
        return open
    }

    /**
     * Closes the database connection if it is currently open
     */
    void close() {
        if (open)
            Base.close()
        open = false
    }

    /**
     * Creates a new database in the currently open database connection
     */
    void createDatabase() {
        resetDatabase()
    }

    /**
     * Resets the database to the original default settings.
     */
    private void resetDatabase() {
    }

    /**
     * Merges the provided data into the current database
     * @param data Data to be merged in Json format
     */
    void mergeData(String table, data) {
        importer.importData(table, data)
    }

    /**
     * Exports the current contents of the database to Json
     * @return Json representation of the database contents
     */
    def dumpData(String table, java.sql.Date since = null) {
        exporter.exportData(table, since)
    }
}
