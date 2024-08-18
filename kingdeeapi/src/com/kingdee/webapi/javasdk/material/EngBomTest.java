import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kingdee.bos.webapi.entity.IdentifyInfo;
import com.kingdee.bos.webapi.entity.QueryParam;
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

public class EngBomTest {
    String FNumber = SeqHelper.genNumber("ENG_BOM");
@Test
public void searchBom() {
//注意 1：此处不再使用参数形式传入用户名及密码等敏感信息，改为在登录配置文件中设置。
//注意 2：必须先配置第三方系统登录授权信息后，再进行业务操作，详情参考各语言版本SDK介绍中的登录配置文件说明。
//读取配置，初始化SDK
    K3CloudApi client = new K3CloudApi();
//请求参数，要求为json字符串
    String jsonData = "{\"CreateOrgId\": 0,\"Number\": "+"\""+FNumber+"\""+",\"Id\": \"\",\"IsSortBySeq\": \"false\"}";
    try{
        //业务对象标识
        String formId = "ENG_BOM";
        //调用接口
        String resultJson = client.view(formId,jsonData);

        //用于记录结果
        Gson gson = new Gson();
        //对返回结果进行解析和校验
        RepoRet repoRet = gson.fromJson(resultJson, RepoRet.class);
        if (repoRet.getResult().getResponseStatus().isIsSuccess()) {
            System.out.printf("接口返回结果: %s%n", gson.toJson(repoRet.getResult()));
        } else {
            fail("接口返回结果: " + gson.toJson(repoRet.getResult().getResponseStatus()));
        }
    } catch (Exception e) {
        fail(e.getMessage());
    }
}
}
