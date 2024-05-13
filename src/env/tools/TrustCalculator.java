package tools;

import java.util.Arrays;

import cartago.*;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Map;

public class TrustCalculator extends Artifact {

  @OPERATION
  public void getHighest_IT_AVG_Temp(Object[] interactionTrustList,
      OpFeedbackParam<String> agent) {

    System.out.println(Arrays.toString(interactionTrustList));

    HashMap<String, Double> map = new HashMap<String, Double>();
    for (Object interactionTrust : interactionTrustList) {
      Object[] obj = (Object[]) interactionTrust;
      String id = (String) obj[1];
      double rating = ((Number) obj[3]).doubleValue();
      if (map.containsKey(id)) {
        Double newAvg = (map.get(id) + rating) / 2;
        map.put(id, newAvg);
      } else {
        map.put(id, Double.valueOf(rating));
      }
    }
    String keyWithHighestValue = "";
    Double highestValue = null;

    for (Map.Entry<String, Double> entry : map.entrySet()) {
      String key = entry.getKey();
      Double value = entry.getValue();

      if (highestValue == null || value > highestValue) {
        keyWithHighestValue = key;
        highestValue = value;
      }
    }

    System.out.println("Agent with the highest average interaction trust: " + keyWithHighestValue
        + " with average rating: " + highestValue);
    agent.set(keyWithHighestValue);
  }

  @OPERATION
  public void getHighest_IT_CR_Temp(Object[] interactionTrustList,
      OpFeedbackParam<String> agent) {

    HashMap<String, Double> map = new HashMap<String, Double>();
    for (Object interactionTrust : interactionTrustList) {
      Object[] obj = (Object[]) interactionTrust;
      String id = (String) obj[1];
      double rating = ((Number) obj[3]).doubleValue();
      if (map.containsKey(id)) {
        Double newAvg = (map.get(id) + rating) / 2;
        map.put(id, newAvg);
      } else {
        map.put(id, Double.valueOf(rating));
      }
    }
    String keyWithHighestValue = "";
    Double highestValue = null;

    for (Map.Entry<String, Double> entry : map.entrySet()) {
      String key = entry.getKey();
      Double value = entry.getValue();

      if (highestValue == null || value > highestValue) {
        keyWithHighestValue = key;
        highestValue = value;
      }
    }

    System.out.println("Agent with the highest average interaction trust: " + keyWithHighestValue
        + " with average rating: " + highestValue);
    agent.set(keyWithHighestValue);
  }

  @OPERATION
  public void getHighest_IT_CR_WR_Temp(Object[] interactionTrustList,
      OpFeedbackParam<String> agent) {

    HashMap<String, Double> map = new HashMap<String, Double>();
    for (Object interactionTrust : interactionTrustList) {
      Object[] obj = (Object[]) interactionTrust;
      String id = (String) obj[1];
      double rating = ((Number) obj[3]).doubleValue();
      if (map.containsKey(id)) {
        Double newAvg = (map.get(id) + rating) / 2;
        map.put(id, newAvg);
      } else {
        map.put(id, Double.valueOf(rating));
      }
    }
    String keyWithHighestValue = "";
    Double highestValue = null;

    for (Map.Entry<String, Double> entry : map.entrySet()) {
      String key = entry.getKey();
      Double value = entry.getValue();

      if (highestValue == null || value > highestValue) {
        keyWithHighestValue = key;
        highestValue = value;
      }
    }

    System.out.println("Agent with the highest average interaction trust: " + keyWithHighestValue
        + " with average rating: " + highestValue);
    agent.set(keyWithHighestValue);
  }

}
