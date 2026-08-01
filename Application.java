/*
Nadim Ramrattan
CEN 3024C NBA LEGENDS DMS

This class runs the command-line interface for the NBA Legends
Data Management System. It allows the user to load player data
from a text file, display all data, add players, update players,
remove players, compare players, and exit the program. */

import java.util.ArrayList;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        // Create an Application object so the main method stays short.
        Application app = new Application();

        // Start the command-line program.
        app.runProgram();
    }

    /*
     The program continues until the user chooses Exit.
     */
    public boolean runProgram() {

        Scanner scanner = new Scanner(System.in);

        FileManager fileManager = new FileManager();
        Validation validation = new Validation();

        ArrayList<NBAPlayer> players = new ArrayList<>();
        PlayerManager manager = new PlayerManager(players);

        boolean running = true;

        while (running) {

            displayMainMenu();

            int choice = getValidMenuChoice(scanner);

            switch (choice) {

                case 1:
                    manager = loadPlayers(scanner, fileManager);
                    break;

                case 2:
                    displayPlayers(manager);
                    break;

                case 3:
                    addPlayer(scanner, manager, validation);
                    break;

                case 4:
                    updatePlayer(scanner, manager, validation);
                    break;

                case 5:
                    removePlayer(scanner, manager, validation);
                    break;

                case 6:
                    comparePlayers(scanner, manager, validation);
                    break;

                case 7:
                    running = false;
                    System.out.println("\nExiting NBA Legends DMS. Goodbye!");
                    break;

                default:
                    // This should never happen because getValidMenuChoice()
                    // only returns values from 1 through 7.
                    System.out.println("Invalid option.");
                    break;
            }
        }

        scanner.close();

        return true;
    }

    /*
     Prints the main menu for the user.
     */
    private boolean displayMainMenu() {

        System.out.println("\n====================================");
        System.out.println("        NBA LEGENDS DMS");
        System.out.println("====================================");
        System.out.println("1. Load Players from Text File");
        System.out.println("2. Display All Players");
        System.out.println("3. Add New Player");
        System.out.println("4. Update Player");
        System.out.println("5. Remove Player");
        System.out.println("6. Compare Two Players");
        System.out.println("7. Exit");
        System.out.print("Enter menu choice: ");

        return true;
    }

    /*
     Gets a valid menu option from the user.
     This method keeps asking until the user enters a number from 1 to 7.
     */
    private int getValidMenuChoice(Scanner scanner) {

        int choice;

        while (true) {

            try {

                choice = Integer.parseInt(scanner.nextLine());

                if (choice >= 1 && choice <= 7) {
                    return choice;
                }

                System.out.print("Invalid option. Please enter 1 through 7: ");

            } catch (NumberFormatException e) {

                System.out.print("Invalid input. Please enter a number from 1 to 7: ");
            }
        }
    }

    /*
     Loads NBA players from a text file.
     */
    private PlayerManager loadPlayers(Scanner scanner, FileManager fileManager) {

        while (true) {

            System.out.print("\nEnter file name or file path, or type CANCEL to return to menu: ");

            String fileName = scanner.nextLine();

            if (fileName.equalsIgnoreCase("CANCEL")) {
                System.out.println("File loading cancelled.");
                return new PlayerManager(new ArrayList<NBAPlayer>());
            }

            if (fileName == null || fileName.trim().isEmpty()) {
                System.out.println("File name cannot be blank. Please try again.");
                continue;
            }

            ArrayList<NBAPlayer> loadedPlayers = fileManager.loadFile(fileName);

            if (loadedPlayers.isEmpty()) {
                System.out.println("No players were loaded. Check the file name, path, or file format.");
                System.out.print("Would you like to try another file? (Y/N): ");

                String answer = scanner.nextLine();

                if (!answer.equalsIgnoreCase("Y")) {
                    return new PlayerManager(new ArrayList<NBAPlayer>());
                }

                continue;
            }

            System.out.println(loadedPlayers.size() + " player(s) are now available in the system.");

            return new PlayerManager(loadedPlayers);
        }
    }

    /*
     Displays all NBA players currently stored in the system.
     The display logic is handled through PlayerManager
     */
    private boolean displayPlayers(PlayerManager manager) {

        System.out.println(manager.displayPlayers());

        return true;
    }

    /*
     Adds a new NBA legend to the system.
     Duplicate IDs are checked
     Invalid input does NOT return the user to the main menu.
     Users continue entering data until every field is valid.
     Teams are selected from a menu instead of free text.
     */
    private boolean addPlayer(Scanner scanner,
                              PlayerManager manager,
                              Validation validation) {

        System.out.println("\n========== ADD NEW PLAYER ==========");

        int id;

        // ----------------------------
        // Player ID
        // ----------------------------
        while (true) {

            try {

                System.out.print("Enter Player ID (or type -1 to cancel): ");

                id = Integer.parseInt(scanner.nextLine());

                if (id == -1) {

                    System.out.println("Player creation cancelled.");
                    return false;

                }

                if (!validation.validPlayerID(id)) {

                    System.out.println("Player ID must be greater than zero.");
                    continue;

                }

                // Check duplicate immediately

                if (manager.findPlayer(id) != null) {

                    System.out.println("That Player ID already exists.");
                    continue;

                }

                break;

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid integer.");

            }

        }

        // ----------------------------
        // Name
        // ----------------------------
        String name;

        while (true) {

            System.out.print("Enter Player Name: ");

            name = scanner.nextLine();

            if (validation.validString(name)) {

                break;

            }

            System.out.println("Player name cannot be blank.");

        }

        // ----------------------------
        // Team
        // ----------------------------

        String team;

        while (true) {

            System.out.println(validation.getTeamMenu());

            System.out.print("Choose Team Number: ");

            try {

                int choice = Integer.parseInt(scanner.nextLine());

                team = validation.getTeamByNumber(choice);

                if (team != null) {

                    break;

                }

                System.out.println("Invalid selection.");

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid team number.");

            }

        }

        // ----------------------------
        // Position
        // ----------------------------

        String position;

        while (true) {

            System.out.print("Position (PG/SG/SF/PF/C): ");

            position = scanner.nextLine().trim().toUpperCase();

            if (validation.validPosition(position)) {

                break;

            }

            System.out.println("Invalid position.");

        }

        // ----------------------------
        // PPG
        // ----------------------------

        double ppg;

        while (true) {

            try {

                System.out.print("Points Per Game: ");

                ppg = Double.parseDouble(scanner.nextLine());

                if (validation.validDouble(ppg)) {

                    break;

                }

                System.out.println("PPG cannot be negative.");

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number.");

            }

        }

        // ----------------------------
        // RPG
        // ----------------------------

        double rpg;

        while (true) {

            try {

                System.out.print("Rebounds Per Game: ");

                rpg = Double.parseDouble(scanner.nextLine());

                if (validation.validDouble(rpg)) {

                    break;

                }

                System.out.println("RPG cannot be negative.");

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number.");

            }

        }

        // ----------------------------
        // APG
        // ----------------------------

        double apg;

        while (true) {

            try {

                System.out.print("Assists Per Game: ");

                apg = Double.parseDouble(scanner.nextLine());

                if (validation.validDouble(apg)) {

                    break;

                }

                System.out.println("APG cannot be negative.");

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number.");

            }

        }

        // ----------------------------
        // Championships
        // ----------------------------

        int championships;

        while (true) {

            try {

                System.out.print("Championships Won: ");

                championships = Integer.parseInt(scanner.nextLine());

                if (validation.validChampionships(championships)) {

                    break;

                }

                System.out.println("Championships cannot be negative.");

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid integer.");

            }

        }

        // ----------------------------
        // Create Player
        // ----------------------------

        NBAPlayer player = new NBAPlayer(
                id,
                name,
                team,
                position,
                ppg,
                rpg,
                apg,
                championships
        );

        boolean added = manager.addPlayer(player);

        if (added) {

            System.out.println("\nPlayer successfully added!");
            System.out.println(player);

        } else {

            System.out.println("Player could not be added.");

        }

        return added;

    }

    /*
     Updates one field of an existing NBAPlayer object.
     User only updates the field they choose.
     User does not have to retype every field.
     Invalid input keeps the user inside the update menu.
     The player record is displayed before and after updates.
     */
    private boolean updatePlayer(Scanner scanner,
                                 PlayerManager manager,
                                 Validation validation) {

        System.out.println("\n========== UPDATE PLAYER ==========");

        int id;

        while (true) {

            try {

                System.out.print("Enter Player ID to update (or type -1 to cancel): ");

                id = Integer.parseInt(scanner.nextLine());

                if (id == -1) {
                    System.out.println("Update cancelled.");
                    return false;
                }

                if (!validation.validPlayerID(id)) {
                    System.out.println("Player ID must be greater than zero.");
                    continue;
                }

                if (manager.findPlayer(id) == null) {
                    System.out.println("Player was not found.");
                    continue;
                }

                break;

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid integer.");

            }
        }

        boolean updating = true;

        while (updating) {

            NBAPlayer player = manager.findPlayer(id);

            System.out.println("\nCurrent Player:");
            System.out.println(player);

            System.out.println("\nWhat would you like to update?");
            System.out.println("1. Name");
            System.out.println("2. Team");
            System.out.println("3. Position");
            System.out.println("4. Points Per Game");
            System.out.println("5. Rebounds Per Game");
            System.out.println("6. Assists Per Game");
            System.out.println("7. Championships");
            System.out.println("8. Finish Updating");
            System.out.print("Choose option: ");

            int choice;

            try {

                choice = Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {

                System.out.println("Please enter a number from 1 to 8.");
                continue;

            }

            switch (choice) {

                case 1:
                    String name;

                    while (true) {
                        System.out.print("Enter new player name: ");
                        name = scanner.nextLine();

                        if (validation.validString(name)) {
                            break;
                        }

                        System.out.println("Name cannot be blank.");
                    }

                    manager.updateName(id, name);
                    System.out.println("Name updated successfully.");
                    break;

                case 2:
                    String team;

                    while (true) {
                        System.out.println(validation.getTeamMenu());
                        System.out.print("Choose new team number: ");

                        try {
                            int teamChoice = Integer.parseInt(scanner.nextLine());
                            team = validation.getTeamByNumber(teamChoice);

                            if (team != null) {
                                break;
                            }

                            System.out.println("Invalid team selection.");

                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid team number.");
                        }
                    }

                    manager.updateTeam(id, team);
                    System.out.println("Team updated successfully.");
                    break;

                case 3:
                    String position;

                    while (true) {
                        System.out.print("Enter new position (PG/SG/SF/PF/C): ");
                        position = scanner.nextLine().trim().toUpperCase();

                        if (validation.validPosition(position)) {
                            break;
                        }

                        System.out.println("Invalid position.");
                    }

                    manager.updatePosition(id, position);
                    System.out.println("Position updated successfully.");
                    break;

                case 4:
                    double ppg;

                    while (true) {
                        try {
                            System.out.print("Enter new Points Per Game: ");
                            ppg = Double.parseDouble(scanner.nextLine());

                            if (validation.validDouble(ppg)) {
                                break;
                            }

                            System.out.println("PPG cannot be negative.");

                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid number.");
                        }
                    }

                    manager.updatePpg(id, ppg);
                    System.out.println("PPG updated successfully.");
                    break;

                case 5:
                    double rpg;

                    while (true) {
                        try {
                            System.out.print("Enter new Rebounds Per Game: ");
                            rpg = Double.parseDouble(scanner.nextLine());

                            if (validation.validDouble(rpg)) {
                                break;
                            }

                            System.out.println("RPG cannot be negative.");

                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid number.");
                        }
                    }

                    manager.updateRpg(id, rpg);
                    System.out.println("RPG updated successfully.");
                    break;

                case 6:
                    double apg;

                    while (true) {
                        try {
                            System.out.print("Enter new Assists Per Game: ");
                            apg = Double.parseDouble(scanner.nextLine());

                            if (validation.validDouble(apg)) {
                                break;
                            }

                            System.out.println("APG cannot be negative.");

                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid number.");
                        }
                    }

                    manager.updateApg(id, apg);
                    System.out.println("APG updated successfully.");
                    break;

                case 7:
                    int championships;

                    while (true) {
                        try {
                            System.out.print("Enter new championships won: ");
                            championships = Integer.parseInt(scanner.nextLine());

                            if (validation.validChampionships(championships)) {
                                break;
                            }

                            System.out.println("Championships cannot be negative.");

                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid integer.");
                        }
                    }

                    manager.updateChampionships(id, championships);
                    System.out.println("Championships updated successfully.");
                    break;

                case 8:
                    updating = false;
                    System.out.println("\nFinished updating player.");
                    System.out.println(manager.findPlayer(id));
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1 through 8.");
                    break;
            }
        }

        return true;
    }

    /*
     Removes an NBAPlayer from the system.
     Invalid IDs do not crash the program.
     If a player does not exist, the user receives a clear message.
     The user can cancel anytime
     */
    private boolean removePlayer(Scanner scanner,
                                 PlayerManager manager,
                                 Validation validation) {

        System.out.println("\n========== REMOVE PLAYER ==========");

        while (true) {

            try {

                System.out.print("Enter Player ID to remove (or type -1 to cancel): ");

                int id = Integer.parseInt(scanner.nextLine());

                if (id == -1) {
                    System.out.println("Remove cancelled.");
                    return false;
                }

                if (!validation.validPlayerID(id)) {
                    System.out.println("Player ID must be greater than zero.");
                    continue;
                }

                NBAPlayer player = manager.findPlayer(id);

                if (player == null) {
                    System.out.println("Player was not found. Try again or enter -1 to cancel.");
                    continue;
                }

                System.out.println("Removing:");
                System.out.println(player);

                boolean removed = manager.removePlayer(id);

                if (removed) {
                    System.out.println("Player removed successfully.");
                    return true;
                }

                System.out.println("Player could not be removed.");
                return false;

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid integer.");

            }
        }
    }

    /*
     Compares two NBA legends using the custom action.
     */
    private boolean comparePlayers(Scanner scanner,
                                   PlayerManager manager,
                                   Validation validation) {

        System.out.println("\n========== COMPARE PLAYERS ==========");

        int firstID;
        int secondID;

        while (true) {

            try {

                System.out.print("Enter first Player ID (or type -1 to cancel): ");

                firstID = Integer.parseInt(scanner.nextLine());

                if (firstID == -1) {
                    System.out.println("Comparison cancelled.");
                    return false;
                }

                if (!validation.validPlayerID(firstID)) {
                    System.out.println("Player ID must be greater than zero.");
                    continue;
                }

                if (manager.findPlayer(firstID) == null) {
                    System.out.println("First player was not found.");
                    continue;
                }

                break;

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid integer.");

            }
        }

        while (true) {

            try {

                System.out.print("Enter second Player ID (or type -1 to cancel): ");

                secondID = Integer.parseInt(scanner.nextLine());

                if (secondID == -1) {
                    System.out.println("Comparison cancelled.");
                    return false;
                }

                if (!validation.validPlayerID(secondID)) {
                    System.out.println("Player ID must be greater than zero.");
                    continue;
                }

                if (manager.findPlayer(secondID) == null) {
                    System.out.println("Second player was not found.");
                    continue;
                }

                break;

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid integer.");

            }
        }

        String result = manager.comparePlayers(firstID, secondID);

        System.out.println(result);

        return true;
    }
}