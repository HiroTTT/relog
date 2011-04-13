/**
 * 
 */
package jp.xii.relog.sensortest;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

/**
 * @author Iori
 *
 */
public class IndicatorView extends View {

	
	static final int NUM_BAR_NUM = 32;							//�C���W�P�[�^�̃o�[�̑���
	static final double NUM_DELTA = Math.PI * 2 / NUM_BAR_NUM;	//1�{�̐i�ރV�[�^ 
	static final int NUM_DIRECTION_FONT_SIZE = 16;				//���ʂ̃t�H���g�T�C�Y
	static final int NUM_DIRECTION_FONT_SIZE_HALF = NUM_DIRECTION_FONT_SIZE / 2;	//���ʂ̃t�H���g�T�C�Y�̔���
	
	static final double NUM_DISTANCE_EYE_TO_DISPLAY = 5.9055;	//�߂Ɖ�ʂ̋���(inch) = 15cm 
	
	static final int NUM_NORTH_COUNT = 0;						//�k�����ɂȂ�J�E���g
	static final int NUM_EAST_COUNT = NUM_BAR_NUM / 4;			//�������ɂȂ�J�E���g
	static final int NUM_SOUTH_COUNT = NUM_BAR_NUM / 2;			//������ɂȂ�J�E���g
	static final int NUM_WEST_COUNT = NUM_BAR_NUM * 3 / 4;		//�������ɂȂ�J�E���g
	
	private double _locationLatitude = 0.0;			//�ܓx
	private double _locationLongitude = 0.0;		//�o�x
	private double _locationSeaLevel = 0.0;			//�W��
	
	private float _sensorOrientation = 0;			//����
	private float _sensorOrientationPitch = 0;		//�s�b�`
	private float _sensorOrientationRoll = 0;		//���[��

	
	private CharactorObject _mineObject = null;		//�����̈ʒu���
	private ArrayList<CharactorObject> _targetObjects = null;	//�^�[�Q�b�g�̈ʒu���̃��X�g
	
	
	/**
	 * �ܓx
	 * @param _locationLatitude the _locationLatitude to set
	 */
	public void setLocationLatitude(double _locationLatitude) {
		this._locationLatitude = _locationLatitude;
	}
	/**
	 * �ܓx
	 * @return the _locationLatitude
	 */
	public double getLocationLatitude() {
		return _locationLatitude;
	}
	/**
	 * �o�x
	 * @param _locationLongitude the _locationLongitude to set
	 */
	public void setLocationLongitude(double _locationLongitude) {
		this._locationLongitude = _locationLongitude;
	}
	/**
	 * �o�x
	 * @return the _locationLongitude
	 */
	public double getLocationLongitude() {
		return _locationLongitude;
	}
	/**
	 * �W��
	 * @param _locationSeaLevel the _locationSeaLevel to set
	 */
	public void setLocationSeaLevel(double _locationSeaLevel) {
		this._locationSeaLevel = _locationSeaLevel;
	}
	/**
	 * �W��
	 * @return the _locationSeaLevel
	 */
	public double getLocationSeaLevel() {
		return _locationSeaLevel;
	}

