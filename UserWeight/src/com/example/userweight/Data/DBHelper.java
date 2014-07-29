package com.example.userweight.Data;

import android.content.Context;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBHelper extends SQLiteOpenHelper {
	
	private static final String DB_NAME = "DBProject.sqlite";
	private static final int DB_SCHEME_VERSION = 1;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_SCHEME_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		this.CrearTablas(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method
	}
	
	private void CrearTablas(SQLiteDatabase db){
		db.execSQL(DBHistorial.crear_tabla()); //crear tabla historial
		db.execSQL(DBUsers.crear_tabla()); //crear tabla usuarios
	}

}
