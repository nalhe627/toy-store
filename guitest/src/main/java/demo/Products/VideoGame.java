package demo.Products;

/** Class that represents a video game product (not a sublcass of the physical class) */
public class VideoGame extends Product {
    
    // Attribute
    private String team;

    /**
     * Construct for the VideoGame class.
     * @param sku - Sku number of product
     * @param name  - Name of product
     * @param price - Price of product
     * @param team - The team that made the video game
     */
    public VideoGame(String sku, String name, float price, String team) {
        super(sku, name, price);
        this.team = team;
    }

    /**
     * Getter for team
     * @return Team that made the video game
     */
    public String getTeam() {
        return team;
    }

    @Override
    public String toString() {
        return super.getSku() + ": " + super.getName() + ", by " + team + ". $" + String.format("%.2f", super.getPrice());
    }

    @Override
    public String toDatabase() {
        return super.getSku() + ";" + super.getName() + ";" + String.format("%.2f", super.getPrice()) + ";" + team;
    }
}
