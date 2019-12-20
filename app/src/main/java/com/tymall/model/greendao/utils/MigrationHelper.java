package com.tymall.model.greendao.utils;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.internal.DaoConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 描    述:GreenDao升级辅助类，仅用于辅助GreenDao处理数据库升级，原项目地址：https://github.com/yuweiguocn/GreenDaoUpgradeHelper
 * 主要修改了restoreData()方法，解决了在旧表中新增字段（int、double等整数类型和浮点数类型等）时，会自动增加不能为null约束，升级会将旧数据删除问题
 */
public class MigrationHelper {
    public static boolean DEBUG = false;
    private static String TAG = "MigrationHelper";
    private static final String SQLITE_MASTER = "sqlite_master";
    private static final String SQLITE_TEMP_MASTER = "sqlite_temp_master";

    public MigrationHelper() {
    }

    public static void migrate(SQLiteDatabase db, Class... daoClasses) {
        StandardDatabase database = new StandardDatabase(db);
        printLog("【The Old Database Version】" + db.getVersion());
        printLog("【Generate temp table】start");
        generateTempTables(database, daoClasses);
        printLog("【Generate temp table】complete");
        dropAllTables(database, true, daoClasses);
        createAllTables(database, false, daoClasses);
        printLog("【Restore data】start");
        restoreData(database, daoClasses);
        printLog("【Restore data】complete");
    }

    /**
     * 生成临时表，并包括之前表数据
     *
     * @param db
     * @param daoClasses
     */
    private static void generateTempTables(Database db, Class... daoClasses) {
        for (int i = 0; i < daoClasses.length; ++i) {
            String tempTableName = null;
            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
            String tableName = daoConfig.tablename;
            if (!isTableExists(db, false, tableName)) {
                printLog("【New Table】" + tableName);
            } else {
                try {
                    tempTableName = daoConfig.tablename.concat("_TEMP");
                    StringBuilder e = new StringBuilder();
                    e.append("DROP TABLE IF EXISTS ").append(tempTableName).append(";");
                    db.execSQL(e.toString());
                    StringBuilder insertTableStringBuilder = new StringBuilder();
                    insertTableStringBuilder.append("CREATE TEMPORARY TABLE ").append(tempTableName);
                    insertTableStringBuilder.append(" AS SELECT * FROM ").append(tableName).append(";");
                    db.execSQL(insertTableStringBuilder.toString());
                    printLog("【Table】" + tableName + "\n ---Columns-->" + getColumnsStr(daoConfig));
                    printLog("【Generate temp table】" + tempTableName);
                } catch (SQLException var8) {
                    Log.e(TAG, "【Failed to generate temp table】" + tempTableName, var8);
                }
            }
        }

    }

