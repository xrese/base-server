package edu.isu.xrese.server.base.config

/**
 * Data class used to contain configuration information for the component currently operating.
 * This information includes (but not limited to) database connection information and server port number.
 *
 * @author Isaac Griffith
 * @version 1.0.0
 */
class ServerContext {

    Map<String, String> db
    Map<String, String> server
}

