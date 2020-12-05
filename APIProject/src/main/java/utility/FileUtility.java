import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

 

import org.apache.commons.io.FileUtils;

 

import Database.DatabaseConnection;
import OrdersAPI.Models.OrderResponse;

 

public class FileUtility extends Constants
{

 

    public OrderResponse replaceRelatedReferenceInCTNMessage(OrderResponse orderResponse, String filePath)
    {
        String messageGuid = orderResponse.getId().replaceAll("-", "");
        ArrayList<String> tagNames = new ArrayList<String>(Arrays.asList("CLIENTRELMSGID"));
        ArrayList<String> tagValues = new ArrayList<String>(Arrays.asList(messageGuid));
        File file = new File(filePath);
        // replace xml tag in IM with this value
        try
        {
            replaceTagValues(file, tagNames, tagValues, 1);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

 

        return orderResponse;
    }

 

    public void replaceDealReferenceInCTNMessage(String orderReference, String filePath)
    {
        ArrayList<String> tagNames = new ArrayList<String>(Arrays.asList("DealRef"));
        ArrayList<String> tagValues = new ArrayList<String>(Arrays.asList(orderReference));
        File file = new File(filePath);
        // replace xml tag in IM with this value
        try
        {
            replaceTagValues(file, tagNames, tagValues, 1);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void replaceSwitchLegIdInCTNMessage(String orderReference, String filePath)
    {
        ArrayList<String> tagNames = new ArrayList<String>(Arrays.asList("OrdrRef"));
        ArrayList<String> redemptionLegValues = new ArrayList<String>(Arrays.asList(orderReference+"_R"));
        ArrayList<String> subscriptionLegValues = new ArrayList<String>(Arrays.asList(orderReference+"_S"));
        File file = new File(filePath);
        try
        {
            replaceTagValues(file, tagNames, redemptionLegValues, 2);
            replaceTagValues(file, tagNames, subscriptionLegValues, 3);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

 

    public void replaceOrderReferenceInCTNMessage(String orderReference, String filePath)
    {
        ArrayList<String> tagNames = new ArrayList<String>(Arrays.asList("OrdrRef"));
        ArrayList<String> tagValues = new ArrayList<String>(Arrays.asList(orderReference));
        File file = new File(filePath);
        // replace xml tag in IM with this value
        try
        {
            replaceTagValues(file, tagNames, tagValues, 1);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

 

    public void copyFileToDirectory(String filePath, String directory)
    {
        try
        {
            File uniqueFile = new File(makeFileNameUnique(filePath));
            FileUtils.copyFile(new File(filePath), uniqueFile);
            FileUtils.copyFileToDirectory(uniqueFile, new File(routerFolderPath)); 
            while(!uniqueFile.delete());
            CommonUtility.printMessage("Copied file from: " + filePath + " to " + routerFolderPath);
            CommonUtility.threadSleepShort();
        }
        catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

 

    private String makeFileNameUnique(String fileName)
    {
        String uniqueFileName = "";
        int indexOfExtension = fileName.lastIndexOf(".");
        String extension = fileName.substring(indexOfExtension, fileName.length());
        uniqueFileName = fileName.substring(0, indexOfExtension)
                + CommonUtility.uniqueIndexDateSecnoTrim().replaceAll("\\p{Punct}", "") + extension;

 

        return uniqueFileName;
    }

 

    public String replaceTagValues(File file, ArrayList<String> tagNames, ArrayList<String> tagValues, int nthOccurence)
            throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;

 

        while ((line = reader.readLine()) != null)
        {
            stringBuffer.append(line + "\r\n");
        }
        String content = stringBuffer.toString();
        for (int i = 0; i < tagNames.size(); i++)
        {
            Pattern p = Pattern.compile("<" + tagNames.get(i) + ">(.*?)</" + tagNames.get(i) + ">", Pattern.DOTALL
                    | Pattern.CASE_INSENSITIVE);

 

            Matcher m = p.matcher(stringBuffer.toString());
            int count = 0;
            while (m.find())
            {
                count+=1;
                if(count == nthOccurence)
                {
                    int start = m.start();
                    int end = m.end();
                    String match = stringBuffer.toString().substring(start, end);
                    content = content.replace(match, "<" + tagNames.get(i) + ">"+tagValues.get(i)+"</" + tagNames.get(i) + ">");
                }
            }
        }
        reader.close();
        try
        {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
            fw.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return content;
    }

 

    public boolean changeFlushIntervalInMessageAdapter()
    {
        boolean found = false;
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(messageAdapterConfigFilePAth));
            StringBuilder builder = new StringBuilder();
            String line = null;

 

            while ((line = reader.readLine()) != null)
            {
                builder.append(line + "\r\n");
            }
            String content = builder.toString();

 

            Pattern p = Pattern.compile("flushInterval=\"(.*?)\"", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(builder.toString());
            if (m.find())
            {
                found = true;
                content = p.matcher(content).replaceAll("flushInterval=\"00:00:01\"");
            }

 

            reader.close();

 

            FileWriter fw = new FileWriter(new File(messageAdapterConfigFilePAth));
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
            fw.close();
        }
        catch (Exception e)
        {
            CommonUtility.printMessage("Error in changing Flush interval");
            CommonUtility.printMessage(e.getMessage());
        }
        return found;
    }
    public boolean addAdapterToUatAuto(String adapterName)
    {
        boolean found = false;
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(processControllerConfigFilePAth));
            StringBuilder builder = new StringBuilder();
            String line = null;

 

            String adapters = "";
            while ((line = reader.readLine()) != null)
            {
                if (line.contains("<add name=\"UAT-Auto\">"))
                {
                    while ((line = reader.readLine()) != null)
                    {
                        adapters = adapters + line;
                        if (line.contains("</processes>"))
                        {
                            break;
                        }
                    }
                    break;
                }
                else
                {
                    builder.append(line + "\r\n");
                }
            }
            reader.close();
            if (adapters.contains(adapterName))
            {
                return false;
            }
            else
            {
                line = null;
                builder = new StringBuilder();
                reader = new BufferedReader(new FileReader(processControllerConfigFilePAth));
                while ((line = reader.readLine()) != null)
                {
                    if (line.contains("<add name=\"UAT-Auto\">"))
                    {
                        builder.append(line + "\r\n");
                        line = reader.readLine();
                        builder.append(line + "\r\n");
                        builder.append("<add name=\"" + adapterName + "\" />" + "\r\n");
                    }
                    else
                    {
                        builder.append(line + "\r\n");
                    }
                }
                String content = builder.toString();

 

                FileWriter fw = new FileWriter(new File(processControllerConfigFilePAth));
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                fw.close();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return found;
    }

 

    public boolean removeAdapterFromProcessController(String adapterName)
    {
        boolean found = false;
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(processControllerConfigFilePAth));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                if (!line.contains(adapterName))
                {
                    builder.append(line + "\r\n");
                }
            }
            reader.close();
            String content = builder.toString();

 

            FileWriter fw = new FileWriter(new File(processControllerConfigFilePAth));
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
            fw.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return found;
    }
    
    private void replaceFileBySpecifyingRowNumber(String fileName, int row, String reference) throws IOException
    {
        File fileToModify = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(fileToModify));
        ArrayList<String> rowAndColumns = new ArrayList<>();

 

        String fileLine = null;
        while((fileLine =reader.readLine())!=null)
        {
            rowAndColumns.add(fileLine);
        }        
        reader.close();

 

        String existingLine = rowAndColumns.get(row-1);
        String updatedLine = existingLine.replaceFirst("\\d{4}", reference);
        rowAndColumns.set(row - 1, updatedLine);

 

        StringBuilder stringBuilder = new StringBuilder();     
        for(String singleRow : rowAndColumns)
        {
            stringBuilder.append(singleRow);
            stringBuilder.append("\r\n");
        }

 

        try 
        {
            FileWriter fw = new FileWriter(fileToModify);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(stringBuilder.toString());
            bw.close();
            fw.close();
        } 
        catch (Exception e) 
        {
            CommonUtility.printMessage(e.getMessage());
        }
    }
    
    public String getFileContentWithoutChanging(String file) 
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while((line =reader.readLine())!=null)
            {
                stringBuilder.append(line+"\r\n");
            }
            String content=stringBuilder.toString();    
            reader.close();
            
            stringBuilder= new StringBuilder();
            stringBuilder.append(content);
            int lastIndex=stringBuilder.lastIndexOf("\r\n");
            content=stringBuilder.replace(lastIndex, lastIndex+"\r\n".length(), "").toString();
            
            return content;
        }
        catch (Exception e) 
        {
            CommonUtility.printMessage(e.getMessage());
        }
        return null;
    }
    public void updateDataFilesWithClientDetails(String ordersV2Directory) throws Exception
    {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String getCtnIdQuery = "SELECT ClientId FROM [CTN_Report].[dbo].[vwClient] where Name like '%"+Constants.fundManager1ClientCode+"%'";
        String ctnId = databaseConnection.getSingleColumnWithRetry(getCtnIdQuery, 1);
        if(ctnId == null)
        {
            throw new Exception();
        }
        else
        {
            ArrayList<String> tagNames = new ArrayList<String>(Arrays.asList("RECEIVERCLIENTID","RcvrClntId"));
            ArrayList<String> tagValues = new ArrayList<String>(Arrays.asList(ctnId,ctnId));
            for(File file : new File(ordersV2Directory).listFiles())
            {
                try
                {
                    replaceTagValues(file, tagNames, tagValues, 1);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    CommonUtility.printMessage(e.getMessage());
                }
            }
        }
   
    }
       
    public void updateSQLScriptWithClientDetails() throws Exception
    {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String getCtnIdQuery1 = "SELECT ClientId FROM [CTN_Report].[dbo].[vwClient] where Name like '%"+Constants.fundManager1ClientCode+"%'";
        String getCtnIdQuery2 = "SELECT ClientId FROM [CTN_Report].[dbo].[vwClient] where Name like '%"+Constants.fundManager2ClientCode+"%'";
        String ctnId1 = databaseConnection.getSingleColumnWithRetry(getCtnIdQuery1, 1);
        String ctnId2 = databaseConnection.getSingleColumnWithRetry(getCtnIdQuery2, 1);
        if(ctnId1 == null || ctnId2 == null)
        {
            throw new Exception();
        }
        else
        {
            replaceFileBySpecifyingRowNumber(Constants.sqlUpdateScript, 10, ctnId1);
            replaceFileBySpecifyingRowNumber(Constants.sqlUpdateScript, 12, ctnId2);
           
            String sqlUpdateQuery = getFileContentWithoutChanging(Constants.sqlUpdateScript);
            databaseConnection.executeQuery(sqlUpdateQuery);
        }
    }
}
    