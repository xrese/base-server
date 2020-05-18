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

import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

/**
 * Class used for populating an initial database
 * Classes implementing this interface should provide the details specific
 * to a particular database.
 *
 * @author Isaac Griffith
 * @version 1.0.2
 */
abstract class DbPopulator {

    ServerContext context
    List<String> tables = []

    /**
     * Constructs a new DbPopulator for the given ServerContext
     * @param context Context specifying the connection information
     */
    DbPopulator(ServerContext context) {
        this.context = context
        initTables()
    }

    /**
     * Populates a database using the connection details from the provided server context and the provided class
     * used to find the reset scripts which are to be located under /database/reset/[dbms_type].sql
     *
     * @param clazz the class from which the database reset script will be located as a resource
     */
    void populate(Class clazz) {
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

                def text = clazz.getResourceAsStream("/database/reset/${context.db.type}.sql")
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

    /**
     * initializes the tables list, which is simply a list of the table names from the database
     */
    abstract void initTables()
}
