package database;

 

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

 

import utility.CommonUtility;
import utility.Constants;

 

public class DatabaseConnection extends CommonUtility
{
    private String database = "CTN_Message";
    private String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private String connectionString = "jdbc:sqlserver://" + Constants.dbEnv + ";database=" + database;
    private static Connection connection = null;

 

    public DatabaseConnection()
    {
        getConnection();
    }

 

    public void getConnection()
    {
        try
        {
            Class.forName(driver);
            connection = DriverManager.getConnection(connectionString, Constants.dbUn, Constants.dbP);
        }
        catch (Exception e)
        {
            printMessage(e.getMessage());
        }

 

    }

 

    public int getCountWithRetry(String query, int expectedCount)
    {
        printMessage(query);
        ResultSet resultSet = null;
        int count = 0;
        try
        {
            for (int i = 0; i < 60; i++)
            {
                if (count != expectedCount)
                {
                    count = getCount(query);
                }
                else
                {
                    break;
                }
                threadSleepShort();
            }
            return count;
        }
        catch (Exception e)
        {
            printMessage(e.getMessage());
        }
        return count;
    }
    
    public void executeQuery(String query)
    {
        printMessage(query);
        try
        {
            Statement sta = connection.createStatement();
            int code = sta.executeUpdate(query);
            System.out.println(code);
        }
        catch (Exception e)
        {
            printMessage(e.getMessage());
        }
    }

 

    public ResultSet getResultSetWithRetry(String query, int expectedCount)
    {
        printMessage(query);
        ResultSet resultSet = null;
        int count = 0;
        try
        {
            Statement statement = connection.createStatement();
            for (int i = 0; i < 60; i++)
            {
                if (count != expectedCount)
                {
                    count = getCount(query);
                }
                else
                {
                    break;
                }
                threadSleepShort();
            }
            resultSet = statement.executeQuery(query);
            return resultSet;
        }
        catch (SQLException e)
        {
            printMessage(e.getMessage());
        }
        return resultSet;
    }

 

    public String getSingleColumnWithRetry(String query, int expectedCount)
    {
        printMessage(query);
        ResultSet resultSet = null;
        int count = 0;
        String result = null;
        try
        {
            Statement statement = connection.createStatement();
            for (int i = 0; i < 60; i++)
            {
                if (count != expectedCount)
                {
                    count = getCount(query);
                }
                else
                {
                    break;
                }
                threadSleepShort();
            }
            resultSet = statement.executeQuery(query);
            resultSet.next();
            result = resultSet.getString(1);
        }
        catch (Exception e)
        {
            printMessage(e.getMessage());
        }
        printMessage(result);
        return result;
    }

 

    public int getCount(String sql)
    {
        int count = 0;
        try
        {
            getConnection();
            ResultSet resultSet = null;
            Statement sta = connection.createStatement();
            resultSet = sta.executeQuery(sql);
            while (resultSet.next())
            {
                count++;
            }
        }
        catch (Exception e)
        {
            printMessage(e.getMessage());
        }
        return count;
    }

 

    public Pair<Integer, ResultSet> retryDatabase(int expectedCount, String query)
            throws InterruptedException,
            ClassNotFoundException,
            IOException,
            SQLException
    {
        DatabaseConnection database = new DatabaseConnection();
        Pair<Integer, ResultSet> pair = null;
        int count = 0;
        for (int i = 0; i < 10; i++)
        {
            if (count != expectedCount)
            {
                pair = database.getResultSetWithCount(query);
                count = pair.getCount();
            }
            else
            {
                return pair;
            }
        }
        return pair;
    }

 

    public Pair<Integer, ResultSet> getResultSetWithCount(String sql)
            throws ClassNotFoundException,
            IOException,
            SQLException,
            InterruptedException
    {
        Pair<Integer, ResultSet> pair;
        ResultSet rs = null;
        Statement sta = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        while (connection == null || connection.isClosed())
        {
        }
        rs = sta.executeQuery(sql);
        int count = 0;
        try
        {
            while (rs.next())
            {
                count++;
            }
            rs.beforeFirst();
            while (rs.next())
            {
            }
            rs.beforeFirst();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

 

        pair = new Pair<Integer, ResultSet>(count, rs);
        return pair;
    }

 

    public static void closeDatabaseConnection()
    {
        try
        {
            if (connection != null)
            {
                connection.close();
                connection = null;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}