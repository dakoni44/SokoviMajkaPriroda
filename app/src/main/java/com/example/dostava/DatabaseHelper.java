package com.example.dostava;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.dostava.model.User;




public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="Users.db";

    private static final String TABLE_NAME = "korisnici";
    private static final String COLUMN_ID = "id_column";
    private static final String USER_IME = "ime";
    private static final String USER_PREZIME = "prezime";
    private static final String USER_ADRESA = "adresa";
    private static final String USER_GRAD = "grad";
    private static final String USER_DATUM = "datum";
    private static final String USER_TELEFON = "telefon";
    private static final String USER_SOKOVI = "sokovi";
    private static final String USER_CENA = "cena";
    private static final String USER_ISPORUCENO = "isporuceno";

    SQLiteDatabase database=this.getWritableDatabase();

    public DatabaseHelper( Context context) {
        super(context, DATABASE_NAME, null,2);
        database=this.getWritableDatabase();
        database=this.getReadableDatabase();
}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" (id_column INTEGER PRIMARY KEY AUTOINCREMENT, ime TEXT, prezime " +
                "TEXT, adresa TEXT, grad TEXT, datum " +
                "TEXT, telefon TEXT, sokovi " +
                "TEXT, cena TEXT, isporuceno TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS korisnici");
        onCreate(db);
    }
    public boolean addData( String imeS,String prezimeS,String adresaS,String gradS,String datumS,String telefonS,
                           String sokoviS,String cenaS,String isporucenoS){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("ime",imeS);
        contentValues.put("prezime",prezimeS);
        contentValues.put("adresa",adresaS);
        contentValues.put("grad",gradS);
        contentValues.put("datum", datumS);
        contentValues.put("telefon",telefonS);
        contentValues.put("sokovi",sokoviS);
        contentValues.put("cena",cenaS);
        contentValues.put("isporuceno",isporucenoS);
        long result= db.insert(TABLE_NAME,null,contentValues);
        db.close();
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getListContents(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor data= db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        return data;
    }

    public boolean updateData( String idS, String imeS,String prezimeS,String adresaS,String gradS,String datumS,String telefonS,
                              String sokoviS,String cenaS,String isporucenoS){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("id_column",idS);
        contentValues.put("ime",imeS);
        contentValues.put("prezime",prezimeS);
        contentValues.put("adresa",adresaS);
        contentValues.put("grad",gradS);
        contentValues.put("datum", datumS);
        contentValues.put("telefon",telefonS);
        contentValues.put("sokovi",sokoviS);
        contentValues.put("cena",cenaS);
        contentValues.put("isporuceno",isporucenoS);
        db.update(TABLE_NAME,contentValues,"id_column = ?",new String[]{idS});
        return true;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,"id_column = ?", new String[] {id});

    }

    public boolean deleteAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("drop table if exists "+ TABLE_NAME);
        onCreate(db);
        return true;
    }

}
