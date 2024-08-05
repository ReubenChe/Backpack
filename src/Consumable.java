public class Consumable extends Item {
    public Consumable(String id, String name, int price, String description) {
        super(id, name, "消耗品", price, description);
    }

    @Override
    public void destroy() {
        System.out.println("消耗品 " + getName() + " 已销毁");
    }

    @Override
    public void trade() {
        System.out.println("消耗品 " + getName() + " 已交易");
    }
}
