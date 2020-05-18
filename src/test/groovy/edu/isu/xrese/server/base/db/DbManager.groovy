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
