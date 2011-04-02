package jp.xii.relog.customlibrary.mediastore;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

public class MediaStoreAccess {

	public static final String DEBUG_TAG_NAME = "MediaStoreAccess";
	
	private static final String VOLUME_NAME_INTERNAL = "internal";
	private static final String VOLUME_NAME_EXTERNAL = "external";

	public static final String PHONE_GALAXY_S = "GT-I9000";
	
	/**
	 * �d�b�̎�ށi�@��ˑ������p���j
	 * @author Iori
	 *
	 */
	enum PhoneType{
		Standard		//�W���[��
		, GalaxyS		//����炭���[
	}
	
	/**
	 * �Ώۃ��f�B�A
	 * @author Iori
	 *
	 */
	public enum StoragePlaceType{
		Internal
		,External
	}
	
	/**
	 * �Ώۏ��
	 * @author Iori
	 *
	 */
	public enum InfomationType{
		Media
		, Album
		, Artist
		, Genre
		, Playlist
		, PlaylistMember
	}
	

	private Activity _activityParent = null;
	private PhoneType _phoneType = PhoneType.Standard;	//�@��ˑ����
	
	private boolean _isDebuging = false;				//�f�o�b�O���邩
	private ArrayList<String> _debugLog = null;						//�f�o�b�O���O
	
	/**
	 * �@��ˑ����
	 * @return
	 */
	protected PhoneType getPhoneType(){
		return _phoneType;
	}
	
	public MediaStoreAccess(Activity activity){
		_activityParent = activity;
		
		checkPhoneType();
	}

	/**
	 * �@��^�C�v�����肷��
	 */
	public void checkPhoneType(){
		
		//�Ƃ肠�����v���C���X�g���擾���Ă݂Ĕ��f����
		PhoneType[] types = {PhoneType.GalaxyS
							, PhoneType.Standard
							};
		Cursor cursor = null;
		for (PhoneType type : types) {
			_phoneType = type;
	        cursor = getMediaStoreInfo(StoragePlaceType.External
					, InfomationType.Playlist
					, null, null, null, null);
			if(cursor == null){
			}else{
				//OK�Ȃ̂ŏI��
				break;
			}
		}

//		if(Build.DEVICE.compareTo(PHONE_GALAXY_S) == 0){
//			_phoneType = PhoneType.GalaxyS;
//		}else{
//			_phoneType = PhoneType.Standard;
//		}

		if(isDebuging()){
	    	Log("//-- Device info --");
			Log( " BOARD:" + Build.BOARD);
			Log( " BRAND:" + Build.BRAND);
			
			Log( " CPU_ABI:" + Build.CPU_ABI);
			Log( " DEVICE:" + Build.DEVICE);
			Log( " DISPLAY:" + Build.DISPLAY);
			Log( " FINGERPRINT:" + Build.FINGERPRINT);
			Log( " HOST:" + Build.HOST);
			Log( " ID:" + Build.ID);
			Log( " MANUFACTURER:" + Build.MANUFACTURER);
			Log( " MODEL:" + Build.MODEL);
			Log( " PRODUCT:" + Build.PRODUCT);
			Log( " TAGS:" + Build.TAGS);
			Log( " TYPE:" + Build.TYPE);
			Log( " USER:" + Build.USER);
			Log( " TIME:" + Build.TIME);
			Log( " CODENAME:" + Build.VERSION.CODENAME);
			Log( " INCREMENTAL:" + Build.VERSION.INCREMENTAL);
			Log( " RELEASE:" + Build.VERSION.RELEASE);
			Log( " SDK:" + Build.VERSION.SDK);
			Log( " SDK_INT:" + Build.VERSION.SDK_INT);
		}
	}
	
	/**
	 * �f�o�b�O���邩
	 * @param _isDebuging the _isDebuging to set
	 */
	public void setIsDebuging(boolean _isDebuging) {
		this._isDebuging = _isDebuging;
	}
	/**
	 * �f�o�b�O���邩
	 * @return the _isDebuging
	 */
	public boolean isDebuging() {
		return _isDebuging;
	}

	/**
	 * �f�o�b�O���O
	 * @return the _debugLog
	 */
	public ArrayList<String> getDebugLog() {
		if(_debugLog == null){
			_debugLog = new ArrayList<String>();
		}
		return _debugLog;
	}
	public String getDebugLogAll(){
		String log = "";
		for (String item : getDebugLog()) {
			log += item + "\n";
		}
		return log;
	}
	
	

