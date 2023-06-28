package advisor;

import java.io.IOException;
import java.util.Scanner;

public class SpotifyApp {

    static final String link = "https://accounts.spotify.com/authorize?client_id=3ee4813ac0564841b12062fcfede07c6&redirect_uri=http://localhost:8080&response_type=code";

    public static void run(String spotifySite) throws IOException, InterruptedException {

        Boolean authorized = false;
        Scanner scanner = new Scanner(System.in);
        Server spotifyServer = new Server(spotifySite);

        while (true) {
            String input = scanner.nextLine();
            if ( authorized == false){
                if (input.equals("auth")){
                    System.out.println("use this link to request the access code:");
                    System.out.println(link);
                    System.out.println("waiting for code...");
                    authorized = spotifyServer.run();
                } else if (input.equals("exit")){
                    System.out.println("---GOODBYE!---");
                    break;
                } else {
                    System.out.println("Please, provide access for application.");
                }
            }else if (authorized) {
                switch (input) {
                    case "new" -> {

                        System.out.println("---NEW RELEASES---");
                        System.out.println("Mountains [Sia, Diplo, Labrinth]");
                        System.out.println("Runaway [Lil Peep]");
                        System.out.println("The Greatest Show [Panic! At The Disco]");
                        System.out.println("All Out Life [Slipknot]");
                    }
                    case "featured" -> {
                        System.out.println("---FEATURED---");
                        System.out.println("Mellow Morning");
                        System.out.println("Wake Up and Smell the Coffee");
                        System.out.println("Monday Motivation");
                        System.out.println("Songs to Sing in the Shower");
                    }
                    case "categories" -> {
                        System.out.println("---CATEGORIES---");
                        System.out.println("Top Lists");
                        System.out.println("Pop");
                        System.out.println("Mood");
                        System.out.println("Latin");
                    }
                    case "playlists Mood" -> {
                        System.out.println("---MOOD PLAYLISTS---");
                        System.out.println("Walk Like A Badass");
                        System.out.println("Rage Beats");
                        System.out.println("Arab Mood Booster");
                        System.out.println("Sunday Stroll");
                    }
                    default -> System.out.println("Invalid command. Please try again.");
                }
            } else {
                System.out.println("Please, provide access for application.");
            }
        }
    }
}