    private static boolean isTableExists(Database db, boolean isTemp, String tableName) {
        if (db != null && !TextUtils.isEmpty(tableName)) {
            String dbName = isTemp ? "sqlite_temp_master" : "sqlite_master";
            String sql = "SELECT COUNT(*) FROM " + dbName + " WHERE type = ? AND name = ?";
            Cursor cursor = null;
            int count = 0;

            try {
                cursor = db.rawQuery(sql, new String[]{"table", tableName});
                if (cursor == null || !cursor.moveToFirst()) {
                    boolean e = false;
                    return e;
                }

                count = cursor.getInt(0);
            } catch (Exception var11) {
                var11.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }

            }

            return count > 0;
        } else {
            return false;
        }
    }

    private static String getColumnsStr(DaoConfig daoConfig) {
        if (daoConfig == null) {
            return "no columns";
        } else {
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < daoConfig.allColumns.length; ++i) {
                builder.append(daoConfig.allColumns[i]);
                builder.append(",");
            }

            if (builder.length() > 0) {
                builder.deleteCharAt(builder.length() - 1);
            }

            return builder.toString();
        }
    }

    /**
     * 清除所有表
     *
     * @param db
     * @param ifExists
     * @param daoClasses
     */
    private static void dropAllTables(Database db, boolean ifExists, @NonNull Class... daoClasses) {
        reflectMethod(db, "dropTable", ifExists, daoClasses);
        printLog("【Drop all table】");
    }

    /**
     * 创建所有表
     *
     * @param db
     * @param ifNotExists
     * @param daoClasses
     */
    private static void createAllTables(Database db, boolean ifNotExists, @NonNull Class... daoClasses) {
        reflectMethod(db, "createTable", ifNotExists, daoClasses);
        printLog("【Create all table】");
    }

    /**
     * 重新创建表和清除所有表
     *
     * @param db
     * @param methodName
     * @param isExists
     * @param daoClasses
     */
    private static void reflectMethod(Database db, String methodName, boolean isExists, @NonNull Class... daoClasses) {
        if (daoClasses.length >= 1) {
            try {
                Class[] e = daoClasses;
                int var5 = daoClasses.length;

                for (int var6 = 0; var6 < var5; ++var6) {
                    Class cls = e[var6];
                    Method method = cls.getDeclaredMethod(methodName, new Class[]{Database.class, Boolean.TYPE});
                    method.invoke((Object) null, new Object[]{db, Boolean.valueOf(isExists)});
                }
            } catch (NoSuchMethodException var9) {
                var9.printStackTrace();
            } catch (InvocationTargetException var10) {
                var10.printStackTrace();
            } catch (IllegalAccessException var11) {
                var11.printStackTrace();
            }

        }
    }

    /**
     * 从临时表复制数据到新表中
     *
     * @param db
     * @param daoClasses
     */
    private static void restoreData(Database db, Class... daoClasses) {
        for (int i = 0; i < daoClasses.length; ++i) {
            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
            //得到表名
            String tableName = daoConfig.tablename;
            //得到临时表名
            String tempTableName = daoConfig.tablename.concat("_TEMP");
            //判断临时表是否存在
            if (isTableExists(db, true, tempTableName)) {
                try {
                    //得到临时表所有字段
                    List e = getColumns(db, tempTableName);
                    ArrayList<String> extracolumns = new ArrayList<>();
                    extracolumns.addAll(e);
                    //新表的字段存储
                    ArrayList properties = new ArrayList();

                    for (int dropTableStringBuilder = 0; dropTableStringBuilder < daoConfig.properties.length; ++dropTableStringBuilder) {
                        Property property = daoConfig.properties[dropTableStringBuilder];
                        String insertTableStringBuilder = property.columnName;
                        Class<?> type = property.type;
                        //判断临时表中是否包含此字段
                        if (e.contains(insertTableStringBuilder)) {
                            properties.add(insertTableStringBuilder);
                        } else {
                            //为新增字段设置默认值
                            properties.add(insertTableStringBuilder);
                            if (float.class.getName().equals(type.getName())
                                    || Float.class.getName().equals(type.getName())) {
                                extracolumns.add("0.0");

                            } else if (byte.class.getName().equals(type.getName())
                                    || Byte.class.getName().equals(type.getName())) {
                                extracolumns.add("0");

                            } else if (int.class.getName().equals(type.getName())
                                    || Integer.class.getName().equals(type.getName())) {
                                extracolumns.add("0");

                            } else if (double.class.getName().equals(type.getName())
                                    || Double.class.getName().equals(type.getName())) {
                                extracolumns.add("0.00");

                            } else if (long.class.getName().equals(type.getName())
                                    || Long.class.getName().equals(type.getName())) {
                                extracolumns.add("0");

                            } else if (boolean.class.getName().equals(type.getName())
                                    || Boolean.class.getName().equals(type.getName())) {
                                extracolumns.add("false");

                            } else {
                                extracolumns.add("null");
                            }
                        }
                    }

                    if (properties.size() > 0) {
                        String var11 = TextUtils.join(",", properties);
                        String var20 = TextUtils.join(",", extracolumns);
                        StringBuilder var13 = new StringBuilder();
                        var13.append("INSERT INTO ").append(tableName).append(" (");
                        var13.append(var11);
                        var13.append(") SELECT ");
                        var13.append(var20);
                        var13.append(" FROM ").append(tempTableName).append(";");
                        printLog("从临时表复制数据到表中===" + var13.toString());
                        db.execSQL(var13.toString());
                        printLog("【Restore data】 to " + tableName);
                    }

                    StringBuilder var12 = new StringBuilder();
                    var12.append("DROP TABLE ").append(tempTableName);
                    db.execSQL(var12.toString());
                    printLog("【Drop temp table】" + tempTableName);
                } catch (SQLException var10) {
                    Log.e(TAG, "【Failed to restore data from temp table 】" + tempTableName, var10);
                }
            }
        }

    }

    private static List<String> getColumns(Database db, String tableName) {
        Object columns = null;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 0", (String[]) null);
            if (null != cursor && cursor.getColumnCount() > 0) {
                columns = Arrays.asList(cursor.getColumnNames());
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }

            if (null == columns) {
                columns = new ArrayList();
            }

        }

        return (List) columns;
    }

    private static void printLog(String info) {
        if (DEBUG) {
            Log.d(TAG, info);
        }

    }

}