	/**
	 * ����
	 * @param _sensorOrientation the _sensorOrientation to set
	 */
	public void setSensorOrientation(float _sensorOrientation) {
		this._sensorOrientation = _sensorOrientation;
	}
	/**
	 * ����
	 * @return the _sensorOrientation
	 */
	public float getSensorOrientation() {
		return _sensorOrientation;
	}
	/**
	 * �s�b�`
	 * @param _sensorOrientationPitch the _sensorOrientationPitch to set
	 */
	public void setSensorOrientationPitch(float _sensorOrientationPitch) {
		this._sensorOrientationPitch = _sensorOrientationPitch;
	}
	/**
	 * �s�b�`
	 * @return the _sensorOrientationPitch
	 */
	public float getSensorOrientationPitch() {
		return _sensorOrientationPitch;
	}
	/**
	 * ���[��
	 * @param _sensorOrientationRoll the _sensorOrientationRoll to set
	 */
	public void setSensorOrientationRoll(float _sensorOrientationRoll) {
		this._sensorOrientationRoll = _sensorOrientationRoll;
	}
	/**
	 * ���[��
	 * @return the _sensorOrientationRoll
	 */
	public float getSensorOrientationRoll() {
		return _sensorOrientationRoll;
	}

	
	/**
	 * �R���X�g���N�^
	 * @param context
	 */
	public IndicatorView(Context context) {
		super(context);
		
		_mineObject = new CharactorObject();
		
		_targetObjects = new ArrayList<CharactorObject>();
		
		CharactorObject target = new CharactorObject();
		target.setName("���N���N���");
		target.setLocationLatitude(35.1671122695654);
		target.setLocationLongitude(136.898309290409);
		target.setLocationSeaLevel(0.0);
		_targetObjects.add(target);
		
		target = new CharactorObject();
		target.setName("������_��");
		target.setLocationLatitude(36.2547510789974);
		target.setLocationLongitude(136.905784606934);
		target.setLocationSeaLevel(500.0);
		_targetObjects.add(target);
		
	}

	
	/**
	 * �`�揈��
	 */
	@Override
	protected void onDraw(Canvas canvas){
		Paint paint = new Paint();
		
		//���݈ʒu�X�V
		updateLocationInfo();
		
		//��
		paint.setColor(Color.argb(255, 128, 255, 227));
		paint.setStrokeWidth(3);

		//�e�L�X�g
		paint.setTextSize(NUM_DIRECTION_FONT_SIZE);

		
		//���S�i�c����]����̂Œ��Ӂj
		float center_x = this.getHeight() / 2;
		float center_y = this.getWidth() / 2;
		
		//32�{
		//360 / 32 = 11�x��1�{
		//RAD�ɂ���� 2PI = 360������
		//PI / 16 = dsi-ta �����i��
		
		//��ʂ��c�����ɉ�]
		canvas.rotate(-90);
		canvas.translate(-1 * this.getHeight(), 0);

		//���ʂ̃C���W�P�[�^
		drawOrientationIndicator(canvas, paint
							, center_x, center_y - center_y / 3
							, center_x * 3 / 4);
		
		//�s�b�`�̃C���W�P�[�^
		drawPitchIndicator(canvas, paint
							, center_x - center_x / 2, center_y
							, center_x / 2);
		
		//�^�[�Q�b�g
		for (CharactorObject target : _targetObjects) {
			drawTarge(canvas, paint	, target, _mineObject);
		}
		
	}


	/**
	 * ���ʂ̃C���W�P�[�^��`��
	 * @param canvas
	 * @param paint
	 */
	private void drawOrientationIndicator(Canvas canvas, Paint paint
										, float center_x, float center_y
										, float amplitude){
		
		double base_rad = Math.PI * getSensorOrientation() / 180;	//�`���͂��߂̃I�t�Z�b�g
//		double base_ori = Math.PI * 2 * getSensorOrientation() / 360;	//�`���͂��߂̃I�t�Z�b�g
//		double base_ori = Math.PI * 2 * 45 / 360;	//�`���͂��߂̃I�t�Z�b�g
		float x = 0;
		float y = 0;
		float h = 0;
		double now_rad = 0.0;
		String direction_name = "";
		
		for(int i=0; i<NUM_BAR_NUM; i++){
			//���݂̊p�x
			now_rad = NUM_DELTA * i - base_rad;
			
			//0�`360�x�̊Ԃɋ��Ȃ��Ƃ��͖߂�
			now_rad = adjustRadian(now_rad);
			
			if((now_rad) > (Math.PI / 2) && (now_rad) < (Math.PI * 3 / 2)){
				//90-270�x�̊Ԃ͕\�����Ȃ�
			}else{
				//���W�v�Z
				x = (float) (amplitude *  Math.sin(now_rad) + center_x);
				y = (float) (-1 * 20 * Math.cos(now_rad) + center_y);
				//������I��
				switch(i){
				case NUM_NORTH_COUNT:
					h = 20;
					direction_name = "N";
					break;
				case NUM_EAST_COUNT:
					h = 20;
					direction_name = "E";
					break;
				case NUM_SOUTH_COUNT:
					h = 20;
					direction_name = "S";
					break;
				case NUM_WEST_COUNT:
					h = 20;
					direction_name = "W";
					break;
				default:
					direction_name = "";
					h = 10;
					break;
				}
				//���ʂ��w�肳��Ă���ꍇ�͕`��
				if(direction_name.length() > 0){
					paint.setAntiAlias(true);
					canvas.drawText(direction_name, x - NUM_DIRECTION_FONT_SIZE_HALF
							, y - h - NUM_DIRECTION_FONT_SIZE_HALF
							, paint);
				}
				paint.setAntiAlias(false);
				canvas.drawLine(x, y - h,  x, y , paint);
			}
		}
	}
	
