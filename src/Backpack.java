import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Backpack implements Serializable {
    private List<Slot> slots;
    private Wallet wallet;

    public Backpack() {
        slots = new ArrayList<>(30);
        for (int i = 0; i < 30; i++) {
            slots.add(new Slot());
        }
        wallet = new Wallet();
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void queryAllItems() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < slots.size(); i++) {
            Slot slot = slots.get(i);
            if (slot.getItem() != null) {
                Item item = slot.getItem();
                result.append("Slot ").append(i + 1).append(": ")
                        .append("名称: 【").append(item.getName()).append("】")
                        .append("编号: ").append(item.getId()).append(", ")
                        .append("类型: ").append(item.getType()).append(", ")
                        .append("价格: ").append(item.getPrice()).append(", ")
                        .append("描述: ").append(item.getDescription()).append(", ")
                        .append("数量: 【").append(slot.getQuantity()).append("】\n");
            } else {
                result.append("Slot ").append(i + 1).append(": 空闲\n");
            }
        }
        JOptionPane.showMessageDialog(null, result.toString(), "查询所有物品", JOptionPane.INFORMATION_MESSAGE);
    }



    public void queryItem(String nameOrId) {
        StringBuilder result = new StringBuilder();
        for (Slot slot : slots) {
            if (slot.getItem() != null && (slot.getItem().getName().equals(nameOrId) || slot.getItem().getId().equals(nameOrId))) {
                Item item = slot.getItem();
                result.append("名称: ").append(item.getName()).append(", ")
                        .append("类型: ").append(item.getType()).append(", ")
                        .append("数量: ").append(slot.getQuantity()).append("\n");
                JOptionPane.showMessageDialog(null, result.toString(), "查询指定物品", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "查询物品不存在", "查询指定物品", JOptionPane.INFORMATION_MESSAGE);
    }

    public void queryMoney() {
        JOptionPane.showMessageDialog(null, "当前金钱数量: " + wallet.getMoney(), "查询金钱", JOptionPane.INFORMATION_MESSAGE);
    }

    public void identifyItem(String nameOrId) {
        for (Slot slot : slots) {
            if (slot.getItem() != null && (slot.getItem().getName().equals(nameOrId) || slot.getItem().getId().equals(nameOrId))) {
                if (slot.getItem().isIdentified()) {
                    JOptionPane.showMessageDialog(null, "该物品已被鉴定", "鉴定物品", JOptionPane.INFORMATION_MESSAGE);
                } else if (slot.getItem() instanceof Prop) {
                    JOptionPane.showMessageDialog(null, "该物品不可鉴定", "鉴定物品", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    slot.getItem().identify();
                    JOptionPane.showMessageDialog(null, "该物品鉴定完毕", "鉴定物品", JOptionPane.INFORMATION_MESSAGE);
                    FileManager.saveBackpack(this);
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "鉴定物品不存在", "鉴定物品", JOptionPane.INFORMATION_MESSAGE);
    }


    public void addItem(String nameOrId, int quantity) {
        int remainingQuantity = quantity;

        // 先尝试将物品添加到已有的相同物品的格子中
        for (Slot slot : slots) {
            if (slot.getItem() != null && (slot.getItem().getName().equals(nameOrId) || slot.getItem().getId().equals(nameOrId))) {
                int availableSpace = 5 - slot.getQuantity();
                if (availableSpace > 0) {
                    int addQuantity = Math.min(availableSpace, remainingQuantity);
                    slot.setQuantity(slot.getQuantity() + addQuantity);
                    remainingQuantity -= addQuantity;
                    if (remainingQuantity == 0) {
                        JOptionPane.showMessageDialog(null, "添加成功", "添加物品", JOptionPane.INFORMATION_MESSAGE);
                        FileManager.saveBackpack(this);
                        return;
                    }
                }
            }
        }

        // 如果还有剩余的物品，尝试将其添加到空闲格子中
        for (Slot slot : slots) {
            if (slot.getItem() == null) {
                int addQuantity = Math.min(5, remainingQuantity);
                slot.setItem(createItem(nameOrId));
                slot.setQuantity(addQuantity);
                remainingQuantity -= addQuantity;
                if (remainingQuantity == 0) {
                    JOptionPane.showMessageDialog(null, "添加成功", "添加物品", JOptionPane.INFORMATION_MESSAGE);
                    FileManager.saveBackpack(this);
                    return;
                }
            }
        }

        // 如果还有剩余的物品，说明背包已满
        if (remainingQuantity > 0) {
            JOptionPane.showMessageDialog(null, "背包已满", "添加物品", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void destroyItem(String nameOrId, int quantity) {
        int remainingQuantity = quantity;
        boolean itemFound = false;

        for (Slot slot : slots) {
            if (slot.getItem() != null && (slot.getItem().getName().equals(nameOrId) || slot.getItem().getId().equals(nameOrId))) {
                itemFound = true;

                // 检查物品是否允许销毁
                if (!(slot.getItem() instanceof Equipment || slot.getItem() instanceof Material || slot.getItem() instanceof Consumable)) {
                    JOptionPane.showMessageDialog(null, "该物品不允许销毁", "销毁物品", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (slot.getQuantity() >= remainingQuantity) {
                    slot.setQuantity(slot.getQuantity() - remainingQuantity);
                    if (slot.getQuantity() == 0) {
                        slot.setItem(null);
                    }
                    JOptionPane.showMessageDialog(null, "销毁成功: " + nameOrId + " x" + quantity, "销毁物品", JOptionPane.INFORMATION_MESSAGE);
                    FileManager.saveBackpack(this);
                    return;
                } else {
                    remainingQuantity -= slot.getQuantity();
                    slot.setItem(null);
                    slot.setQuantity(0);
                }
            }
        }

        if (itemFound && remainingQuantity > 0) {
            JOptionPane.showMessageDialog(null, "背包物品数量不足，销毁失败", "销毁物品", JOptionPane.INFORMATION_MESSAGE);
        } else if (!itemFound) {
            JOptionPane.showMessageDialog(null, "销毁物品不存在", "销毁物品", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void tradeItem(String nameOrId, int quantity) {
        int remainingQuantity = quantity;
        boolean itemFound = false;
        int totalTradeAmount = 0;

        for (Slot slot : slots) {
            if (slot.getItem() != null && (slot.getItem().getName().equals(nameOrId) || slot.getItem().getId().equals(nameOrId))) {
                itemFound = true;

                if (!slot.getItem().isIdentified()) {
                    JOptionPane.showMessageDialog(null, "未鉴定的物品不能交易", "交易物品", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (slot.getQuantity() >= remainingQuantity) {
                    int tradeAmount = (int) (slot.getItem().getPrice() * remainingQuantity * 0.9); // 10% tax
                    totalTradeAmount += tradeAmount;
                    wallet.setMoney(wallet.getMoney() + tradeAmount);
                    slot.setQuantity(slot.getQuantity() - remainingQuantity);
                    if (slot.getQuantity() == 0) {
                        slot.setItem(null);
                    }
                    JOptionPane.showMessageDialog(null, "交易成功，获得金钱: " + totalTradeAmount + "\n当前钱包金钱数量: " + wallet.getMoney(), "交易物品", JOptionPane.INFORMATION_MESSAGE);
                    FileManager.saveBackpack(this);
                    return;
                } else {
                    int tradeAmount = (int) (slot.getItem().getPrice() * slot.getQuantity() * 0.9); // 10% tax
                    totalTradeAmount += tradeAmount;
                    wallet.setMoney(wallet.getMoney() + tradeAmount);
                    remainingQuantity -= slot.getQuantity();
                    slot.setItem(null);
                    slot.setQuantity(0);
                }
            }
        }

        if (itemFound && remainingQuantity > 0) {
            JOptionPane.showMessageDialog(null, "背包物品数量不足，交易失败", "交易物品", JOptionPane.INFORMATION_MESSAGE);
        } else if (!itemFound) {
            JOptionPane.showMessageDialog(null, "交易物品不存在", "交易物品", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void organizeBackpack() {
        List<Item> items = new ArrayList<>();

        // 收集所有物品
        for (Slot slot : slots) {
            if (slot.getItem() != null) {
                for (int i = 0; i < slot.getQuantity(); i++) {
                    items.add(slot.getItem());
                }
                slot.setItem(null);
                slot.setQuantity(0);
            }
        }

        // 按照物品类型排序
        items.sort((item1, item2) -> {
            int type1 = getTypePriority(item1.getType());
            int type2 = getTypePriority(item2.getType());
            return Integer.compare(type1, type2);
        });

        // 重新分配物品到背包
        int index = 0;
        while (!items.isEmpty() && index < slots.size()) {
            Item currentItem = items.get(0);
            int count = 0;
            while (count < 5 && !items.isEmpty() && items.get(0).equals(currentItem)) {
                count++;
                items.remove(0);
            }
            slots.get(index).setItem(currentItem);
            slots.get(index).setQuantity(count);
            index++;
        }

        JOptionPane.showMessageDialog(null, "背包已整理", "整理背包", JOptionPane.INFORMATION_MESSAGE);
        FileManager.saveBackpack(this);
    }

    private int getTypePriority(String type) {
        switch (type) {
            case "装备":
                return 1;
            case "道具":
                return 2;
            case "材料":
                return 3;
            case "消耗品":
                return 4;
            default:
                return 5;
        }
    }


    public void expandBackpack() {
        // 检查当前背包是否已经扩容到最大容量
        if (slots.size() >= 40) {
            JOptionPane.showMessageDialog(null, "背包已达到最大容量", "扩容背包", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // 扩容背包，每次增加10个格子
        for (int i = 0; i < 10; i++) {
            slots.add(new Slot());
        }

        JOptionPane.showMessageDialog(null, "背包已扩容", "扩容背包", JOptionPane.INFORMATION_MESSAGE);
        FileManager.saveBackpack(this);
    }


    private Item createItem(String nameOrId) {
        String goodsInfo = GoodsList.getGoodsInfo(nameOrId);
        if (goodsInfo == null) {
            return null;
        }

        String[] parts = goodsInfo.split(" ");
        String name = parts[0];
        String id = parts[1];
        String type = parts[2];
        int price = Integer.parseInt(parts[3]);
        String description = parts[4];

        switch (type) {
            case "消耗品":
                return new Consumable(id, name, price, description);
            case "材料":
                return new Material(id, name, price, description);
            case "装备":
                int level = Integer.parseInt(parts[5]);
                int quality = Integer.parseInt(parts[6]);
                return new Equipment(id, name, price, description, level, quality);
            case "道具":
                String taskDisplay = parts[5];
                return new Prop(id, name, price, description, taskDisplay);
            default:
                return null;
        }
    }
}


