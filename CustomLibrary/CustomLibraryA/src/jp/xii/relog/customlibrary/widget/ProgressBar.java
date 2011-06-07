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
package jp.xii.relog.customlibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.util.Log;
import jp.xii.relog.customlibrary.view.OriginalView;

//������肩���̕��u�i
public class ProgressBar extends OriginalView {
	

	private int _progress = 0;		//���݂̐i��
	private int _max = 100;			//�ő�l
	
	
	/**
	 * ���݂̐i��
	 * @param _progress the _progress to set
	 */
	public void setProgress(int _progress) {
		this._progress = _progress;
	}
	/**
	 * ���݂̐i��
	 * @return the _progress
	 */
	public int getProgress() {
		return _progress;
	}
	
	/**
	 * �ő�l
	 * @return
	 */
	public int getMax() {
		return _max;
	}
	/**
	 * �ő�l
	 * @param max
	 */
	public void setMax(int max) {
		_max = max;
	}


	/**
	 * �R���X�g���N�^
	 * @param context
	 * @param attrs
	 */
	public ProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		Log.d("mpremocon","ProgressBar");

	}

	
	/**
	 * �T�C�Y�����肷��
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int spec_width = MeasureSpec.getSize(widthMeasureSpec);
		int spec_height = MeasureSpec.getSize(heightMeasureSpec);

		Log.d("mpremocon","onMeasure spec_width="+spec_width + " , spec_height=" + spec_height);

		//�T�C�Y�ݒ�
		setMeasuredDimension(spec_width, spec_height);

		if(_currentDrawable != null){
			_currentDrawable.setBounds(0, 0, spec_width, spec_height);
		}
	}

	/**
	 * �T�C�Y���ύX���ꂽ
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
	//	super.onSizeChanged(w, h, oldw, oldh);
		
		Log.d("mpremocon","onSizeChanged w=" + w + " , h=" + h + " , getWidth=" + getWidth() + " , getHeight=" + getHeight());

		if(_currentDrawable != null){
			_currentDrawable.setBounds(0, 0, w, h);
		}
	}
	
	/**
	 * �`�揈��
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	
		Log.d("mpremocon","onDraw");

		if(_currentDrawable == null){
			int[] colors = new int[]{0x00ffffff, 0xffffffff, 0x00ffffff};
			LinearGradient shader = new LinearGradient(getWidth(), 0, getWidth(), getHeight(), colors, null, TileMode.CLAMP);
			RectF rect = new RectF(0, 0, getWidth(), getHeight());

			Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(Color.WHITE);
			
			
			//�O���f��ݒ�
			paint.setShader(shader);
			//�F�̕ϊ����[�h���w��
			//paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
			
			//�l�p�`��
			canvas.drawRoundRect(rect, 5, 5, paint);
		}else{
			//canvas.save();
			//canvas.translate(getPaddingLeft(), getPaddingTop());//getWidth(), getHeight());

			_currentDrawable.draw(canvas);
			//canvas.restore();
		}
		
	}
	
	private Drawable _currentDrawable = null;
	/**
	 * Drawable��ݒ肷��
	 * @param d
	 */
	public void setProgressDrawable(Drawable d){
		Log.d("mpremocon","setProgressDrawable");
		if(d == null){
		}else if(d.getClass() != LayerDrawable.class){
		}else{
			//d.
			if(_currentDrawable != null){
				_currentDrawable.setCallback(null);
				unscheduleDrawable(_currentDrawable);
			}
			_currentDrawable = d;
			_currentDrawable.setCallback(this);
            if (_currentDrawable.isStateful()) {
            	_currentDrawable.setState(getDrawableState());
            }
            
            requestLayout();
            //onSizeChanged(getWidth(), getHeight(), getWidth(), getHeight());
            invalidate();
            
			//setBackgroundDrawable(d);
//			LayerDrawable layer = (LayerDrawable) d;
//
//			GradientDrawable background = (GradientDrawable) layer.findDrawableByLayerId(android.R.id.background);
////			ClipDrawable progress = (ClipDrawable) layer.findDrawableByLayerId(android.R.id.progress);
//			
//			//�w�i
//			if(background != null){
//				Log.d("mpremocon", "i=" + background.getConstantState().getClass());
//				
////				GradientState state = (GradientState) background.getConstantState();
////				for(int i: state.mColors){
////					Log.d("mpremocon", "i=" + i);
////				}
////				int[] state = background.getState();
////				for (int i : state) {
////					Log.d("mpremocon", "i=" + i);
////				}
//			}
			
		}
	}
	
//    @Override
//    protected boolean verifyDrawable(Drawable who) {
//    	Log.d("mpremocon","verifyDrawable");
//       return who == _currentDrawable// || who == mIndeterminateDrawable
//                || super.verifyDrawable(who);
//    }
//
//    @Override
//    public void invalidateDrawable(Drawable drawable) {
//    	Log.d("mpremocon","invalidateDrawable");
//        if (verifyDrawable(drawable)) {
//            final Rect dirty = drawable.getBounds();
//
//            invalidate(dirty.left , dirty.top,
//                    dirty.right, dirty.bottom);
//        }else{
//        	super.invalidateDrawable(drawable);
//        }
//    }

	
}
