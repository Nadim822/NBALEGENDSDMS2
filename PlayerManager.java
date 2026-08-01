/*
Nadim Ramrattan
CEN 3024C

This class manages the collection of NBAPlayer objects
stored in the Data Management System.
This class acts as the "manager" for the application's data.
It performs all CRUD operations while NBAPlayer simply stores
the information. */

import java.util.ArrayList;

public class PlayerManager {

    private ArrayList<NBAPlayer> players;

    /*
     Creates a PlayerManager using an existing player list.
     */
    public PlayerManager(ArrayList<NBAPlayer> players) {
        this.players = players;
    }

    /*
     Returns the current list of players.
     Useful for unit testing.
     */
    public ArrayList<NBAPlayer> getPlayers() {
        return players;
    }

    /*
     Finds a player by ID.
     */
    public NBAPlayer findPlayer(int id) {

        for (NBAPlayer player : players) {
            if (player.getPlayerId() == id) {
                return player;
            }
        }

        return null;
    }

    /*
     Adds a player only if the player is not null
     and the ID is not already being used.
     */
    public boolean addPlayer(NBAPlayer player) {

        if (player == null) {
            return false;
        }

        if (findPlayer(player.getPlayerId()) != null) {
            return false;
        }

        players.add(player);
        return true;
    }

    /*
     Removes a player by ID.
     */
    public boolean removePlayer(int id) {

        NBAPlayer player = findPlayer(id);

        if (player == null) {
            return false;
        }

        players.remove(player);
        return true;
    }

    /*
     Updates all fields of a player.
     This method is useful for unit testing because it accepts arguments
     directly instead of collecting input with Scanner.
     */
    public boolean updatePlayer(int id, String name, String team, String position,
                                double ppg, double rpg, double apg, int championships) {

        NBAPlayer player = findPlayer(id);

        if (player == null) {
            return false;
        }

        player.setName(name);
        player.setTeam(team);
        player.setPosition(position);
        player.setPpg(ppg);
        player.setRpg(rpg);
        player.setApg(apg);
        player.setChampionships(championships);

        return true;
    }

    /*
     Updates only the player's name.
     */
    public boolean updateName(int id, String name) {

        NBAPlayer player = findPlayer(id);

        if (player == null) {
            return false;
        }

        return player.setName(name);
    }

    /*
     Updates only the player's team.
     */
    public boolean updateTeam(int id, String team) {

        NBAPlayer player = findPlayer(id);

        if (player == null) {
            return false;
        }

        return player.setTeam(team);
    }

    /*
     Updates only the player's position.
     */
    public boolean updatePosition(int id, String position) {

        NBAPlayer player = findPlayer(id);

        if (player == null) {
            return false;
        }

        return player.setPosition(position);
    }

    /*
     Updates only points per game.
     */
    public boolean updatePpg(int id, double ppg) {

        NBAPlayer player = findPlayer(id);

        if (player == null) {
            return false;
        }

        return player.setPpg(ppg);
    }

    /*
     Updates only rebounds per game.
     */
    public boolean updateRpg(int id, double rpg) {

        NBAPlayer player = findPlayer(id);

        if (player == null) {
            return false;
        }

        return player.setRpg(rpg);
    }

    /*
     Updates only assists per game.
     */
    public boolean updateApg(int id, double apg) {

        NBAPlayer player = findPlayer(id);

        if (player == null) {
            return false;
        }

        return player.setApg(apg);
    }

    /*
     Updates only championships.
     */
    public boolean updateChampionships(int id, int championships) {

        NBAPlayer player = findPlayer(id);

        if (player == null) {
            return false;
        }

        return player.setChampionships(championships);
    }

    /*
     Displays all players as one formatted String.
     */
    public String displayPlayers() {

        if (players.isEmpty()) {
            return "No players currently exist in the system.";
        }

        StringBuilder output = new StringBuilder();

        output.append("\n========== NBA LEGENDS ==========\n\n");

        for (NBAPlayer player : players) {
            output.append(player).append("\n");
        }

        return output.toString();
    }

    /*
     Compares two NBA legends and calculates which player leads
     in PPG, RPG, APG, and championships.
     */
    public String comparePlayers(int firstID, int secondID) {

        NBAPlayer firstPlayer = findPlayer(firstID);
        NBAPlayer secondPlayer = findPlayer(secondID);

        if (firstPlayer == null || secondPlayer == null) {
            return "One or both Player IDs were not found.";
        }

        StringBuilder result = new StringBuilder();

        result.append("\n==============================\n");
        result.append(" NBA LEGEND COMPARISON\n");
        result.append("==============================\n\n");

        result.append(firstPlayer.getName())
                .append(" VS ")
                .append(secondPlayer.getName())
                .append("\n\n");

        result.append("Points Per Game Winner: ")
                .append(compareStat(firstPlayer.getName(), firstPlayer.getPpg(),
                        secondPlayer.getName(), secondPlayer.getPpg()))
                .append("\n");

        result.append("Rebounds Per Game Winner: ")
                .append(compareStat(firstPlayer.getName(), firstPlayer.getRpg(),
                        secondPlayer.getName(), secondPlayer.getRpg()))
                .append("\n");

        result.append("Assists Per Game Winner: ")
                .append(compareStat(firstPlayer.getName(), firstPlayer.getApg(),
                        secondPlayer.getName(), secondPlayer.getApg()))
                .append("\n");

        result.append("Championships Winner: ")
                .append(compareStat(firstPlayer.getName(), firstPlayer.getChampionships(),
                        secondPlayer.getName(), secondPlayer.getChampionships()))
                .append("\n");

        return result.toString();
    }

    /*
     Helper method used by comparePlayers().
     It returns the winner of a stat category or "Tie."
     */
    private String compareStat(String firstName, double firstValue,
                               String secondName, double secondValue) {

        if (firstValue > secondValue) {
            return firstName;
        }

        if (secondValue > firstValue) {
            return secondName;
        }

        return "Tie";
    }
}