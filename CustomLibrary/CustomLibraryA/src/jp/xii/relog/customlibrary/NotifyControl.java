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

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

public class NotifyControl {
	
	private Activity _activity = null;
	private int _icon_resourse_id = android.R.drawable.stat_sys_warning;
	
	//�A�b�v�f�[�g�Ԋu�̐���
	private long _lastUpdateTime = 0;		//�O��A�b�v�f�[�g��������
	private long _updateInterval = 500;		//�X�V�Ԋu(�f�t�H���g500msec)
	
	//�ʒm�̈�\���֌W
	private NotificationManager _notificationManager = null;	
	private Notification _notification = null;
	private Intent _notifyIntent = null;
	private PendingIntent _contentIntent = null;


	/**
	 * �e�̃A�N�e�B�r�e�B�̎擾
	 * @return
	 */
	public Activity getActivity(){
		return _activity;
	}
	
	/**
	 * �A�C�R���̃��\�[�XID�̎w��
	 * @param icon
	 */
	public void setIcon(int icon) {
		_icon_resourse_id = icon;
	}


	/**
	 * �X�V�Ԋu
	 * @param _updateInterval the _updateInterval to set
	 */
	public void setUpdateInterval(long _updateInterval) {
		this._updateInterval = _updateInterval;
	}
	/**
	 * �X�V�Ԋu
	 * @return the _updateInterval
	 */
	public long getUpdateInterval() {
		return _updateInterval;
	}

	/**
	 * �R���X�g���N�^
	 * @param context
	 */
	public NotifyControl(Activity activity, int icon){
		_activity = activity;
		setIcon(icon);
	}
	
	public NotifyControl(){
		
	}
	
	/**
	 * Notify�̏�����
	 * @param context
	 */
	public void initNotify(String message){
		initNotify(getActivity(), _icon_resourse_id, message);
	}

	/**
	 * Notify�̏�����
	 * @param activity
	 * @param icon
	 * @param message
	 */
	public void initNotify(Activity activity, int icon, String message){
		if((activity == null) || (message == null)){
		}else{
			_notificationManager = (NotificationManager)activity.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
			_notification = new Notification(
						 	icon,
							message,
							System.currentTimeMillis());
			_notifyIntent = activity.getIntent();
			_contentIntent = PendingIntent.getActivity(activity, 0, _notifyIntent, 0);
			
			//�o�^������N���A
			_lastUpdateTime = System.currentTimeMillis();
		}
	}
	
	/**
	 * Notify��\������
	 * @param title
	 * @param message
	 * @param app_name
	 */
	public void viewNotify(String title, String message, int app_name){
		viewNotify(getActivity(), title, message, app_name);
	}	
	
	/**
	 * Notify��\������
	 * @param activity �Ăяo�����A�N�e�B�r�e�B
	 * @param title �^�C�g��
	 * @param message ���ɂł郁�b�Z�[�W
	 * @param app_name �A�v�����i�^�X�N�o�[�I�ȂƂ���ɂł����������ȁj
	 */
	public void viewNotify(Activity activity, String title, String message, int app_name){
		viewNotify(activity, title, message, app_name, _contentIntent);
	}

	/**
	 * Notify��\������
	 * @param activity
	 * @param title
	 * @param message
	 * @param app_name
	 * @param pending_intent
	 */
	public void viewNotify(Activity activity, String title, String message, int app_name
							,PendingIntent pending_intent){
		long now = System.currentTimeMillis();
		
		if((_notification == null) || (_notificationManager == null)
				|| (activity == null)){
		}else if((now - _lastUpdateTime) < getUpdateInterval()){
			//�ďo�����߂���
		}else{
			_notification.when = now;
			_notification.setLatestEventInfo(
					activity,
					title,
					message,
					pending_intent);
			
			_notificationManager.notify(app_name, _notification);

			//�O��̍X�V���Ԃ�ۑ�
			_lastUpdateTime = now;
		}
	}
	
	
	/**
	 * Notify������
	 */
	public void clearNotify(){
		if(_notificationManager != null){
			_notificationManager.cancelAll();
		}
	}
	
}