package com.meijia.utils.huanxin;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.JerseyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meijia.utils.huanxin.comm.Constants;
import com.meijia.utils.huanxin.comm.HTTPMethod;
import com.meijia.utils.huanxin.comm.Roles;
import com.meijia.utils.huanxin.utils.JerseyUtils;
import com.meijia.utils.huanxin.vo.ClientSecretCredential;
import com.meijia.utils.huanxin.vo.Credential;
import com.meijia.utils.huanxin.vo.EndPoints;

/**
 * REST API Demo : 聊天记录 Jersey2.9实现
 * 
 * Doc URL: http://www.easemob.com/docs/rest/chatmessage/
 * 
 * @author Lynch 2014-07-12
 * 
 */
public class EasemobChatMessage {

	private static Logger LOGGER = LoggerFactory.getLogger(EasemobChatMessage.class);
	private static JsonNodeFactory factory = new JsonNodeFactory(false);
	private static final String APPKEY = Constants.APPKEY;

    // 通过app的client_id和client_secret来获取app管理员token
    private static Credential credential = new ClientSecretCredential(Constants.APP_CLIENT_ID,
            Constants.APP_CLIENT_SECRET, Roles.USER_ROLE_APPADMIN);

    /**
     * Main Test
     *
     * @param args
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */
    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {

        // 聊天消息 获取最新的20条记录
//        ObjectNode queryStrNode = factory.objectNode();
//        queryStrNode.put("limit", "20");
//        ObjectNode messages = getChatMessages(queryStrNode);

        // 聊天消息 获取7天以内的消息
//        String currentTimestamp = String.valueOf(System.currentTimeMillis());
//        String senvenDayAgo = String.valueOf(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000);
//        ObjectNode queryStrNode1 = factory.objectNode();
//        queryStrNode1.put("ql", "select * where  timestamp > " + senvenDayAgo + " and timestamp < " + currentTimestamp);
//        ObjectNode messages1 = getChatMessages(queryStrNode1);
    	
    	ObjectMapper mapper = new ObjectMapper();
        // 聊天消息 分页获取
        ObjectNode queryStrNode2 = factory.objectNode();
        queryStrNode2.put("limit", "100");
        // 第一页
        ObjectNode messages2 = getChatMessages(queryStrNode2);
        
        ArrayNode data =(ArrayNode)messages2.get("entities");

        for (int i=0; i < data.size(); i++) {
          String uuid=data.get(i).get("uuid").asText();
          String content = data.get(i).get("payload").toString();
          System.out.println(content);
          JsonNode contentJsonNode = mapper.readValue(content, JsonNode.class); 
          if (contentJsonNode.get("bodies") != null) {
        	  System.out.println(contentJsonNode.get("bodies").toString());
        	  
        	  if (contentJsonNode.get("bodies").get(0) != null) {
                  System.out.println(contentJsonNode.get("bodies").get(0).get("type").toString());
                  System.out.println(contentJsonNode.get("bodies").get(0).get("msg").toString());        		  
        	  }
          }
          

          
        }
        // 第二页
        String cursor = messages2.get("cursor").asText();
        ObjectNode messages3 = null;
        while (true) {
        	try {
	            queryStrNode2.put("cursor", cursor);
	            messages3 = getChatMessages(queryStrNode2);  
	            if (messages3.get("cursor") == null ) break;
	            cursor = messages3.get("cursor").asText();
        	} catch(Exception e) {
        		e.printStackTrace();
        		break;
        	}
        }

    }

    /**
	 * 获取聊天消息
	 * 
	 * @param queryStrNode
	 *
	 */
	public static ObjectNode getChatMessages(ObjectNode queryStrNode) {
		ObjectNode objectNode = factory.objectNode();
		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);
			objectNode.put("message", "Bad format of Appkey");
			return objectNode;
		}
		try {
			JerseyWebTarget webTarget = EndPoints.CHATMESSAGES_TARGET.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1]);
			if (null != queryStrNode && null != queryStrNode.get("ql") && !StringUtils.isEmpty(queryStrNode.get("ql").asText())) {
				webTarget = webTarget.queryParam("ql", queryStrNode.get("ql").asText());
			}
			if (null != queryStrNode && null != queryStrNode.get("limit") &&!StringUtils.isEmpty(queryStrNode.get("limit").asText())) {
				webTarget = webTarget.queryParam("limit", queryStrNode.get("limit").asText());
			}
			if (null != queryStrNode && null != queryStrNode.get("cursor") &&!StringUtils.isEmpty(queryStrNode.get("cursor").asText())) {
				webTarget = webTarget.queryParam("cursor", queryStrNode.get("cursor").asText());
			}
			objectNode = JerseyUtils.sendRequest(webTarget, null, credential, HTTPMethod.METHOD_GET, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectNode;
	}

}
