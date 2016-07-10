package xyz.aungpyaephyo.padc.myanmarattractions.data.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import xyz.aungpyaephyo.padc.myanmarattractions.data.persistence.AttractionsContract.AttractionEntry;
import xyz.aungpyaephyo.padc.myanmarattractions.data.persistence.AttractionsContract.AttractionImageEntry;

/**
 * Created by aung on 7/9/16.
 */
public class AttractionDBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "attractions.db";

    private static final String SQL_CREATE_ATTRACTION_TABLE = "CREATE TABLE " + AttractionEntry.TABLE_NAME + " (" +
            AttractionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            AttractionEntry.COLUMN_TITLE + " TEXT NOT NULL, "+
            AttractionEntry.COLUMN_DESC + " TEXT NOT NULL, "+

            " UNIQUE (" + AttractionEntry.COLUMN_TITLE + ") ON CONFLICT IGNORE" +
            " );";

    private static final String SQL_CREATE_ATTRACTION_IMAGE_TABLE = "CREATE TABLE " + AttractionImageEntry.TABLE_NAME + " (" +
            AttractionImageEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            AttractionImageEntry.COLUMN_ATTRACTION_TITLE + " TEXT NOT NULL, "+
            AttractionImageEntry.COLUMN_IMAGE + " TEXT NOT NULL, "+

            " UNIQUE (" + AttractionImageEntry.COLUMN_ATTRACTION_TITLE + ", "+
            AttractionImageEntry.COLUMN_IMAGE + ") ON CONFLICT IGNORE" +
            " );";

    public AttractionDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ATTRACTION_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_ATTRACTION_IMAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AttractionImageEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AttractionEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
