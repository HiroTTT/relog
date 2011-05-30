package jp.xii.relog.customlibrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * EULA�\���w���p�[�N���X
 * @author Iori
 *
 */
public class EulaHelper {

	private Activity _parentActivity = null;	//�Ăяo�����A�N�e�B�r�e�B
	private boolean _isAgreed = false;			//���ӂ��Ă邩
	private String _appName = "";				//�A�v���̖���
	private String _eulaMessage = "";			//���b�Z�[�W
	
	/**
	 * ���ӂ��Ă��邩
	 * @param _isAgreed the _isAgreed to set
	 */
	public void setIsAgreed(boolean _isAgreed) {
		this._isAgreed = _isAgreed;
	}
	/**
	 * ���ӂ��Ă��邩
	 * @return the _isAgreed
	 */
	public boolean isAgreed() {
		return _isAgreed;
	}
	
	/**
	 * �A�v���̖��̂�ݒ肷��
	 * @param _appName the _appName to set
	 */
	public void setAppName(String _appName) {
		this._appName = _appName;
	}
	/**
	 * �A�v���̖��̂��擾����
	 * @return the _appName
	 */
	public String getAppName() {
		return _appName;
	}
	
	/**
	 * EULA�{����ݒ肷��
	 * �܂邲�Ə���������̂Œ���
	 * @param _eulaMessage the _eulaMessage to set
	 */
	public void setEulaMessage(String _eulaMessage) {
		this._eulaMessage = _eulaMessage;
	}
	/**
	 * EULA�{�����擾����
	 * @return the _eulaMessage
	 */
	public String getEulaMessage() {
		return _eulaMessage;
	}
	/**
	 * EULA�{����������Ԃɂ��܂�
	 * activity���w�肵�ĂȂ��Ə����邾���ł�
	 */
	public void clearEulaMessage(){
		if(_parentActivity == null){
			_eulaMessage = "";
		}else{
			try{
				_eulaMessage = String.format(_parentActivity.getString(R.string.eula_message)
						, getAppName()
						, _parentActivity.getString(R.string.eula_developer_name)
						, _parentActivity.getString(R.string.eula_cout)
						);
			}catch(Exception e){
			}
		}
		
	}

	
	/**
	 * �R���X�g���N�^
	 * @param activity
	 */
	public EulaHelper(Activity activity, String app_name){
		_parentActivity = activity;
		_appName = app_name;
		clearEulaMessage();
	}
	
	/**
	 * �\�����Č��ʂ𔽉f����
	 */
	public void show(){
		//�ǂݍ���
		load(_parentActivity);
		//�������J�n
		if(_parentActivity == null){
		}else if(isAgreed()){
			//���ɓ��ӂ��Ă���
		}else{
			AlertDialog.Builder ad = new AlertDialog.Builder(_parentActivity);
			ad.setTitle(_parentActivity.getString(R.string.eula_title));
			ad.setMessage(getEulaMessage());
			//OK�{�^��
			ad.setPositiveButton(R.string.eula_agree
							, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//���ӂ������Ƃ�ۑ�����
					save(_parentActivity, true);
				}
			});
			//Cancel�{�^��
			ad.setNegativeButton(R.string.eula_disagree
							, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//���ӂ��Ȃ��̂ŏI������
					_parentActivity.finish();
				}
			});
			ad.create();
			ad.show();
		}
	}
	
	/**
	 * ���ӂ������Ƃ�ۑ�����
	 */
	private void save(Context context, boolean is_agreed){
		if(context == null){
		}else{
			SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
			if(pref == null){
			}else{
	    		SharedPreferences.Editor editor = pref.edit();
	    		editor.putBoolean(context.getString(R.string.eula_is_agreed_key), is_agreed);
	    		editor.commit();
			}
		}
	}
	/**
	 * ���ӏ󋵂�ǂݍ���
	 */
	public void load(Context context){
		if(context == null){
		}else{
			SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
			if(pref == null){
			}else{
				setIsAgreed(pref.getBoolean(context.getString(R.string.eula_is_agreed_key), false));
			}
		}
	}
	
}
