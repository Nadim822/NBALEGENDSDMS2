/*
Nadim Ramrattan
CEN 3024C
 This class is responsible for validating all user input
 before it is stored in the Data Management System.
 By validating input before creating or updating objects,
 the program prevents invalid data from entering the system

 */

import java.util.ArrayList;

public class Validation {

    /*
     List of valid NBA teams.
     This prevents users from entering random or unrealistic team names.
     */
    private final String[] validTeams = {
            "Atlanta Hawks",
            "Boston Celtics",
            "Brooklyn Nets",
            "Charlotte Hornets",
            "Chicago Bulls",
            "Cleveland Cavaliers",
            "Dallas Mavericks",
            "Denver Nuggets",
            "Detroit Pistons",
            "Golden State Warriors",
            "Houston Rockets",
            "Indiana Pacers",
            "Los Angeles Clippers",
            "Los Angeles Lakers",
            "Memphis Grizzlies",
            "Miami Heat",
            "Milwaukee Bucks",
            "Minnesota Timberwolves",
            "New Orleans Pelicans",
            "New York Knicks",
            "Oklahoma City Thunder",
            "Orlando Magic",
            "Philadelphia 76ers",
            "Phoenix Suns",
            "Portland Trail Blazers",
            "Sacramento Kings",
            "San Antonio Spurs",
            "Toronto Raptors",
            "Utah Jazz",
            "Washington Wizards"
    };

    /*
      Validates that a string is not null or blank.
     */
    public boolean validString(String input) {
        return input != null && !input.trim().isEmpty();
    }

    /*
     Validates that a player ID is greater than zero.
     */
    public boolean validPlayerID(int id) {
        return id > 0;
    }

    /*
      Validates that a statistic is zero or greater.
     */
    public boolean validDouble(double number) {
        return number >= 0;
    }

    /*
     Validates championships.
     Zero championships is valid.
     */
    public boolean validChampionships(int championships) {
        return championships >= 0;
    }

    /*
     Validates standard basketball positions.
     */
    public boolean validPosition(String position) {

        if (position == null) {
            return false;
        }

        position = position.trim().toUpperCase();

        return position.equals("PG") ||
                position.equals("SG") ||
                position.equals("SF") ||
                position.equals("PF") ||
                position.equals("C");
    }

    /*
     Validates that the team entered is one of the current NBA teams.
     */
    public boolean validTeam(String team) {

        if (team == null) {
            return false;
        }

        team = team.trim();

        for (String validTeam : validTeams) {
            if (validTeam.equalsIgnoreCase(team)) {
                return true;
            }
        }

        return false;
    }

    /*
     Returns all valid teams as a formatted menu string.
     The Application class will display this when the user chooses a team.
     */
    public String getTeamMenu() {

        StringBuilder menu = new StringBuilder();

        menu.append("\nChoose a team:\n");

        for (int i = 0; i < validTeams.length; i++) {
            menu.append(i + 1)
                    .append(". ")
                    .append(validTeams[i])
                    .append("\n");
        }

        return menu.toString();
    }

    /*
     Returns a team name based on a user's menu selection.
     */
    public String getTeamByNumber(int choice) {

        if (choice < 1 || choice > validTeams.length) {
            return null;
        }

        return validTeams[choice - 1];
    }

    /*
     Checks if a Player ID already exists in the current player list.
     */
    public boolean idExists(ArrayList<NBAPlayer> players, int playerID) {

        for (NBAPlayer player : players) {
            if (player.getPlayerId() == playerID) {
                return true;
            }
        }

        return false;
    }
}