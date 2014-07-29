package com.example.userweight.Fragments;

import com.example.userweight.CoreActivity;
import com.example.userweight.R;
import com.example.userweight.Data.DBUsers;
import com.example.userweight.Model.User;
import com.example.userweight.Model.UserContainer;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Config_fragment extends Fragment implements OnClickListener{
	
	private EditText et_name,et_email,et_contactemail;
	private Button b_goon,b_more;
	private RadioGroup rg_weighttype; 
	private int WeightType = 0;
	
	private final int CONTACT_PICKER_RESULT = 0;

	public Config_fragment(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view;
		view = inflater.inflate(R.layout.fragment_config, container, false);
		
		 rg_weighttype = (RadioGroup)view.findViewById(R.id.rg_weighttype);
	     et_name = (EditText)view.findViewById(R.id.et_name);
	     et_email = (EditText)view.findViewById(R.id.et_email);
	     et_contactemail = (EditText)view.findViewById(R.id.et_contactemail);
	     b_more = (Button)view.findViewById(R.id.b_more);
	     b_goon = (Button)view.findViewById(R.id.b_goon);
	     b_goon.setOnClickListener(this);
	     b_more.setOnClickListener(this);
		
	     return view;
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == b_goon.getId()){  //press "Go On!" button!
			if(et_name.getText().toString().length() < 3)
				Toast.makeText(getActivity(), "'Name' field must have minimum 3 characters" ,Toast.LENGTH_LONG).show();
			else
				if(et_email.getText().toString().length() < 7 || et_contactemail.getText().toString().length() < 7)
					Toast.makeText(getActivity(), "Complete correctly the email field" ,Toast.LENGTH_LONG).show();
				else
				{  //all passed
					int selectedid = rg_weighttype.getCheckedRadioButtonId();
					if ( selectedid == R.id.radio1)
						WeightType = 1;
					else
						WeightType = 0;
					
					if( UserContainer.GiveMeUser() == null ){  //first time (user has not been created)
						DBUsers dbus = new DBUsers(getActivity());
						dbus.ModoEscritura();
						dbus.insertar(et_name.getText().toString(), et_email.getText().toString(), et_contactemail.getText().toString(), WeightType);
						dbus.ModoLectura();
						UserContainer.UserAsingnation(dbus.GiveMeUser());
						dbus.Cerrar();
						//jump to the new_weight fragment
						NewHistorialRow_fragment frag = new NewHistorialRow_fragment();
						((CoreActivity)getActivity()).ChangeFragmentContainer(frag);
						
					}else  //user has been created (modify user!)
						{
						User us_new = new User(UserContainer.GiveMeUser().getId(), et_name.getText().toString(),
												et_email.getText().toString(), et_contactemail.getText().toString(),
												WeightType);  //new user with modifications!
						DBUsers dbus = new DBUsers(getActivity());
						dbus.ModoEscritura();
						dbus.ModifyUser(us_new);
						dbus.Cerrar();
						UserContainer.UserAsingnation(us_new);
						Toast.makeText(getActivity(), "User modification complete!" ,Toast.LENGTH_LONG).show();
						//jump to the new_weight fragment
						NewHistorialRow_fragment frag = new NewHistorialRow_fragment();
						((CoreActivity)getActivity()).ChangeFragmentContainer(frag);
						
					}
				}
		}
		else
			if( v.getId() == b_more.getId() ){  //press "More" button!
				CharSequence options[] = new CharSequence[]{"Myself","Choose existing contact","Create new contact"};
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Select Option");
				builder.setItems(options, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) { //the user click the option
						if(which == 0){ //Myself
							if(UserContainer.GiveMeUser() == null){ //user is loged
								if( et_email.getText().toString().length() > 0){ //has something
									et_contactemail.setText(et_email.getText().toString());
								}else  //nothing
								{
									Toast.makeText(getActivity(), "Complete email field or create new user" ,Toast.LENGTH_LONG).show();
								}
							}else
								et_contactemail.setText(UserContainer.GiveMeUser().getMail());
						}else
							if(which == 1){ //Existing contact
								Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,Contacts.CONTENT_URI);
							    startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
							}else
								{  //new contact
								 Intent intent = new Intent(Intent.ACTION_INSERT,ContactsContract.Contacts.CONTENT_URI);
								 startActivity(intent);
								}
					}
				});
				builder.show();
			}
	}
	
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		switch (requestCode) {
		case CONTACT_PICKER_RESULT:
				Cursor cursor = null;
	            String email = "", name = "";
	            try {
	                Uri result = data.getData();
	                // get the contact id from the Uri
	                String id = result.getLastPathSegment();

	                // query for everything email
	                cursor = getActivity().getContentResolver().query(Email.CONTENT_URI,  null, Email.CONTACT_ID + "=?", new String[] { id }, null);

	                int nameId = cursor.getColumnIndex(Contacts.DISPLAY_NAME);

	                int emailIdx = cursor.getColumnIndex(Email.DATA);

	                // let's just get the first email
	                if (cursor.moveToFirst()) {
	                    email = cursor.getString(emailIdx);
	                    name = cursor.getString(nameId);
	                  
	                } else {
	                   
	                }
	            } catch (Exception e) {
	            	
	            } finally {
	                if (cursor != null) {
	                    cursor.close();
	                }
	                et_contactemail.setText(email);
	                if (email.length() == 0 && name.length() == 0) 
	                {
	                    Toast.makeText(getActivity(), "No Email for Selected Contact",Toast.LENGTH_LONG).show();
	                }
	            }
			break;

		default:
			break;
		}
		
	}

}
