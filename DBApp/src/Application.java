import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import cache.DataSourceProxy;
import cache.handler.CacheHandler;
import cache.handler.CacheListener;
import datasource.DBSource;
import db.Database;
import db.element.Row;
import db.element.Table;
import db.element.column.ColumnData;
import db.element.column.IntData;
import db.element.column.NumberData;
import db.element.column.StringData;
import db.element.utils.RowUtils;
import db.exception.MissingPKsException;
import db.instance.generic.wrapper.TableWrapper;
import db.instance.specific.sqlite.SQLiteDatabase;
import db.instance.specific.sqlite.column.SQLiteStringData;

/**
 * @author Daniel J. Rivers
 *         Software Engineer
 *         Quanterion Solutions Incorporated
 *         2013
 *
 * Created: Sep 18, 2013, 10:51:58 AM 
 */
public class Application {
	
	public static void main( String[] args ) {
		createTables();
		pullTables();
		basicAppTest();
	}
	
	public static void basicAppTest() {
		try {
			Connection c = Database.getInstance().getDatabaseWrapper().getConnection();
    		DataSourceProxy proxy = DataSourceProxy.getInstance();
    		( (DBSource)proxy.getDataSource() ).getScanner().startPolling( 5000 );
    		CacheListener l = new CacheListener() {
    			@Override
    			public void created( Row r ) {
    				System.out.println( "Created: " + r );
    			}
    
    			@Override
    			public void deleted( Row r ) {
    				System.out.println( "Deleted: " + r );
    			}
    
    			@Override
    			public void updated( Row r ) {
    				System.out.println(  "Updated: " + r );
    			}
    		};
    		proxy.getHandler( "TestTable" ).addListener( l );
    		Vector<ColumnData> row = new Vector<ColumnData>();
    		row.add( new StringData( "TEST_STRING", "T" ) );
    		row.add( new NumberData( "TEST_NUM", 1111.1111f ) );
    		row.add( new IntData( "TEST_INT", 7 ) );
    		row.add( new SQLiteStringData( "TIMESTAMP", new Timestamp( System.currentTimeMillis() + 600000 ) ) );
    		TableWrapper.insertRow( new Row( "TestTable", row ), c );
    		row = new Vector<ColumnData>();
    		row.add( new StringData( "TEST_STRING", "THIS IS A TEST" ) );
			row.add( new NumberData( "TEST_NUM", 1.234f ) );
			row.add( new IntData( "TEST_INT", 999 ) );
			row.add( new SQLiteStringData( "TIMESTAMP", new Timestamp( System.currentTimeMillis() ) ) );
			TableWrapper.updateRow( proxy.getCache().getTableDefinition( "TestTable" ), new Row( row ), c );
    		try {
    			System.err.println( "Sleeping for 5 sec" );
				Thread.sleep( 5000 );
			} catch ( InterruptedException e ) {
				e.printStackTrace();
			}
    		System.err.println( "Wake up" );
//    		TableWrapper.deleteRow(  proxy.getCache().getTableDefinition( "TestTable" ), new Row( row ), c );
    		CacheHandler h = proxy.getCache().getHandler( "TestTable" );
    		
    		//test serialization
    		byte[] rb = RowUtils.serialize( new Row( row ) );
    		Row serial = RowUtils.deserialize( rb );
    		System.err.println( serial.toString() );
    		
    		System.err.println( "GETTING ROW FROM DB" );
    		System.err.println( ( (DBSource)proxy.getDataSource() ).get( proxy.getCache().getTableDefinition( "TestTable" ), new Row( row ) ) );
    		
    		System.err.println( "GETTING ROW FROM HANDLER" );
    		System.err.println( h.getRow( row ) );
    		
    		
    		System.err.println( "GETTING ALL ROWS WITH 7 FROM HANDLER" );
    		Vector<ColumnData> r2 = new Vector<ColumnData>();
    		r2.add( new IntData( "TEST_INT", 7 ) );
    		for ( Row rz : h.getRows( r2 ) ) {
    			System.err.println( rz );
    		}
    		
    		//test delete
//    		for ( Row r : h.getAllRows() ) {
//    			h.delete( r );
//    		}
			c.close();
		} catch ( ClassNotFoundException | SQLException | MissingPKsException e ) {
			e.printStackTrace();
		}
	}
	
	public static void pullTables() {
		DataSourceProxy proxy = DataSourceProxy.getInstance();
		proxy.setDataSource( new DBSource() );
		DBSource db = (DBSource)( proxy.getDataSource() );
		db.createDatabase( new SQLiteDatabase() );
		db.getDatabase().getDatabaseWrapper().setName( "test.db" );
		db.startScanner( true );
//		proxy.getCache().printCache();
	}
	
	public static void createTables() {
		try {
			Database.create( new SQLiteDatabase() ).getDatabaseWrapper().setName( "test.db" );
			Connection c = Database.getInstance().getDatabaseWrapper().getConnection();
			Vector<ColumnData> def = new Vector<ColumnData>();
			Table t = new Table( "TestTable", new ColumnData[] { new StringData( "TEST_STRING" ), new NumberData( "TEST_NUM" ), new IntData( "TEST_INT" ), new StringData( "TIMESTAMP" ) } );
			t.setPKs( new String[] { "TEST_STRING", "TEST_NUM" } );
			TableWrapper.create( t, true, c );
			def.clear();
			def.add( new StringData( "TEST_STRING", "THIS IS A TEST" ) );
			def.add( new NumberData( "TEST_NUM", 1.234f ) );
			def.add( new IntData( "TEST_INT" ) );
			def.add( new SQLiteStringData( "TIMESTAMP", new Timestamp( System.currentTimeMillis() ) ) );
			TableWrapper.insertRow( t, new Row( def ), c );
			def.clear();
			def.add( new StringData( "TEST_STRING", "THIS IS A TEST2" ) );
			def.add( new NumberData( "TEST_NUM", 4.321f ) );
			def.add( new IntData( "TEST_INT", 5 ) );
			def.add( new SQLiteStringData( "TIMESTAMP", new Timestamp( System.currentTimeMillis() ) ) );
			TableWrapper.insertRow( t, new Row( def ), c );
			for ( Row r : TableWrapper.getAllRows( t, c ) ) {
				System.out.println( r.toString() );
			}
			c.close();
		} catch ( ClassNotFoundException | SQLException e ) {
			e.printStackTrace();
		}
	}
}