public class Prop extends Item {
    private String taskDisplay;

    public Prop(String id, String name, int price, String description, String taskDisplay) {
        super(id, name, "道具", price, description);
        this.taskDisplay = taskDisplay;
    }

    public String getTaskDisplay() {
        return taskDisplay;
    }

    public void setTaskDisplay(String taskDisplay) {
        this.taskDisplay = taskDisplay;
    }

    @Override
    public void destroy() {
        System.out.println("道具 " + getName() + " 已销毁");
    }

    @Override
    public void trade() {
        System.out.println("道具 " + getName() + " 已交易");
    }
}
