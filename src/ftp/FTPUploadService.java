package ftp;

import android.R.integer;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;

public class FTPUploadService extends Service{
	//FTP������  
    private FTPUtils ftpUtils = null;
    String localFileName = "";
    String remoteFileName = "";
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		System.out.println("service is on!!!");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		this.localFileName = intent.getStringExtra("localfilename");
		this.remoteFileName = intent.getStringExtra("remotefilename");
		UploadFileThread uploadFileThread = new UploadFileThread();
		new Thread(uploadFileThread).start();
		return super.onStartCommand(intent, flags, startId);
	}
	
	public void InitFTPServerSetting() {  
        // TODO Auto-generated method stub  
        ftpUtils = FTPUtils.getInstance();  
        boolean flag = ftpUtils.initFTPSetting(FTPConfig.FTPSERVER, FTPConfig.FTPPORT, FTPConfig.FTPUSERNAME, FTPConfig.FTPPASSWORD); 
        if (flag) {
			System.out.println("ftp login success");
		}else{
			System.out.println("ftp login failed");
		}
    }  
	
	class UploadFileThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			InitFTPServerSetting();
			ftpUtils.uploadFile(localFileName, remoteFileName);
			System.out.println("ftp upload finish");
		}
		
	}

}
