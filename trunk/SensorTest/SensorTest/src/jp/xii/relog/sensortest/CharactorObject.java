package jp.xii.relog.sensortest;

/**
 * �\���I�u�W�F�N�g�̊��N���X
 * @author Iori
 *
 */
public class CharactorObject {

	static final double NUM_1DEGREE_KM_ON_YOKO = 90.163292;		//1�x�̋���(km)�ԓ�����
	static final double NUM_1DEGREE_KM_ON_TATE = 110.968304;	//1�x�̋���(km)�q�ߐ�����i�c�����j
	
	//�̏��
	private String _name = "";					//���O
	
	//�ʒu���
	private double _locationLatitude = 0.0;		//�ܓx
	private double _locationLongitude = 0.0;	//�o�x
	private double _locationSeaLevel = 0.0;		//�W��
	
	
	
	/**
	 * ���O
	 * @param _name the _name to set
	 */
	public void setName(String _name) {
		this._name = _name;
	}
	/**
	 * ���O
	 * @return the _name
	 */
	public String getName() {
		return _name;
	}
	
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
	 * ���̃I�u�W�F�N�g�Ǝw�肵���I�u�W�F�N�g�̋������v�Z����
	 * @param src ��r�Ώ�
	 * @return ����(km)
	 */
	public double calcDistance(CharactorObject src){
		double ret = 0.0;
		
		if(src == null){
			//NG
		}else{
			double d_lati = getLocationLatitude() - src.getLocationLatitude();
			double d_long = getLocationLongitude() - src.getLocationLongitude();
			
			ret = Math.sqrt(Math.pow(d_lati * NUM_1DEGREE_KM_ON_TATE, 2)
							+ Math.pow(d_long * NUM_1DEGREE_KM_ON_YOKO, 2));
		}
		
		return ret;
	}

	/**
	 * ���̃I�u�W�F�N�g�Ǝw�肵���I�u�W�F�N�g�̖k��0�x�Ƃ����p�x���v�Z����
	 * @param src ��r�Ώہi��j
	 * @return �p�x�i���W�A���j
	 */
	public double calcAngle(CharactorObject src){
		double ret = 0.0;
		
		if(src == null){
			//NG
		}else{
			double d_lati = getLocationLatitude() - src.getLocationLatitude();
			double d_long = getLocationLongitude() - src.getLocationLongitude();
			
			ret = Math.atan2(d_long, d_lati);
		}
		
		return ret;
	}

	/**
	 * ���̃I�u�W�F�N�g�Ǝw�肵���I�u�W�F�N�g�̋p���v�Z����
	 * @param src ��r�Ώہi��j
	 * @return �p�x�i���W�A���j
	 */
	public double calcElevationAngle(CharactorObject src){
		double ret = 0.0;
		
		if(src == null){
			//NG
		}else{
			double d_sea = getLocationSeaLevel() - src.getLocationSeaLevel();
			d_sea *= 0.001;		//m��km�ɒ���
			
			ret = -1 * Math.atan2(d_sea, calcDistance(src));
		}
		
		return ret;
	}
}
