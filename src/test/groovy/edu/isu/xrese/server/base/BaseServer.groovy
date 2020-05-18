package edu.isu.xrese.server.base

import io.javalin.Javalin
import io.javalin.core.JavalinConfig

import java.util.function.Consumer

/**
 * Class containing the basic logic needed to setup and get a server running.
 * This does not contain any route information. Rather it leaves this to the
 * child classes. Note that this is an implementation of the Template Method
 * pattern.
 *
 * @author Isaac Griffith
 * @version 1.0.0
 */
abstract class BaseServer {

    protected Javalin app
    protected ServerContext context
    protected DbManager manager

    /**
     * Initializes the loads the configuration, sets up the database manager instance,
     * creates the server and calls to setup the routes.
     */
    final void init() {
        ConfigLoader loader = ConfigLoader.instance
        context = loader.load(new File("server.conf"))
        manager = DbManager.instance

        app = Javalin.create(config())

        routes()
    }

    /**
     * Start the server on the port specified in the configuration
     * or at port 7000 if the configuration is null or the port is
     * not specified or not a number
     */
    final void start() {
        int port
        try {
            port = Integer.parseInt(context.server.port)
        } catch (Exception ex) {
            port = 7000
        }

        manager.open(context)
        app.start(port)
    }

    /**
     * Stops the server
     */
    final void stop() {
        app.stop()
        manager.close()
    }

    /**
     * Hook method for setting up the routes for this server. This
     * method is called by init()
     */
    abstract void routes()

    /**
     * Hook Method which provides the JavalinConfig information to the server
     * the default returns an empty configuration
     *
     * @return a Consumer of the JavalinConfig used in customizing the server
     */
    Consumer<JavalinConfig> config() {
        return {}
    }
}