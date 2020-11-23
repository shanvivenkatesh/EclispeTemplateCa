package calas.constants;

import com.Tests.App;

public class FilePathConstants 
{
    public static final String regressionFolder="\\\\calafile02\\QA\\Sprints\\QA.TestScripts\\Regression";
    public static final String failedResults="\\FailedResults\\";
    public static final String expectedResults="\\ExpectedResults\\";
    public static final String actualResults="\\ActualResults\\";
    public static final String facadeMessages = "//" + App.endp + "/CTN/FacadeMessages/";
    public static final String fromSwift15022 = facadeMessages+"Swift/FromSAA.CTN.15022/";
    public static final String fromSwift20022 = facadeMessages+"Swift/FromSAA.CTN.20022/";
    public static final String toSwift15022 = facadeMessages+"Swift/ToSAA.CTN.15022/";
    public static final String toSwift20022 = facadeMessages+"Swift/ToSAA.CTN.20022/";
    public static final String ctnServiceClient = facadeMessages+"CtnServiceClient/";
    public static final String sftpDirectory = "//" + App.endp+"/SFTP/Root/Clients";

 

    
    public static final String serviceFacade = "https://" + App.sN + "/servicefacade/";
    public static final String messageAdapterConfigFilePath = "//" + App.endp
            + "\\ctn\\MessageAdapter\\Calastone.Core.MessageAdapter.Console.exe.config";
}
