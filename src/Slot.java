import java.io.Serializable;
/*@desc
@author Bings
@date 2024/8/3 14:43*/
public class Slot implements Serializable{
    private Item item;
    private int quantity;

    public Slot() {
        this.item = null;
        this.quantity = 0;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}


