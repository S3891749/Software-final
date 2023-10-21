package ArtistPackage;

import ArtistPackage.Artist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;

public class ArtistTest {

    private Artist artist;

    @BeforeEach
    public void setUp() {
        // Initialize an Artist object with valid data for testing
        ArrayList<String> occupations = new ArrayList<>(Arrays.asList("singer", "songwriter"));
        ArrayList<String> genres = new ArrayList<>(Arrays.asList("rock","jazz"));
        ArrayList<String> awards = new ArrayList<>(Arrays.asList("2005, Grammy Award", "2010, Billboard Music Award"));

        artist = new Artist(
                "678MMMRR_%", // Valid ID
                "John Doe",
                "Melbourne|Victoria|Australia", // Valid address
                "01-01-2000", // Valid birth date
                "A talented artist.", // Valid bio
                occupations, // Valid occupations
                genres, // Valid genres
                awards // Valid awards
        );
    }

    @Test
    public void testAddValidArtist() {
        // Test adding a valid artist
        boolean added = artist.addArtist();
        Assertions.assertTrue(added);
    }

    @Test
    public void testAddInvalidArtist() {
        // Modify the artist data to make it invalid
        artist.setID("123"); // Invalid ID length
        artist.setAddress("Invalid Address"); // Invalid address format
        artist.setBirthdate("01/01/1990"); // Invalid birth date format
        artist.setBio("Short Bio"); // Bio with less than 10 words
        artist.setOccupations(new ArrayList<>()); // No occupations
        artist.setGenres(new ArrayList<>()); // No genres
        artist.setAwards(new ArrayList<>(Arrays.asList("2000, Award"))); // Invalid award format

        // Test adding an invalid artist
        boolean added = artist.addArtist();
        Assertions.assertFalse(added);
    }

    @Test
    public void testUpdateValidArtist() {
        // Test updating a valid artist
        boolean updated = artist.updateArtist();
        Assertions.assertTrue(updated);
    }

    @Test
    public void testInvalidArtistIDCharacters() {
        // Create an artist with an invalid ID format (special character in last two characters)
        Artist artist = new Artist("12345ABCD@", "John Doe", "Melbourne|Victoria|Australia", "15-01-2000", "Sample Bio",
                new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());

        // Attempt to add the artist
        boolean added = artist.addArtist();

        // Assert that the artist was not added successfully due to invalid ID characters
        Assertions.assertFalse(added);
    }

    @Test
    public void testInvalidBirthdateFormat() {
        ArrayList<String> occupations = new ArrayList<>();
        occupations.add("Singer");
        occupations.add("Songwriter");
        occupations.add("Composer");
        // Create an artist with an invalid birthdate format
        Artist artist = new Artist("678MMMRR_%", "John Doe", "Melbourne|Victoria|Australia", "2023/02/22", "Sample Bio",
                new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());

        // Attempt to add the artist
        boolean added = artist.addArtist();

        // Assert that the artist was not added successfully due to invalid birthdate format
        Assertions.assertFalse(added);
    }

    @Test
    public void testInvalidAddressFormat() {
        ArrayList<String> occupations = new ArrayList<>();
        occupations.add("Singer");
        occupations.add("Songwriter");
        // Create an artist with an invalid address format
        Artist artist = new Artist("678MMMRR_%", "John Doe", "Melbourne,Victoria|Australia", "15-01-2000", "Sample Bio",
                new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());

        // Attempt to add the artist
        boolean added = artist.addArtist();

        // Assert that the artist was not added successfully due to invalid address format
        Assertions.assertFalse(added);
    }

    @Test
    public void testInvalidBioLength() {
        ArrayList<String> occupations = new ArrayList<>();
        occupations.add("Singer");
        occupations.add("Songwriter");
        occupations.add("Composer");
        occupations.add("Guitarist");
        occupations.add("Pianist");

        // Create an artist with a bio that has fewer than 10 characters
        Artist artist = new Artist("678MMMRR_%", "John Doe", "Melbourne|Victoria|Australia", "15-01-2000", "Short Bio",
                new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());

        // Attempt to add the artist
        boolean added = artist.addArtist();

        // Assert that the artist was not added successfully due to invalid bio length
        Assertions.assertFalse(added);
    }
    @Test
    public void testInvalidOccupations() {
        // Create an artist with more than 5 occupations
        ArrayList<String> occupations = new ArrayList<>();
        occupations.add("Singer");
        occupations.add("Songwriter");
        occupations.add("Composer");
        occupations.add("Guitarist");
        occupations.add("Pianist");
        occupations.add("Violinist"); // More than 5 occupations

        Artist artist = new Artist("678MMMRR_%", "John Doe", "Melbourne|Victoria|Australia", "15-01-2000", "Sample Bio",
                occupations, new ArrayList<String>(), new ArrayList<String>());

        // Attempt to add the artist
        boolean added = artist.addArtist();

        // Assert that the artist was not added successfully due to too many occupations
        Assertions.assertFalse(added);
    }

    @Test
    public void testUpdateInvalidOccupationForPre2000Artist() {
        // Modify the artist's birthdate to make them born before 2000
        artist.setBirthdate("01-01-1995");

        // Modify the artist's occupation
        artist.getOccupations().clear();
        artist.getOccupations().add("actor");

        // Test updating the artist's occupation (should fail due to Condition 2)
        boolean updated = artist.updateArtist();
        Assertions.assertFalse(updated);
    }

    @Test
    public void testAddInvalidArtistWithPopAndRockGenres() {
        // Set 'pop' and 'rock' as genres for the artist
        artist.setGenres(new ArrayList<>(Arrays.asList("pop", "rock")));

        // Test adding an artist with 'pop' and 'rock' genres at the same time (invalid)
        boolean added = artist.addArtist();
        Assertions.assertFalse(added);
    }
}
