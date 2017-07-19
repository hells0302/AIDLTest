// IOnNewBookArrivedListener.aidl
package com.study.ipctest_aidl;

// Declare any non-default types here with import statements
import com.study.ipctest_aidl.Book;
interface IOnNewBookArrivedListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onNewBookArrived(in Book newBook);
}
