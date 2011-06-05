package jp.xii.relog.customlibrary.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

public class OriginalView extends View {

	public OriginalView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * �F����ϊ�����
	 * @return
	 */
	protected int ColorToInt(String temp){
		int ret = 0;
		
		if(temp.indexOf("@color/") == 0){
			int id = getContext().getResources().getIdentifier(temp.replace("@color/",""), "color", getContext().getPackageName());
			ret = getContext().getResources().getColor(id);
		}else{
			ret = Color.parseColor(temp);
		}

		return ret;
	}
	
	/**
	 * dip�l�����W�l�ɕϊ�����
	 * @param temp
	 * @return
	 */
	protected int DtoInt(String temp){
		int ret = 0;
		float density = getContext().getResources().getDisplayMetrics().density;
		
		if(temp.indexOf("dip") >= 0){
			temp = temp.replace("dip", "");
			ret = (int) (Float.valueOf(temp) * density);
		}else if(temp.indexOf("dp") >= 0){
			temp = temp.replace("dp", "");
			ret = (int) (Float.valueOf(temp) * density);
		}else if(temp.indexOf("sp") >= 0){
			temp = temp.replace("sp", "");
			ret = (int) (Float.valueOf(temp) * density);
		}else{
			try{
				ret = Integer.valueOf(temp);
			}catch(Exception e){
			}
		}

		return ret;
	}
	
	/**
	 * dip�l�����W�l�ɕϊ�����
	 * @param temp
	 * @return
	 */
	protected int DtoInt(int temp){
		return (int) (temp * getContext().getResources().getDisplayMetrics().density);
	}

	
}
