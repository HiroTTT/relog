package jp.xii.relog.customlibrary.widget;


import jp.xii.relog.customlibrary.view.OriginalView;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

public class ProgressCircle extends OriginalView {

	private final static String STR_ATTR_RADIUS = "radius";			//���a
	private final static String STR_ATTR_WEIGHT = "weight";			//���̑���
	private final static String STR_ATTR_COLOR = "color";			//�F
	private final static String STR_ATTR_BASE_COLOR= "base_color";	//�i�����ĂȂ����̐F

	private int _progress;			//���݂̐i��
	private int _max;				//�ő�l
	private int _weight;			//���̑���
	private int _color;				//�F
	private int _base_color;		//�i�����ĂȂ����̐F

	private int _radius;			//���a
	
	/**
	 * ���݂̐i��
	 * @param _progress the _progress to set
	 */
	public void setProgress(int _progress) {
		if(_progress > getMax()){
			_progress = getMax();
		}
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
	 * @param _max the _max to set
	 */
	public void setMax(int _max) {
		if(_max <= 0){
			_max = 1;
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
	 * ���̑���
	 * @param _weight the _weight to set
	 */
	public void setWeight(int _weight) {
		if(_weight < 1){
			_weight = 1;
		}
		this._weight = _weight;
	}


	/**
	 * ���̑���
	 * @return the _weight
	 */
	public int getWeight() {
		return _weight;
	}

	/**
	 * �F
	 * @param _color the _color to set
	 */
	public void setColor(int _color) {
		this._color = _color;
	}


	/**
	 * �F
	 * @return the _color
	 */
	public int getColor() {
		return _color;
	}

	/**
	 * �i�����ĂȂ����̐F
	 * @param _base_color the _base_color to set
	 */
	public void setBaseColor(int _base_color) {
		this._base_color = _base_color;
	}


	/**
	 * �i�����ĂȂ����̐F
	 * @return the _base_color
	 */
	public int getBaseColor() {
		return _base_color;
	}

	
	/**
	 * �~�`�̃v���O���X�o�[
	 * @param context
	 * @param attrs
	 */
	public ProgressCircle(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		setProgress(0);
		setMax(100);
		setWeight(1);
		setColor(Color.WHITE);
		setBaseColor(Color.GRAY);
		_radius = 30;
		
		String temp;

	
		//���a
		temp = attrs.getAttributeValue(null, STR_ATTR_RADIUS);
		if(temp != null){
			_radius = DtoInt(temp);
		}
		
		//���̑���
		temp = attrs.getAttributeValue(null, STR_ATTR_WEIGHT);
		if(temp != null){
			setWeight(DtoInt(temp));
		}
		
		//�F
		temp = attrs.getAttributeValue(null, STR_ATTR_COLOR);
		if(temp != null){
			setColor(ColorToInt(temp));
		}

		//�x�[�X�F
		temp = attrs.getAttributeValue(null, STR_ATTR_BASE_COLOR);
		if(temp != null){
			setBaseColor(ColorToInt(temp));
		}
		
	}
	
	/**
	 * �T�C�Y�����肷��
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int spec_width = MeasureSpec.getSize(widthMeasureSpec);
		int spec_height = MeasureSpec.getSize(heightMeasureSpec);
		int mine = _radius * 2;
		//��ԏ������̂��g��
		int spec_size = Math.min(mine, spec_width);
		spec_size = Math.min(spec_size, spec_height);
		//�T�C�Y�ݒ�
		setMeasuredDimension(spec_size, spec_size);
	}
	
	/**
	 * �`�揈��
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		
		Paint paint = new Paint();				//�`��t�H�[�}�b�g�쐬
		paint.setAntiAlias(true);				//�A���`�G�C���A�X�L��
		paint.setStyle(Paint.Style.STROKE);		//�������ɂ���
		
		//�~�̃G���A��`
		RectF rect = new RectF(0 + getWeight(), 0 + getWeight()
							, getWidth() - getWeight(), getHeight() - getWeight());

		//�i���O
		paint.setColor(getBaseColor());
		paint.setStrokeWidth(getWeight());
		canvas.drawArc(rect, 270, 360, false, paint);
		
		//�i��
		paint.setColor(getColor());
		paint.setStrokeWidth(getWeight());
		canvas.drawArc(rect, 270,(float)(360 * (float)getProgress() / (float)getMax()), false, paint);
	}

}
