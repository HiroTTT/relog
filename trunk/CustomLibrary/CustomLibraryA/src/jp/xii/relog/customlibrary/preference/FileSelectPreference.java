package jp.xii.relog.customlibrary.preference;

import java.io.File;

import jp.xii.relog.customlibrary.FileListDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;


/**
 * �t�@�C�����X�g�v���t�@�����X
 * @author Iori
 *
 * �g���p�����[�^
 * rootPath : �\���J�n����p�X�i�t���p�X�j
 */
public class FileSelectPreference extends DialogPreference
	implements FileListDialog.onFileListDialogListener{

	private String _rootPath = "/";			//���[�g�p�X
	private String _defaultPath = "";		//�t�@�C�����X�g�őI�������p�X
	
	/**
	 * �R���X�g���N�^
	 * @param context
	 * @param attrs
	 */
	public FileSelectPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		//�f�t�H���g�o�����[�擾
		String default_value = attrs.getAttributeValue(null, "defaultValue");
		if(default_value == null){
			_defaultPath = "/";
		}else{
			_defaultPath = default_value;
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
			_defaultPath = pref.getString(getKey(),  _defaultPath);
		}

		//�T�}���[�Ɍ��ݒl��ݒ�
		setSummary(_defaultPath);

		//����͂Ȃ����Ōザ��Ȃ��ƃC�P�Ȃ��炵��
		super.onBindView(view);
	}

	/**
	 * �v���t�@�����X�̃N���b�N�C�x���g
	 */
	@Override
	protected void onClick(){
		//�ǂ�����onClick�łł�����ȌĂяo��������
		FileListDialog dlg = new FileListDialog(getContext());
		//���X�i�[�̓o�^
		dlg.setOnFileListDialogListener(this);
		//�f�B���N�g����I�����邩
		//dlg.setDirectorySelect(true);
		//�\���i�Ƃ肠�������[�g����j
		dlg.show( _rootPath, _rootPath);
		
	}


	/**
	 * �t�@�C���̑I�����ʂ̃C�x���g
	 */
	@Override
	public void onClickFileList(File file) {
		
		if(file == null){
			//Toast.makeText(getContext(), "�t�@�C���̎擾���ł��܂���ł���", Toast.LENGTH_SHORT).show();
		}else{
			//�m�F�_�C�A���O�\��
			setDialogTitle("�m�F");
			setDialogMessage(file.getAbsolutePath());
			showDialog(null);
		}
	}
	

	/**
	 * �m�F�_�C�A���O���������̃C�x���g
	 */
	@Override
	protected void onDialogClosed(boolean positiveResult) {
		super.onDialogClosed(positiveResult);
		
		if(!positiveResult){
		}else{
			//�ݒ��ۑ�
			SharedPreferences.Editor editor = getEditor();
			editor.putString(getKey(), (String) getDialogMessage());
			editor.commit();

			//�T�}���[���X�V
			notifyChanged();
		}
	}
	
}
