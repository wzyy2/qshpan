package com.ui;

import com.Data.Common;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Fragment_A extends Fragment {
	public Handler mHandler;
	TextView txacc;
	TextView txgrv;
	TextView txgyr;
	TextView txpid;
	boolean if_calirate = false;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("ComputerFragment--->onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("ConputerFragment--->onCreateView");	
		View Layout = inflater.inflate(R.layout.fragment_post, container, false);


		return Layout;
		// 返回R.layout.tab_a所指的view
	}

	@Override
	public void onStop() {
		System.out.println("ConputerFragment--->onStop");
		super.onStop();
	}
}