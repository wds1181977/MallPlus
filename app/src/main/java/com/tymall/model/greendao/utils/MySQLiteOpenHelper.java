package com.tymall.model.greendao.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tymall.model.greendao.DaoMaster;


/**
 * 描    述:自定义SqliteOpenHelper，用于升级数据库版本
 * <p>
 * <b>注意：所有需要存储到数据库中的实体类的xxxDao.class文件配置到onUpgrade方法中MigrationHelper.migrate(db, UserDao.class);中<b />
 */
public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {
    public MySQLiteOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //此处传所有实体类所生成的XXXDao.Class文件(旧表和新表都传是所有的)
    }
}
