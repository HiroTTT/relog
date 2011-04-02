/**
 * 
 */
package jp.xii.relog.customlibrary.preference;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

/**
 * @author Iori
 *
 */
public class TimePickerPreference extends OriginalDialogPreference {

	//�ǉ������̖���
	private final static String STR_ATTR_DEFAULT_HOUR = "defaultHour";
	private final static String STR_ATTR_DEFAULT_MINUTE = "defaultMinute";
	private final static String STR_ATTR_IS_24HOUR = "is24Hour";
	
	//�v���t�@�����X�ۑ����̃L�[���̒ǉ�������
	private final static String STR_KEY_HOUR = "_Hour";		
	private final static String STR_KEY_MINUTE = "_Minute";
	
	private int _defaultHour = 0;			//�ݒ�l�i���j
	private int _defaultMinute = 0;			//�ݒ�l�i���j
	private boolean _is24HourView = false;	//�ݒ�l�i�Q�S���ԕ\���j
	
	

	/**
	 * 24���ԕ\����
	 * @param _is24HourView the _is24HourView to set
	 */
	public void setIs24HourView(boolean _is24HourView) {
		this._is24HourView = _is24HourView;
	}

	/**
	 * 24���ԕ\����
	 * @return the _is24HourView
	 */
	public boolean is24HourView() {
		return _is24HourView;
	}

	
	/**
	 * �R���X�g���N�^
	 * @param context
	 * @param attrs
	 */
	public TimePickerPreference(Context context, AttributeSet attrs) {
		super(context, attrs);

		String temp;
		
		//���Ԃ��擾
		temp = attrs.getAttributeValue(null, STR_ATTR_DEFAULT_HOUR);
		if(temp != null){
			_defaultHour = Integer.valueOf(temp);
		}
		//�����擾
		temp = attrs.getAttributeValue(null, STR_ATTR_DEFAULT_MINUTE);
		if(temp != null){
			_defaultMinute = Integer.valueOf(temp);
		}
		//�Q�S���ԕ\�����擾
		temp = attrs.getAttributeValue(null, STR_ATTR_IS_24HOUR);
		if(temp == null){
		}else if(temp.toLowerCase().compareTo("true") != 0){
		}else{
			_is24HourView = true;			
		}
		
	}

	/**
	 * �\�������Ƃ��ɌĂ΂��
	 */
	@Override
	protected void onBindView(View view) {
		//�ݒ��ǂݍ���
		SharedPreferences pref = getSharedPreferences();
		if(pref == null){
		}else{
			_defaultHour = pref.getInt(getKey() + STR_KEY_HOUR, _defaultHour);
			_defaultMinute = pref.getInt(getKey() + STR_KEY_MINUTE, _defaultMinute);
		}

		//�T�}���[�Ɍ��ݒl��ݒ�
		String summary = "";
		if(_is24HourView){
			summary = String.format("%s%02d:%02d", getDefaultSummary(), _defaultHour, _defaultMinute);
		}else{
			if(_defaultHour > 12){
				summary = String.format("%s%02d:%02d PM", getDefaultSummary(), _defaultHour - 12, _defaultMinute);
			}else if(_defaultHour == 0){
				summary = String.format("%s%02d:%02d AM", getDefaultSummary(), 12, _defaultMinute);
			}else{
				summary = String.format("%s%02d:%02d AM", getDefaultSummary(), _defaultHour, _defaultMinute);
			}
		}
		setSummary((summary));

		//����͂Ȃ����Ōザ��Ȃ��ƃC�P�Ȃ��炵��
		super.onBindView(view);
	}

	/**
	 * �v���t�@�����X�̃N���b�N�C�x���g
	 */
	@Override
	protected void onClick(){

		//�_�C�A���O�\��
		final TimePicker picker = new TimePicker(getContext());
		picker.setIs24HourView(_is24HourView);
		picker.setCurrentHour(_defaultHour);
		picker.setCurrentMinute(_defaultMinute);
		showCustumDialog(getContext(), (String)getDialogTitle(), (String)getDialogMessage()
							, picker, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// �ݒ�ۑ�
				SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
				SharedPreferences.Editor editor = pref.edit();
				editor.putInt(getKey() + STR_KEY_HOUR, picker.getCurrentHour());
				editor.putInt(getKey() + STR_KEY_MINUTE, picker.getCurrentMinute());
				editor.commit();
				
				//�\�����X�V
				notifyChanged();
			}
		});

	}
	
}
