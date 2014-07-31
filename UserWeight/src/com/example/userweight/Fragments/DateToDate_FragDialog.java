package com.example.userweight.Fragments;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.example.userweight.R;
import com.example.userweight.Data.DBHistorial;
import com.example.userweight.Model.HistorialRow;
import com.example.userweight.Model.UserContainer;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

public class DateToDate_FragDialog extends DialogFragment implements View.OnClickListener{
	
	private DatePicker dp1,dp2;
	private Button b_send;
	
	
	public DateToDate_FragDialog(){
		//need a empty constructor
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_dtd_dialog, container, false);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		
		dp1 = (DatePicker)view.findViewById(R.id.dp1);
		dp2 = (DatePicker)view.findViewById(R.id.dp2);
		b_send = (Button)view.findViewById(R.id.b_send);
		b_send.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == b_send.getId()){
			
			Calendar caldp1 = Calendar.getInstance();
			caldp1.set(dp1.getYear(), dp1.getMonth(), dp1.getDayOfMonth());
			Calendar caldp2 = Calendar.getInstance();
			caldp2.set(dp2.getYear(), dp2.getMonth(), dp2.getDayOfMonth());
			
			if(caldp1.compareTo(caldp2) <= 0){ //if first es before or equal than second!
				
				String email_destiny = UserContainer.GiveMeUser().getContactEmail();
				int type = UserContainer.GiveMeUser().getWeightType();
				String text = "";
				
				String date_from = new SimpleDateFormat("yyyy-MM-dd").format(caldp1.getTime());
				String date_to = new SimpleDateFormat("yyyy-MM-dd").format(caldp2.getTime());
				
				DBHistorial dbh = new DBHistorial(getActivity());
				dbh.ModoLectura();
				List<HistorialRow>  list_h =  dbh.GiveMeFromToDate(date_from, date_to);

				Toast.makeText(getActivity(), String.valueOf(list_h.size()), Toast.LENGTH_LONG).show();
				
				for(int i=0;i<list_h.size();i++){
					if(type == 0)
						text = text+"\n Date: " + list_h.get(i).getDate() + "  |  Weight: "+ String.valueOf(list_h.get(i).getWeight() / 1000)+"kg";
					else
						text = text+"\n Date: " + list_h.get(i).getDate() + "  |  Weight: "+ String.valueOf(list_h.get(i).getWeight())+"lbs";
				}
							
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { email_destiny });
				intent.putExtra(android.content.Intent.EXTRA_SUBJECT,UserContainer.GiveMeUser().getName() + " weight");
				intent.putExtra(android.content.Intent.EXTRA_TEXT, text);
				intent.setType("message/rfc822");
				getActivity().startActivity(Intent.createChooser(intent,"Send email"));
			}

			this.dismiss();  //close it?
		}
	}
	
}
