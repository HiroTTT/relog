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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;

public class OriginalDialogPreference extends DialogPreference {


	private String _summary = "";			//�T�}���[�̕�����

	/**
	 * �T�}���̏������
	 * @param _summary the _summary to set
	 */
	public void setDefaultSummary(String _summary) {
		this._summary = _summary;
	}

	/**
	 * �T�}���̏������
	 * @return the _summary
	 */
	public String getDefaultSummary() {
		return _summary;
	}

	/**
	 * �R���X�g���N�^
	 * @param context
	 * @param attrs
	 */
	public OriginalDialogPreference(Context context, AttributeSet attrs) {
		super(context, attrs);

		String temp;
		
		//�T�}���[�̏�����Ԃ�ۑ����Ă���
		temp = (String) getSummary();
		if(temp != null){
			setDefaultSummary(temp);
		}

	}
	
	/**
	 * �_�C�A���O�̕\��
	 * @param context �e�̃R���e�L�X�g
	 * @param title �_�C�A���O�̃^�C�g��
	 * @param text �_�C�A���O�̖{��
	 */
	protected void showCustumDialog(Context context, String title, String text ,View custumItem, OnClickListener listener){
		AlertDialog.Builder ad = new AlertDialog.Builder(context);
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
	 * ���l�ɕϊ�����
	 * @param number
	 * @return
	 */
	protected int toInt(String number){
		int num = 0;
		
		try{
			num = Integer.parseInt(number);
		}catch(Exception e){
			num = 0;
		}
		
		return num;
	}

	/**
	 * ���l���m���߂�
	 * @param number
	 * @return
	 */
	protected boolean isInt(String number){
		boolean ret = false;
		
		try{
			Integer.parseInt(number);
			ret = true;
		}catch(Exception e){
			ret = false;
		}
		
		return ret;
	}


}
