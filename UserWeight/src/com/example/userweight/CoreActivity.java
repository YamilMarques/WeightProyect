package com.example.userweight;

import com.example.userweight.Data.DBUsers;
import com.example.userweight.Fragments.Config_fragment;
import com.example.userweight.Fragments.ListHistorial_fragment;
import com.example.userweight.Fragments.NewHistorialRow_fragment;
import com.example.userweight.Model.UserContainer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CoreActivity extends Activity implements OnClickListener{
	
	private ImageButton b_config,b_newweight,b_listhistory;
	private TextView tv_usershow;
	private FrameLayout fl_container;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_core);
		
		b_listhistory = (ImageButton)findViewById(R.id.b_listhistory);
		b_listhistory.setOnClickListener(this);
		b_config = (ImageButton)findViewById(R.id.b_config);
		b_newweight = (ImageButton)findViewById(R.id.b_newweight);
		b_config.setOnClickListener(this);
		b_newweight.setOnClickListener(this);
		tv_usershow = (TextView)findViewById(R.id.tv_usershow);
		fl_container = (FrameLayout)findViewById(R.id.fl_container);
		
		DBUsers dbus = new DBUsers(this);
		dbus.ModoLectura();
		UserContainer.UserAsingnation(dbus.GiveMeUser());
		dbus.Cerrar();
		
		if( UserContainer.GiveMeUser() == null ){
			GoConfiguration();
		}else{ //Go to new_weight fragment!
			ChangeTextViewUser(UserContainer.GiveMeUser().getName().toString());
			NewHistorialRow_fragment frag = new NewHistorialRow_fragment();
			ChangeFragmentContainer(frag);
		}		
		
		//LAAAAAAAAAAAAAAAAAAAAAAAAAAA
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.core, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		
		if( UserContainer.GiveMeUser() != null ){
			if( v.getId() == b_config.getId() ){ //config button has been pressed
				GoConfiguration();
			}else
				if( v.getId() == b_newweight.getId() ){  //new weight to the list!
					NewHistorialRow_fragment frag = new NewHistorialRow_fragment();
					ChangeFragmentContainer(frag);
				}else
					if( v.getId() == b_listhistory.getId()){ //list history button
						ListHistorial_fragment frag = new ListHistorial_fragment();
						ChangeFragmentContainer(frag);
					}
		}else
			Toast.makeText(this, "Add new user first!" ,Toast.LENGTH_LONG).show();
		
	}
	
	public void ChangeFragmentContainer(Fragment frag){
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.fl_container, frag);
		ft.addToBackStack(null);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.commit();
	}
	
	public void UnattachFragment(){
		fl_container.removeAllViews();
	}
	
	public void ChangeTextViewUser(String username){
		tv_usershow.setText(username);
	}
	
	public void GoConfiguration(){
		Config_fragment frag = new Config_fragment();
		ChangeFragmentContainer(frag);
	}
	
	@Override
	public void onBackPressed() {
		FragmentManager fm = getFragmentManager();
		if (fm.getBackStackEntryCount() > 0) {
	        fm.popBackStack();
	    } else {
	        super.onBackPressed();  
	    }
	}
}
