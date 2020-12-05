package utility;

 

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

 

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

 

import Database.DatabaseConnection;
import OrdersAPI.Models.CtnMessage;

 

//Break up this class to the different classes as split below into OrdersUtility, CommonUtility(print, dateTime etc) and remove all EMS stuff once POM is implemented and incorporated
public class CommonUtility extends FileUtility
{

 

    public static String dateTodayFormatted()
    {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String datetoday = ft.format(dNow);
        return datetoday;
    }

 

    public static String dateToday()
    {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
        String datetoday = ft.format(dNow);
        return datetoday;
    }

 

    public static String dateYesterday()
    {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

 

        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
        String datetoday = ft.format(cal.getTime());

 

        return datetoday;
    }

 

    public static String dateTomorrow()
    {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +1);

 

        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
        String datetoday = ft.format(cal.getTime());

 

        return datetoday;
    }

 

    public String uniqueIndex()
    {

 

        String dategenerated = null;
        try
        {
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
            // SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd
            // HH:mm:ss");

 

            dategenerated = ft.format(dNow);
            dategenerated = dategenerated.replace(":", "");
            dategenerated = dategenerated.replace("-", "");

 

            return dategenerated;
        }
        catch (Exception e)
        {

 

        }
        return dategenerated;

 

    }

 

    public static String uniqueIndexDateSecnoTrim()
    {
        String dateGenerated = null;
        try
        {
            Thread.sleep(1000);
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateGenerated = ft.format(dNow);
            return dateGenerated;
        }
        catch (Exception e)
        {
        }
        return dateGenerated;
    }

 

    public static String uniqueIndexDateSecnoTrimOneHourLess()
    {
        String dategenerated = null;
        try
        {
            Calendar cal = Calendar.getInstance();
            // remove next line if you're always using the current time.
            cal.setTime(new Date());
            cal.add(Calendar.HOUR, -1);
            Date oneHourBack = cal.getTime();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dategenerated = ft.format(oneHourBack);
            return dategenerated;
        }
        catch (Exception e)
        {

 

        }
        return dategenerated;
    }

 

    public static <T> void printMessage(T message)
    {
        System.out.println(message);
    }

 

    public static void threadSleepNormal()
    {
        try
        {
            Thread.sleep(threadSleepNormal);
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

 

    public static void threadSleepShort()
    {
        try
        {
            Thread.sleep(threadSleepShort);
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

 

    public ObjectMapper createObjectMapper()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

 

        return objectMapper;
    }

 

    public ObjectMapper createObjectMapperforXml()
    {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper objectMapper = new XmlMapper(module);

 

        return objectMapper;
    }

 

    public String trimString(String value)
    {
        try
        {
            return value.substring(0, 4);
        }
        catch (Exception e)
        {
            printMessage(e.getMessage());
        }
        return null;
    }

 

    public CtnMessage getCtnMessage(DatabaseConnection connection, String query, int expectedCount)
    {
        CtnMessage ctnMessage = null;
        ObjectMapper objectMapper = createObjectMapperforXml();
        try
        {
            System.out.println(query);
            ResultSet rSet = connection.getResultSetWithRetry(query, expectedCount);

 

            while (rSet.next())
            {
                String message = rSet.getString("CtnMessage");
                ctnMessage = objectMapper.readValue(message, CtnMessage.class);
            }
        }
        catch (SQLException | IOException e)
        {
            printMessage(e.getMessage());
        }
        finally
        {

 

        }
        return ctnMessage;
    }

 

    @SuppressWarnings("hiding")
    public static class Pair<Integer, ResultSet> extends java.util.AbstractMap.SimpleImmutableEntry<Integer, ResultSet>
    {
        private static final long serialVersionUID = 1L;

 

        public Pair(Integer a, ResultSet b)
        {
            super(a, b);
        }

 

        public Integer getCount()
        {
            return getKey();
        }

 

        public ResultSet getResultSet()
        {
            return getValue();
        }

 

        public String toString()
        {
            return "[" + getKey() + "," + getValue() + "]";
        }

 

    }
}