package advisor;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        String spotifySite = "https://accounts.spotify.com";
        if (args.length >= 2 && args[0].equals("-access")) {
            spotifySite = args[1];
        } else if (args.length >= 2 && args[0].equals("-resource")) {
            spotifySite = args[1];
        }
        SpotifyApp.run(spotifySite);
    }
}
