package com.example.userweight.Adapters;

import java.util.List;

import com.example.userweight.CoreActivity;
import com.example.userweight.R;
import com.example.userweight.Data.DBHistorial;
import com.example.userweight.Fragments.ListHistorial_fragment;
import com.example.userweight.Fragments.ModifyHRow_fragment;
import com.example.userweight.Model.HistorialRow;
import com.example.userweight.Model.UserContainer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemAdapter extends ArrayAdapter {

	private List<HistorialRow> list_h;
	private Activity activitycore;
	private int layoutResID;

	public ItemAdapter(Activity act, int layoutResourceId,List<HistorialRow> list) {
		super(act, layoutResourceId, list);

		this.list_h = list;
		this.activitycore = act;
		this.layoutResID = layoutResourceId;

		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		NewsHolder holder = null;
		View row = convertView;
		holder = null;
		final int pos = position;

		if (row == null) {
			LayoutInflater inflater = activitycore.getLayoutInflater();
			row = inflater.inflate(layoutResID, parent, false);

			holder = new NewsHolder();

			holder.tv_weight2 = (TextView) row.findViewById(R.id.tv_weight2);
			holder.tv_date2 = (TextView) row.findViewById(R.id.tv_date2);
			holder.button1 = (ImageButton) row.findViewById(R.id.img_btn1);
			holder.button2 = (ImageButton) row.findViewById(R.id.img_btn2);
			holder.button3 = (ImageButton) row.findViewById(R.id.img_btn3);
			row.setTag(holder);
		} else {
			holder = (NewsHolder) row.getTag();
		}

		HistorialRow h_r = list_h.get(position);
		holder.tv_date2.setText(h_r.getDate().toString());
		if (UserContainer.GiveMeUser().getWeightType() == 0)
			holder.tv_weight2.setText(String.valueOf(h_r.getWeight() / 1000));
		else
			holder.tv_weight2.setText(String.valueOf(h_r.getWeight()));

		holder.button1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {  //SEND ONE ROW

				String text;
				HistorialRow h_row = list_h.get(pos);

				if (UserContainer.GiveMeUser().getWeightType() == 0)
					text = "Date: " + h_row.getDate() + " | Weight: "+ String.valueOf(h_row.getWeight() / 1000)+"kg";
				else
					text = "Date: " + h_row.getDate() + " | Weight: "+ String.valueOf(h_row.getWeight())+"lbs";
				
				String email_destiny = UserContainer.GiveMeUser().getContactEmail();		
				SendEmail(text, email_destiny);
			}
		});

		holder.button2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) { //MODIFY
				ModifyHRow_fragment frag = new ModifyHRow_fragment();
				Bundle bun = new Bundle();
				bun.putInt("idrow", list_h.get(pos).getId());
				bun.putString("date_old", list_h.get(pos).getDate());
				bun.putDouble("weight_old", list_h.get(pos).getWeight());
				frag.setArguments(bun);
				((CoreActivity) activitycore).ChangeFragmentContainer(frag);
			}
		});

		holder.button3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) { //SEND ALL
				String text="";
				int type = UserContainer.GiveMeUser().getWeightType();
				for(int i=0;i<list_h.size();i++){
					if(type == 0)
						text = text+"\n Date: " + list_h.get(i).getDate() + "  |  Weight: "+ String.valueOf(list_h.get(i).getWeight() / 1000)+"kg";
					else
						text = text+"\n Date: " + list_h.get(i).getDate() + "  |  Weight: "+ String.valueOf(list_h.get(i).getWeight())+"lbs";
				}
				String email_destiny = UserContainer.GiveMeUser().getContactEmail();
				SendEmail(text, email_destiny);
			}
		});

		return row;

	}

	static class NewsHolder {

		TextView tv_hidden_id;
		TextView tv_weight2;
		TextView tv_date2;
		ImageButton button1;
		ImageButton button2;
		ImageButton button3;
	}

	public List<HistorialRow> getList() {
		return list_h;
	}

	private void SendEmail(String text,String email_destiny){
		
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { email_destiny });
		intent.putExtra(android.content.Intent.EXTRA_SUBJECT,UserContainer.GiveMeUser().getName() + " weight");
		intent.putExtra(android.content.Intent.EXTRA_TEXT, text);
		intent.setType("message/rfc822");
		activitycore.startActivity(Intent.createChooser(intent,"Send email"));
	}
	
}