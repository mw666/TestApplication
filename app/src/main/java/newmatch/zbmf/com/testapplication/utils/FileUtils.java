package newmatch.zbmf.com.testapplication.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created By pq
 * on 2019/5/27
 * 文件读写操作类
 */
public class FileUtils {

    //检查文件目录是否存在
    private static void checkDir(String dirsPath) {
        File dirs = new File(dirsPath);
        if (!dirs.exists()) dirs.mkdirs();
    }

    /**
     * 往目标文件路径写入数据
     *
     * @param data     文件数据
     * @param dirsPath 文件夹路径
     * @param fileName 文件名
     */
    public static boolean writFile(byte[] data, String dirsPath, String fileName) {
        boolean save = false;
        checkDir(dirsPath);
        File file = new File(fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.flush();
            save = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return save;
    }

}
