package newmatch.zbmf.com.testapplication.utils.fileUtils;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created By pq
 * on 2019/7/25
 * Uri,File,Path之间的相互转换
 * 原文：https://blog.csdn.net/hust_twj/article/details/76665294
 */
public class UriFilePathExUtils {

    /**
     * uri转file
     *
     * @param uri
     * @return
     */
    public static File uriToFile(Uri uri) {
        try {
            return new File(new URI(uri.toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * file转URI
     *
     * @param file
     * @return
     */
    public static Uri fileToUri(File file) {
        return Uri.parse(file.getAbsolutePath());
    }

    /**
     * uri转Path
     *
     * @param context
     * @param uri
     * @return
     */
    public static String uriToPath(Context context, Uri uri) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri, null,
                null, null, null);
        if (cursor == null) {
            return null;
        }
        if (cursor.moveToFirst()) {
            try {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return path;
    }

    /**
     * 路径转Uri
     *
     * @param path
     * @return
     */
    public static Uri pathToUri(String path) {
        return Uri.parse(path);
    }

    /**
     * file转path
     * @param file
     * @return
     */
    public static String fileToPath(File file) {
        return file.getPath();
    }

    /**
     * path转file
     * @param path
     * @return
     */
    public static File pathToFile(String path){
        return new File(path);
    }

}
