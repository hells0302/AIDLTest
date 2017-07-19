package com.study.ipctest_aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
//    private static final String TAG="MainActivity";
//    private IBookManager mRemoteBookManager;
//    private Handler mHandler=new Handler()
//    {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what)
//            {
//                case 1:
//                    Log.i(TAG,"receive new book :"+msg.obj);
//                    break;
//                default:
//                    super.handleMessage(msg);
//            }
//
//        }
//    };
//    private ServiceConnection connection=new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            IBookManager bookManager=IBookManager.Stub.asInterface(service);
//            try
//            {
//                mRemoteBookManager=bookManager;
//                List<Book> list=bookManager.getBookList();
//                //Log.i(TAG,"query book list,list type:"+list.getClass().getCanonicalName());
//                Log.i(TAG,"query book list:"+list.toString());
//                Book newBook=new Book(4,"Android艺术探索");
//                bookManager.addBook(newBook);
//                Log.i(TAG,"add book:"+newBook);
//                List<Book> newList=bookManager.getBookList();
//                Log.i(TAG,"query book list:"+newList.toString());
//                bookManager.registerListener(mOnNewBookArrivedListener);
//            }catch(RemoteException e)
//            {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            mRemoteBookManager=null;
//            Log.e(TAG,"binder died");
//        }
//    };
//    private IOnNewBookArrivedListener mOnNewBookArrivedListener=new IOnNewBookArrivedListener.Stub() {
//        @Override
//        public void onNewBookArrived(Book newBook) throws RemoteException {
//            mHandler.obtainMessage(1,newBook).sendToTarget();
//        }
//    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  Intent intent=new Intent(this,BookManagerService.class);
      //  bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {

//        if(mRemoteBookManager!=null&&mRemoteBookManager.asBinder().isBinderAlive())
//        {
//            try
//            {
//                Log.i(TAG,"unregister listener:"+mOnNewBookArrivedListener);
//                mRemoteBookManager.unregisterListener(mOnNewBookArrivedListener);
//            }catch(RemoteException e)
//            {
//                e.printStackTrace();
//            }
//        }
//        unbindService(connection);
        super.onDestroy();
    }
}
