package com.example.userweight.Fragments;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.userweight.CoreActivity;
import com.example.userweight.R;
import com.example.userweight.Data.DBHistorial;
import com.example.userweight.Model.UserContainer;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewHistorialRow_fragment extends Fragment implements View.OnClickListener {
	
	private DatePicker dp_date;
	private EditText et_k_p;
	private TextView tv_typew;
	private Button b_addw;
	private int flag;
	
	private Formatter format;
	
	private int year,month,day;
	private Calendar calendar_now;
	
	public NewHistorialRow_fragment(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view;
		view = inflater.inflate(R.layout.fragment_nhr, container ,false);
		
		dp_date = (DatePicker)view.findViewById(R.id.dp_date);
		et_k_p = (EditText)view.findViewById(R.id.et_k_p);
		tv_typew = (TextView)view.findViewById(R.id.tv_typew);
		b_addw = (Button)view.findViewById(R.id.b_addw);
		b_addw.setOnClickListener(this);
		
		flag = UserContainer.GiveMeUser().getWeightType();
		if(flag == 0)
			tv_typew.setText("Kilograms");
		else
			tv_typew.setText("Pounds");
		
		calendar_now = Calendar.getInstance();
		year = calendar_now.get(Calendar.YEAR);
		month = calendar_now.get(Calendar.MONTH);
		day = calendar_now.get(Calendar.DAY_OF_MONTH);	
		dp_date.updateDate(year, month, day);
		
		return view;
	}

	@Override
	public void onClick(View v) {
		
		if( v.getId() == b_addw.getId() ){
			Calendar cal = Calendar.getInstance();
			cal.set(dp_date.getYear(), dp_date.getMonth(), dp_date.getDayOfMonth());
			if( cal.compareTo(calendar_now) < 0 ) //date is older than now
				Toast.makeText(getActivity(), "Date would be higher!" ,Toast.LENGTH_LONG).show();
			else//date is right!
				if(	et_k_p.getText().toString().length() > 0 ){
					Double weight = Double.valueOf(et_k_p.getText().toString());
					DBHistorial dbh = new DBHistorial(getActivity());
					dbh.ModoEscritura();
					if(flag == 0)  //change kilograms to grams!
						weight = weight*1000;
					dbh.insertar(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()), weight);
					dbh.Cerrar();
					//CLOSE FRAGMENT!  OR GO TO THE LISTVIEW FRAGMENT!
					Toast.makeText(getActivity(), "New Weight Added!" ,Toast.LENGTH_LONG).show();
					ListHistorial_fragment frag = new ListHistorial_fragment();
					((CoreActivity)getActivity()).ChangeFragmentContainer(frag);  //Show the list fragment!
				
				}
				else //incomplete field
					Toast.makeText(getActivity(), "Weight is not complete!" ,Toast.LENGTH_LONG).show();
		}
		
	}
	

}
