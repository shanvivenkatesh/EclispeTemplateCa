package com.Database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.Tests.App;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

public class Database {private final int retryCount=30;
private final int threadWaitTime=5000;
private final int threadWaitOnce=10000;
private final int MAX_WAIT_TIME_IN_SECONDS=30;



public static Connection GetConnection(String databasename) throws SQLSyntaxErrorException,SQLServerException
{
    SQLServerDataSource ds = new SQLServerDataSource();
    ds.setUser(App.dbUn);
    ds.setPassword(App.dbP);
    ds.setServerName(App.dbEnv);
    ds.setDatabaseName(databasename);
    Connection dbConnection = ds.getConnection(); 
    return dbConnection;
}



public String sqlExecuteQuery(String database, String sQL, String columnName)
        throws ClassNotFoundException, IOException, SQLException,SQLServerException, InterruptedException {
    Connection conn = null;
    ResultSet rs = null;
    String result=null;
    conn = GetConnection(database);
    Statement sta = conn.createStatement();
    App.logger.info(sQL);
    rs = sta.executeQuery(sQL);
    try
    {
        if(!rs.next())       
        {    
            synchronized (sta) 
            {
                sta.wait(threadWaitOnce);
                sta.notify();
            }
        }
        result=rs.getString(columnName);
        App.logger.info(result);
    }
    catch(Exception e)
    {App.logger.info(e.getMessage());}
    finally {
        Closedbconnection(rs);
    }



    return result;
}



/*
 * This method will only return the first select attribute from the SQL query as the method name suggests
 */
public String getSingleColumnWithRetryCount(String database, String sQL)
        throws ClassNotFoundException, IOException, SQLException,SQLServerException, InterruptedException {
    Connection conn = null;
    ResultSet rs = null;
    String result=null;
    conn = GetConnection(database);
    Statement statement = conn.createStatement();
    App.logger.info(sQL);
    rs = statement.executeQuery(sQL);
    try
    {
        long maxWaitTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(MAX_WAIT_TIME_IN_SECONDS);
        long currentTime = System.currentTimeMillis();
        while(currentTime <= maxWaitTime && !rs.next())
        {
            rs = statement.executeQuery(sQL);
            currentTime = System.currentTimeMillis();
        }
        
        result=rs.getString(1);
        App.logger.info(result);
    }
    catch(Exception e)
    {System.out.println(e.getMessage());}
    finally {
        Closedbconnection(statement);
    }



    return result;
}



public ArrayList<String> getMultipleColumnsWithRetryCount(String database, String sQL)
        throws ClassNotFoundException, IOException, SQLException,SQLServerException, InterruptedException {
    Connection conn = null;
    ResultSet rs = null;



    conn = GetConnection(database);
    Statement statement = conn.createStatement();
    App.logger.info(sQL);
    ArrayList<String> results= new ArrayList<>();
    rs = statement.executeQuery(sQL);
    try
    {
        long maxWaitTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(MAX_WAIT_TIME_IN_SECONDS);
        long currentTime = System.currentTimeMillis();
        while(currentTime <= maxWaitTime && !rs.next())
        {
            rs = statement.executeQuery(sQL);
            currentTime = System.currentTimeMillis();
        }



        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();



        for(int i=0;i<columnsNumber;i++)
        {
            App.logger.info(rs.getString(i+1));
            results.add(rs.getString(i+1));
        }



    }
    catch(Exception e)
    {System.out.println(e.getMessage());}
    finally {
        Closedbconnection(statement);
    }



    return results;
}
public void Closedbconnection(ResultSet rsToClose) throws SQLException
{    
    Statement statementToClose=rsToClose.getStatement();
    Connection connetionToClose=statementToClose.getConnection();
    rsToClose.close();
    statementToClose.close();
    connetionToClose.close();
}



public void Closedbconnection(Statement stmtToClose) throws SQLException
{   
    try 
    {
        Connection connetionToClose=stmtToClose.getConnection();



        stmtToClose.close();
        connetionToClose.close();
    }
    catch(Exception e )
    {
        App.logger.info("problem with closing database connection - "+e);
    }
}



public int runSqlScript(String database, String filepath)
// it should be RunSqlScript changed to check in preprod
throws ClassNotFoundException, IOException, SQLException {
    String aSQLScriptFilePath = filepath;
    Connection con = GetConnection(database);
    Statement stmt = null;
    int result=0;



    try {
        stmt = con.createStatement();
        App.logger.info("Running script ---" + aSQLScriptFilePath);
        // Give the input file to Reader
        BufferedReader in = new BufferedReader(new FileReader(aSQLScriptFilePath));
        String str;
        StringBuffer sb = new StringBuffer();
        while ((str = in.readLine()) != null) {
            sb.append(str+"\n");
        }
        in.close();
        System.out.println(sb.toString());
        result = stmt.executeUpdate(sb.toString());



    } catch (Exception e) {
        System.err
        .println("Failed to Execute" + aSQLScriptFilePath + " The error is " + e.getMessage());
    }
    finally 
    {
        Closedbconnection(stmt);
    }
    return result;
}




public int getDatabaseResultsCountWithRetry(String database, String sql, int expectedCount)
        throws ClassNotFoundException, IOException, SQLException, InterruptedException 
{
    Connection conn = null;
    ResultSet rs = null;
    Statement statement = null;
    int count = 0;



    try 
    {
        conn = GetConnection(database);



        App.logger.info(sql);
        statement = conn.createStatement();
        for(int i=0;i<retryCount;i++)
        {
            rs = statement.executeQuery(sql);
            count=0;



            while (rs.next()) 
            {
                count++;
            }
            if(count==expectedCount){break;}
            Thread.sleep(threadWaitTime);
        }



    } catch (Exception e) 
    {
        App.logger.info(e.getMessage());
    }
    finally 
    {
        Closedbconnection(statement);
    }
    return count;
}



public void executeUpdate(String database, String sql)
        throws ClassNotFoundException, IOException, SQLException, InterruptedException {
    Connection conn = null;
    conn = GetConnection(database);
    App.logger.info("Update query execution "+sql);
    Statement sta = conn.createStatement();
    sta.executeUpdate(sql);
    Closedbconnection(sta);
}



}
