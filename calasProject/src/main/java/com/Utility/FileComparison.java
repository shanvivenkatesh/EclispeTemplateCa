package com.Utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

import com.Tests.App;

import calas.constants.Constants.fileFormat;
import calas.CmmonFunction.CmmonFunction;
import calas.constants.FilePathConstants;

public class FileComparison extends CmmonFunction{
	
	private String expectedResults;
    private String failedResults;
    private String actualResults;
    private String regressionFolder;
    private boolean ignoreLauKey = true;
    private final int MAX_WAIT_TIME_IN_SECONDS=30;

 

    
    public FileComparison(String regressionFolderAbsolutePath)
    {    
        regressionFolder=regressionFolderAbsolutePath;
        expectedResults= regressionFolder+FilePathConstants.expectedResults;
        failedResults= regressionFolder+FilePathConstants.failedResults;
        actualResults= regressionFolder+FilePathConstants.actualResults;
    }
    
    public FileComparison()
    {    
    }
    
    @SuppressWarnings("unchecked")
    public <T> boolean compareWithExpectedFile(String currentContent, String fileName, ArrayList<T> tagNames, fileFormat fileFormat ) throws IOException
    {
        String expectedContent="";
        ArrayList<String> tagValues;
        createFolders();
        createFileWithContent(currentContent,actualResults+fileName);
        
        switch (fileFormat) 
        {
            case Swift15022:
                if(ignoreLauKey)
                {
                    currentContent = currentContent + "{S:\r\n{MDG:E935E666D241A331AB66FFFCB2CA438C3EF4465046686EED70F26BDB6EC91C43}}";
                    createFileWithContent(currentContent,actualResults+fileName);
                }
                tagValues=getTagValuesFrom15022Message((ArrayList<String>)tagNames,actualResults+fileName);
                expectedContent=readAndReplaceTagValuesIn15022Message(expectedResults+fileName, (ArrayList<String>)tagNames,tagValues, false);
                break;
            case CtnMessage:
                tagValues=getTagValuesFromXMLMessage((ArrayList<String>)tagNames,actualResults+fileName);
                expectedContent=readAndReplaceTagValuesFromXmlMessage(expectedResults+fileName, (ArrayList<String>)tagNames,tagValues);
                break;
            case Swift20022:
                if(ignoreLauKey)
                {
                    int lauTag =  currentContent.indexOf("</Saa:DataPDU>");
                    currentContent = currentContent.substring(0, lauTag).concat("<Saa:LAU>\r\n<Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\">\r\n<SignedInfo>\r\n<CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\" />\r\n<SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#hmac-sha256\" />\r\n<Reference URI=\"\">\r\n<Transforms>\r\n<Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\" />\r\n<Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\" />\r\n</Transforms>\r\n<DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\" />\r\n<DigestValue>cKlNKEl1XIH4osL17bUxPeFfgTPjqhW62yBuupxUUV8=</DigestValue>\r\n</Reference>\r\n</SignedInfo>\r\n<SignatureValue>NpJzhro/8ahjuj36C4sRmA8hfYSHLV3nO8mUK0B0CGU=</SignatureValue>\r\n</Signature>\r\n</Saa:LAU>\r\n</Saa:DataPDU>");
                    createFileWithContent(currentContent,actualResults+fileName);
                }
                tagValues=getTagValuesFromXMLMessage((ArrayList<String>)tagNames,actualResults+fileName);
                expectedContent=readAndReplaceTagValuesFromXmlMessage(expectedResults+fileName, (ArrayList<String>)tagNames,tagValues);
                break;
            case FlatFile:
                tagValues=getColumnValuesFromSingleRowInTextFile((ArrayList<Integer>)tagNames,actualResults+fileName);            
                expectedContent=readAndreplaceValuesInAllRowsInTextFile(expectedResults+fileName, (ArrayList<Integer>)tagNames,tagValues);
                break;
            default:
                break;
        }
        
        if(currentContent.replaceAll("\\s", "").equals(expectedContent.replaceAll("\\s", ""))&&!currentContent.isEmpty()&&!expectedContent.isEmpty())
        {
            createFileWithContent(currentContent,expectedResults+fileName);
            return true;
        }
        else
        {
            String dateTime=uniqueIndexDateSecFolderName();
            createDirectory(failedResults+dateTime);
            createFileWithContent(currentContent,failedResults+dateTime+"\\"+fileName);
            return false;
        }
    }
    public String getOutputMessageFromFolder(String absolutePathOfOutputFolder, String orderReference)
    {
        int MAX_WAIT_TIME_IN_SECONDS=30;
        long maxWaitTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(MAX_WAIT_TIME_IN_SECONDS);
        long currentTime = System.currentTimeMillis();
        while(currentTime <= maxWaitTime)
        {
            currentTime = System.currentTimeMillis();
        
            File directory= new File(absolutePathOfOutputFolder);
            File[] files = directory.listFiles();
            File outputFile = null;
            Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
            for(int i = 0; i<10; i++)
            {
                try 
                {
                    if(checkTextExists(orderReference, files[i].getAbsolutePath()))
                    {
                        outputFile = files[i];
                    }
                } 
                catch (Exception e) 
                {
                    break;
                }
            }
            if(outputFile!=null)
            {
                App.logger.info("File found from: "+outputFile.getAbsolutePath());
                String outputFileContent = getFileContentWithoutChanging(outputFile.getAbsolutePath());
                outputFile.delete();
                return outputFileContent;
            }
        }
        App.logger.info("File with reference "+orderReference+" not found from: "+absolutePathOfOutputFolder);
        return null;
    }
    