	/**
	 * ���f�B�A�����J�[�\���Ŏ󂯎��
	 * @param place �Ώۂ̕ۑ����f�B�A
	 * @param info �擾������
	 * @param projection �擾����J����
	 * @param selection �����Ώ�
	 * @param selectionArgs ���������̒l
	 * @param sortOrder �\�[�g����
	 * @return
	 */
	public Cursor getMediaStoreInfo(StoragePlaceType place
									, InfomationType info
									, String[] projection
									, String selection
									, String[] selectionArgs
									, String sortOrder){
		Cursor cursor = null;
		Uri uri = null;
		
		switch(info){
		default:
		case Media:
			uri = getMediaContentsUri(place);
			break;
		case Album:
			uri = getAlbumContentsUri(place);
			break;
		case Artist:
			uri = getArtistContentsUri(place);
			break;
		case Genre:
			uri = getGenreContentsUri(place);
			break;
		case Playlist:
			uri = getPlaylistContentsUri(place);
			break;
		case PlaylistMember:
			uri = null;
			break;
		}
		if(uri == null){
			Log("uri:null");
		}else{
//			Log( "uri:" + uri.toString());
		}
		
		//�擾
		if(_activityParent != null){
			try{
				cursor = _activityParent.managedQuery(uri, projection, selection
													, selectionArgs, sortOrder);
			}catch(Exception e){
	        	Log("managedQuery : " + e.getMessage());
			}
		}else{
        	Log("_activityParent == null");
		}
		
		return cursor;
	}


	/**
	 * �v���C���X�g�̃����o�[���擾
	 * @param place
	 * @param playlist_id
	 * @return
	 */
	public Cursor getPlaylistMembers(StoragePlaceType place
									, int playlist_id
									, String[] projection
									, String selection
									, String[] selectionArgs
									, String sortOrder){

		Cursor cursor = null;
		Uri uri = null;

		//URI
		uri = getPlaylistsMembersContentsUri(place, playlist_id);	//�W��
		
		//�擾
		if(_activityParent != null){
			try{
				cursor = _activityParent.managedQuery(uri, projection, selection, selectionArgs, sortOrder);
			}catch(Exception e){
			}
		}
		
		return cursor;
	}

	/**
	 * ���f�B�A�p��URI���擾����
	 * @param place
	 * @return
	 */
	protected Uri getMediaContentsUri(StoragePlaceType place){
		Uri uri = null;
		if(place == StoragePlaceType.Internal){
			uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
		}else{
			uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		}
		return uri;
	}
	/**
	 * �A���o����URI���擾����
	 * @param place
	 * @return
	 */
	protected Uri getAlbumContentsUri(StoragePlaceType place){
		Uri uri = null;
		if(place == StoragePlaceType.Internal){
			uri = MediaStore.Audio.Albums.INTERNAL_CONTENT_URI;
		}else{
			uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
		}
		return uri;
	}
	
	/**
	 * �A�[�e�B�X�g��URI���擾����
	 * @param place
	 * @return
	 */
	protected Uri getArtistContentsUri(StoragePlaceType place){
		Uri uri = null;
		if(place == StoragePlaceType.Internal){
			uri = MediaStore.Audio.Artists.INTERNAL_CONTENT_URI;
		}else{
			uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
		}
		return uri;
	}
	/**
	 * �W��������URI���擾����
	 * @param place
	 * @return
	 */
	protected Uri getGenreContentsUri(StoragePlaceType place){
		Uri uri = null;
		if(place == StoragePlaceType.Internal){
			uri = MediaStore.Audio.Genres.INTERNAL_CONTENT_URI;
		}else{
			uri = MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI;
		}
		return uri;
	}
	
	/**
	 * �v���C���X�g��URI���擾����
	 * @param place
	 * @return
	 */
	protected Uri getPlaylistContentsUri(StoragePlaceType place){
		Uri uri = null;
		
		switch(_phoneType){
		default:
		case Standard:
			//�W���[��
			if(place == StoragePlaceType.Internal){
				uri = MediaStore.Audio.Playlists.INTERNAL_CONTENT_URI;
			}else{
				uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
			}
			break;
		case GalaxyS:
			//�M�����N�V�[
			String volumeName = "";
			if(place == StoragePlaceType.Internal){
				volumeName = VOLUME_NAME_INTERNAL;
			}else{
				volumeName = VOLUME_NAME_EXTERNAL;
			}
			uri = Uri.parse("content://media/" + volumeName + "/audio/music_playlists");
			break;
		}

		return uri;
	}
	
	/**
	 * �v���C���X�g���擾���鎞��URI���擾����
	 * @param volumeName
	 * @param genreId
	 * @return
	 */
	protected Uri getPlaylistsMembersContentsUri(StoragePlaceType place, int genreId){
		Uri uri = null;
		String volumeName = "";
		
		if(place == StoragePlaceType.Internal){
			volumeName = VOLUME_NAME_INTERNAL;
		}else{
			volumeName = VOLUME_NAME_EXTERNAL;
		}
		
		switch(_phoneType){
		default:
		case Standard:
			//�W���[��
			uri = MediaStore.Audio.Playlists.Members.getContentUri(volumeName, genreId);
			break;
		case GalaxyS:
			//�M�����N�V�[
			uri = Uri.parse("content://media/" + volumeName + "/audio/music_playlists/" + genreId + "/members");
			break;
		}
		return uri;
	}


	/**
	 * ���O���o��
	 * @param message
	 */
	public void Log(String message){
		if(message == null){
		}else if(message.length() == 0){
		}else{
			Log.d(DEBUG_TAG_NAME, message);
			
			if(isDebuging()){
				getDebugLog().add(message);
				if(getDebugLog().size() > 400){
					getDebugLog().remove(0);
				}
			}
		}
	}
}
