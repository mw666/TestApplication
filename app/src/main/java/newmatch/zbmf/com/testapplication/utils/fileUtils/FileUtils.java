package newmatch.zbmf.com.testapplication.utils.fileUtils;

import android.os.Environment;

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

    //获取夜嗨根目录文件夹的路径
    public static String getYeHiRoot() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "YeHi";
    }

    //获取夜嗨跟文件夹下的图片的存储路径
    public static String getYeHiImgPath() {
        return getYeHiRoot() + File.separator + "img";
    }

    //获取夜嗨跟文件夹下的用户图片的存储路径
    public static String getYeHiImgUserPath() {
        return getYeHiImgPath() + File.separator + "userImg";
    }

    //获取夜嗨跟文件夹下可协助的图片路径
    public static String getYehiImgAssistPath() {
        return getYeHiImgPath() + File.separator + "assistImg";
    }

    //获取用户的展示封面图
    public static String getUserShowCover(String timeStamp) {
        return getYeHiImgUserPath() + File.separator + "coverImg" + timeStamp + ".png";
    }

    //检查文件目录是否存在
    public static void checkDir(String dirsPath) {
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
