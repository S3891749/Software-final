package ArtistPackage;

import java.io.*;
import java.util.ArrayList;

public class Artist {

    private String ID;
    private String Name;
    private String Address;
    private String Birthdate;
    private String Bio;
    private ArrayList<String> Occupations; // like singer, songwriter, composer
    private ArrayList<String> Genres; // like rock, jazz, blues, pop, classical
    private ArrayList<String> Awards;

    public Artist(String ID, String Name, String Address, String Birthdate, String Bio,
                  ArrayList<String> Occupations, ArrayList<String> Genres, ArrayList<String> Awards) {
        this.ID = ID;
        this.Name = Name;
        this.Address = Address;
        this.Birthdate = Birthdate;
        this.Bio = Bio;
        this.Occupations = Occupations;
        this.Genres = Genres;
        this.Awards = Awards;
    }

    public boolean addArtist() {

        // Condition 1: Artist ID should be exactly 10 characters long
        if (ID.length() != 10) {
            System.out.println("Artist ID should be exactly 10 characters long.");
            return false;
        }
        // Condition 1: The first three characters should be numbers between 5 to 9
        String firstThreeDigits = ID.substring(0, 3);
        try {
            int idPrefix = Integer.parseInt(firstThreeDigits);
            if (idPrefix < 5 ) {
                System.out.println("The first three characters of Artist ID should be between 5 and 9.");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("The first three characters of Artist ID should be numeric.");
            return false;
        }

        // Condition 1: Characters 4th to 8th should be upper case letters (A-Z)
        String letters = ID.substring(3, 8);
        if (!letters.matches("[A-Z]+")) {
            System.out.println("There should be 5 uppercase alphabets after the first three digits in Artist ID.");
            return false;
        }

        // Condition 1: Last two characters should be a special character
        String specialChars = ID.substring(8);
        if (specialChars.matches("[!@#$%^&*()-]")) {
            System.out.println("The last two characters of Artist ID should be special characters.");
            return false;
        }

        // Condition 2: The format of the birth date should be DD-MM-YYYY
        if (!Birthdate.matches("\\d{2}-\\d{2}-\\d{4}")) {
            System.out.println("The birth date should be in the format DD-MM-YYYY.");
            return false;
        }


        // Condition 3: The address of the artist should follow the format City|State|Country
        String[] addressParts = Address.split("\\|");
        if (addressParts.length != 3) {
            System.out.println("The address of the artist should follow the format City|State|Country.");
            return false;
        }
        for (String part : addressParts) {
            if (!part.matches("[A-Za-z]+")) {
                System.out.println("The address of the artist should follow the format City|State|Country.");
                return false;
            }
        }

        // Condition 4: The bio should between 10 and 30 characters
        if (Bio.length() < 10 || Bio.length() > 30){
            System.out.println("The Bio must be more than 10 characters and less than 30 characters");
            return false;
        }

        // Condition 5: An artist should have at least one occupation or a maximum of five occupations
        if (Occupations.isEmpty() || Occupations.size() > 5) {
            System.out.println("An artist should have at least one occupation and a maximum of five occupations.");
            return false;
        }

        // Condition 7: An artist should be active in at least two music genres and a maximum of five genres.
        if (Genres.size() < 2 || Genres.size() > 5) {
            System.out.println("An artist should be active in at least two music genres and a maximum of five genres.");
            return false;
        }

        // Check if both 'pop' and 'rock' genres are present
        boolean hasPopGenre = Genres.contains("pop");
        boolean hasRockGenre = Genres.contains("rock");

        if (hasPopGenre && hasRockGenre) {
            System.out.println("Artists cannot be active in both 'pop' and 'rock' genres at the same time.");
            return false;
        }

        // All conditions are met, add artist information to the TXT file
        try {
            FileWriter writer = new FileWriter("/Users/buiman/IdeaProjects/SEF_Music_App/src/ArtistPackage/artists.txt", true);
            writer.write(this.toString() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean updateArtist() {
        // Condition 1: All constraints discussed for the addArtist function also need to be considered
        if (!addArtist()) {
            return false;
        }

        // Condition 2: If an artist was born before 2000, their occupation cannot be changed
        int birthYear = Integer.parseInt(Birthdate.substring(6));
        if (birthYear < 2000) {
            System.out.println("Occupation cannot be changed for artists born before 2000.");
            return false;
        }

        // Condition 3: Awards that were given to an artist before 2000 cannot be changed
        ArrayList<String> updatedAwards = new ArrayList<>();
        for (String award : Awards) {
            String[] awardParts = award.split(", ");
            int awardYear = Integer.parseInt(awardParts[0]);
            if (awardYear < 2000) {
                updatedAwards.add(award);
            } else {
                // Modify the title part of awards given after 2000
                updatedAwards.add(awardParts[0] + ", Updated Title");
            }
        }
        Awards = updatedAwards;

        // Update artist information in the TXT file
        String fileName = "/Users/buiman/IdeaProjects/SEF_Music_App/src/ArtistPackage/artists.txt";
        String tempFileName = "/Users/buiman/IdeaProjects/SEF_Music_App/src/ArtistPackage/temp.txt";
        String currentLine;
        boolean updated = false;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            FileWriter fileWriter = new FileWriter(tempFileName, true);

            while ((currentLine = bufferedReader.readLine()) != null) {
                // Find the line that matches the current artist
                String[] artistInfo = currentLine.split(", ");
                String currentArtistID = artistInfo[0];
                if (currentArtistID.equals(ID)) {
                    // Update the artist information
                    String updatedLine = this.toString();
                    fileWriter.write(updatedLine + "\n");
                    updated = true;
                } else {
                    // Write the original line
                    fileWriter.write(currentLine + "\n");
                }
            }

            fileReader.close();
            bufferedReader.close();
            fileWriter.close();

            // Rename the temp file to the original file
            File oldFile = new File(fileName);
            File newFile = new File(tempFileName);
            oldFile.delete();
            newFile.renameTo(oldFile);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return updated;
    }



    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getBirthdate() {
        return Birthdate;
    }

    public void setBirthdate(String birthdate) {
        Birthdate = birthdate;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public ArrayList<String> getOccupations() {
        return Occupations;
    }

    public void setOccupations(ArrayList<String> occupations) {
        Occupations = occupations;
    }

    public ArrayList<String> getGenres() {
        return Genres;
    }

    public void setGenres(ArrayList<String> genres) {
        Genres = genres;
    }

    public ArrayList<String> getAwards() {
        return Awards;
    }

    public void setAwards(ArrayList<String> awards) {
        Awards = awards;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ID).append(", ");
        sb.append(Name).append(", ");
        sb.append(Address).append(", ");
        sb.append(Birthdate).append(", ");
        sb.append(Bio).append(", ");
        sb.append(String.join(", ", Occupations)).append(", ");
        sb.append(String.join(", ", Genres)).append(", ");
        sb.append(String.join(", ", Awards));
        return sb.toString();
    }


}
