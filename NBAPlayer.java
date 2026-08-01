/*
 Nadim Ramrattan
 CEN 3024C

 This class represents a single NBA legend in the Data
 Management System. Every player object stores career
 statistics and information that can be managed through
 CRUD operations. This class is responsible for storing player data.

 */
public class NBAPlayer {

    private int playerId;
    private String name;
    private String team;
    private String position;
    private double ppg;
    private double rpg;
    private double apg;
    private int championships;

    /*
     Creates an NBAPlayer object with all required attributes.
     */
    public NBAPlayer(int playerId, String name, String team, String position,
                     double ppg, double rpg, double apg, int championships) {

        this.playerId = playerId;
        this.name = name;
        this.team = team;
        this.position = position;
        this.ppg = ppg;
        this.rpg = rpg;
        this.apg = apg;
        this.championships = championships;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public String getPosition() {
        return position;
    }

    public double getPpg() {
        return ppg;
    }

    public double getRpg() {
        return rpg;
    }

    public double getApg() {
        return apg;
    }

    public int getChampionships() {
        return championships;
    }

    public boolean setName(String name) {
        this.name = name;
        return true;
    }

    public boolean setTeam(String team) {
        this.team = team;
        return true;
    }

    public boolean setPosition(String position) {
        this.position = position;
        return true;
    }

    public boolean setPpg(double ppg) {
        this.ppg = ppg;
        return true;
    }

    public boolean setRpg(double rpg) {
        this.rpg = rpg;
        return true;
    }

    public boolean setApg(double apg) {
        this.apg = apg;
        return true;
    }

    public boolean setChampionships(int championships) {
        this.championships = championships;
        return true;
    }

    /*
      Returns the player as one formatted line for CLI display.
     */
    @Override
    public String toString() {
        return String.format(
                "ID: %d | Name: %s | Team: %s | Position: %s | PPG: %.1f | RPG: %.1f | APG: %.1f | Championships: %d",
                playerId, name, team, position, ppg, rpg, apg, championships
        );
    }
}