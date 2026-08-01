/*
Nadim Ramrattan
CEN 3024C
This class is responsible for reading NBA player data
from a text file and converting each line into an
NBA Player Object
Invalid records are skipped instead of causing the program to crash.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {

    /*
     Opens a text file and loads valid NBAPlayer records.
     @param fileName the file name or file path entered by the user
     @return list of valid NBAPlayer objects
     */
    public ArrayList<NBAPlayer> loadFile(String fileName) {

        ArrayList<NBAPlayer> players = new ArrayList<>();

        if (fileName == null || fileName.trim().isEmpty()) {
            System.out.println("File name cannot be blank.");
            return players;
        }

        try {
            File file = new File(fileName);
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                if (!line.trim().isEmpty()) {
                    NBAPlayer player = parsePlayer(line);

                    if (player != null && !idAlreadyLoaded(players, player.getPlayerId())) {
                        players.add(player);
                    }
                }
            }

            fileScanner.close();
            System.out.println(players.size() + " player(s) successfully loaded.");

        } catch (FileNotFoundException e) {
            System.out.println("File could not be opened. Please check the file name or path.");
        }

        return players;
    }

    /*
     Converts one comma-separated line into an NBAPlayer object.
     Invalid records are skipped instead of crashing the program.
     */
    private NBAPlayer parsePlayer(String line) {

        try {
            String[] data = line.split(",");

            if (data.length != 8) {
                System.out.println("Skipping invalid record: " + line);
                return null;
            }

            int id = Integer.parseInt(data[0].trim());
            String name = data[1].trim();
            String team = data[2].trim();
            String position = data[3].trim().toUpperCase();
            double ppg = Double.parseDouble(data[4].trim());
            double rpg = Double.parseDouble(data[5].trim());
            double apg = Double.parseDouble(data[6].trim());
            int championships = Integer.parseInt(data[7].trim());

            Validation validation = new Validation();

            if (!validation.validPlayerID(id) ||
                    !validation.validString(name) ||
                    !validation.validTeam(team) ||
                    !validation.validPosition(position) ||
                    !validation.validDouble(ppg) ||
                    !validation.validDouble(rpg) ||
                    !validation.validDouble(apg) ||
                    !validation.validChampionships(championships)) {

                System.out.println("Skipping invalid record: " + line);
                return null;
            }

            return new NBAPlayer(id, name, team, position, ppg, rpg, apg, championships);

        } catch (NumberFormatException e) {
            System.out.println("Skipping invalid numeric record: " + line);
            return null;
        }
    }

    /*
     Prevents duplicate IDs from being loaded from the text file.
     */
    private boolean idAlreadyLoaded(ArrayList<NBAPlayer> players, int id) {

        for (NBAPlayer player : players) {
            if (player.getPlayerId() == id) {
                return true;
            }
        }

        return false;
    }
}