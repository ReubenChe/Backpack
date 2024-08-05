import java.io.Serializable;
/*@desc
@author Bings
@date 2024/8/3 14:43*/
public abstract class Item implements Serializable{
    private String name;
    private String type;
    private String id;
    private int price;
    private String description;
    private boolean identified;

    public Item(String id, String name, String type, int price, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.description = description;
        this.identified = false;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public boolean isIdentified() {
        return identified;
    }

    public void identify() {
        this.identified = true;
    }

    public abstract void destroy();

    public abstract void trade();
}

