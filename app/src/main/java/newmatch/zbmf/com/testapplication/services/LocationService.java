package newmatch.zbmf.com.testapplication.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class LocationService extends Service {

    private static final int RECEIVE_MESSAGE_CODE = 0x0001;
    private static final int SEND_MESSAGE_CODE = 0x0002;

    private Timer timer;
    private MyTask myTask;
    private Messenger locationMessenger;
    private Messenger messenger = new Messenger(new LocationHandler());

    public LocationService() {
    }

    @SuppressLint("HandlerLeak")
    private class LocationHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == RECEIVE_MESSAGE_CODE) {
                locationMessenger = msg.replyTo;
//                tgxg661API = (TGXG661API) msg.obj;
//                if (tgxg661API != null) {
//                    timer.schedule(myTask, 1000, 1000);
//                }
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        timer = new Timer();
        myTask = new MyTask();
        //客户端可以通过调用这个方法获取到DevService的Binder
        return messenger.getBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("===TAG===", " LocationService 的 onDestroy  ");
        releaseTimerTask();
    }

    private void releaseTimerTask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (myTask != null) {
            myTask.cancel();
            myTask = null;
        }
    }

    private class MyTask extends TimerTask {

        @Override
        public void run() {


//            //如果设备开启，获取设备当前的状态
//            int devStatus = tgxg661API.TGGetDevStatus();
//            Log.d("===TAG===", "  DevService 获取设备状态  :"+devStatus);
//            Message devServiceMessage = Message.obtain();
//            devServiceMessage.what = SEND_MESSAGE_CODE;
//            if (devStatus >= 0) {
//                //设备已经连接
//                devServiceMessage.arg1 = 0;
//            } else {
//                writeCMD();
//                //设备未连接
//                devServiceMessage.arg1 = -2;
//            }
//            if (tg661JMessennger != null) {
//                try {
//                    tg661JMessennger.send(devServiceMessage);
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                    Log.d("===TAG===", "  DevService 向客户端发送信息失败！");
//                }
//            }
        }
    }


}