	/**
	 * �s�b�`�̃C���W�P�[�^
	 */
	private void drawPitchIndicator(Canvas canvas, Paint paint
									, float center_x, float center_y
									, float amplitude){
		double base_rad = Math.PI * (getSensorOrientationPitch() + 90) / 180;	//�`���͂��߂̃I�t�Z�b�g
//		double base_rad = Math.PI * 2 * (-90 + 90) / 360;	//�`���͂��߂̃I�t�Z�b�g
		float x = 0;
		float y = 0;
		float h = 10;
		double now_rad = 0.0;

		//���
		y = center_y;
		x = center_x;
		h = 20;
		canvas.drawLine(x - h, y, x, y , paint);
		
		//�X�̃�����
		for(int i=0; i<NUM_BAR_NUM; i++){
			//���݂̊p�x
			now_rad = NUM_DELTA * i - base_rad;
			
			//0�`360�x�̊Ԃɋ��Ȃ��Ƃ��͖߂�
			now_rad = adjustRadian(now_rad);
			
			if((now_rad) > (Math.PI / 2) && (now_rad) < (Math.PI * 3 / 2)){
				//90-270�x�̊Ԃ͕\�����Ȃ�
			}else{
				//���W�v�Z
				y = (float) (amplitude *  Math.sin(now_rad) + center_y);
				x = (float) (-1 * 20 * Math.cos(now_rad) + center_x);
				//������I��
				if(i == 0){
					h = 20;
					paint.setAntiAlias(true);
					canvas.drawText("0", x - h - 6, y - 6, paint);
				}else{
					h = 10;
				}
				paint.setAntiAlias(false);
				canvas.drawLine(x - h, y, x, y , paint);
			}
		}
	}
	
	
	/**
	 * �^�[�Q�b�g��`��
	 * @param canvas
	 * @param paint
	 * @param target
	 * @param me
	 */
	private void drawTarge(Canvas canvas, Paint paint
						, CharactorObject target, CharactorObject me){
		//�^�[�Q�b�g�Ƃ̋����Ɗp�x���v�Z
		double distance = target.calcDistance(me);					//����
		double angle = target.calcAngle(me);						//���ʊp
		double ele_angle = target.calcElevationAngle(me);			//�p
		double base_rad = Math.PI * getSensorOrientation() / 180;	//�`���͂��߂̃I�t�Z�b�g
		double base_ele_rad = Math.PI * (getSensorOrientationPitch() + 90) / 180;	//���݂̃s�b�`
		double in_degree = Math.PI / 12;

		//�A���O���𒲐�
		base_rad = adjustRadian2(base_rad);
		base_ele_rad = adjustRadian2(base_ele_rad);
		angle = adjustRadian2(angle);

		if((angle > (base_rad - in_degree) && angle < (base_rad + in_degree))
				&& (ele_angle > (base_ele_rad - in_degree) && (ele_angle < (base_ele_rad + in_degree)))){
			//����͈͓̔���������`��
			float x = (float) (NUM_DISTANCE_EYE_TO_DISPLAY * Math.tan(angle - base_rad) * 160 + this.getHeight() / 2);
			float y = (float) (NUM_DISTANCE_EYE_TO_DISPLAY * Math.tan(ele_angle - base_ele_rad) * 160 + this.getHeight() / 2);
//			float x = (float) (this.getHeight() / 2 + this.getHeight() * (angle - base_rad) / (in_degree * 2));
//			float y = (float) (this.getWidth() / 2 + this.getWidth() * (ele_angle - base_ele_rad) / (in_degree * 2));
			RectF r = new RectF(x - 5, y - 5
								, x + 5, y + 5);
			paint.setStyle(Paint.Style.STROKE);
			paint.setAntiAlias(false);
			canvas.drawRect(r, paint);
			
			//�������
			paint.setStyle(Paint.Style.FILL);
			paint.setAntiAlias(true);
			
			//���O
			String info = String.format("%s", target.getName());
			canvas.drawText(info, x + 5, y - NUM_DIRECTION_FONT_SIZE, paint);
			//����
			info = String.format(" %.1f(km)", distance);
			canvas.drawText(info, x + 5, y , paint);
			//���፷
			info = String.format(" %.1f(m)", target.getLocationSeaLevel() - me.getLocationSeaLevel());
			canvas.drawText(info, x + 5, y + NUM_DIRECTION_FONT_SIZE, paint);
		}else{
			//�͈͊O�Ȃ̂ŕ����w����`��
			
			//��ʊO�ɏ��������̍��W�i���̎��͌��_��0,0�j
			float x = (float) (NUM_DISTANCE_EYE_TO_DISPLAY * Math.tan(adjustRadian2(angle - base_rad)) * 160);
			float y = (float) (NUM_DISTANCE_EYE_TO_DISPLAY * Math.tan(ele_angle - base_ele_rad) * 160);
//			float x = (float) (this.getHeight() * (angle - base_rad) / (in_degree * 2));
//			float y = (float) (this.getWidth() * (ele_angle - base_ele_rad) / (in_degree * 2));
			
			if(adjustRadian2(angle - base_rad) > 0){
				//�E�ɂ���
				x = Math.abs(x);
			}else{
				//���ɂ���
				x = -1 * Math.abs(x);
			}

			//�O�p
			double mark_angle = Math.atan2(y, x);
			
			//�O�p�`�̍��W
			float tri_x = 0;
			float tri_y = 0;
			float apex = 20;
			tri_x = this.getHeight() / 2 - 25;
			apex = 20;

			float[] pts = {tri_x, tri_y - 8, tri_x, tri_y + 8
					, tri_x, tri_y + 8, tri_x + apex, tri_y
					, tri_x + apex, tri_y, tri_x, tri_y - 8};
			for(int i=0; i<pts.length; i+= 2){
				double temp_x = pts[i];
				double temp_y = pts[i+1];
				pts[i] = (float) (calcRotationX(temp_x, temp_y, mark_angle) + this.getHeight() / 2);
				pts[i+1] = (float) (calcRotationY(temp_x, temp_y, mark_angle) + this.getWidth() / 2);
			}

			//�O�p��`��
			paint.setStrokeCap(Paint.Cap.ROUND);
			canvas.drawLines(pts, paint);

//			String info = String.format("%.1f %.1f", adjustRadian2(angle - base_rad) * 180 / Math.PI
//													, mark_angle * 180 / Math.PI);
//			canvas.drawText(info, 10, this.getHeight() - 5, paint);
		}
	}
	
	
	/**
	 * 0�`360�x�̊Ԃ̒l�ɒ��߂���
	 * @param base
	 * @return
	 */
	public double adjustRadian(double now_rad){
		
		if(now_rad < 0){
			while(now_rad < 0){
				now_rad += Math.PI * 2;
			}
		}else if(now_rad > (Math.PI * 2)){
			while(now_rad > (Math.PI * 2)){
				now_rad -= Math.PI * 2;
			}
		}

		return now_rad;
	}

