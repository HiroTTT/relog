package jp.xii.relog.customlibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;


/*
	<jp.xii.relog.customlibrary.widget.HorizontalAutoScrollTextView
			android:id="@+id/AutoScrollTest"
			android:layout_width="fill_parent"
			android:layout_height="wrap_content"
			text="�����e�L�X�g�̃T���v���ł����A�݂͂ł����ˁ[�H"
			android:onClick="onHorizontalAutoScrollTestClick"
			>
	</jp.xii.relog.customlibrary.widget.HorizontalAutoScrollTextView>

 */


public class HorizontalAutoScrollTextView extends ViewGroup implements AnimationListener {

	private final static String STR_ATTR_TEXT = "text";			//�e�L�X�g

	
	private String _text = "";						//�e�L�X�g
	private TextView _textView0 = null;				//���ɔz�u����e�L�X�g�r���[
	
	
	/**
	 * ���ɔz�u����e�L�X�g�r���[
	 * @return the _textView0
	 */
	public TextView getTextView0() {
		if(_textView0 == null){
			_textView0 = new TextView(getContext());
			_textView0.setHorizontallyScrolling(true);
			_textView0.setTextSize(20);
		}
		return _textView0;
	}

	/**
	 * @param _text the _text to set
	 */
	public void setText(String _text) {
		this._text = _text;
	}

	/**
	 * @return the _text
	 */
	public String getText() {
		return _text;
	}
	

	public HorizontalAutoScrollTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		String temp;
		temp = attrs.getAttributeValue(null, STR_ATTR_TEXT);
		if(temp != null){
			setText(temp);
			getTextView0().setText(temp);
		}
	}

	/**
	 * �T�C�Y�̌��肷��
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		getTextView0().setLayoutParams(lp);

		// �q�v�f�ɕK�v�ȑ傫�����v��
		int childWidthSpec = ViewGroup.getChildMeasureSpec(widthMeasureSpec, 0, lp.width);
		int childHeightSpec = ViewGroup.getChildMeasureSpec(heightMeasureSpec, 0, lp.height);
		getTextView0().measure(childWidthSpec, childHeightSpec);

		//�T�C�Y�ݒ�
		setMeasuredDimension(getTextView0().getMeasuredWidth(), getTextView0().getMeasuredHeight());
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		//super.onLayout(changed, left, top, right, bottom);
		
		addViewInLayout(getTextView0(), -1, getTextView0().getLayoutParams());
		
        // �q�v�f�̕`��͈͂�n���ă��C�A�E�g
        getTextView0().layout(0, 0, getTextView0().getMeasuredWidth(), getTextView0().getMeasuredHeight());
	}
	
	/**
	 * �X�N���[���J�n
	 */
	public void startScroll(){
		Log.d("mpremocon", "startScroll");
		TranslateAnimation trans = new TranslateAnimation(0, -1 * getTextView0().getWidth(), 0, 0);
		trans.setDuration(5000);
		trans.setFillAfter(true);
		trans.setFillBefore(true);
		trans.setAnimationListener(this);
		getTextView0().startAnimation(trans);
	}

	/**
	 * �A�j���[�V�����J�n
	 */
	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * �A�j���[�V�����J��Ԃ�
	 */
	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * �A�j���[�V�����I��
	 */
	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		
	}


}
