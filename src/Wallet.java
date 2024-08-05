import java.io.Serializable;
/*@desc
@author Bings
@date 2024/8/3 14:43*/
public class Wallet implements Serializable{
    private int money;

    public Wallet() {
        this.money = 0;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}

