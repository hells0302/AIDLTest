// IBookManager.aidl
package com.study.ipctest_aidl;

// Declare any non-default types here with import statements
import com.study.ipctest_aidl.Book;
import com.study.ipctest_aidl.IOnNewBookArrivedListener;
interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
