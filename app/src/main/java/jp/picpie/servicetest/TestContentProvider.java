package jp.picpie.servicetest;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.collection.SimpleArrayMap;

public class TestContentProvider extends ContentProvider {
    public TestContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        //throw new UnsupportedOperationException("Not yet implemented");

        Cursor cur = new MyCursor();
        return cur;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

class MyCursor extends BaseCursor {
    String[] columnnames = {"_id","Float1", "String2", "Blob3", "Null4"};
    int[] columntypes = {Cursor.FIELD_TYPE_INTEGER,Cursor.FIELD_TYPE_FLOAT, Cursor.FIELD_TYPE_STRING, Cursor.FIELD_TYPE_BLOB, Cursor.FIELD_TYPE_NULL};

    public MyCursor(){
        setColumnNames(columnnames);
        setColumnTypes(columntypes);
        //ToDo: データベース読み出しとか

    }

    @Override
    public long getLong(int columnIndex) {
        long rtv = getPosition()+100;
        Log.d("CP", "getLong columnIndex("+Integer.toString(columnIndex)+"): "+String.valueOf(rtv));
        return rtv;
    }

}

class BaseCursor  implements Cursor {
    int count = 3;
    int pos = -1;
    String[] columnNames;
    int[] colmnTypes;

    void setColumnNames(String[] _columnnames){
        Log.d("CP", "setColumnNames _columnnames="+_columnnames.toString());
        columnNames = _columnnames;
    }

    void setColumnTypes(int[] _colmntypes){
        Log.d("CP", "setColumnTypes _colmntypes="+_colmntypes.toString());
        colmnTypes = _colmntypes;
    }

    void setCount(int _count){
        count = _count;
    }

    @Override
    public int getType(int columnIndex) {
        Log.d("CP", "getType columnIndex="+Integer.toString(columnIndex));
        if( columnIndex < colmnTypes.length ){
            return colmnTypes[columnIndex];
        }
        return Cursor.FIELD_TYPE_NULL;
    }

    @Override
    public int getCount() {
        Log.d("CP", "getCount count="+Integer.toString(count));
        return count;
    }

    @Override
    public int getPosition() {
        Log.d("CP", "getPosition pos="+Integer.toString(pos));
        return pos;
    }

    @Override
    public boolean move(int offset) {
        Log.d("CP", "move offset="+Integer.toString(offset));
        return true;
    }

    @Override
    public boolean moveToPosition(int position) {
        Log.d("CP", "moveToPosition position="+Integer.toString(position));
        pos = position;
        return true;
    }

    @Override
    public boolean moveToFirst() {
        Log.d("CP", "moveToFirst");
        pos = -1 ;
        return true;
    }

    @Override
    public boolean moveToLast() {
        Log.d("CP", "moveToFirst");
        return false;
    }

    @Override
    public boolean moveToNext() {
        Log.d("CP", "moveToNext pos="+Integer.toString(pos));
        if( pos <= count ) {
            pos = pos + 1;
            return true;
        }
        return false;
    }

    @Override
    public boolean moveToPrevious() {
        Log.d("CP", "moveToPrevious pos="+Integer.toString(pos));
        return false;
    }

    @Override
    public boolean isFirst() {
        Log.d("CP", "isFirst pos="+Integer.toString(pos));
        return true;
    }

    @Override
    public boolean isLast() {
        Log.d("CP", "isLast pos="+Integer.toString(pos));
        return false;
    }

    @Override
    public boolean isBeforeFirst() {
        Log.d("CP", "isBeforeFirst pos="+Integer.toString(pos));
        return false;
    }

    @Override
    public boolean isAfterLast() {
        Log.d("CP", "isAfterLast pos="+Integer.toString(pos));
        return false;
    }

