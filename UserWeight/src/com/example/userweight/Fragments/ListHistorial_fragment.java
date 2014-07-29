package com.example.userweight.Fragments;

import java.util.List;

import com.example.userweight.CoreActivity;
import com.example.userweight.R;
import com.example.userweight.Adapters.ItemAdapter;
import com.example.userweight.Data.DBHistorial;
import com.example.userweight.Model.*;
import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListHistorial_fragment extends Fragment{
	
	private TextView tv_korp;
	
	private SwipeListView swipelistview;
	private List<HistorialRow> list_hr;
	private SwipeListView lv_history;
	private ItemAdapter adapter;
	
	
	public ListHistorial_fragment(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view;
		view = inflater.inflate(R.layout.fragment_listh, container, false);
		
		tv_korp = (TextView)view.findViewById(R.id.tv_korp);
		if(UserContainer.GiveMeUser().getWeightType() == 0)  //its kilograms!
			tv_korp.setText("Kilograms");
		else
			tv_korp.setText("Pounds");
		
		DBHistorial dbh = new DBHistorial(getActivity());
		dbh.ModoLectura();
		list_hr = dbh.ListaHistorial();
		lv_history = (SwipeListView)view.findViewById(R.id.lv_history1);
		adapter = new ItemAdapter(getActivity(), R.layout.custom_row, list_hr);
		lv_history.setAdapter(adapter);
		dbh.Cerrar();
		
		lv_history.setSwipeListViewListener(new BaseSwipeListViewListener() {
	         @Override
	         public void onOpened(int position, boolean toRight) {
	         }
	 
	         @Override
	         public void onClosed(int position, boolean fromRight) {
	         }
	 
	         @Override
	         public void onListChanged() {
	         }
	 
	         @Override
	         public void onMove(int position, float x) {
	         }
	 
	         @Override
	         public void onStartOpen(int position, int action, boolean right) {
	             Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));
	         }
	 
	         @Override
	         public void onStartClose(int position, boolean right) {
	             Log.d("swipe", String.format("onStartClose %d", position));
	         }
	 
	         @Override
	         public void onClickFrontView(int position) {
	             Log.d("swipe", String.format("onClickFrontView %d", position));
	 
	             //swipelistview.openAnimate(position); //when you touch front view it will open
	 
	         }
	 
	         @Override
	         public void onClickBackView(int position) {
	             Log.d("swipe", String.format("onClickBackView %d", position));
	 
	             //swipelistview.closeAnimate(position);//when you touch back view it will close
	         }
	 
	         @Override
	         public void onDismiss(int[] reverseSortedPositions) {
	        	 
	        	 List<HistorialRow> list = adapter.getList();
	        	 int pos2 = 0,id_row=0;
	        	 
	        	 for(int position : reverseSortedPositions){
	        		 id_row = list.get(position).getId();
	        		 list.remove(position);	//remove from list
	        	 }
	        	 
	        	 DBHistorial dbh = new DBHistorial(getActivity());
	        	 dbh.ModoEscritura();
	        	 dbh.eliminar(id_row);		//remove from database
	        	 dbh.Cerrar();
	        	 
	        	 adapter.notifyDataSetChanged();
	         } 
	     });
		
		//These are the swipe listview settings. you can change these
		 //setting as your requrement
		 lv_history.setSwipeMode(SwipeListView.SWIPE_MODE_BOTH); // there are five swiping modes
		 lv_history.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_DISMISS); //there are four swipe actions
		 lv_history.setSwipeActionRight(SwipeListView.SWIPE_ACTION_REVEAL);
		 lv_history.setOffsetLeft(convertDpToPixel(260f)); // left side offset
		 lv_history.setOffsetRight(convertDpToPixel(180f)); // right side offset
		 lv_history.setAnimationTime(80); // animarion time
		 lv_history.setSwipeOpenOnLongPress(true); // enable or disable SwipeOpenOnLongPress
		
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
	}

	public int convertDpToPixel(float dp) {
	       DisplayMetrics metrics = getResources().getDisplayMetrics();
	       float px = dp * (metrics.densityDpi / 160f);
	       return (int) px;
	   }
	
	public void ChangeFragmentofDad(Fragment frag){
		((CoreActivity)getActivity()).ChangeFragmentContainer(frag);
	}
}
