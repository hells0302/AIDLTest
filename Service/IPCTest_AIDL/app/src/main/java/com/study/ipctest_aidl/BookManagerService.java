package com.study.ipctest_aidl;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by dnw on 2017/7/17.
 */

public class BookManagerService extends Service {
    private CopyOnWriteArrayList<Book> mBookList=new CopyOnWriteArrayList<Book>();
    private AtomicBoolean mIsServiceDestoryed=new AtomicBoolean(false);
    private RemoteCallbackList<IOnNewBookArrivedListener> mListener=new RemoteCallbackList<IOnNewBookArrivedListener>();
    private Binder mBinder=new IBookManager.Stub()
    {
        @Override
        public IBinder asBinder() {
            return super.asBinder();
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            return super.onTransact(code, data, reply, flags);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
           mListener.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListener.unregister(listener);
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1,"Android"));
        mBookList.add(new Book(2,"IOS"));
        mBookList.add(new Book(3,"Java"));
        new Thread(new ServiceWorker()).start();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed.set(true);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        int check=checkCallingOrSelfPermission("com.study.ipctest_aidl.ACCESS_BOOK_SERVICE");
        if(check== PackageManager.PERMISSION_DENIED)
        {
            return null;
        }
        return mBinder;
    }
    public void onNewBookArrived(Book book) throws RemoteException
    {
        mBookList.add(book);
        final int N=mListener.beginBroadcast();
        for(int i=0;i<N;i++)
        {
            IOnNewBookArrivedListener listener=mListener.getBroadcastItem(i);
          if(listener!=null)
          {
              try
              {
                  listener.onNewBookArrived(book);
              }catch(RemoteException e)
              {
                  e.printStackTrace();
              }
          }

        }
        mListener.finishBroadcast();
    }

    private class ServiceWorker implements Runnable
    {
        @Override
        public void run() {
            while(!mIsServiceDestoryed.get())
            {
                try
                {
                    Thread.sleep(5000);
                }catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                int bookId=mBookList.size()+1;
                Book newBook=new Book(bookId,"new book#"+bookId);
                try
                {
                    onNewBookArrived(newBook);
                }catch(RemoteException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
