package jp.xii.relog.customlibrary.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

public class HorizontalAutoScrollView extends HorizontalScrollView {

	private final static String STR_ATTR_FRAME_INTERVAL = "frameInterval";	//�t���[���Ԃ̎���
	private final static String STR_ATTR_FRAME_DELTA = "frameDelta";		//�t���[���Ԃ̈ړ���
	private final static String STR_ATTR_LAPEL_INTERVAL = "lapelInterval";	//�܂�Ԃ����̃t���[���Ԋu
	private final static String STR_ATTR_IS_LOOP = "isLoop";				//���[�v���邩
	private final static String STR_ATTR_LOOP_INTERVAL = "loopInterval";	//���[�v����Ƃ��̃t���[���Ԋu

	private Handler _handlerAnimation = null;		//�A�j���[�V�����p
	private int _frameInterval = 100;				//�t���[���Ԃ̎���
	private int _frameDelta = 2;					//�t���[���Ԃ̈ړ���
	private int _lapelInterval = 500;				//�܂�Ԃ����̃t���[���Ԋu
	private boolean _isDerectionLeft = true;		//���֓����Ă��邩
	private int _prev_x = 0;						//�O��̏ꏊ
	
	private boolean _isLoop = false;				//���[�v���邩
	private int _loopInterval = 500;				//���[�v���鎞�̃t���[���Ԋu
	
	private String _innerTextBackup = "";			//���X�̕�����̃o�b�N�A�b�v
	private int _innerTextWidth = 0;				//���݂̕�����̒����i2���j
	
	/**
	 * �`��X���b�h
	 */
	private final Runnable _runAnimationThread = new Runnable(){
		public void run(){

			updateAutoScroll();
			
		}
	};

	/**
	 * �A�j���[�V�����p�̃n���h��
	 * @return
	 */
	private Handler getHandlerAnimation(){
		if(_handlerAnimation == null){
			_handlerAnimation = new Handler();
		}
		return _handlerAnimation;
	}

	/**
	 * �q���̃e�L�X�g�r���[���擾����
	 * @return
	 */
	private TextView getInnerTextView(){
		TextView tv = null;
		try{
			View v = getChildAt(0);
			if(v.getClass() == TextView.class){
				tv = (TextView)v;
			}
		}catch(Exception e){
		}
		return tv;
	}
	
	/**
	 * ���X�N���[������e�L�X�g�r���[�̃R���X�g���N�^
	 * @param context
	 * @param attrs
	 */
	public HorizontalAutoScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		setSmoothScrollingEnabled(true);
		
		String temp = null;
		
		//�t���[���Ԃ̎���
		temp = attrs.getAttributeValue(null, STR_ATTR_FRAME_INTERVAL);
		if(temp != null){
			_frameInterval = Integer.valueOf(temp);
		}
		//�t���[���Ԃ̈ړ���
		temp = attrs.getAttributeValue(null, STR_ATTR_FRAME_DELTA);
		if(temp != null){
			_frameDelta = Integer.valueOf(temp);
		}
		//�܂�Ԃ����̃t���[���Ԋu
		temp = attrs.getAttributeValue(null, STR_ATTR_LAPEL_INTERVAL);
		if(temp != null){
			_lapelInterval = Integer.valueOf(temp);
		}
		
		//���[�v���̃t���[���Ԋu
		temp = attrs.getAttributeValue(null, STR_ATTR_LOOP_INTERVAL);
		if(temp != null){
			_loopInterval = Integer.valueOf(temp);
		}
		//���[�v���邩
		temp = attrs.getAttributeValue(null, STR_ATTR_IS_LOOP);
		if(temp != null){
			_isLoop = Boolean.valueOf(temp);
		}
	}

	
	/**
	 * �\����Ԃ��ς����
	 */
	@Override
	protected void onWindowVisibilityChanged(int visibility) {
		super.onWindowVisibilityChanged(visibility);
		
		if(visibility == View.VISIBLE){
			startAutoScroll();
		}else{
			stopAutoScroll();
		}
	}

	/**
	 * �蓮�ŃX�N���[���ł��Ȃ��悤�ɂ���
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return true;//super.onTouchEvent(ev);
	}
	
	/**
	 * �����X�N���[�����J�n����
	 */
	public void startAutoScroll(){
		//�Ď����J�n
		getHandlerAnimation().postDelayed(_runAnimationThread, _frameInterval);
	}
	
	/**
	 * �����X�N���[�����~�߂�
	 */
	public void stopAutoScroll(){
		//��~����
		getHandlerAnimation().removeCallbacks(_runAnimationThread);
		//�ʒu��߂�
		scrollTo(0, getScrollY());

	}
	
	/**
	 * 1�ڂ̃e�L�X�g�֐ݒ肷��
	 * @param text
	 */
	public void setText(String text){
		if(getInnerTextView() == null){
			//�e�L�X�g���Ȃ�
		}else{
			getInnerTextView().setText(text);

			//�ăX�^�[�g
			stopAutoScroll();
			startAutoScroll();
		}
	}
	
	/**
	 * 1�ڂ̃e�L�X�g���擾����
	 * @return
	 */
	public String getText(){
		return _innerTextBackup;
	}

	/**
	 * �����X�N���[���̏�ԍX�V
	 */
	public void updateAutoScroll(){
		int next_interval = _frameInterval;
		int x = getScrollX();
		
		if(getChildAt(0) == null){
		}else if(getChildAt(0).getWidth() <= getWidth()){
			//�͂ݏo�ĂȂ�
			next_interval *= 2;	//�X�N���[���̕K�v�������̂ŊԊu���L����
			
			_isDerectionLeft = true;
			_prev_x = 0;
			x = 0;
			
			_innerTextBackup = "";
			_innerTextWidth = 0;
		}else{
			//�͂ݏo�Ă�
			
			if(_isLoop && getInnerTextView() != null){
				//���[�v�ł����Ƀe�L�X�g���܂܂��

				//���e��2�d�ɂ���
				if(_innerTextWidth != 0){
					//���ɓ��e��2�d�ɂ��Ă���
				}else{
					//���߂�
					_innerTextBackup = (String) getInnerTextView().getText();
					_innerTextWidth = getInnerTextView().getWidth();
					getInnerTextView().setText(_innerTextBackup + _innerTextBackup);
				}

				//����
				x += _frameDelta;

				//�\��̕���������߂�
				if(_innerTextWidth == x){
					x = 0;
					next_interval = _loopInterval;
				}
			}else{
				//���E�ړ�

				//�ʒu���v�Z
				if(_isDerectionLeft){
					//����
					x += _frameDelta;
				}else{
					//�E��
					x -= _frameDelta;
				}

				//������ς���
				if(x == _prev_x){
					_isDerectionLeft = !_isDerectionLeft;
					next_interval = _lapelInterval;
				}
			}

			_prev_x = x;
			
		}

		//�ړ�
		scrollTo(x, getScrollY());

		//���̃Z�b�g
		getHandlerAnimation().postDelayed(_runAnimationThread, next_interval);
	}
	
}
