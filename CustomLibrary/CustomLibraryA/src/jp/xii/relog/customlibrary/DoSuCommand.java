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
package jp.xii.relog.customlibrary;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.apache.http.protocol.HTTP;

public class DoSuCommand {

	Process _process = null;				//su�v���Z�X
	DataOutputStream _outputStream = null;	//�o�̓X�g���[��
	DataInputStream _inputStream = null;	//���̓X�g���[��
	
	
	public boolean init(){
		boolean ret = false;
		
		try {
			//su���s
			_process = Runtime.getRuntime().exec("su");
			
			//���o�̓X�g���[���擾
			_outputStream = new DataOutputStream(_process.getOutputStream());
			_inputStream = new DataInputStream(_process.getInputStream());
			
			//�o�[�W�������擾����
			ret = true;
//			if(!write("su -v\n")){
//			}else{
//				String[] results = read().split("\n");
//				for (String line : results) {
//					if(line.length() > 0){
//						//�o�[�W�������Ƃꂽ�̂Ő���
//						ret = true;
//					}
//				}
//			}
		} catch (IOException e) {
		}
		
		return ret;
	}
	
	public void deinit(){
		if(_inputStream != null){
			try {
				_inputStream.close();
			} catch (IOException e) {
			}
		}
		if(_outputStream != null){
			try {
				if(_process != null){
					//�v���Z�X�Əo�̓X�g���[��������ꍇ��
					//�V�F�����I������
					_outputStream.writeBytes("exit\n");
					_outputStream.flush();
					try {
						//�V�F���̏I����҂�
						_process.waitFor();
					} catch (InterruptedException e) {
					}
				}
				_outputStream.close();
			} catch (IOException e) {
			}
		}
		
		if(_process != null){
			_process.destroy();
		}
		
		_outputStream = null;
		_inputStream = null;
		_process = null;
	}
	
	/**
	 * �R�}���h��su�V�F���֓�����
	 * �Ō��\n��t����܂Ŏ��s����Ȃ�
	 * @param command
	 */
	public boolean write(String command){
		boolean ret = false;
		if(_outputStream == null){
		}else{
			try {
				_outputStream.writeBytes(command);
				_outputStream.flush();
				ret = true;
			} catch (IOException e) {
			}
		}
		return ret;
	}
	
	/**
	 * su�V�F���̏o�͂𕶎���ɂ���
	 * ���Ȃ炸���ʂ��߂��Ă��鎞�Ɏg��
	 * ����ȊO�Ŏg���Ƃ��ǂ��Ă��Ȃ��Ȃ�̂Œ���
	 * ������񌋉ʂ͕W���o�͂ŃG���[�o�͂̓_��
	 * �R�}���h�̌��ʂ������s�ɂȂ�ꍇ��
	 * split�Ȃǂ��g���ăo�����Ďg��
	 * @param timeout
	 * @return
	 */
	public String read(){//int timeout){
		String ret = "";
		
		if(_inputStream == null){
		}else{
			int size = 0;
			byte[] buffer = new byte[1024];
//			long start_time = System.currentTimeMillis();
			
			try {
				do {
					size = _inputStream.read(buffer);
					if(size > 0){
						ret += new String(buffer, 0, size, HTTP.UTF_8);
					}
				}while(_inputStream.available() > 0);
//				do{
//					while(_inputStream.available() > 0){
//						size = _inputStream.read(buffer);
//						if(size > 0){
//							ret += new String(buffer, 0, size, HTTP.UTF_8);
//						}
//					}
//
//					//�Ȃɂ��擾�ł��Ă���I��
//					if(ret.length() > 0){
//						break;
//					}else{
//						try {
//							Thread.sleep(10);
//						} catch (InterruptedException e) {
//						}
//					}
//					
//					//�^�C���A�E�g���Ԃ����ĂȂ���ΌJ��Ԃ�
//				}while((System.currentTimeMillis() - start_time) < timeout);
			} catch (IOException e) {
			}
		}

		return ret;
	}
}
