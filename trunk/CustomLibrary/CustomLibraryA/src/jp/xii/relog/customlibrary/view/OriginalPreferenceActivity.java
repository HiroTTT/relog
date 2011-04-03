package jp.xii.relog.customlibrary.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.view.KeyEvent;

public abstract class OriginalPreferenceActivity extends PreferenceActivity {

	/**
	 * �ݒ���T�}���[�ɕ\������
	 */
	protected abstract void updateSettingSummary();	
	
	
	/**
	 * ��~����ĊJ
	 */
	@Override
	protected void onResume() {
	  super.onResume();
	  getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(_listener);
	}
	/**
	 * �ꎞ��~
	 */
	@Override
	protected void onPause() {
	  super.onPause();
	  getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(_listener);
	}
	/**
	 * �v���t�@�����X�̕ύX���̃C�x���g�n���h��
	 */
	private SharedPreferences.OnSharedPreferenceChangeListener _listener = 
	  new SharedPreferences.OnSharedPreferenceChangeListener() {
	    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
	    	updateSettingSummary();
	    }
	};

	/**
	 *�L�[�C�x���g���� 
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event){
		//BACK�L�[
		if(event.getAction() == KeyEvent.ACTION_DOWN){
			if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
				//�p�����[�^�ۑ��i�󂯓n���p�j

				//�A�N�e�B�r�e�B�̏I��
				setResult(RESULT_OK);
				finish();
			}
		}
		return super.dispatchKeyEvent(event);
	}

	
	/**
	 * �܂Ƃ߂ăN���b�N���X�i�[��o�^����
	 * @param context �Ăяo���p�b�P�[�W�̃R���e�L�X�g
	 * @param res_ids �v���t�@�����Xkey�ɂ��Ă��镶����̃��\�[�XID
	 * @param listener ���X�i�[
	 */
	protected void setOnPreferenceClickListener(Context context, int[] res_ids, OnPreferenceClickListener listener){
		if((context == null) || (res_ids == null) || (listener == null)){
		}else{
			Preference pref;

			for (int id : res_ids) {
				pref = this.findPreference(context.getString(id));
				if(pref != null){
					pref.setOnPreferenceClickListener(listener);
				}
			}
		}
	}
	
	/**
	 * �܂Ƃ߂ėL��������ݒ肷��
	 * @param context �Ăяo���p�b�P�[�W�̃R���e�L�X�g
	 * @param res_ids �v���t�@�����Xkey�ɂ��Ă��镶����̃��\�[�XID
	 * @param enabled �L���ɂ��邩�����ɂ��邩
	 */
	protected void setPreferenceEnabled(Context context, int[] res_ids, boolean enabled){
		if((context == null) || (res_ids == null)){
		}else{
			Preference pref;

			for (int id : res_ids) {
				pref = this.findPreference(context.getString(id));
				if(pref != null){
					pref.setEnabled(enabled);
				}
			}
		}
	}

	
	/**
	 * ���X�g�v���t�@�����X�̃T�}���[��I����e�ŃZ�b�g����
	 * @param pref
	 * @param values_id
	 * @param entries_id
	 */
	protected void setListPreferenceSummary(Preference pref
										, int values_id, int entries_id
										, String set_value){
		if(pref == null){
		}else{
			String[] values = getResources().getStringArray(values_id);
			String[] entries = getResources().getStringArray(entries_id);
			for(int i=0; i<values.length; i++){
				if(values[i].compareTo(set_value) == 0){
					pref.setSummary(entries[i]);
				}
			}
		}
	}

}
