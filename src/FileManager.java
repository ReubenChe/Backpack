import java.io.*;

/*@desc
@author Bings   //文件操作
@date 2024/8/3 16:15*/

public class FileManager {
    private static final String FILE_NAME = "backpack.dat";

    public static void saveBackpack(Backpack backpack) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(backpack);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Backpack loadBackpack() {
        Backpack backpack = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            backpack = (Backpack) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("背包数据文件未找到，初始化新数据。");
            backpack = new Backpack();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("...系统初始化成功");
        return backpack;
    }
}



