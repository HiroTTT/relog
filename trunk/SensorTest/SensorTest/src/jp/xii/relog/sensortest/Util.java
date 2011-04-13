package jp.xii.relog.sensortest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Util {
	
	/**
	 * �_�C�A���O�̕\��
	 * @param activity
	 * @param title
	 * @param text
	 */
	public static void showDialog(final Activity activity, String title, String text){
		AlertDialog.Builder ad = new AlertDialog.Builder(activity);
		ad.setIcon(android.R.drawable.ic_menu_more);
		ad.setTitle(title);
		ad.setMessage(text);
		ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				activity.setResult(Activity.RESULT_OK);
			}
		});
		ad.create();
		ad.show();
	}
	
	/**
	 * �g�[�X�g��\������
	 * @param activity
	 * @param message
	 */
	public static void showToast(final Activity activity, String message){
		if(message == null){
		}else{
			Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
		}
	}
	
	
	/**
	 * ���O���o�͂���
	 * @param message
	 */
	public static void outputDebugLog(String message){
		if(message == null){
		}else{
			Log.d("sensortest", message);
		}
	}
	
	
	/**
	 * ���X�g�_�C�A���O��\������
	 * @param activity
	 * @param title
	 * @param item_list
	 * @param lisner
	 */
	public static void showListDialog(final Activity activity, String title, String[] item_list
									,OnClickListener lisner){
		activity.setResult(-1);
		
		AlertDialog.Builder ad = new AlertDialog.Builder(activity);
		ad.setIcon(android.R.drawable.ic_menu_more);
		ad.setTitle(title);
		ad.setItems(item_list, lisner).show();
		
	}

	/**
	 * �_�C�A���O�̕\��
	 * @param activity
	 * @param title
	 * @param text
	 */
	public static void showDialog(final Activity activity, String title, String text, OnClickListener listener){
		AlertDialog.Builder ad = new AlertDialog.Builder(activity);
		ad.setIcon(android.R.drawable.ic_menu_more);
		ad.setTitle(title);
		ad.setMessage(text);
		ad.setPositiveButton("OK", listener);
		ad.setNegativeButton("Cancel", null);
		ad.create();
		ad.show();
	}
	
	/**
	 * �_�C�A���O�̕\��
	 * @param activity
	 * @param title
	 * @param text
	 */
	public static void showCustumDialog(final Activity activity, String title, String text ,View custumItem, OnClickListener listener){
		AlertDialog.Builder ad = new AlertDialog.Builder(activity);
		ad.setIcon(android.R.drawable.ic_menu_more);
		ad.setTitle(title);
		if(text != null){
			ad.setMessage(text);
		}
		ad.setView(custumItem);
		ad.setPositiveButton("OK", listener);
		ad.setNegativeButton("Cancel", null);
		ad.create();
		ad.show();
	}


}
