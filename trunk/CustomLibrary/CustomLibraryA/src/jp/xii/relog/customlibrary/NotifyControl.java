package jp.xii.relog.customlibrary;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class NotifyControl {
	
	private Activity _activity = null;
	private int _icon_resourse_id = android.R.drawable.stat_sys_warning;
	
	//�ʒm�̈�\���֌W
	private NotificationManager _notificationManager = null;	
	private Notification _notification = null;
	private Intent _notifyIntent = null;
	private PendingIntent _contentIntent = null;

	/**
	 * �R���e�L�X�g�̎擾
	 * @return
	 */
	public Context getContext() {
		return _activity;
	}

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
	 * �R���X�g���N�^
	 * @param context
	 */
	public NotifyControl(Activity activity, int icon){
		_activity = activity;
		setIcon(icon);
	}
	
	/**
	 * Notify�̏�����
	 * @param context
	 */
	public void initNotify(String message){
		
		_notificationManager = (NotificationManager)getActivity().getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		_notification = new Notification(
					 	_icon_resourse_id,
						message,
						System.currentTimeMillis());
		_notifyIntent = getActivity().getIntent();
		_contentIntent = PendingIntent.getActivity(getContext(), 0, _notifyIntent, 0);
	}
	
	/**
	 * Notify��\������
	 * @param context
	 * @param message
	 */
	public void viewNotify(String title, String message, int app_name){

		if(_notification == null && _notificationManager == null){
		}else{
			_notification.when = System.currentTimeMillis();
			_notification.setLatestEventInfo(
					getContext(),
					title,
					message,
					_contentIntent);
			
			_notificationManager.notify(app_name, _notification);
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
