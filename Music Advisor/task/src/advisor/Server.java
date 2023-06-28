package advisor;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.http.HttpClient;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public class Server {

    private String code = "";
    private static final String client_id = "";
    public static final String redirect_uri = "";
    public static final String client_secret = "";
    static String accessToken = "";
    private static String token = "https://accounts.spotify.com";
//    private final Boolean access = false;
    final Boolean[] access = {false};
    public Server(String spotifySite) {
        token = spotifySite + "/api/token";
//        authorize = spotifySite + "/authorize?client_id="
//                + client_id + "&response_type=code&redirect_uri=" + redirect_uri;
    }

    public Boolean run() throws IOException, InterruptedException {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        HttpServer spotifyServer = HttpServer.create(new InetSocketAddress(8080), 0);
        spotifyServer.start();
//        Boolean access = false;
//        AtomicReference<Boolean> access = new AtomicReference<>(false);
        spotifyServer.createContext("/",
                exchange -> {
                    String query = exchange.getRequestURI().getQuery();
                    String response;
                    if (query != null && query.contains("code")) {
                        code = query.substring(5);
                        System.out.println("code received");
//                        access = true;
                        access[0] = true; // Update array element

                        response = "Got the code. Return back to your program.";
                    } else {
                        response = "Authorization code not found. Try again.";
                    }
                    exchange.sendResponseHeaders(200, response.length());
                    OutputStream outputStream = exchange.getResponseBody();
                    outputStream.write(response.getBytes());
                    outputStream.close();
                    spotifyServer.stop(0);
                        try {
                            boolean access = access_token(code);
                            future.complete(access);
//                            access = access_token(code); //.set(access_token(code));
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                }
        );
        return future.join();
    }

    public static Boolean access_token(String code) throws IOException, InterruptedException {
        System.out.println("making http request for access_token...");
        HttpRequest request_access_token = HttpRequest.newBuilder()
                .POST( HttpRequest.BodyPublishers.ofString(
                        "client_id=" + client_id
                                + "&client_secret=" + client_secret
                                + "&grant_type=" + "authorization_code"
                                + "&code=" + code
                                + "&redirect_uri=" + redirect_uri))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(token))
                .build();
        HttpClient client = HttpClient.newBuilder().build();

        try {
            String responseWithAccessToken = client.send(request_access_token, HttpResponse.BodyHandlers.ofString()).body();
            System.out.println("response:");
            if (responseWithAccessToken != null){
                System.out.println(responseWithAccessToken);
                System.out.println("---SUCCESS---");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
