package external;

import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.monkeylearn.ExtraParam;
import com.monkeylearn.MonkeyLearn;
import com.monkeylearn.MonkeyLearnException;
import com.monkeylearn.MonkeyLearnResponse;

public class MonkeyLearnClient {
	private static final String API_KEY = "2128d6d4e6755efd7eb2aca041cbb2ad682eb823";
	
	public static void main(String[] args) throws MonkeyLearnException {
		String[] textList = {"Elon Musk has shared a photo of the spacesuit designed by SpaceX. This is the second image shared of the new design and the first to feature the spacesuit’s full-body look."};
		List<List<String>> words = extractKeywords(textList);
		for (List<String> ws : words) {
			for (String w : ws) {
				System.out.println(w);
			}
			System.out.println();
		}
	}
	
	public static List<List<String>> extractKeywords(String[] text) {
		// Use the API key from your account
		MonkeyLearn ml = new MonkeyLearn(API_KEY);

		// Use the keyword extractor
		ExtraParam[] extraParams = { new ExtraParam("max_keywords", "3") };
		MonkeyLearnResponse response = null;
		try {
			response = ml.extractors.extract("ex_YCya9nrn", text, extraParams);
			JSONArray resultArray = response.arrayResult;
			return getKeywords(resultArray);
		} catch (MonkeyLearnException e) {// it’s likely to have an exception
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	private static List<List<String>> getKeywords(JSONArray array) {
		List<List<String>> topKeywords = new ArrayList<>();
		for (int i = 0; i < array.size(); i++) {
			JSONArray subArray = (JSONArray) array.get(i);
			List<String> keywords = new ArrayList<>();
			for (int j = 0; j < subArray.size(); j++) {
				JSONObject obj = (JSONObject) subArray.get(j);
				String keyword = (String) obj.get("keyword");
				keywords.add(keyword);
			}
			topKeywords.add(keywords);
		}
		return topKeywords;
	}
}
