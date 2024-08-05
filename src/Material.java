public class Material extends Item {
    public Material(String id, String name, int price, String description) {
        super(id, name, "材料", price, description);
    }

    @Override
    public void destroy() {
        System.out.println("材料 " + getName() + " 已销毁");
    }

    @Override
    public void trade() {
        System.out.println("材料 " + getName() + " 已交易");
    }
}
