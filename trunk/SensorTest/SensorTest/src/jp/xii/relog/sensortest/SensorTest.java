package jp.xii.relog.sensortest;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SensorTest extends Activity 
	implements LocationListener, SensorEventListener{
	
	private TickHandler _tickHandler;

	//�J����
	private Camera _cameraPreview;
	
	//�`��n
	private IndicatorView _indicatorView = null;
	private SurfaceView _surfaceView = null;

	//�ʒu���֘A
	private LocationManager _locationManger = null;
	private double _locationLatitude = 0.0;		//�ܓx
	private double _locationLongitude = 0.0;	//�o�x
	private double _locationAltitude = 0.0;		//���x
	
	//�Z���T�[�֌W
	private SensorManager _sensorManager = null;
	private float _sensorOrientation = 0;
	private float _sensorOrientationPitch = 0;
	private float _sensorOrientationRoll = 0;
//	private float _sensorMagneticX = 0;
//	private float _sensorMagneticY = 0;
//	private float _sensorMagneticZ = 0;
	
	
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //�t���X�N���[��
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //�^�C�g���o�[��\��
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //�r���[���m��
        setContentView(R.layout.main);
        
        
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.MainView);
        
        //�J����
        _surfaceView = new SurfaceView(this);
        //�T�[�t�F�XHolder�ɃR�[���o�b�N�ƃ^�C�v���w��
        SurfaceHolder holder = _surfaceView.getHolder();
        holder.addCallback(_SurfaceHolderCallback);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        
        
        //���C�A�E�g�ɃC���W�P�[�^��\������r���[��ǉ�����
        _indicatorView = new IndicatorView(this);
        _indicatorView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT
        														, RelativeLayout.LayoutParams.FILL_PARENT));
        layout.addView(_indicatorView,0);
        layout.addView(_surfaceView,0);
        
        //�Z���T�[
        _locationManger = (LocationManager)getSystemService(LOCATION_SERVICE);
        _sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
    }
    
    
    //�A�v���̍ĊJ
    @Override
    public void onResume(){
    	super.onResume();
    	
    	//�ʒu���̃v���o�C�_�擾
    	String provider = _locationManger.getBestProvider(new Criteria(), true);
    	if(provider == null){
    		//�Ȃɂ��Ȃ�
    	}else{
    		_locationManger.requestLocationUpdates(provider, 0, 0,  this);
    		//�Ō�Ɏ擾�����ʒu�����擾
    		Location location = _locationManger.getLastKnownLocation(provider);
    		if(location != null){
    			onLocationChanged(location);
    		}
    	}
    	
    	//���ʃZ���T�[
    	List<Sensor> list = _sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
    	if(list == null){
    	}else if(list.size() < 1){
    	}else{
    		//���ʃZ���T�[�擾
    		Sensor sensor = list.get(0);
    		//���X�i�[�o�^
    		_sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    	}
    	
    	//�n���C�Z���T�[
    	list = _sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
    	if(list == null){
    	}else if(list.size() < 1){
    	}else{
    		//���C�Z���T�[�擾
    		Sensor sensor = list.get(0);
    		//���X�i�[�o�^
    		_sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    	}
    	
    	//��������n���h��
    	_tickHandler = new TickHandler();
    	_tickHandler.sleep(0);
    }
    
    //�A�v���̈ꎞ��~
    @Override
    public void onPause(){
    	super.onPause();
    	
    	_tickHandler = null;
    	
    	//���X�i������
    	_sensorManager.unregisterListener(this);
    	_locationManger.removeUpdates(this);
    }
    
    
    //��������n���h��
    public class TickHandler extends Handler{
    	//�������
    	@Override
    	public void handleMessage(Message msg){
    		//�ĕ`��
    		_indicatorView.invalidate();
    		updateView();
    		
    		if(_tickHandler != null){
    			_tickHandler.sleep(100);
    		}
    	}
    	
    	//�X���[�v
    	public void sleep(long delayMills){
    		removeMessages(0);
    		sendMessageDelayed(obtainMessage(0), delayMills);
    	}
    }

    /**
     * �J�����̃v���r���[�֘A�̃R�[���o�b�N
     */
    private SurfaceHolder.Callback _SurfaceHolderCallback = new SurfaceHolder.Callback() {
		
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			//�J�����[�r���[���~
			_cameraPreview.stopPreview();
			//�J�������J��
			_cameraPreview.release();
			_cameraPreview = null;
			
		}
		
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			//�J�������N�����܂�
			_cameraPreview = Camera.open();
			try{
				_cameraPreview.setPreviewDisplay(holder);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			//�J�����̃v���r���[�J�n
			_cameraPreview.startPreview();
		}
	};
    
    
    
    /**
     * �\�����e���X�V
     */
    private void updateView(){
    	TextView text = null;
    	
    	//�ʒu
    	text = (TextView)findViewById(R.id.LocationInfo);
    	text.setText("La : " + _locationLatitude 
    			+ " , Lo : " + _locationLongitude
    			+ " , Al : " + _locationAltitude);
    	
    	//����
    	text = (TextView)findViewById(R.id.OrientationInfo);
    	text.setText("Ori : " + _sensorOrientation
    				+ "\nPitch : " + _sensorOrientationPitch
    				+ "\nRoll :  " + _sensorOrientationRoll);
    	
    	//�n���C
//    	text = (TextView)findViewById(R.id.MagneticInfo);
//    	double L = Math.sqrt(_sensorMagneticX * _sensorMagneticX + _sensorMagneticY * _sensorMagneticY);	//�Ε�L
//    	text.setText("X : " + _sensorMagneticX + "\nY : " + _sensorMagneticY + "\nZ : " + _sensorMagneticZ
//    				+ "\natan(X/Y) : " + Math.atan2(_sensorMagneticX, _sensorMagneticY) * 180 / Math.PI
//    				+ "\natan(Z/L) : " + Math.atan2(_sensorMagneticZ, L) * 180 / Math.PI);
    }

    /**
     * �ʒu���ς������
     */
	@Override
	public void onLocationChanged(Location location) {
		_locationLatitude = location.getLatitude();		//�ܓx
		_locationLongitude = location.getLongitude();	//�o�x
		_locationAltitude = location.getAltitude();		//���x
		_indicatorView.setLocationLatitude(_locationLatitude);
		_indicatorView.setLocationLongitude(_locationLongitude);
		_indicatorView.setLocationSeaLevel(_locationAltitude);
	}

	/**
	 * �v���o�C�_�������ɂȂ�����
	 */
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * �v���o�C�_���L���ɂȂ�����
	 */
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * �v���o�C�_�̏�Ԃ��ω�������Ăяo�����
	 */
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}


	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * �Z���T�[�̒l���ω�������
	 */
	@Override
	public void onSensorChanged(SensorEvent event) {
		if(event.sensor.getType() == Sensor.TYPE_ORIENTATION){
			//���ʎ擾
			_sensorOrientation = event.values[0];
			_sensorOrientationPitch = event.values[1];
			_sensorOrientationRoll = event.values[2];
			_indicatorView.setSensorOrientation(_sensorOrientation);
			_indicatorView.setSensorOrientationPitch(_sensorOrientationPitch);
			_indicatorView.setSensorOrientationRoll(_sensorOrientationRoll);
		}else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
			//�n���C
//			_sensorMagneticX = event.values[0];
//			_sensorMagneticY = event.values[1];
//			_sensorMagneticZ = event.values[2];
		}
	}

}