package com.example.userweight.Data;

import java.util.ArrayList;
import java.util.List;

import com.example.userweight.Model.*;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DBHistorial {

	//Nombre de la Tabla
	private static final String TABLE_NAME = "Historial";
	
	//Como se llamaran las columnas
	public static final String CN_ID = "_id"; //necesario para los cursores
	public static final String CN_DATE = "date";
	public static final String CN_WEIGHT = "weight";
	
	public static String crear_tabla(){
		String query = "create table "+TABLE_NAME+"("+
								CN_ID+" integer primary key not null,"+
								CN_DATE+" date not null,"+
								CN_WEIGHT+" real not null);";
		return query;
	}
	
	private String[] columns = new String[]{CN_ID,CN_DATE,CN_WEIGHT};
	
	private DBHelper helper;
	private SQLiteDatabase db;
	
	public DBHistorial(Context context){
		helper = new DBHelper(context);
	}
	
	//Abrir la tabla en modo lectura
	public void ModoLectura(){ db = helper.getReadableDatabase(); }
	//Abrir la tabla en modo escritura
	public void ModoEscritura(){ db = helper.getWritableDatabase(); }
	//Cerrar la base de datos
	public void Cerrar(){
		if(db != null)
			db.close();
	}
	
	private ContentValues Contenedorvalores(String date,double weight){
		ContentValues cont = new ContentValues();
		cont.put(CN_DATE, date);
		cont.put(CN_WEIGHT, weight);
		return cont;
	}
	
	public void insertar(String date,double weight){   //date entra como un string debe ser: "Año-Mes-Dia" ---> "1992-27-12"
		db.insert(TABLE_NAME, null, Contenedorvalores(date, weight));
	}
	
	public void eliminar(int id){
		String query = "delete from "+TABLE_NAME+" where "+CN_ID+" = "+id;
		db.execSQL(query);
	}
	
	public List<HistorialRow> CrearLista(Cursor c){
		List<HistorialRow> lista_hrow = new ArrayList<HistorialRow>();
		if(c.moveToFirst()){
			do{
				int id = c.getColumnIndex(CN_ID);
				int date = c.getColumnIndex(CN_DATE);
				int weight = c.getColumnIndex(CN_WEIGHT);
				HistorialRow h_row =new HistorialRow(c.getInt(id), c.getString(date), c.getInt(weight));
				lista_hrow.add(h_row);
			}while(c.moveToNext());
		}
		c.close();
		return lista_hrow;
	}
	
	public List<HistorialRow> ListaHistorial(){
		Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, CN_DATE+" ASC");  //Ascendet mod!
		return CrearLista(c);
	}
	
	public void ModifyRow(HistorialRow h_r){
		String query = "UPDATE "+TABLE_NAME+" SET "+CN_DATE+" = '"+h_r.getDate()+"', "+CN_WEIGHT+" = "+h_r.getWeight()+" WHERE "+CN_ID+" = "+h_r.getId();
		db.execSQL(query);
	}
	
	public List<HistorialRow> GiveMeFromToDate(String date_from,String date_to){
		String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+CN_DATE+" <= '"+date_from+"' AND "+CN_DATE+" >= '"+date_to+"'";
		Cursor c = db.rawQuery(query, null);
		
		return CrearLista(c);
	}
	
}
