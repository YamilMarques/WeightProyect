package com.example.userweight.Data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.userweight.Model.HistorialRow;
import com.example.userweight.Model.User;

public class DBUsers {

	//Nombre de la Tabla
	private static final String TABLE_NAME = "Users";
	
	//Como se llamaran las columnas
	public static final String CN_ID = "_id"; //necesario para los cursores
	public static final String CN_NAME = "name";
	public static final String CN_MAIL = "mail";
	public static final String CN_CONTACTEMAIL = "contactemail";
	public static final String CN_WEIGHTTYPE = "weighttype";
	
	public static String crear_tabla(){
		String query = "create table "+TABLE_NAME+"("+
								CN_ID+" integer primary key not null,"+
								CN_NAME+" date not null,"+
								CN_MAIL+" text not null,"+
								CN_CONTACTEMAIL+" text,"+
								CN_WEIGHTTYPE+" integer not null);";
		return query;
	}
	
	private String[] columns = new String[]{CN_ID,CN_NAME,CN_MAIL,CN_CONTACTEMAIL,CN_WEIGHTTYPE};
	
	private DBHelper helper;
	private SQLiteDatabase db;
	
	public DBUsers(Context context){
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
	
	private ContentValues Contenedorvalores(String name,String mail,String contactemail,int weighttype){
		ContentValues cont = new ContentValues();
		cont.put(CN_NAME, name);
		cont.put(CN_MAIL, mail);
		cont.put(CN_CONTACTEMAIL, contactemail);
		cont.put(CN_WEIGHTTYPE, weighttype);
		return cont;
	}
	
	public void insertar(String name,String mail,String contactemail,int weighttype){   //date entra como un string debe ser: "Año-Mes-Dia" ---> "1992-27-12"
		db.insert(TABLE_NAME, null, Contenedorvalores(name, mail,contactemail,weighttype));
	}
	
	public void eliminar(int id){
		String query = "delete from "+TABLE_NAME+" where "+CN_ID+" = "+id;
		db.execSQL(query);
	}
	
	/*private List<User> CrearLista(Cursor c){
		List<User> lista_users = new ArrayList<User>();
		if(c.moveToFirst()){
			do{
				int id = c.getColumnIndex(CN_ID);
				int name = c.getColumnIndex(CN_NAME);
				int mail = c.getColumnIndex(CN_MAIL);
				int contactemail = c.getColumnIndex(CN_CONTACTEMAIL);
				int weighttype = c.getColumnIndex(CN_WEIGHTTYPE);
				User user = new User(c.getInt(id), c.getString(name), c.getString(mail), c.getString(contactemail), c.getInt(weighttype));
				lista_users.add(user);
			}while(c.moveToNext());
		}
		c.close();
		return lista_users;
	}*/
	
	/*public List<User> ListUsers(){
		Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);
		return CrearLista(c);
	}*/
	
	public User GiveMeUser(){
		Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);
		if(c.moveToFirst()){
				int id = c.getColumnIndex(CN_ID);
				int name = c.getColumnIndex(CN_NAME);
				int mail = c.getColumnIndex(CN_MAIL);
				int contactemail = c.getColumnIndex(CN_CONTACTEMAIL);
				int weighttype = c.getColumnIndex(CN_WEIGHTTYPE);
				User user = new User(c.getInt(id), c.getString(name), c.getString(mail), c.getString(contactemail), c.getInt(weighttype));
				return user;
		}
		else
			return null;
	}
	
	public void ModifyUser(User us_new){
		String query = "UPDATE "+TABLE_NAME+
						" SET "+CN_NAME+" = '"+us_new.getName()+"' , "+CN_MAIL+" = '"+us_new.getMail()+
						"' , "+CN_CONTACTEMAIL+" = '"+us_new.getContactEmail()+"' , "+CN_WEIGHTTYPE+" = '"+us_new.getWeightType()+
						"' WHERE "+CN_ID+" = "+us_new.getId();
		db.execSQL(query);
	}
	
}
