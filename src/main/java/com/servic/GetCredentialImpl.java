package com.servic;

import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.tencent.cloud.CosStsClient;

@Service
public class GetCredentialImpl implements GetCredential {

	@Override
	public JSONObject getCredential(Map<String, String> map) {
		String bucket = (String) map.get("bucket");
		String region = (String) map.get("region");
		int exp = Integer.valueOf(map.get("exp") == null || "".equals(map.get("exp")) ? "1800" : map.get("exp"));
		String secId = (String) map.get("secId");
		String secKey = (String) map.get("secKey");
		String policy = map.get("policy").toString();
		String[] actions = policy.split(",");
		String[] pol = new String[actions.length];
		for (int i = 0; i < actions.length; i++) {
			pol[i] = "name/cos:" + actions[i];
		}
		JSONObject json = testGetCredential(bucket, region, exp, secId, secKey, pol);
		return json;
	}

	public JSONObject testGetCredential(String bucket, String region, int exp, String secId, String secKey,
			String[] polciy) {
		TreeMap<String, Object> config = new TreeMap<String, Object>();

		try {
			Properties properties = new Properties();

			// 云 api 密钥 SecretId
			config.put("secretId", secId);
			// 云 api 密钥 SecretKey
			config.put("secretKey", secKey);

			if (properties.containsKey("https.proxyHost")) {
				System.setProperty("https.proxyHost", properties.getProperty("https.proxyHost"));
				System.setProperty("https.proxyPort", properties.getProperty("https.proxyPort"));
			}

			// 临时密钥有效时长，单位是秒
			config.put("durationSeconds", exp);

			// 换成你的 bucket
			config.put("bucket", bucket);
			// 换成 bucket 所在地区
			config.put("region", region);

			// 这里改成允许的路径前缀，可以根据自己网站的用户登录态判断允许上传的目录，例子：* 或者 a/* 或者 a.jpg
			config.put("allowPrefix", "*");

			// 密钥的权限列表。简单上传和分片需要以下的权限，其他权限列表请看 https://cloud.tencent.com/document/product/436/31923
			/*String[] allowActions = new String[] {
					// 简单上传
					"name/cos:PutObject", "name/cos:PostObject",
					// 分片上传
					"name/cos:InitiateMultipartUpload", "name/cos:ListMultipartUploads", "name/cos:ListParts",
					"name/cos:UploadPart", "name/cos:CompleteMultipartUpload" };
			config.put("allowActions", allowActions);*/
			config.put("allowActions", polciy);

			JSONObject credential = CosStsClient.getCredential(config);
			return credential;
			//System.out.println(credential.toString(4));
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

}
