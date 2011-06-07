/*
 * Copyright 2011 IoriAYANE
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.xii.relog.customlibrary;

import java.io.File;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Utility {
	private static boolean DEBUG = false;
	
	public static void setDebug(boolean on){
		DEBUG = on;
	}
	
	/**
	 * �_�C�A���O�̕\��
	 * @param activity
	 * @param title
	 * @param text
	 */
	public static void showDialog(final Activity activity, String title, String text){
		AlertDialog.Builder ad = new AlertDialog.Builder(activity);
		ad.setIcon(R.drawable.ic_menu_more);
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
		}else if(!DEBUG){
		}else{
			Log.d("test", message);
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
		ad.setIcon(R.drawable.ic_menu_more);
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
		ad.setIcon(R.drawable.ic_menu_more);
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
		ad.setIcon(R.drawable.ic_menu_more);
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

	/**
	 * SD�J�[�h�̃p�X���擾����
	 * �g���Ȃ����null
	 * �Ō��/�͂Ȃ�
	 * @return
	 */
	public static String getSdcardPath(){
		String ret = null;
		
		String status = Environment.getExternalStorageState(); 
		File sdcard = Environment.getExternalStorageDirectory();
		if(!status.equals(Environment.MEDIA_MOUNTED)){
			//�}�E���g����ĂȂ�
//		}else if(!sdcard.canWrite()){
//			//�g���Ȃ�
		}else{
			//�g����
			ret = sdcard.toString();
		}

		return ret;
	}
	
	
	/**
	 * Wifi��on�����ׂ�
	 * @param context
	 * @return
	 */
	public static boolean isWifiEnabled(Context context){
		boolean ret = false;
		
		if(context == null){
		}else{
			WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
			if(!wifiManager.isWifiEnabled()){
			}else{
				WifiInfo wifiInfo = wifiManager.getConnectionInfo();
				int wifiState = wifiManager.getWifiState();

				if(wifiState != WifiManager.WIFI_STATE_ENABLED){
				}else if(wifiInfo.getSSID() == null){
				}else if(wifiInfo.getIpAddress() == 0){
				}else{
					ret = true;
				}
			}
		}
		
		return ret;
	}
	
	
	/**
	 * ��莞�ԃX���[�v����
	 * @param time
	 */
	public static void Sleep(long time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

}
