package jp.xii.relog.customlibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import jp.xii.relog.customlibrary.view.OriginalView;

/**
 * �͈͂�I�����Ă��炤���߂̃o�[
 * @author Iori
 *
 */
public class RangeSelectBar extends OriginalView {

	//�萔
	
	//������
	private final static String STR_ATTR_MAX = "max";						//�ő�l
	private final static String STR_ATTR_MIN = "min";						//�ŏ��l
	private final static String STR_ATTR_STEP = "step";						//�ϒl
	private final static String STR_ATTR_DEFAULT_FIRST = "default_first";	//���̏����l
	private final static String STR_ATTR_DEFAULT_LAST = "default_last";		//�E�̏����l

	//���̑�
	private final static int NUM_MAX_HEIGHT = 35;					//�����̍ő�l(dip)
	private final static float NUM_BASE_BAR_HEIGHT_RATIO = 0.4f;	//�S�̂̃o�[���ǂꂾ�����������邩
	private final static float NUM_SELECT_BAR_HEIGHT_RATIO = 0.2f;	//�I���̃o�[���ǂꂾ�����������邩
	private final static int NUM_POINT_HIT_RADIUS = 30;				//�ő�ŏ���ύX����G���A�̔��a(dip)
	private final static int NUM_WIDTH_KNOB = 15;					//�܂݂̉���(dip)
	private final static float NUM_KNOB_HEIGHT_RATIO = 0.1f;		//�܂݂̍������ǂꂾ�����������邩
	
	private enum HitAreaType{
		None
		, First		//�ŏ�
		, Last		//�Ō�
		, Bar		//�o�[
	}
	
	
	private int _max = 100;		//�ő�l
	private int _min = 0;		//�ŏ��l
	private int _step = 10;		//�ϒl
	
	private int _first = 40;		//�ŏ��̒l
	private int _last = 80;			//�Ō�̒l
	private boolean _isLoop = true;		//���E�����[�v���邩
	
	private Point _prevDownPoint = null;	//�O�񉟂��Ă��ʒu
	private HitAreaType _hitArea = HitAreaType.None;	//�����Ă�G���A
	
	private int _widthKnob = 0;	//�܂݂̉���
	
	private onRangeSelectBarChangeListener _changeListener = null;	//�ω��������̃��X�i�[
	
	
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
	 * �O�񉟂��Ă��ʒu
	 * @return the _prevDownPoint
	 */
	public Point getPrevDownPoint() {
		if(_prevDownPoint == null){
			_prevDownPoint = new Point();
		}
		return _prevDownPoint;
	}

	/**
	 * getFirst/getLast�̒l�����ۂ̉����ƍŏ��ő�l����v�Z���邽�߂̌W�������߂�
	 * @return
	 */
	public float getWidthRatio(){
		return (float)(getWidth()) / (float)(getMax() - getMin());
	}
	
	
	/**
	 * �܂݂̉���
	 * @return the _widthKnob 
	 */
	public int getWidthKnob() {
		if(_widthKnob == 0){
			_widthKnob = DtoInt(NUM_WIDTH_KNOB);
		}
		return _widthKnob;
	}

	
	/**
	 * �ω��������̃��X�i�[
	 * @param _changeListener the _changeListener to set
	 */
	public void setOnRangeSelectBarChangeListener(onRangeSelectBarChangeListener _changeListener) {
		this._changeListener = _changeListener;
	}
	/**
	 * �ω��������̃��X�i�[
	 * @return the _changeListener
	 */
	public onRangeSelectBarChangeListener getOnRangeSelectBarChangeListener() {
		return _changeListener;
	}
	
