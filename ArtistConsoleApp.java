package ArtistPackage;

import java.util.ArrayList;
import java.util.Scanner;

public class ArtistConsoleApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Artist ID: ");
        String ID = scanner.nextLine();

        System.out.println("Enter Artist Name: ");
        String Name = scanner.nextLine();

        System.out.println("Enter Artist Address (City|State|Country): ");
        String Address = scanner.nextLine();

        System.out.println("Enter Artist Birthdate (DD-MM-YYYY): ");
        String Birthdate = scanner.nextLine();

        System.out.println("Enter Artist Bio: ");
        String Bio = scanner.nextLine();

        ArrayList<String> Occupations = new ArrayList<>();
        System.out.println("Enter Artist Occupations (comma-separated, e.g., singer,songwriter): ");
        String occupationsInput = scanner.nextLine();
        String[] occupationArray = occupationsInput.split(",");
        for (String occupation : occupationArray) {
            Occupations.add(occupation.trim());
        }

        ArrayList<String> Genres = new ArrayList<>();
        System.out.println("Enter Artist Genres (comma-separated, e.g., rock,jazz): ");
        String genresInput = scanner.nextLine();
        String[] genresArray = genresInput.split(",");
        for (String genre : genresArray) {
            Genres.add(genre.trim());
        }

        ArrayList<String> Awards = new ArrayList<>();
        System.out.println("Enter Artist Awards (comma-separated, e.g., 2022,Best Song,2021,Second Best Song): ");
        String awardsInput = scanner.nextLine();
        String[] awardsArray = awardsInput.split(",");
        for (int i = 0; i < awardsArray.length; i += 2) {
            if (i + 1 < awardsArray.length) {
                String year = awardsArray[i].trim();
                String title = awardsArray[i + 1].trim();
                Awards.add(year + ", " + title);
            } else {
                System.out.println("Invalid format for awards. Please provide both year and title for each award.");
                scanner.close();
                return;
            }
        }

        // Create an Artist object with the input data
        Artist artist = new Artist(ID, Name, Address, Birthdate, Bio, Occupations, Genres, Awards);

        // Add the artist if validation passes
        if (artist.addArtist()) {
            System.out.println("Artist added successfully!");
        } else {
            System.out.println("Failed to add artist. Please check the input.");
        }

        scanner.close();
    }
}
