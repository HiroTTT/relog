package jp.xii.relog.customlibrary.widget;

import java.util.ArrayList;

import android.R.color;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class BreadCrumbList extends HorizontalScrollView
	implements OnClickListener{

	private LinearLayout _buttonArea = null;				//���X�g��z�u���郌�C�A�E�g
	private ArrayList<LinearLayout> _buttonList = null;		//�p���������X�g�̃{�^��
	private OnClickListener _onClickListener = null;		//���X�i�[
	private int _textColor = 0xff000000;					//�e�L�X�g�J���[
	private int _buttonBackgroundResourceId = -1;			//�{�^���w�i�̃��\�[�XID
	private int _separatorResourceId = -1;					//�Z�p���[�^�̃��\�[�XID
	private boolean _isUnResponse = false;					//�{�^���������Ă��������Ȃ��悤�ɂ���
	
	/**
	 * ���X�g��z�u���郌�C�A�E�g
	 * @return
	 */
	public LinearLayout getButtonArea(){
		if(_buttonArea == null){
			_buttonArea = new LinearLayout(getContext());
		}
		return _buttonArea;
	}
	
	/**
	 * �p���������X�g�̃{�^��
	 * @return the _buttonList
	 */
	public ArrayList<LinearLayout> getButtonList() {
		if(_buttonList == null){
			_buttonList = new ArrayList<LinearLayout>();
		}
		return _buttonList;
	}

	/**
	 * ���X�i�[�̓o�^
	 * @param listner
	 */
	public void setOnClickListener(OnClickListener listner){
		_onClickListener = listner;
	}


	/**
	 * �e�L�X�g�J���[
	 * @param _textColor the _textColor to set
	 */
	public void setTextColor(int _textColor) {
		this._textColor = _textColor;
	}

	/**
	 * �e�L�X�g�J���[
	 * @return the _textColor
	 */
	public int getTextColor() {
		return _textColor;
	}

	/**
	 * �{�^���w�i�̃��\�[�XID
	 * @param _buttonBackgroundResourceId the _buttonBackgroundResourceId to set
	 */
	public void setButtonBackgroundResourceId(int _buttonBackgroundResourceId) {
		this._buttonBackgroundResourceId = _buttonBackgroundResourceId;
	}

	/**
	 * �{�^���w�i�̃��\�[�XID
	 * @return the _buttonBackgroundResourceId
	 */
	public int getButtonBackgroundResourceId() {
		return _buttonBackgroundResourceId;
	}

	/**
	 * �Z�p���[�^�̃��\�[�XID
	 * @param _separatorResourceId the _separatorResourceId to set
	 */
	public void setSeparatorResourceId(int _separatorResourceId) {
		this._separatorResourceId = _separatorResourceId;
	}

	/**
	 * �Z�p���[�^�̃��\�[�XID
	 * @return the _separatorResourceId
	 */
	public int getSeparatorResourceId() {
		return _separatorResourceId;
	}

	/**
	 * �{�^���������Ă��������Ȃ��悤�ɂ���
	 * @param _isUnResponse the _isUnResponse to set
	 */
	public void setIsUnResponse(boolean _isUnResponse) {
		this._isUnResponse = _isUnResponse;
	}

	/**
	 * �{�^���������Ă��������Ȃ��悤�ɂ���
	 * @return the _isUnResponse
	 */
	public boolean isUnResponse() {
		return _isUnResponse;
	}

	/**
	 * �R���X�g���N�^
	 * @param context
	 * @param attrs
	 */
	public BreadCrumbList(Context context, AttributeSet attrs) {
		super(context, attrs);

//		//xml�p�����[�^�̉��
//        TypedArray a = context.obtainStyledAttributes(attrs,
//                R.styleable.BreadCrumbList, 0, 0);
//
//        //�Z�p���[�^��Drawable���擾
//        
//        Drawable d = a.getDrawable(R.styleable.BreadCrumbList_separater_src);
//        setSeparatorDrawable(d);
//		Log.d("mpremocon", "onCreate d="+ d + ",a.length="+a.length());
		
		//�e�L�X�g�F
		setTextColor(getResources().getColor(color.primary_text_light));

		//�Z�p���[�^���w�肷��
		setSeparatorResourceId(jp.xii.relog.customlibrary.R.drawable.im_breadcrumblist_separator);
		
		//�{�^���̔z�u�p�̈�Ԑe�̃��C�A�E�g��ǉ�����
		addView(getButtonArea());

		//�㑤�̌��Ԓ���
//		getButtonArea().setPadding(getButtonArea().getPaddingLeft()
//				, getHorizontalScrollbarHeight()
//				, getButtonArea().getPaddingRight()
//				, getHorizontalScrollbarHeight()//getButtonArea().getPaddingBottom()
//				);

	}

	/**
	 * �㉺�̌��Ԃ��X�N���[���o�[�̃T�C�Y�Œ��߂��邩
	 * @param top
	 * @param bottom
	 */
	public void adjustPadding(boolean top, boolean bottom){
		int top_padding = 0;
		int bottom_padding = 0;
		if(top){
			top_padding = getHorizontalScrollbarHeight();
		}
		if(bottom){
			bottom_padding = getHorizontalScrollbarHeight();
		}
		getButtonArea().setPadding(getButtonArea().getPaddingLeft()
				, top_padding
				, getButtonArea().getPaddingRight()
				, bottom_padding
				);
	}
	
	/**
	 * ���C�A�E�g���m�肳����
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);


		//��ԉE�ֈړ�
		fullScroll(FOCUS_RIGHT);
	}
	
	/**
	 * �A�C�e����ǉ�����
	 * @param name
	 */
	public void push(String name){
		//���C�A�E�g�쐬
		LinearLayout layout = new LinearLayout(getContext());
		
		//�Z�p���[�^���w�肳��Ă����ꍇ�͒ǉ�
		if(getButtonList().size() == 0){
		}else if(getSeparatorResourceId() < 0){
		}else{
			ImageView image = new ImageView(getContext());
			image.setBackgroundResource(getSeparatorResourceId());
			layout.addView(image);
		}

		//�{�^���쐬
		Button button = new Button(getContext());
		button.setText(name);
		button.setOnClickListener(this);
		button.setId(1);
		button.setTextColor(getTextColor());
		if(getButtonBackgroundResourceId() >= 0){
			button.setBackgroundResource(getButtonBackgroundResourceId());
		}
		layout.addView(button);

		//�^�񒆊񂹂ɂ���
		layout.setGravity(Gravity.CENTER);

		//�e�ɒǉ�
		getButtonList().add(layout);
		getButtonArea().addView(layout);
	}

	/**
	 * �A�C�e�����P�O��
	 */
	public LinearLayout pop(){
		int del_index = getButtonList().size() - 1;
		LinearLayout ret = getButtonList().get(del_index);
		//���X�g����폜
		getButtonList().remove(del_index);
		//���C�A�E�g����폜
		getButtonArea().removeView(ret);
		
		return ret;
	}

	/**
	 * �܂܂��A�C�e���̐�
	 */
	public int size(){
		return getButtonList().size();
	}

	/**
	 * �N���b�N�C�x���g
	 */
	@Override
	public void onClick(View v) {
		if(_onClickListener == null){
			//���X�i�[���o�^����ĂȂ��Ƃ��͏���Ɍ��炳�Ȃ�
		}else if(isUnResponse()){
			//���b�N���͔������Ȃ�
		}else{
			//�������{�^�������X�g�̒�����T��
			for(int i=0; i<getButtonList().size(); i++){
				if(getButtonList().get(i).findViewById(1).equals(v)){
					//���������炻������̃{�^��������
					while((i+1) < getButtonList().size()){
						pop();
					}
					
					//�C�x���g���Ă�
					_onClickListener.onClick(v, i);
					break;
				}
			}
		}
	}
	
	/**
	 * �N���b�N���X�i�[
	 * @author Iori
	 *
	 */
	public interface OnClickListener{
		void onClick(View v, int position);
	}
}
