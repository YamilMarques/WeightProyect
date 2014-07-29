package com.example.userweight.Fragments;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.userweight.CoreActivity;
import com.example.userweight.R;
import com.example.userweight.Data.DBHistorial;
import com.example.userweight.Model.HistorialRow;
import com.example.userweight.Model.UserContainer;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ModifyHRow_fragment extends Fragment implements View.OnClickListener{
	
	private TextView tv_dateold,tv_weightold,tv_typewightmod;
	private DatePicker dp_mod;
	private EditText et_weight_mod;
	private Button b_modify;
	private HistorialRow h_r;
	
	private int flag;
	
	public ModifyHRow_fragment(){
		
	}
	//Bundle with "date_old" and "weight_old" "idrow"
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view;
		view = inflater.inflate(R.layout.fragment_mod_hr, container, false);
		
		flag = UserContainer.GiveMeUser().getWeightType(); //weight type 0:KILOGRAMS 1:POUNDS
		
		tv_dateold = (TextView)view.findViewById(R.id.tv_dateold);
		tv_weightold = (TextView)view.findViewById(R.id.tv_weightold);
		tv_typewightmod = (TextView)view.findViewById(R.id.tv_typewieght_mod);
		dp_mod = (DatePicker)view.findViewById(R.id.dp_mod);
		et_weight_mod = (EditText)view.findViewById(R.id.et_weight_mod);
		
		b_modify = (Button)view.findViewById(R.id.b_modify);
		b_modify.setOnClickListener(this);
		
		Bundle bun = getArguments();
		h_r = new HistorialRow(bun.getInt("idrow"), bun.getString("date_old"), bun.getInt("weight_old"));
		if(bun != null && bun.containsKey("date_old") && bun.containsKey("weight_old")){
			String date_old = bun.getString("date_old");
			int weight_old = bun.getInt("weight_old");
			tv_dateold.setText(date_old);
			if (flag == 0)  //0:KILOGRAMS 1:POUNDS
				tv_weightold.setText(String.valueOf(weight_old/1000)+"kg");  
			else
				tv_weightold.setText(String.valueOf(weight_old)+"lbs");		
		}
		
		if(UserContainer.GiveMeUser().getWeightType() == 0)
			tv_typewightmod.setText("Kilograms");
		else
			tv_typewightmod.setText("Pounds");
		
		if(flag == 0)
			et_weight_mod.setHint(String.valueOf(bun.getInt("weight_old")/1000));
		else
			et_weight_mod.setHint(String.valueOf(bun.getInt("weight_old")));
		
		String dateold = bun.getString("date_old");
		int year = Integer.valueOf(dateold.substring(0, 4)); //year
		int month = Integer.valueOf(dateold.substring(5, 7));  //month
		int day = Integer.valueOf(dateold.substring(8, 10)); //day
		dp_mod.updateDate(year, month-1, day);
		
		return view;
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == b_modify.getId()){
			if(et_weight_mod.getText().toString().length() > 0){
				Calendar cal = Calendar.getInstance();
				cal.set(dp_mod.getYear(), dp_mod.getMonth(), dp_mod.getDayOfMonth());
				int weight = Integer.valueOf(et_weight_mod.getText().toString());
				DBHistorial dbh = new DBHistorial(getActivity());
				dbh.ModoEscritura();
				if(flag == 0)  //change kilograms to grams!
					weight = weight*1000;
				h_r.setDate(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));  //REFRESH THE DATA!
				h_r.setWeight(weight);													//
				dbh.ModifyRow(h_r);
				dbh.Cerrar();
				Toast.makeText(getActivity(), "Modify success", Toast.LENGTH_SHORT).show();
				ListHistorial_fragment frag = new ListHistorial_fragment();
				((CoreActivity)getActivity()).ChangeFragmentContainer(frag);
			}
			else
				Toast.makeText(getActivity(), "Complete the weight field", Toast.LENGTH_SHORT).show();
		}
		
	}
		

}