	/**
	 * �͈͂�I������o�[
	 * @param context
	 * @param attrs
	 */
	public RangeSelectBar(Context context, AttributeSet attrs) {
		super(context, attrs);

	
		String temp = null;

		//�ő�l
		temp = attrs.getAttributeValue(null, STR_ATTR_MAX);
		if(temp != null){
			setMax(DtoInt(temp));
		}
		//�ŏ��l
		temp = attrs.getAttributeValue(null, STR_ATTR_MIN);
		if(temp != null){
			setMin(DtoInt(temp));
		}
		//�ϒl
		temp = attrs.getAttributeValue(null, STR_ATTR_STEP);
		if(temp != null){
			setStep(DtoInt(temp));
		}
		
		//�ŏ��̒l�̏����l
		temp = attrs.getAttributeValue(null, STR_ATTR_DEFAULT_FIRST);
		if(temp != null){
			setFirst(DtoInt(temp));
		}
		//�Ō�̒l�̏����l
		temp = attrs.getAttributeValue(null, STR_ATTR_DEFAULT_LAST);
		if(temp != null){
			setLast(DtoInt(temp));
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
	 * �T�C�Y�����肷��
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int spec_width = MeasureSpec.getSize(widthMeasureSpec);
		int spec_height = MeasureSpec.getSize(heightMeasureSpec);

		//��ԏ������̂��g��
		int mine = DtoInt(NUM_MAX_HEIGHT);
		int spec_size = Math.min(mine, spec_height);

		//�T�C�Y�ݒ�
		setMeasuredDimension(spec_width, spec_size);
		
	}

	/**
	 * �`�揈��
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
//		int padding = 5;//DtoInt("5dip");
//		int width = getWidth() - padding * 2;							//�`��G���A
//		float ratio = (float)(width) / (float)(getMax() - getMin());	//�o�[�̐��l������W�֕ϊ��W��
		
		
		Paint paint = new Paint();				//�`��t�H�[�}�b�g�쐬
		paint.setAntiAlias(true);				//�A���`�G�C���A�X�L��
		
		RectF rect;
		LinearGradient shader;
		
		//�O���f�[�V�����F
		int[] colors_base = {ColorToInt("#00034179")
				, ColorToInt("#ff034179")
				, ColorToInt("#ffffffff")};
		int[] colors_select = {ColorToInt("#ffffffff")
				, ColorToInt("#FF8DBAE2")
				, ColorToInt("#ff034179")};
		int[] colors_point = {ColorToInt("#FFdddddd")
				, ColorToInt("#FF888888")
				, ColorToInt("#FF222222")};
		//55FF9900 ffFF9900
		//558DBAE2 FF8DBAE2
		
		//�x�[�X
		rect = new RectF(0, getHeight() * NUM_BASE_BAR_HEIGHT_RATIO
						, getWidth(), getHeight() * (1.0f - NUM_BASE_BAR_HEIGHT_RATIO));
		shader = new LinearGradient(0, 0, 0, getHeight()
											, colors_base
											, null
											, TileMode.CLAMP);
		paint.setShader(shader);	//�V�F�[�_�[�o�^
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		canvas.drawRoundRect(rect, 5, 5, paint);

		//�I��͈�
		if(getFirst() < getLast()){
			//�ʏ���
			rect = new RectF((getFirst() - getMin()) * getWidthRatio()
					, getHeight() * NUM_SELECT_BAR_HEIGHT_RATIO
					, (getLast() - getMin()) * getWidthRatio()
					, getHeight() * (1.0f - NUM_SELECT_BAR_HEIGHT_RATIO));
			shader = new LinearGradient(0, 0, 0, getHeight()
												, colors_select
												, null
												, TileMode.CLAMP);
			paint.setShader(shader);	//�V�F�[�_�[�o�^
			paint.setStyle(Paint.Style.FILL_AND_STROKE);
			canvas.drawRoundRect(rect, 5, 5, paint);
		}else{
			//���[�v��ԁi���j
			rect = new RectF((getMin() - getMin()) * getWidthRatio()
					, getHeight() * NUM_SELECT_BAR_HEIGHT_RATIO
					, (getLast() - getMin()) * getWidthRatio()
					, getHeight() * (1.0f - NUM_SELECT_BAR_HEIGHT_RATIO));
			shader = new LinearGradient(0, 0, 0, getHeight()
												, colors_select
												, null
												, TileMode.CLAMP);
			paint.setShader(shader);	//�V�F�[�_�[�o�^
			paint.setStyle(Paint.Style.FILL_AND_STROKE);
			canvas.drawRoundRect(rect, 5, 5, paint);

			//���[�v��ԁi�E�j
			rect = new RectF((getFirst() - getMin()) * getWidthRatio()
					, getHeight() * NUM_SELECT_BAR_HEIGHT_RATIO
					, (getMax() - getMin()) * getWidthRatio()
					, getHeight() * (1.0f - NUM_SELECT_BAR_HEIGHT_RATIO));
			canvas.drawRoundRect(rect, 5, 5, paint);
		}
		
		//���Ƃ���̕`��i���j
		rect = new RectF((getFirst() - getMin()) * getWidthRatio()
				, getHeight() * NUM_KNOB_HEIGHT_RATIO
				, (getFirst() - getMin()) * getWidthRatio() + getWidthKnob()
				, getHeight() * (1.0f - NUM_KNOB_HEIGHT_RATIO));
		shader = new LinearGradient(0, 0, 0, getHeight()
											, colors_point
											, null
											, TileMode.CLAMP);
		paint.setShader(shader);	//�V�F�[�_�[�o�^
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		canvas.drawRoundRect(rect, 5, 5, paint);

		//���Ƃ���̕`��i�E�j
		rect = new RectF((getLast() - getMin()) * getWidthRatio() - getWidthKnob()
				, getHeight() * NUM_KNOB_HEIGHT_RATIO
				, (getLast() - getMin()) * getWidthRatio()
				, getHeight() * (1.0f - NUM_KNOB_HEIGHT_RATIO));
		canvas.drawRoundRect(rect, 5, 5, paint);
	}

	
		
	/**
	 * �^�b�`�C�x���g
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean ret = false;
		
		int action = event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();

		switch(action){
		case MotionEvent.ACTION_DOWN:
			//�|�C���g�ۑ�
			getPrevDownPoint().set(x, y);
			//�h�R�������Ă邩
			_hitArea = calcHitArea(x, y);
			ret = true;
			break;
			
		case MotionEvent.ACTION_MOVE:
			int diff = (int) ((getPrevDownPoint().x - x) / getWidthRatio());

			if(Math.abs(diff) >= getStep()){
				//�����Ƃ�����

				//�X�e�b�v�Ŋۂ߂�
				diff = (diff / getStep()) * getStep();

				//�ŏ��ƍŌ�̒l���Čv�Z
				switch(_hitArea){
				case First:
					//�ŏ��̒l
					moveFirst(diff);
					break;
				case Last:
					//�Ō�̒l
					moveLast(diff);
					break;
				case Bar:
					//�o�[
					moveBar(diff);
					break;
					
				default:
					break;
				}
								
				//�O�񕪂Ƃ��ĕۑ�
				getPrevDownPoint().set(x, y);

				//�ĕ`��
				invalidate();
				
				//�C�x���g
				if(getOnRangeSelectBarChangeListener() != null){
					getOnRangeSelectBarChangeListener()
							.onProgressChanged(this, getFirst(), getLast());
				}
			}
			ret = true;
			break;
			
		case MotionEvent.ACTION_UP:
			break;
			
		default:
			break;
		}
		
		if(ret){
			return true;
		}else{
			return super.onTouchEvent(event);
		}
	}
	
	/**
	 * �G���Ă�G���A�𔻒肷��
	 * @param x
	 * @param y
	 * @return
	 */
	private HitAreaType calcHitArea(int x, int y){
		HitAreaType ret = HitAreaType.Bar;
		int first = (int) ((getFirst() - getMin()) * getWidthRatio());
		int last = (int) ((getLast() - getMin()) * getWidthRatio());
		int range = DtoInt(NUM_POINT_HIT_RADIUS); 
		int diff = 0;
		
		//�ŏ��̒l�̃G���A
		diff = Math.abs(first - x);
		if(diff < range){
			//hit
			ret = HitAreaType.First;
		}

		//�Ō�̒l�̃G���A
		diff = Math.abs(last - x);
		if(diff < range){
			//hit
			ret = HitAreaType.Last;
		}

		return ret;
	}
	
	
	/**
	 * �ŏ��̏ꏊ���ړ�������
	 * @param diff
	 */
	private void moveFirst(int diff){
		if(getFirst() < getLast()){
			//�ʏ�̈ʒu�֌W
			if((getFirst() - diff) >= getLast()){
				//����Ⴄ�̂łȂɂ����Ȃ�
			}else{
				setFirst(getFirst() - diff);
			}
		}else{
			//����ւ���Ă鎞
			if((getFirst() - diff) < getLast()){
				//����Ⴄ�̂łȂɂ����Ȃ�
			}else{
				setFirst(getFirst() - diff);
			}
		}
	}
	
	/**
	 * �Ō�̏ꏊ���ړ�������
	 * @param diff
	 */
	private void moveLast(int diff){
		if(getFirst() < getLast()){
			//�ʏ�̈ʒu�֌W
			if((getLast() - diff) <= getFirst()){
				//����Ⴄ�̂łȂɂ����Ȃ�
			}else{
				setLast(getLast() - diff);
			}
		}else{
			//����ւ���Ă鎞
			if((getLast() - diff) > getFirst()){
				//����Ⴄ�̂łȂɂ����Ȃ�
			}else{
				setLast(getLast() - diff);
			}
		}
	}
	
	/**
	 * �ŏ��ƍŌ�̒l���Čv�Z����
	 * @param diff
	 */
	private void moveBar(int diff){
		if(isLoop()){
			//���[�v
			//��
			if((getFirst() - diff) < getMin()){
				//���ɂ͂ݏo��
				setFirst(getMax() - (getMin() - (getFirst() - diff)));
			}else if((getFirst() - diff) > getMax()){
				//�E�ɂ͂ݏo��
				setFirst(getMin() + ((getFirst() - diff) - getMax()));
			}else{
				setFirst(getFirst() - diff);
			}
			//�E
			if((getLast() - diff) < getMin()){
				//���ɂ͂ݏo��
				setLast(getMax() - (getMin() - (getLast() - diff)));
			}else if((getLast() - diff) > getMax()){
				//�E�ɂ͂ݏo��
				setLast(getMin() + ((getLast() - diff) - getMax()));
			}else{
				setLast(getLast() - diff);
			}
		}else{
			//�~�߂�
			if((getFirst() - diff) < getMin()){
				//���ɂ͂ݏo��
				setLast(getLast() - getFirst() + getMin());
				setFirst(getMin());
			}else if((getLast() - diff) > getMax()){
				//�E�ɂ͂ݏo��
				setFirst(getMax() - (getLast() - getFirst()));
				setLast(getMax());
			}else{
				setFirst(getFirst() - diff);
				setLast(getLast() - diff);
			}
		}
	}


	/**
	 * �ω����N�������Ƃ��̃C���^�[�t�F�[�X�N���X
	 * @author Iori
	 *
	 */
	public interface onRangeSelectBarChangeListener{
		void onProgressChanged(RangeSelectBar rangeSelectBar, int first, int last);
	}

}
