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
package jp.xii.relog.customlibrary.app;

import java.io.File;
import jp.xii.relog.customlibrary.Utility;
import jp.xii.relog.customlibrary.widget.FileListView;
import jp.xii.relog.customlibrary.widget.FileListView.onFileListListener;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;


/**
 * �t�@�C�����X�g�_�C�A���O�N���X
 * @author Iori
 *
 */
public class FileListDialog implements onFileListListener 
	{

	private Context _parent = null;							//�e
	private File _currentFile = null;						//���݂̑I��
	private onFileListDialogListener _listener = null;		//���X�i�[
	private boolean _isDirectorySelect = false;			//�f�B���N�g���I�������邩�H
	private CustomAlertDialog _dialog = null;

	/**
	 * �f�B���N�g���I�������邩�H
	 * @param is
	 */
	public void setDirectorySelect(boolean is){
		_isDirectorySelect = is;
	}
	public boolean isDirectorySelect(){
		return _isDirectorySelect;
	}
	
	/**
	 * �I�����ꂽ�t�@�C�����擾
	 * @return
	 */
	public String getSelectedFileName(){
		String ret = "";
		if(_currentFile != null){
			ret = _currentFile.getAbsolutePath();
		}
		return ret;
	}
	
	/**
	 * �t�@�C���I���_�C�A���O
	 * @param context �e
	 */
	public FileListDialog(Context context){
		_parent = context;
	}
	
	/**
	 * �_�C�A���O�\��
	 * @param context �e
	 * @param path �\���������f�B���N�g��
	 * @param title �_�C�A���O�̃^�C�g��
	 */
	public void show(String path, String title){
		
		if(path == null){
			path = Utility.getSdcardPath();
		}else if(path.length() == 0){
			path = Utility.getSdcardPath();
		}
		
		FileListView list = new FileListView(_parent, new File(path), isDirectorySelect());
		list.setOnFileListListener(this);
		
		_dialog = new CustomAlertDialog(_parent);
		_dialog.setTitle(title);
		_dialog.setView(list);
		_dialog.setButton("Cancel", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//nop
			}
		});
		_dialog.show();
	}
	
	/**
	 * ���X�i�[�̃Z�b�g
	 * @param listener
	 */
	public void setOnFileListDialogListener(onFileListDialogListener listener){
		_listener = listener;
	}
	

	/**
	 * �_�C�A���O�Ńt�@�C�����I�����ꂽ
	 */
	@Override
	public void onSelectFile(File file) {
		_currentFile = file;
		if(_dialog != null){
			_dialog.dismiss();
			_dialog = null;
		}
		if(_listener != null){
			_listener.onClickFileList(file);
		}
	}
	/**
	 * �_�C�A���O�Ńf�B���N�g�����I�����ꂽ
	 */
	@Override
	public void onSelectDirectory(File file) {
		_currentFile = file;
		if(_dialog != null){
			_dialog.dismiss();
			_dialog = null;
		}
		if(_listener != null){
			_listener.onClickFileList(file);
		}
	}
	/**
	 * �f�B���N�g�����ύX���ꂽ
	 */
	@Override
	public void onChangeDirectory(File file) {
		//nop
	}

	
	
	/**
	 * �N���b�N�C�x���g�̃C���^�[�t�F�[�X�N���X
	 * @author Iori
	 *
	 */
	public interface onFileListDialogListener{
		public void onClickFileList(File file);
	}
	
	/**
	 * �J�X�^���_�C�A���O
	 * @author Iori
	 *
	 */
	public class CustomAlertDialog extends AlertDialog {

		protected CustomAlertDialog(Context context) {
			super(context);
		}

		
		protected CustomAlertDialog(Context context, int theme) {
			super(context, theme);
		}


		protected CustomAlertDialog(Context context, boolean cancelable,
				OnCancelListener cancelListener) {
			super(context, cancelable, cancelListener);
		}
	}

}

