package com.study.ipctest_aidl1;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.study.ipctest_aidl.Book;
import com.study.ipctest_aidl.IBookManager;
import com.study.ipctest_aidl.IOnNewBookArrivedListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

        private static final String TAG="MainActivity";
    private IBookManager mRemoteBookManager;
    private Handler mHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 1:
                    Log.i(TAG,"receive new book :"+msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    };
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager bookManager=IBookManager.Stub.asInterface(service);
            try
            {
                mRemoteBookManager=bookManager;
                List<Book> list=bookManager.getBookList();
                //Log.i(TAG,"query book list,list type:"+list.getClass().getCanonicalName());
                Log.i(TAG,"query book list:"+list.toString());
                Book newBook=new Book(4,"Android艺术探索");
                bookManager.addBook(newBook);
                Log.i(TAG,"add book:"+newBook);
                List<Book> newList=bookManager.getBookList();
                Log.i(TAG,"query book list:"+newList.toString());
                bookManager.registerListener(mOnNewBookArrivedListener);
            }catch(RemoteException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteBookManager=null;
            Log.e(TAG,"binder died");
        }
    };
    private IOnNewBookArrivedListener mOnNewBookArrivedListener=new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            mHandler.obtainMessage(1,newBook).sendToTarget();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //解决隐式调用service方法一
//        final Intent intent=new Intent();
//        intent.setAction("com.study.ipctest_aidl.BookManagerService");
//        final Intent intent1=new Intent(createExplicitFromImplicitIntent(MainActivity.this,intent));
//        bindService(intent1,connection,BIND_AUTO_CREATE);
        //解决隐式调用service方法二
        Intent intent=new Intent("com.study.ipctest_aidl.BookManagerService");
        intent.setPackage("com.study.ipctest_aidl");
        bindService(intent,connection,BIND_AUTO_CREATE);
    }
    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }
}
