package edu.isu.xrese.server.base.db


import edu.isu.xrese.server.base.config.ServerContext

import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

/**
 * Class used for populating an initial database
 * Classes implementing this interface should provide the details specific
 * to a particular database.
 *
 * @author Isaac Griffith
 * @version 1.0.0
 */
abstract class DbPopulator {

    ServerContext context
    List<String> tables = []

    DbPopulator(ServerContext context) {
        this.context = context
        initTables()
    }

    void populate() {
        try {
            Connection conn = DriverManager.getConnection(context.db.url, context.db.user, context.db.pass)

            if (conn) {
                tables.each {
                    try {
                        Statement stmt = conn.createStatement()
                        stmt.execute("drop table $it;")
                        stmt.closeOnCompletion()
                    } catch (Exception e) {
                    }
                }

                def text = DbManager.class.getResourceAsStream("/edu/isu/xrese/gamma/domain/reset_${context.db.type}.sql")
                // be sure to not have line starting with "--" or "/*" or any other non aplhabetical character

                // here is our splitter ! We use ";" as a delimiter for each request
                // then we are sure to have well formed statements
                String[] inst = text.split(";")

                for (int i = 0; i < inst.length; i++) {
                    // we ensure that there is no spaces before or after the request string
                    // in order to not execute empty statements
                    if (inst[i].trim() != "") {
                        Statement stmt = conn.createStatement()
                        stmt.execute(inst[i])
                        System.out.println(">>" + inst[i])
                        stmt.closeOnCompletion()
                    }
                }
                conn.close()
            }
        }
        catch (Exception e ) {
            System.out.println("*** Error : " + e.toString())
            System.out.println("*** ")
            System.out.println("*** Error : ")
            e.printStackTrace()
            System.out.println("################################################")
            System.out.println(sb.toString())
        }
    }

    abstract List<String> initTables()
}
