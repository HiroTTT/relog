package jp.xii.relog.customlibrary.widget;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import jp.xii.relog.customlibrary.R;
import jp.xii.relog.customlibrary.Utility;

public class FileListView extends ViewGroup 
	implements AdapterView.OnItemClickListener
			, View.OnClickListener{

	
	//������
	public final static String STR_ATTR_SELECT_DIR = "select_dir";		//�f�B���N�g���I��
	public final static String STR_ATTR_DEFAULT_DIR = "default_dir";	//�����f�B���N�g��

	
	private View _mainView = null;
	
	private boolean _isSelectDir = false;			//�f�B���N�g���I��
	private File _currentDir = null;				//�J�����g�f�B���N�g��
	private ArrayList<File> _currentDirFileList = null;		//���݂̃f�B���N�g���̃t�@�C���̈ꗗ
	private Stack<File> _historyFileList = null;	//���ǂ����p�X�̃X�^�b�N
	
	private onFileListListener _listener = null;	//���X�i

	
	/**
	 * �f�B���N�g���I��
	 * @param _isSelectDir the _isSelectDir to set
	 * �ォ��ύX�s��
	 */
	private void setIsSelectDir(boolean _isSelectDir) {
		this._isSelectDir = _isSelectDir;
	}
	/**
	 * �f�B���N�g���I��
	 * @return the _isSelectDir
	 */
	public boolean isSelectDir() {
		return _isSelectDir;
	}


	/**
	 * �J�����g�f�B���N�g��
	 * @param _currentDir the _currentDir to set
	 */
	public void setCurrentDirectory(File _currentDir) {
		this._currentDir = _currentDir;
	}
	/**
	 * �J�����g�f�B���N�g��
	 * @return the _currentDir
	 */
	public File getCurrentDirectory() {
		if(_currentDir == null){
			_currentDir = new File("/");
		}
		return _currentDir;
	}
	
	/**
	 * ���݂̃f�B���N�g���̃t�@�C���̈ꗗ
	 * @return
	 */
	public ArrayList<File> getCurrentDirFileList() {
		if(_currentDirFileList == null){
			_currentDirFileList = new ArrayList<File>();
		}
		return _currentDirFileList;
	}

	/**
	 * ���ǂ����p�X�̃X�^�b�N
	 * @return the _historyFileList
	 */
	public Stack<File> getHistoryFileList() {
		if(_historyFileList == null){
			_historyFileList = new Stack<File>();
		}
		return _historyFileList;
	}

	
	/**
	 * ���X�i
	 * @param _listener the _listener to set
	 */
	public void setOnFileListListener(onFileListListener _listener) {
		this._listener = _listener;
	}
	/**
	 * ���X�i
	 * @return the _listener
	 */
	public onFileListListener getOnFileListListener() {
		return _listener;
	}
	
	
	/**
	 * �R���X�g���N�^
	 * @param context
	 * @param attrs
	 */
	public FileListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		String temp = null;

		//�f�B���N�g���I��
		temp = attrs.getAttributeValue(null, STR_ATTR_SELECT_DIR);
		if(temp != null){
			if(temp.compareTo("true") == 0){
				setIsSelectDir(true);
			}
		}
		//�����f�B���N�g��
		temp = attrs.getAttributeValue(null, STR_ATTR_DEFAULT_DIR);
		if(temp != null){
			setCurrentDirectory(new File(temp));
		}else{
			//���w��̏ꍇ��microSD
			setCurrentDirectory(new File(Utility.getSdcardPath()));
		}

		Utility.setDebug(true);

	    //inflater���g����xml�̃��C�A�E�g��View�ɔ��f����
	    LayoutInflater inflater = (LayoutInflater)getContext()
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    int id = 0;
	    if(isSelectDir()){
	    	id = jp.xii.relog.customlibrary.R.layout.file_list_view_dir;
	    }else{
	    	id = jp.xii.relog.customlibrary.R.layout.file_list_view;
	    }
	    _mainView = inflater.inflate(id, null);

	    //�f�B���N�g���I���C�x���g
	    Button button = (Button)_mainView.findViewById(R.id.FileSelectListOK_Button);
	    if(button != null){
		    button.setOnClickListener(this);
	    }
	    //���X�g�r���[�̃C�x���g
		ListView list = (ListView)_mainView.findViewById(R.id.FileList_ListView);
		if(list != null){
			list.setOnItemClickListener(this);
		}
		
		//�����\��
	    viewFiles(getCurrentDirectory());
	}


	/**
	 * ���C�A�E�g�m��
	 */
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
	        int bottom) {
	 
	    ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
	                ViewGroup.LayoutParams.WRAP_CONTENT
	                , ViewGroup.LayoutParams.WRAP_CONTENT);
	     
	    addViewInLayout(_mainView, -1, lp);
	 
	    //�q�v�f�̕`��͈͂Ń��C�A�E�g���쐬����
	    _mainView.layout(0, 0, _mainView.getMeasuredWidth(), _mainView.getMeasuredHeight());
	 
	}
	
	/**
	 * �T�C�Y�v��
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    //�q�v�f�ɕK�v�ȑ傫�����v��������
	    _mainView.measure(widthMeasureSpec, heightMeasureSpec);

		int spec_width = MeasureSpec.getSize(widthMeasureSpec);
		int spec_height = _mainView.getMeasuredHeight();

		//�T�C�Y�ݒ�
		setMeasuredDimension(spec_width, spec_height);
	}

	/**
	 * �L�[�C�x���g
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		boolean ret = false;
		if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
			if(event.getAction() == KeyEvent.ACTION_DOWN){
				//�߂�{�^���������ꂽ
				try{
					File dir = getHistoryFileList().pop();
					dir = getHistoryFileList().pop();
					ret = viewFiles(dir);
				}catch(Exception e){
				}
			}
		}
		if(ret){
			return ret;
		}else{
			return super.dispatchKeyEvent(event);
		}
	}
	
	/**
	 * �t�@�C���ꗗ��o�^����
	 * @param path
	 */
	private boolean viewFiles(File dir){
		boolean ret = false;
		ListView list = (ListView)_mainView.findViewById(R.id.FileList_ListView);
		if(dir == null || list == null){
		}else if(!dir.canRead()){
			//�ǂ߂Ȃ�
		}else{
			//�A�_�v�^�쐬
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext()
					, android.R.layout.simple_list_item_1);

			File[] file_lists = dir.listFiles();

			//�N���A
			getCurrentDirFileList().clear();
			//���݂̃f�B���N�g��
			setCurrentDirectory(dir);
			viewCurrentDirectory(dir);

			if(file_lists == null){
			}else{
				//�ǉ�
				for (File file : file_lists) {
					if(file.isDirectory()){
						//�f�B���N�g���̏ꍇ
						adapter.add(file.getName() + "/");
						getCurrentDirFileList().add(file);
					}else{
						//�ʏ�̃t�@�C��
						if(isSelectDir()){
							//�f�B���N�g���I�����[�h�̎��͉������Ȃ�
						}else{
							adapter.add(file.getName());
							getCurrentDirFileList().add(file);
						}
					}
				}
			}
			//�����
			if(adapter.getCount() == 0){
				adapter.add(getContext().getString(R.string.file_list_empty));
			}
			//�����ɕۑ�
			getHistoryFileList().push(dir);

			//�A�_�v�^��ݒ�
			list.setAdapter(adapter);
			
			ret = true;
		}
		return ret;
	}

	/**
	 * ���݂̃p�X��\������
	 * @param dir
	 */
	private void viewCurrentDirectory(File dir){
		TextView text = (TextView)_mainView.findViewById(R.id.FileListCurrentPath_TextView);
		if(dir == null || text == null){
		}else{
			text.setText(dir.getAbsolutePath());
		}
	}
	
	/**
	 * �A�C�e���̑I��
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		if(getCurrentDirFileList() == null){
		}else if(getCurrentDirFileList().size() <= position){
		}else{
			File file = getCurrentDirFileList().get(position);
			
			if(file.isDirectory()){
				//�f�B���N�g�������ǂ�
				if(!viewFiles(file)){
					file = null;	//���s��null��ʒm����
				}
				if(getOnFileListListener() != null){
					getOnFileListListener().onChangeDirectory(file);
				}
			}else{
				//�t�@�C���������̂ŃC�x���g�Œʒm
				if(getOnFileListListener() != null){
					getOnFileListListener().onSelectFile(file);
				}
			}
		}
	}
	
	
	/**
	 * �{�^���������ꂽ
	 */
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.FileSelectListOK_Button:
			//���݂̃f�B���N�g�����C�x���g�Œʒm
			if(getOnFileListListener() != null){
				getOnFileListListener().onSelectDirectory(getCurrentDirectory());
			}
			break;
		default:
			break;
		}
	}
	

	/**
	 * �I���������̃��X�i�p�C���^�[�t�F�[�X�N���X
	 * @author Iori
	 *
	 */
	public interface onFileListListener{
		public void onSelectFile(File file);
		public void onSelectDirectory(File file);
		public void onChangeDirectory(File file);
	}

}
