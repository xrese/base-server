package edu.isu.xrese.server.base

/**
 * Utility class which loads the liquid-gamma configuration file and
 * produces an instance of ModelContext
 *
 * @author Isaac Griffith
 * @version 1.0.0
 */
@Singleton
class ConfigLoader {

    /**
     * Loads the configuration from the provided File
     *
     * @param path File representing the location of the configuration
     * @return A ModelContext instance containing the configuration information
     * @throws FileNotFoundException if the provided file cannot be found.
     */
    def load(File path) {
        if (!path.exists()) {
            System.err << "Config file ${path.name} does not exist\n"
            throw new FileNotFoundException("Config file ${path.name} does not exist\n")
        }

        ConfigSlurper slurper = new ConfigSlurper()
        def confObj = slurper.parse(path.text)
        buildContext(confObj)
    }

    /**
     * Constructs the ModelContext instance from the provided ConfigObject
     *
     * @param config Object resulting from the use of ConfigSlurper to read in the configuration file
     * @return An instance of ModelContext containing all of the LiquidGamma configuration information
     * @throws IllegalArgumentException if config is null
     */
    def buildContext(ConfigObject config) {
        if (!config)
            throw new IllegalArgumentException("buildContext: config cannot be null")

        ServerContext context = new ServerContext()
        context.db     = config.db     ?: [:]
        context.server = config.server ?: [:]

        context
    }
}
