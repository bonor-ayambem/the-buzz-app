package edu.lehigh.cse216.alb323.backend;

// Import the Spark package, so that we can make use of the "get" function to 
// create an HTTP GET route
import spark.Spark;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

// Import Google's JSON library
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;



/**
 * For now, our app creates an HTTP server that can only get and add data.
 */
public class App {
        /**
     * Get an integer environment varible if it exists, and otherwise return the
     * default value.
     * 
     * @envar      The name of the environment variable to get.
     * @defaultVal The integer value to use as the default if envar isn't found
     * 
     * @returns The best answer we could come up with for a value for envar
     */
    static int getIntFromEnv(String envar, int defaultVal) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get(envar) != null) {
            return Integer.parseInt(processBuilder.environment().get(envar));
        }
        return defaultVal;
    }

    /**
 * Set up CORS headers for the OPTIONS verb, and for every response that the
 * server sends.  This only needs to be called once.
 * 
 * @param origin The server that is allowed to send requests to this server
 * @param methods The allowed HTTP verbs from the above origin
 * @param headers The headers that can be sent with a request from the above
 *                origin
 */
    private static void enableCORS(String origin, String methods, String headers) {
        // Create an OPTIONS route that reports the allowed CORS headers and methods
        Spark.options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });

        // 'before' is a decorator, which will run before any 
        // get/post/put/delete.  In our case, it will put three extra CORS
        // headers into the response
        Spark.before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
        });
    }
    private static void setupEnvironmentVars()
    {

    }


    public static void main(String[] args) { 
        setupEnvironmentVars();
        Memcached mc = new Memcached();

        DriveAPI dr = new DriveAPI();
        if (dr.getDrive() == null) {
            System.err.println("lfg"); //lfg
        }

        // Get the port on which to listen for requests
        Spark.port(getIntFromEnv("PORT", 4567));

        // gson provides us with a way to turn JSON into objects, and objects
        // into JSON.
        //
        // NB: it must be final, so that it can be accessed from our lambdas
        //
        // NB: Gson is thread-safe.  See 
        // https://stackoverflow.com/questions/10380835/is-it-ok-to-use-gson-instance-as-a-static-field-in-a-model-bean-reuse
        final Gson gson = new Gson();

        // dataStore holds all of the data that has been provided via HTTP 
        // requests
        //
        // NB: every time we shut down the server, we will lose all data, and 
        //     every time we start the server, we'll have an empty dataStore,
        //     with IDs starting over from 0.


        // get the Postgres configuration from the environment
        Map<String, String> env = System.getenv();
        // String ip = env.get("POSTGRES_IP");
        // String port = env.get("POSTGRES_PORT");
        // String user = env.get("POSTGRES_USER");
        // String pass = env.get("POSTGRES_PASS");
        String db_url = env.get("DATABASE_URL"); //Phase 1: NEW db_url variable

        // Get a fully-configured connection to the database, or exit 
        // immediately
        
        //Database dataStore = Database.getDatabase(ip, port, user, pass);
        /*  Phase 1: I commented out the original dataStore variable and replaced it with
        *   the dataStore variable below
        */
        
        Database dataStore = Database.getDatabase(db_url);        
        //PHASE 2
        
        dataStore.dropTableUser();
        dataStore.createTableUser();
        
        dataStore.dropTableMessages();
        dataStore.createTableMessages();

        dataStore.dropTableComment();
        dataStore.createTableComment();

        dataStore.dropTableLikes();
        dataStore.createTableLikes();

        // Set up the location for serving static files.  If the STATIC_LOCATION
        // environment variable is set, we will serve from it.  Otherwise, serve
        // from "/web"
        String static_location_override = System.getenv("STATIC_LOCATION");
        if (static_location_override == null) {
            Spark.staticFileLocation("/web");
        } else {
            Spark.staticFiles.externalLocation(static_location_override);
        }

        String cors_enabled = env.get("CORS_ENABLED");
        if (cors_enabled.equals("True")) {
            final String acceptCrossOriginRequestsFrom = "*";
            final String acceptedCrossOriginRoutes = "GET,PUT,POST,DELETE,OPTIONS";
            final String supportedRequestHeaders = "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin";
            enableCORS(acceptCrossOriginRequestsFrom, acceptedCrossOriginRoutes, supportedRequestHeaders);
        }

        // Set up a route for serving the main page
        Spark.get("/", (req, res) -> {
            res.redirect("/index.html");
            return "";
        });

                /**
         * Set up CORS headers for the OPTIONS verb, and for every response that the
         * server sends.  This only needs to be called once.
         * 
         * @param origin The server that is allowed to send requests to this server
         * @param methods The allowed HTTP verbs from the above origin
         * @param headers The headers that can be sent with a request from the above
         *                origin
         */


        // GET route that returns all message titles and Ids.  All we do is get 
        // the data, embed it in a StructuredResponse, turn it into JSON, and 
        // return it.  If there's no data, we return "[]", so there's no need 
        // for error handling.
        Spark.get("/messages/:session_key", (request, response) -> {
            String session_key = request.params("session_key");
            if (!mc.checkUser(session_key, dataStore)) {
                return gson.toJson(new StructuredResponse("error", "user not in database", null));
            }
            // ensure status 200 OK, with a MIME type of JSON

            response.status(200);
            response.type("application/json");

            ArrayList<MessageRow> data = dataStore.selectAllMessages();
            if (data == null) {
                return gson.toJson(new StructuredResponse("could not return messages", null, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, data));
            }
        });

        // GET route that returns everything for a single row in the DataStore.
        // The ":id" suffix in the first parameter to get() becomes 
        // request.params("id"), so that we can get the requested row ID.  If 
        // ":id" isn't a number, Spark will reply with a status 500 Internal
        // Server Error.  Otherwise, we have an integer, and the only possible 
        // error is that it doesn't correspond to a row with data.
        Spark.get("/messages/:id/:session_key", (request, response) -> {
            String session_key = request.params("session_key");
            if (!mc.checkUser(session_key, dataStore)) {
                return gson.toJson(new StructuredResponse("error", "user not in database", null));
            }

            int idx = Integer.parseInt(request.params("id"));
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            MessageRow data = dataStore.selectOneMessage(idx);
            if (data == null) {
                return gson.toJson(new StructuredResponse("error", idx + " not found", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, data));
            }
        });
        
        // POST route for adding a new element to the DataStore.  This will read
        // JSON from the body of the request, turn it into a SimpleMessageRequest 
        // object, extract the title and message, insert them, and return the 
        // ID of the newly created row.
        Spark.post("/messages/:session_key", (request, response) -> {
            String session_key = request.params("session_key");
            if (!mc.checkUser(session_key, dataStore)) {
                return gson.toJson(new StructuredResponse("error", "user not in database", null));
            }
            // NB: if gson.Json fails, Spark will reply with status 500 Internal 
            // Server Error

            System.err.println("Request body: \n" + request.body());
            MessageRow req = gson.fromJson(request.body(), MessageRow.class);
            // ensure status 200 OK, with a MIME type of JSON
            // NB: even on error, we return 200, but with a JSON object that
            //     describes the error.
            response.status(200);
            response.type("application/json");
            // NB: createEntry checks for null title and message
            int newId = dataStore.insertRowMessage(req.mTitle, req.mContent, req.mUser);
            if (newId == -1) {
                return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", "" + newId, null));
            }
        });
        
        // PUT route for updating a row in the DataStore.  This is almost 
        // exactly the same as POST
        Spark.put("/messages/:messageID/:session_key", (request, response) -> {
            String session_key = request.params("session_key");
            if (!mc.checkUser(session_key, dataStore)) {
                return gson.toJson(new StructuredResponse("error", "user not in database", null));
            }
            // If we can't get an ID or can't parse the JSON, Spark will send
            // a status 500
            int idx = Integer.parseInt(request.params("messageID"));
            SimpleMessageRequest req = gson.fromJson(request.body(), SimpleMessageRequest.class);
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            int result = dataStore.updateOneMessage(idx, req.mContent);
            if (result == -1) { 
                return gson.toJson(new StructuredResponse("error", "unable to update row " + idx, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, result));
            }
        });

        // GET route that returns all the comments for a specific message ID.
        // The ":id" suffix in the first parameter to get() becomes 
        // request.params("id"), so that we can get the requested row ID.  If 
        // ":id" isn't a number, Spark will reply with a status 500 Internal
        // Server Error.  Otherwise, we have an integer, and the only possible 
        // error is that it doesn't correspond to a row with data.
        Spark.get("/comments/:messageID/:session_key", (request, response) -> {
            String session_key = request.params("session_key");
            if (!mc.checkUser(session_key, dataStore)) {
                return gson.toJson(new StructuredResponse("error", "user not in database", null));
            }
            int idx = Integer.parseInt(request.params("messageID"));
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            ArrayList<CommentRow> data = dataStore.selectAllComments(idx);
            if (data == null) {
                return gson.toJson(new StructuredResponse("error", idx + " not found", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, data));
            }
        });

        Spark.get("/comment/:commentID/:session_key", (request, response) -> {
            String session_key = request.params("session_key");
            if (!mc.checkUser(session_key, dataStore)) {
                return gson.toJson(new StructuredResponse("error", "user not in database", null));
            }
            int idx = Integer.parseInt(request.params("commentID"));
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            CommentRow data = dataStore.selectOneComment(idx);
            if (data == null) {
                return gson.toJson(new StructuredResponse("error", idx + " not found", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, data));
            }
        });

        // PUT route for updating a row's likes up by 1 in the DataStore.  This is almost 
        // exactly the same as POST
        //
        Spark.put("/like/:id/:session_key", (request, response) -> {
            String session_key = request.params("session_key");
            if (!mc.checkUser(session_key, dataStore)) {
                return gson.toJson(new StructuredResponse("error", "user not in database", null));
            }
            // If we can't get an ID or can't parse the JSON, Spark will send
            // a status 500
            int idx = Integer.parseInt(request.params("id"));
            LikeRequest req = gson.fromJson(request.body(), LikeRequest.class);
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            int result = dataStore.updateOneLike(idx, req.flag);
            if (result == -1) {
                return gson.toJson(new StructuredResponse("error", "unable to update row " + idx, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, result));
            }
        });

        Spark.post("/comment/:session_key", (request, response) ->{
            String session_key = request.params("session_key");
            if (!mc.checkUser(session_key, dataStore)) {
                return gson.toJson(new StructuredResponse("error", "user not in database", null));
            }
            // NB: if gson.Json fails, Spark will reply with status 500 Internal 
            // Server Error
            CommentRow req = gson.fromJson(request.body(), CommentRow.class);
            // ensure status 200 OK, with a MIME type of JSON
            // NB: even on error, we return 200, but with a JSON object that
            //     describes the error.
            response.status(200);
            response.type("application/json");
            // NB: createEntry checks for null title and message
            int newId = dataStore.insertRowComment(req.mMessageID, req.mUserID, req.mContent);
            if (newId == -1) {
                return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", "" + newId, null));
            }
        });

        Spark.post("/user", (request, response) ->{
            // NB: if gson.Json fails, Spark will reply with status 500 Internal 
            // Server Error
            UserRow req = gson.fromJson(request.body(), UserRow.class);
            // ensure status 200 OK, with a MIME type of JSON
            // NB: even on error, we return 200, but with a JSON object that
            //     describes the error.
            response.status(200);
            response.type("application/json");
            // NB: createEntry checks for null title and message
            int newId = dataStore.insertRowUser(req.mUser, req.mFirstName, req.mLastName, req.mEmail);
            if (newId == -1) {
                return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
            } else {
                String session_key = mc.addUser(newId);
                return gson.toJson(new StructuredResponse("ok", "" + newId, session_key));
            }
        });

        Spark.get("/user/:userID/:session_key", (request, response) -> {
            String session_key = request.params("session_key");
            if (!mc.checkUser(session_key, dataStore)) {
                return gson.toJson(new StructuredResponse("error", "user not in database", null));
            }
            int idx = Integer.parseInt(request.params("userID"));
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            UserRow data = dataStore.selectOneUser(idx);
            if (data == null) {
                return gson.toJson(new StructuredResponse("error", idx + " not found", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, data));
            }
        });


        Spark.post("/like/:session_key", (request, response) ->{
            String session_key = request.params("session_key");
            if (!mc.checkUser(session_key, dataStore)) {
                return gson.toJson(new StructuredResponse("error", "user not in database", null));
            }
            // NB: if gson.Json fails, Spark will reply with status 500 Internal 
            // Server Error
            LikesRow req = gson.fromJson(request.body(), LikesRow.class);
            // ensure status 200 OK, with a MIME type of JSON
            // NB: even on error, we return 200, but with a JSON object that
            //     describes the error.
            response.status(200);
            response.type("application/json");
            // NB: createEntry checks for null title and message
            int newId = dataStore.insertRowLike(req.mUserID, req.mMessageID, req.mFlag);
            if (newId == -1) {
                return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", "" + newId, null));
            }
        });

        Spark.get("/like/:messageID/:session_key", (request, response) -> {
            String session_key = request.params("session_key");
            if (!mc.checkUser(session_key, dataStore)) {
                return gson.toJson(new StructuredResponse("error", "user not in database", null));
            }
            int idx = Integer.parseInt(request.params("messageID"));
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            LikesData data = dataStore.selectOneLike(idx);
            if (data == null) {
                return gson.toJson(new StructuredResponse("error", idx + " not found", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, data));
            }
        });



        // PUT route for updating a row in the DataStore.  This is almost 
        // exactly the same as POST
        Spark.put("/comment/:commentID/:session_key", (request, response) -> {
            String session_key = request.params("session_key");
            if (!mc.checkUser(session_key, dataStore)) {
                return gson.toJson(new StructuredResponse("error", "user not in database", null));
            }
            // If we can't get an ID or can't parse the JSON, Spark will send
            // a status 500
            int idx = Integer.parseInt(request.params("commentID"));
            SimpleMessageRequest req = gson.fromJson(request.body(), SimpleMessageRequest.class);
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
        
            int result = dataStore.updateOneComment(idx, req.mContent);
        
            if (result == -1) { 
                return gson.toJson(new StructuredResponse("error", "unable to update row " + idx, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, result));
            }
            
        });

        // DELETE route for removing a row from the DataStore
        Spark.delete("/messages/:id/:session_key", (request, response) -> {
            String session_key = request.params("session_key");
            if (!mc.checkUser(session_key, dataStore)) {
                return gson.toJson(new StructuredResponse("error", "user not in database", null));
            }
            // If we can't get an ID, Spark will send a status 500
            int idx = Integer.parseInt(request.params("id"));
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            // NB: we won't concern ourselves too much with the quality of the 
            //     message sent on a successful delete
            int result = dataStore.deleteRowMessage(idx);
            if (result == -1) {
                return gson.toJson(new StructuredResponse("error", "unable to delete row " + idx, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, null));
            }
        });

        Spark.delete("/comment/:id/:session_key", (request, response) -> {
            String session_key = request.params("session_key");
            if (!mc.checkUser(session_key, dataStore)) {
                return gson.toJson(new StructuredResponse("error", "user not in database", null));
            }
            // If we can't get an ID, Spark will send a status 500
            int idx = Integer.parseInt(request.params("id"));
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            // NB: we won't concern ourselves too much with the quality of the 
            //     message sent on a successful delete
            int result = dataStore.deleteRowComment(idx);
            if (result == -1) {
                return gson.toJson(new StructuredResponse("error", "unable to delete row " + idx, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, null));
            }
        });

        Spark.delete("/user/:id/:session_key", (request, response) -> {
            String session_key = request.params("session_key");
            if (!mc.checkUser(session_key, dataStore)) {
                return gson.toJson(new StructuredResponse("error", "user not in database", null));
            }
            // If we can't get an ID, Spark will send a status 500
            int idx = Integer.parseInt(request.params("id"));
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            // NB: we won't concern ourselves too much with the quality of the 
            //     message sent on a successful delete
            int result = dataStore.deleteRowUser(idx);
            if (result == -1) {
                return gson.toJson(new StructuredResponse("error", "unable to delete row " + idx, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, null));
            }
        });

        Spark.delete("/like/:id/:session_key", (request, response) -> {
            String session_key = request.params("session_key");
            if (!mc.checkUser(session_key, dataStore)) {
                return gson.toJson(new StructuredResponse("error", "user not in database", null));
            }
            // If we can't get an ID, Spark will send a status 500
            int idx = Integer.parseInt(request.params("id"));
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            // NB: we won't concern ourselves too much with the quality of the 
            //     message sent on a successful delete
            int result = dataStore.deleteRowLike(idx);
            if (result == -1) {
                return gson.toJson(new StructuredResponse("error", "unable to delete row " + idx, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, null));
            }
        });


        Spark.post("/auth", (request, response) -> { //post bc ADDING new user 
            //Get the token from post request
            OAuthRequest req = gson.fromJson(request.body(), OAuthRequest.class);
            response.status(200);
            response.type("application/json");

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
            // Specify the CLIENT_ID of the app that accesses the backend:
            .setAudience(Collections.singletonList("28723562916-ssidthsaupb78fbp0u3s6ap4s7n7fpbs.apps.googleusercontent.com"))
            // Or, if multiple clients access the backend:
            //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
            .build();
        
            // (Receive idTokenString by HTTPS POST)
            
            GoogleIdToken idToken = verifier.verify(req.token);
            if (idToken != null) {
                Payload payload = idToken.getPayload();
                
                // Print user identifier
                String username = payload.getSubject();
                System.err.println("User ID: " + username);
                // Get profile information from payload
                String email = payload.getEmail();
                System.err.println("Email: " + email);
                String lastName = (String) payload.get("family_name");
                System.err.println("Family Name: " + lastName);
                String firstName = (String) payload.get("given_name");
                System.err.println("Given Name: " + firstName);
                int newId = dataStore.insertRowUser(username, firstName, lastName, email);


                if (newId == -1) {
                    return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                } else {
                    String session_key = mc.addUser(newId);
                    return gson.toJson(new StructuredResponse("ok", "" + newId, session_key));
                }

            } else {
                System.out.println("Invalid ID token.");
                return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
            }
        });
    }
}