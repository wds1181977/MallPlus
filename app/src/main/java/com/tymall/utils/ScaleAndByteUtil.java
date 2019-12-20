package com.tymall.utils;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.DimenRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.PermissionChecker;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import com.tymall.app.MyApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;

public class ScaleAndByteUtil {

    private static Point displaySize;

    private static Context getContext() {
        return MyApplication.getContext();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private static Point getDisplaySize() {
        if (displaySize == null) {
            WindowManager windowManager = (WindowManager) getContext()
                    .getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            display.getSize(displaySize = new Point());
        }
        return displaySize;
    }

    private ScaleAndByteUtil() {
    }

    /**
     * 从资源获取尺寸
     */
    public static float getResDimen(@DimenRes int dimen) {
        return getContext().getResources().getDimension(dimen);
    }

    /**
     * 获取屏幕宽度：PX
     *
     * @return
     */
    public static int getDisplayWidth() {
        return getDisplaySize().x;
    }

    /**
     * 获取屏幕高度：PX
     *
     * @return
     */
    public static int getDisplayHeight() {
        return getDisplaySize().y;
    }

    /**
     * DIP转PX
     *
     * @param value
     * @return
     */
    public static int dip2px(float value) {
        float dipScale = getContext().getResources().getDisplayMetrics().density;
        return (int) (value * dipScale + 0.5f);
    }

    /**
     * DIP转PX
     */
    public static int dip2px(int dip, Fragment fragment) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dip, fragment.getActivity().getResources().getDisplayMetrics());
    }

    /**
     * PX转DIP
     *
     * @param value
     * @return
     */
    public static int px2dip(int value) {
        float dipScale = getContext().getResources().getDisplayMetrics().density;
        return (int) (value / dipScale + 0.5f);
    }

    /**
     * SP转PX
     *
     * @param value
     * @return
     */
    public static int sp2px(float value) {
        float spScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (value * spScale + 0.5f);
    }

    /**
     * dp转换px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * PX转SP
     *
     * @param value
     * @return
     */
    public static int px2sp(int value) {
        float spScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (value / spScale + 0.5f);
    }

    /**
     * 计算屏幕宽的比例值 displayWidth / value
     *
     * @param value
     * @return
     */
    public static int scaleWidth(int value) {
        if (value <= 0)
            value = 1;
        return getDisplayWidth() / value;
    }

    /**
     * 计算屏幕高的比例值 displayHeight / value
     *
     * @param value
     * @return
     */
    public static int scaleHeight(int value) {
        if (value <= 0)
            value = 1;
        return getDisplayHeight() / value;
    }

    public static float getWidthPixels() {

        return getContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static float getHeightPixels() {

        return getContext().getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 判断屏幕是横屏还是竖屏
     *
     * @return boolean true 横屏 false 竖屏
     */
    public static boolean isScreenChange(Context context) {

        Configuration mConfiguration = context.getResources()
                .getConfiguration(); // 获取设置的配置信息
        int ori = mConfiguration.orientation; // 获取屏幕方向

        if (ori == Configuration.ORIENTATION_LANDSCAPE) { // 横屏
            return true;
        } else if (ori == Configuration.ORIENTATION_PORTRAIT) { // 竖屏
            return false;
        }
        return false;
    }

    /**
     * 获取app版本号
     */
    public static String getVersion(Context context) {

        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return packageInfo.versionName;
    }

    /**
     * 获取app版本号
     */
    public static int getVersionCode(Context context) {

        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return packageInfo.versionCode;
    }


    public static String getBase64Byte(Bitmap bitmap) {

        String photo = "";
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, out);
            out.flush();
            out.close();
            byte[] buffer = out.toByteArray();
            photo = Base64.encodeToString(buffer, Base64.DEFAULT);
            return photo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return photo;
    }

    /**
     * encodeBase64File:(将文件转成base64 字符串). <br/>
     * @author guhaizhou@126.com
     * @param path 文件路径
     * @return
     * @throws Exception
     * @since JDK 1.6
     */
    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int)file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }
    /**
     * 获取文件类型
     */
    public static String getMimeType(String fileName) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String type = fileNameMap.getContentTypeFor(fileName);
        return type;
    }
    /**
     * string转成bitmap
     *
     * @param st
     */
    public static Bitmap convertStringToIcon(String st) {
        byte[] bs = new byte[100];
        YuvImage yuvimage = new YuvImage(bs, ImageFormat.NV21, 280, 280, null);//20、20分别是图的宽度与高度
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        yuvimage.compressToJpeg(new Rect(0, 0, 280, 280), 100, baos);//80--JPG图片的质量[0-100],100最高
        byte[] jdata = baos.toByteArray();

        Bitmap bmp = BitmapFactory.decodeByteArray(jdata, 0, jdata.length);
        return bmp;
    }

    public static Bitmap stringtoBitmap(String string) {
        Bitmap bitmap = null;
        try {
            // 将字符串转换成Bitmap类型
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            byte[] bitmapArray;

            bitmapArray = Base64.decode(string, Base64.DEFAULT);

            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,

                    bitmapArray.length);

        } catch (Exception e) {

            e.printStackTrace();

        }
        return bitmap;
    }

    /**
     * 读取一个缩放后的图片，限定图片大小，避免OOM
     *
     * @param uri       图片uri，支持“file://”、“content://”
     * @param maxWidth  最大允许宽度
     * @param maxHeight 最大允许高度
     * @return 返回一个缩放后的Bitmap，失败则返回null
     */
    public static Bitmap decodeUri(Context context, Uri uri, int maxWidth, int maxHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //只读取图片尺寸
        resolveUri(context, uri, options);

        //计算实际缩放比例
        int scale = 1;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if ((options.outWidth / scale > maxWidth &&
                    options.outWidth / scale > maxWidth * 1.4) ||
                    (options.outHeight / scale > maxHeight &&
                            options.outHeight / scale > maxHeight * 1.4)) {
                scale++;
            } else {
                break;
            }
        }
        Log.e("scale----------",scale+"");
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;//读取图片内容
        options.inPreferredConfig = Bitmap.Config.RGB_565; //根据情况进行修改
        Bitmap bitmap = null;
        try {
            bitmap = resolveUriForBitmap(context, uri, options);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    private static void resolveUri(Context context, Uri uri, BitmapFactory.Options options) {
        if (uri == null) {
            return;
        }

        String scheme = uri.getScheme();
        if (ContentResolver.SCHEME_CONTENT.equals(scheme) ||
                ContentResolver.SCHEME_FILE.equals(scheme)) {
            InputStream stream = null;
            try {
                stream = context.getContentResolver().openInputStream(uri);
                BitmapFactory.decodeStream(stream, null, options);
            } catch (Exception e) {
                Log.w("resolveUri", "Unable to open content: " + uri, e);
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        Log.w("resolveUri", "Unable to close content: " + uri, e);
                    }
                }
            }
        } else if (ContentResolver.SCHEME_ANDROID_RESOURCE.equals(scheme)) {
            Log.w("resolveUri", "Unable to close content: " + uri);
        } else {
            Log.w("resolveUri", "Unable to close content: " + uri);
        }
    }

    private static Bitmap resolveUriForBitmap(Context context, Uri uri, BitmapFactory.Options options) {
        if (uri == null) {
            return null;
        }

        Bitmap bitmap = null;
        String scheme = uri.getScheme();
        if (ContentResolver.SCHEME_CONTENT.equals(scheme) ||
                ContentResolver.SCHEME_FILE.equals(scheme)) {
            InputStream stream = null;
            try {
                stream = context.getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(stream, null, options);
            } catch (Exception e) {
                Log.w("resolveUriForBitmap", "Unable to open content: " + uri, e);
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        Log.w("resolveUriForBitmap", "Unable to close content: " + uri, e);
                    }
                }
            }
        } else if (ContentResolver.SCHEME_ANDROID_RESOURCE.equals(scheme)) {
            Log.w("resolveUriForBitmap", "Unable to close content: " + uri);
        } else {
            Log.w("resolveUriForBitmap", "Unable to close content: " + uri);

        }
        return bitmap;
    }
    public static boolean checkPermissionGranted(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                System.out.println("判断定位权限是否打开-------------111");
                return true;
            } else {
                System.out.println("判断定位权限是否打开-------------222");
            }
        } else {
            if(PermissionChecker.checkPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION,
                    android.os.Process.myPid(), android.os.Process.myUid(), getContext().getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                System.out.println("判断定位权限是否打开-------------333");
                return true;
            }
        }
        return false;
    }
}