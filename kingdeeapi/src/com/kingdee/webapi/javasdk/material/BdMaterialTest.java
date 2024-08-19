import com.google.gson.Gson;
import com.kingdee.bos.webapi.entity.IdentifyInfo;
import com.kingdee.bos.webapi.entity.RepoRet;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import static org.junit.Assert.fail;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BdMaterialTest {
    private static String FNumber = "MFGWL600001"; //单据编码，字符串类型（使用编码时必录）
    private static String FName = "auwl_" + UUID.randomUUID().toString();
    private static String groupid = "";
    private static String materid = "";
    private static String fileid = "";

    /*本接口用于实现物料 (BD_Material) 的查看功能*/
    @Test
    public void ltestviewMaterial() throws Exception {
        Properties properties = new Properties();
        File file = new File((new File("./src")).getCanonicalPath() + "/kdwebapi.properties");
        System.out.println("读取根目录下的配置文件->" + file.getPath());
        InputStream inputStream = new FileInputStream(file.getPath());
        properties.load(new InputStreamReader(inputStream, "utf-8"));
        inputStream.close();
        IdentifyInfo iden = new IdentifyInfo();
        System.out.println(iden.toString());
        iden.setConnectTimeout(Integer.parseInt(properties.getProperty("X-KDApi-ConnectTimeout")));
        iden.setRequestTimeout(Integer.parseInt(properties.getProperty("X-KDApi-RequestTimeout")));
        iden.setUserName(properties.getProperty("X-KDApi-UserName"));
        iden.setAppId(properties.getProperty("X-KDApi-AppID"));
        iden.setdCID(properties.getProperty("X-KDApi-AcctID"));
        iden.setAppSecret(properties.getProperty("X-KDApi-AppSec"));
        iden.setlCID(Integer.parseInt(properties.getProperty("X-KDApi-LCID")));
        iden.setServerUrl(properties.getProperty("X-KDApi-ServerUrl"));
        iden.setStockTimeout(Integer.parseInt(properties.getProperty("X-KDApi-StockTimeout")));
        K3CloudApi api = new K3CloudApi(iden);
        String data = "{\"CreateOrgId\": 1,\"Number\": "+"\""+ FNumber +"\""+",\"Id\": \"\",\"IsSortBySeq\": \"false\"}";
        String result = api.view("BD_Material", data);
        Gson gson = new Gson();
        RepoRet repoRet = gson.fromJson(result, RepoRet.class);
        if (repoRet.getResult().getResponseStatus().isIsSuccess()) {
            System.out.printf("物料查看接口: %s%n", result);
        } else {
            fail("物料查看接口: " + result);
        }
    }
    @Test
    public void ltestMeterials() throws Exception{
        //注意 1：此处不再使用参数形式传入用户名及密码等敏感信息，改为在登录配置文件中设置。
        //注意 2：必须先配置第三方系统登录授权信息后，再进行业务操作，详情参考各语言版本SDK介绍中的登录配置文件说明。
        //读取配置，初始化SDK
        K3CloudApi client = new K3CloudApi();
        //请求参数，要求为json字符串
        String jsonData = "{\"FormId\":\"BD_Material\",\"FieldKeys\":\"FNumber,FName,FCreateOrgId,FUseOrgId\",\"FilterString\":[],\"OrderString\":\"\",\"TopRowCount\":0,\"StartRow\":0,\"Limit\":2000,\"SubSystemId\":\"\"}";
        try {
            //调用接口
            String resultJson = String.valueOf(client.executeBillQuery(jsonData));
            System.out.println("接口返回结果: " + resultJson);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}




