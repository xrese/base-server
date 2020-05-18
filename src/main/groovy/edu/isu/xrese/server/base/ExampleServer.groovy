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
package edu.isu.xrese.server.base

import io.javalin.core.JavalinConfig

import java.util.function.Consumer

/**
 * An example implementation of the Basic Server
 *
 * @author Isaac Griffith
 * @version 1.0.1
 */
@Singleton
class ExampleServer extends BaseServer {

    static void main(String[] args) {
        ExampleServer app = ExampleServer.instance
        app.init(null, null)
        app.start()
    }

    /**
     * Hook method for setting up the routes for this server. This
     * method is called by init()
     */
    @Override
    void routes() {
        app.get("/") { ctx -> ctx.result("Hello World") }
    }

    /**
     * Hook Method which provides the JavalinConfig information to the server
     * the default returns an empty configuration
     *
     * @return a Consumer of the JavalinConfig used in customizing the server
     */
    @Override
    Consumer<JavalinConfig> config() {
        return { JavalinConfig config ->
            config.enableWebjars()
        }
    }
}
