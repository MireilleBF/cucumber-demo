package fr.unice.polytech.biblio.apps;

import com.sun.net.httpserver.HttpServer;
import fr.unice.polytech.biblio.api.httphandlers.LibraryHttpHandler;
import fr.unice.polytech.biblio.services.Bibliotheque;
import fr.unice.polytech.biblio.services.StudentRegistry;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
   * Mireille Blay-Fornarino
   * 2024
   * This class is a simple HTTP server that can be used to serve a library.
   * The server will be listening on port 8000.
   * The server will be able to manage the following requests:
   * - GET /api/library : return the list of all books
   * - POST /api/library : add a new book
   * - GET /api/library/{id} : return the book with the given id
   * - PUT /api/library/{id} : update the book with the given id
   * - DELETE /api/library/{id} : delete
   * and to borrow a book
   * - POST /api/library/{id}/borrow : borrow the book with the given id to the student with the given student number
   * The server will be able to manage the following books:
   * - id : the identifier of the book
   * - title : the title of the book
   * - authors : the authors of the book (array of strings)
   * - isbn : the ISBN of the book
   *
 */
public class SimpleHttpServer4Library {

    //Associe les serveurs à leur port (pour pouvoir les arrêter et en tester plusieurs en même temps)
    private static final Map<Integer, HttpServer> servers = new HashMap<>();

    static Logger logger = Logger.getLogger("SimpleHttpServer4Library");

    static {
        logger.setLevel(Level.SEVERE);
    }
    public static final int DEFAULT_PORT4LIBRARY = 8000;
    public static final int DEFAULT_PORT_4_SCOLARITY = 8001;

    /****
     * Main method to start the server.
     * Attention, the server for scolarity must be started before the library server.
     * @param args
     */
    public static void main(String[] args)  {
        try {
            StudentRegistry studentRegistry = new StudentRegistry();
            SimpleHttpServer4Scolarity.startServer(DEFAULT_PORT_4_SCOLARITY, studentRegistry);
            startServer(DEFAULT_PORT4LIBRARY, new Bibliotheque(), studentRegistry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /****
     * Find a free port starting from the given port.
     * If the given port is free, it is returned.
     * If not, the next free port is returned.
     * The port is reserved (added to the map of servers with a null value) to
     * @param port : the port to start from
     * @return a free port
     * Cette méthode est utilisée pour les tests uniquement -
     * Elle permet de limiter les conflits de ports entre les tests.
     */
    public static int findFreePortFrom(int port) {
        int newPort = port;
        while (servers.containsKey(newPort)) {
            newPort++;
        }
        //pour se réserver le port
        if (newPort > port) {
            servers.put(newPort,null);
        }
        return newPort;
    }

    /***
     * Start the server on the given port.
     * If a server is already started on the given port, it will fail !
     * @param port : the port to start the server on
     * @param bibliotheque : the library to manage
     * @param studentRegistry : the student registry to manage
     * @return the started server
     * @throws IOException
     * Remarks : We decided to give the responsibility of dealing with ports to the end-user
     *  - finding a free port to the caller (findFreePortFrom method can be used for that)
     *  - checking if a port is already used (isRunning method can be used for that)
     *  - stopping a server on a given port (stopServer method can be used for that)
     *  - finding a server on a given port (getServer method can be used for that)
     * This way, we can have multiple servers running on different ports in the same JVM
     */
    public static HttpServer startServer(int port, Bibliotheque bibliotheque, StudentRegistry studentRegistry) throws IOException {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/api/library", new LibraryHttpHandler(bibliotheque, studentRegistry));
            server.setExecutor(null); // creates a default executor
            server.start();
            servers.put(port,server);
            logger.log(Level.SEVERE,"Library Server started on port "+port);
            return  server;
    }

    /***
     * Stop the server on the given port.
     * If no server is started on the given port, nothing is done.
     * @param port : the port to stop the server on
     *
     */
    public static void stopServer(int port) {
        if (servers.get(port) != null) {
            servers.get(port).stop(0);
        }
        servers.remove(port);
    }


    /***
     * Check if a server is running on the given port.
     * @param port : the port to check
     * @return true if a server is running on the given port, false otherwise
     */
    public static boolean isRunning(int port) {
        return servers.containsKey(port);
    }

    /***
     * Get the server running on the given port.
     * @param port : the port to get the server from
     * @return the server running on the given port, or null if no server is running on the given port
     */
    public static HttpServer getServer(int port) {
        return servers.get(port);
    }
}