	/**
	 * -180�`180�x�̊Ԃɒ��߂���
	 * @param now_rad
	 * @return
	 */
	public double adjustRadian2(double now_rad){
		if(now_rad < (-1 * Math.PI)){
			while(now_rad < (-1 * Math.PI)){
				now_rad += Math.PI * 2;
			}
		}else if(now_rad > Math.PI){
			while(now_rad > Math.PI){
				now_rad -= Math.PI * 2;
			}
		}
		return now_rad;
	}
	
	/**
	 * ��]�������WX���v�Z����
	 * @param x ����X
	 * @param y�@����Y
	 * @param rad�@��]����p�x�i���W�A���j
	 * @return ��]�������X
	 */
	public double calcRotationX(double x, double y, double rad){
		double ret = 0.0;
		double alpha = Math.atan2(y, x);
		double r = Math.sqrt(x * x + y * y);
		
		ret = r * Math.cos(alpha + rad);
		
		return ret;
	}
	
	/**
	 * ��]�������WY���v�Z����
	 * @param x ����X
	 * @param y�@����Y
	 * @param rad�@��]����p�x�i���W�A���j
	 * @return ��]�������Y
	 */
	public double calcRotationY(double x, double y, double rad){
		double ret = 0.0;
		double alpha = Math.atan2(y, x);
		double r = Math.sqrt(x * x + y * y);
		
		ret = r * Math.sin(alpha + rad);
		
		return ret;
	}
	
	
	
	/**
	 * �ʒu�����e�I�u�W�F�N�g�ɔ��f����
	 */
	private void updateLocationInfo(){
		
		//�����̈ʒu���X�V
		if(_mineObject == null){
		}else{
			_mineObject.setLocationLatitude(getLocationLatitude());
			_mineObject.setLocationLongitude(getLocationLongitude());
			_mineObject.setLocationSeaLevel(getLocationSeaLevel());
		}
		
	}


}
