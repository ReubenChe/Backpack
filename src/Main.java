import javax.swing.*;
/*@desc
@author Bings
@date 2024/8/3 15:36*/
public class Main {
    public static void main(String[] args) {
        // 加载背包数据
        Backpack backpack = FileManager.loadBackpack();

        while (true) {
            String[] options = {"添加物品", "查询所有物品", "查询指定物品", "查询金钱", "鉴定物品", "交易物品", "销毁物品", "整理背包", "扩容背包", "退出"};
            int choice = JOptionPane.showOptionDialog(null, "选择一个操作", "背包系统",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    addItem(backpack);
                    break;
                case 1:
                    backpack.queryAllItems();
                    break;
                case 2:
                    queryItem(backpack);
                    break;
                case 3:
                    backpack.queryMoney();
                    break;
                case 4:
                    identifyItem(backpack);
                    break;
                case 5:
                    tradeItem(backpack);
                    break;
                case 6:
                    destroyItem(backpack);
                    break;
                case 7:
                    backpack.organizeBackpack();
                    break;
                case 8:
                    backpack.expandBackpack();
                    break;
                case 9:
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }

    private static void addItem(Backpack backpack) {
        String nameOrId = JOptionPane.showInputDialog("输入物品名称或编号:");
        int quantity = Integer.parseInt(JOptionPane.showInputDialog("输入数量:"));
        backpack.addItem(nameOrId, quantity);
    }

    private static void queryItem(Backpack backpack) {
        String nameOrId = JOptionPane.showInputDialog("输入物品名称或编号:");
        backpack.queryItem(nameOrId);
    }

    private static void identifyItem(Backpack backpack) {
        String nameOrId = JOptionPane.showInputDialog("输入物品名称或编号:");
        backpack.identifyItem(nameOrId);
    }

    private static void tradeItem(Backpack backpack) {
        String nameOrId = JOptionPane.showInputDialog("输入物品名称或编号:");
        int quantity = Integer.parseInt(JOptionPane.showInputDialog("输入数量:"));
        backpack.tradeItem(nameOrId, quantity);
    }

    private static void destroyItem(Backpack backpack) {
        String nameOrId = JOptionPane.showInputDialog("输入物品名称或编号:");
        int quantity = Integer.parseInt(JOptionPane.showInputDialog("输入数量:"));
        backpack.destroyItem(nameOrId, quantity);
    }
}