    public void deleteMatchingFileNamesFromFolder(String fileNameRegex, String absolutePathToDirectory)
    {
        File directory= new File(absolutePathToDirectory);
        File[] files = directory.listFiles();
        if(files!=null)
        {
            for(int i = 0; i<files.length; i++)
            {
                String actualFileName = files[i].getName();
                if(actualFileName.matches(fileNameRegex))
                {
                    files[i].delete();
                }
            }
        }
    }
    
    public void createDirectory(String folderName)
    {
        File folder1 = new File(folderName);
        folder1.mkdir();
    }
    
    public boolean createFolders()
    {
        File folder1 = new File(failedResults);
        File folder2= new File(expectedResults);
        File folder3= new File(actualResults);

 

        if(!folder1.exists()||!folder2.exists()||!folder3.exists())
        {
            folder1.mkdir();
            folder2.mkdir();
            folder3.mkdir();
            return true;
        }        
        return false;
    }
    
    public boolean checkFileExistsWithRetry(String fileName)
    {
        File file = new File(fileName);    
        long maxWaitTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(MAX_WAIT_TIME_IN_SECONDS);
        long currentTime = System.currentTimeMillis();
        while(currentTime <= maxWaitTime)
        {
            if(file.exists())
            {        
                return true;
            }    
            currentTime = System.currentTimeMillis();
        }
        return false;
    }
    public boolean checkMatchingFileNamesFromFolderWithRetry(String fileNameRegex, String absolutePathToDirectory)
    {
        long maxWaitTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(MAX_WAIT_TIME_IN_SECONDS);
        long currentTime = System.currentTimeMillis();
        while(currentTime <= maxWaitTime)
        {
            File directory= new File(absolutePathToDirectory);
            File[] files = directory.listFiles();
            if(files!=null)
            {
                for(int i = 0; i<files.length; i++)
                {
                    String actualFileName = files[i].getName();
                    if(actualFileName.matches(fileNameRegex))
                    {
                        return true;
                    }
                }
            }   
            currentTime = System.currentTimeMillis();
        }
        return false;
    }
    public void deleteFileIfExists(String fileName)
    {
        File file = new File(fileName);   
        if(file.exists())
        {       
            file.delete();
        }       
    }
   
    public String getActualResultsAboslutePath()
    {
        return actualResults;
    }
   
    public String getRegressionFolderPath()
    {
        return regressionFolder;
    }
}

