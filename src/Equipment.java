public class Equipment extends Item {
    private int level;
    private int quality;

    public Equipment(String id, String name, int price, String description, int level, int quality) {
        super(id, name, "装备", price, description);
        this.level = level;
        this.quality = quality;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    @Override
    public void destroy() {
        System.out.println("装备 " + getName() + " 已销毁");
    }

    @Override
    public void trade() {
        if (isIdentified()) {
            System.out.println("装备 " + getName() + " 已交易");
        } else {
            System.out.println("未鉴定的装备不能交易");
        }
    }
}