    @Override
    public int getColumnIndex(String columnName) {
        Log.d("CP", "getColumnIndex columnName="+columnName);
        for( int i=0 ; i < columnNames.length; ++i ){
            if( columnNames[i].compareTo(columnName)==0){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
        Log.d("CP", "getColumnIndexOrThrow columnName="+columnName);
        int inx = getColumnIndex(columnName);
        if( inx != -1){
            return inx;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String getColumnName(int columnIndex) {
        Log.d("CP", "getColumnName columnIndex="+Integer.toString(columnIndex));
        return columnNames[columnIndex];
    }

    @Override
    public String[] getColumnNames() {
        Log.d("CP", "getColumnNames");
        return columnNames;
    }

    @Override
    public int getColumnCount() {
        Log.d("CP", "getColumnCount");
        return columnNames.length;
    }

    @Override
    public byte[] getBlob(int columnIndex) {
        Log.d("CP", "getBlob columnIndex="+Integer.toString(columnIndex));
        byte[] rtv = new byte[16];
        int f=1;
        rtv[0] = 0; rtv[1] = 1;
        for( int n=2; n < rtv.length; ++n){
            rtv[n] = (byte)(rtv[n-2]+rtv[n-1]);
        }
        return rtv;
    }

    @Override
    public String getString(int columnIndex) {
        String rtv = columnNames[columnIndex]+" "+Integer.toString(pos)+" value";
        Log.d("CP", "getString columnIndex="+Integer.toString(columnIndex)+" "+rtv);
        return rtv;
    }

    @Override
    public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {
        Log.d("CP", "copyStringToBuffer columnIndex="+Integer.toString(columnIndex));
    }

    @Override
    public short getShort(int columnIndex) {
        Log.d("CP", "getShort columnIndex="+Integer.toString(columnIndex));
        return 0;
    }

    @Override
    public int getInt(int columnIndex) {
        int rtv = (int)getLong( columnIndex );
        Log.d("CP", "getInt columnIndex="+Integer.toString(columnIndex)+" "+String.valueOf(rtv));
        return  rtv;
    }

    @Override
    public long getLong(int columnIndex) {
        long rtv = 0;
        if( columnIndex==0){
            rtv = pos;
        }
        Log.d("CP", "getLong columnIndex="+Integer.toString(columnIndex)+" "+String.valueOf(rtv));
        return rtv;
    }

    @Override
    public float getFloat(int columnIndex) {
        float rtv = 3.14f;
        Log.d("CP", "getFloat columnIndex="+Integer.toString(columnIndex)+" "+String.valueOf(rtv));
        return rtv;
    }

    @Override
    public double getDouble(int columnIndex) {
        double rtv = 3.14159f;
        Log.d("CP", "getDouble columnIndex="+Integer.toString(columnIndex)+" "+String.valueOf(rtv));
        return rtv;
    }

    @Override
    public boolean isNull(int columnIndex) {
        Log.d("CP", "isNull columnIndex="+Integer.toString(columnIndex));
        return false;
    }

    @Override
    public void deactivate() {
        Log.d("CP", "deactivate");
    }

    @Override
    public boolean requery() {
        Log.d("CP", "requery");
        return false;
    }

    @Override
    public void close() {
        Log.d("CP", "close");
    }

    @Override
    public boolean isClosed() {
        Log.d("CP", "isClosed");
        return false;
    }

    @Override
    public void registerContentObserver(ContentObserver observer) {
        Log.d("CP", "registerContentObserver");
    }

    @Override
    public void unregisterContentObserver(ContentObserver observer) {
        Log.d("CP", "unregisterContentObserver");
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        Log.d("CP", "registerDataSetObserver");
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        Log.d("CP", "unregisterDataSetObserver");
    }

    @Override
    public void setNotificationUri(ContentResolver cr, Uri uri) {
        Log.d("CP", "setNotificationUri");
    }

    @Override
    public Uri getNotificationUri() {
        Log.d("CP", "getNotificationUri");
        return null;
    }

    @Override
    public boolean getWantsAllOnMoveCalls() {
        Log.d("CP", "getWantsAllOnMoveCalls");
        return false;
    }

    @Override
    public void setExtras(Bundle extras) {
        Log.d("CP", "setExtras");
    }

    @Override
    public Bundle getExtras() {
        Log.d("CP", "getExtras");
        return null;
    }

    @Override
    public Bundle respond(Bundle extras) {
        Log.d("CP", "respond");
        return null;
    }
}