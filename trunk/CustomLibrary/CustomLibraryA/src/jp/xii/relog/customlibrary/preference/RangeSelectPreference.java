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
package jp.xii.relog.customlibrary.preference;

import jp.xii.relog.customlibrary.widget.RangeSelectBar;
import jp.xii.relog.customlibrary.widget.RangeSelectBar.onRangeSelectBarChangeListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class RangeSelectPreference extends OriginalDialogPreference {

	public final static String STR_ATTR_UNIT = "unit";		//�P��
	public final static String STR_ATTR_VALUE_FORMAT = "value_format";	//�l�̃t�H�[�}�b�g����
	
	private int _max = 100;		//�ő�l
	private int _min = 0;		//�ŏ��l
	private int _step = 10;		//�ϒl
	
	private int _first = 40;		//�ŏ��̒l
	private int _last = 80;			//�Ō�̒l
	private boolean _isLoop = true;		//���E�����[�v���邩

	private String _unitString = "";	//�P�ʂ̕�����i�T�}���̕\���p�j
	private String _valueFormatString = "";	//�l�̃t�H�[�}�b�g�����w��
	
	/**
	 * �ő�l
	 * @param _max the _max to set
	 */
	public void setMax(int _max) {
		if(_max <= getMin()){
			_max = getMin() + 1; 
		}
		this._max = _max;
	}
	/**
	 * �ő�l
	 * @return the _max
	 */
	public int getMax() {
		return _max;
	}

	/**
	 * �ŏ��l
	 * @param _min the _min to set
	 */
	public void setMin(int _min) {
		if(_min >= getMax()){
			_min = getMax() - 1;
		}if(_min < 0){
			_min = 0;
		}
		this._min = _min;
	}
	/**
	 * �ŏ��l
	 * @return the _min
	 */
	public int getMin() {
		return _min;
	}
		
	/**
	 * �ϒl
	 * @param _step the _step to set
	 */
	public void setStep(int _step) {
		this._step = _step;
	}
	/**
	 * �ϒl
	 * @return the _step
	 */
	public int getStep() {
		return _step;
	}
	
	
	/**
	 * �ŏ��̒l
	 * @param _first the _first to set
	 */
	public void setFirst(int _first) {
		this._first = _first;
	}
	/**
	 * �ŏ��̒l
	 * @return the _first
	 */
	public int getFirst() {
		return _first;
	}
	/**
	 * �Ō�̒l
	 * @param _last the _last to set
	 */
	public void setLast(int _last) {
		this._last = _last;
	}
	/**
	 * �Ō�̒l
	 * @return the _last
	 */
	public int getLast() {
		return _last;
	}
	/**
	 * ���E�����[�v���邩
	 * @param _isLoop the _isLoop to set
	 */
	public void setIsLoop(boolean _isLoop) {
		this._isLoop = _isLoop;
	}
	/**
	 * ���E�����[�v���邩
	 * @return the _isLoop
	 */
	public boolean isLoop() {
		return _isLoop;
	}

	/**
	 * �P�ʂ̕�����i�T�}���̕\���p�j
	 * @param _unitString the _unitString to set
	 */
	public void setUnitString(String _unitString) {
		this._unitString = _unitString;
	}
	/**
	 * �P�ʂ̕�����i�T�}���̕\���p�j
	 * @return the _unitString
	 */
	public String getUnitString() {
		return _unitString;
	}
	
	/**
	 * �l�̃t�H�[�}�b�g�����w��
	 * @param _valueFormatString the _valueFormatString to set
	 */
	public void setValueFormatString(String _valueFormatString) {
		this._valueFormatString = _valueFormatString;
	}
	/**
	 * �l�̃t�H�[�}�b�g�����w��
	 * @return the _valueFormatString
	 */
	public String getValueFormatString() {
		return _valueFormatString;
	}
	
	
	/**
	 * �R���X�g���N�^
	 * @param context
	 * @param attrs
	 */
	public RangeSelectPreference(Context context, AttributeSet attrs) {
		super(context, attrs);

		String temp = null;

		//�ő�l
		temp = attrs.getAttributeValue(null, RangeSelectBar.STR_ATTR_MAX);
		if(temp != null){
			setMax(Integer.parseInt(temp));
		}
		//�ŏ��l
		temp = attrs.getAttributeValue(null, RangeSelectBar.STR_ATTR_MIN);
		if(temp != null){
			setMin(Integer.parseInt(temp));
		}
		//�ϒl
		temp = attrs.getAttributeValue(null, RangeSelectBar.STR_ATTR_STEP);
		if(temp != null){
			setStep(Integer.parseInt(temp));
		}
		
		//�ŏ��̒l�̏����l
		temp = attrs.getAttributeValue(null, RangeSelectBar.STR_ATTR_DEFAULT_FIRST);
		if(temp != null){
			setFirst(Integer.parseInt(temp));
		}
		//�Ō�̒l�̏����l
		temp = attrs.getAttributeValue(null, RangeSelectBar.STR_ATTR_DEFAULT_LAST);
		if(temp != null){
			setLast(Integer.parseInt(temp));
		}

		//�P��
		temp = attrs.getAttributeValue(null, STR_ATTR_UNIT);
		if(temp != null){
			setUnitString(temp);
		}
		
		//�����w�蕶����
		temp = attrs.getAttributeValue(null, STR_ATTR_VALUE_FORMAT);
		if(temp != null){
			setValueFormatString(temp);
		}
		

		//�s���Ȓl������
		if(getFirst() < getMin()){
			setFirst(getMin());
		}
		if(getLast() > getMax()){
			setLast(getMax());
		}
	}
	

	/**
	 * �\�������Ƃ��ɌĂ΂��
	 */
	@Override
	protected void onBindView(View view) {
		
		//�ݒ�Ǎ�
		SharedPreferences pref = getSharedPreferences();
		if(pref == null){
		}else{
			String temp = "";
			String[] values = null;
			temp = pref.getString(getKey(), temp);
			values = temp.split(",");
			if(values == null){
			}else if(values.length < 2){
			}else{
				setFirst(Integer.valueOf(values[0]));
				setLast(Integer.valueOf(values[1]));
			}
		}
		
		//�T�}���ɕ\������
		if(getUnitString().length() == 0){
			setSummary("Range : " + getFirst() + " - " + getLast());
		}else{
			setSummary("Range(" + getUnitString() + ") : " + getFirst() + " - " + getLast());
		}
		
		super.onBindView(view);
	}

	/**
	 * �N���b�N�C�x���g
	 */
	@Override
	protected void onClick() {


	    //inflater���g����xml�̃��C�A�E�g��View�ɔ��f����
	    LayoutInflater inflater = (LayoutInflater)getContext()
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View v = inflater.inflate(jp.xii.relog.customlibrary.R.layout.range_select_preference_dialog, null);
		final RangeSelectBar bar = (RangeSelectBar)v.findViewById(jp.xii.relog.customlibrary.R.id.RangeSelect_RangeSelectBar);
		final TextView text_first = (TextView)v.findViewById(jp.xii.relog.customlibrary.R.id.LavelFirst_TextView);
		final TextView text_last = (TextView)v.findViewById(jp.xii.relog.customlibrary.R.id.LavelLast_TextView);
		TextView text_min = (TextView)v.findViewById(jp.xii.relog.customlibrary.R.id.LavelMin_TextView);
		TextView text_max = (TextView)v.findViewById(jp.xii.relog.customlibrary.R.id.LavelMax_TextView);
		TextView text_center = (TextView)v.findViewById(jp.xii.relog.customlibrary.R.id.LavelCenter_TextView);
		bar.setMax(getMax());
		bar.setMin(getMin());
		bar.setFirst(getFirst());
		bar.setLast(getLast());
		bar.setStep(getStep());
		setFormatText(text_first, getFirst());
		setFormatText(text_last, getLast());
		setFormatText(text_max, getMax());
		setFormatText(text_min, getMin());
		setFormatText(text_center, (getMax() + getMin()) / 2);
		
		//�l�ύX���̃C�x���g
		bar.setOnRangeSelectBarChangeListener(new onRangeSelectBarChangeListener() {
			@Override
			public void onProgressChanged(RangeSelectBar rangeSelectBar, int first,
					int last) {
				//�_�C�A���O�̌��݂̒l��ݒ肷��
				setFormatText(text_first, first);
				setFormatText(text_last, last);
			}
		});
		
		
	    //�_�C�A���O�\��
		showCustumDialog(getContext()
						, (String)getDialogTitle(), (String)getDialogMessage()
						, v, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//�_�C�A���O�̃r���[���猋�ʂ��擾
				if(bar != null){
					setFirst(bar.getFirst());
					setLast(bar.getLast());
				}
				// �ݒ�ۑ�
				SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
				SharedPreferences.Editor editor = pref.edit();
				editor.putString(getKey(), getFirst() + "," + getLast());
				editor.commit();
				
				//�\�����X�V
				notifyChanged();
			}
		});
	}
	
	/**
	 * �����w��̕�������e�L�X�g�r���[�Ɏw�肷��
	 * @param v
	 * @param value
	 */
	private void setFormatText(TextView v, int value){
		if(v == null){
		}else{
			try{
				v.setText(String.format(getValueFormatString(), value));
			}catch(Exception e){
				v.setText(String.valueOf(value));
			}
		}
	}
	
}
