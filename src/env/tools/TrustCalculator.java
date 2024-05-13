package tools;

import cartago.*;

import java.util.*;

public class TrustCalculator extends Artifact {

  @OPERATION
  public void getHighest_IT_AVG_Agent(Object[] ITList,
      OpFeedbackParam<String> agent) {
    HashMap<String, List<Double>> map = new HashMap<String, List<Double>>();
    for (Object IT : ITList) {
      Object[] obj = (Object[]) IT;
      String id = (String) obj[1];
      double rating = ((Number) obj[3]).doubleValue();
      if (map.containsKey(id)) {
        List<Double> newList = map.get(id);
        newList.add(rating);
        map.put(id, newList);
      } else {
        List<Double> list = new ArrayList<Double>();
        list.add(rating);
        map.put(id, list);
      }
    }
    String keyWithHighestValue = "";
    Double highestValue = null;

    for (Map.Entry<String, List<Double>> entry : map.entrySet()) {
      String key = entry.getKey();
      Double value = 0.0;
      for (Double rating : entry.getValue()) {
        value += rating;
      }
      value = value / entry.getValue().size();
      if (highestValue == null || value > highestValue) {
        keyWithHighestValue = key;
        highestValue = value;
      }
    }
    agent.set(keyWithHighestValue);
  }

  @OPERATION
  public void getHighest_IT_CR_Agent(Object[] ITList, Object[] CRList,
      OpFeedbackParam<String> agent) {
    HashMap<String, List<Double>> itmap = new HashMap<String, List<Double>>();
    for (Object IT : ITList) {
      Object[] obj = (Object[]) IT;
      String id = (String) obj[1];
      double rating = ((Number) obj[3]).doubleValue();
      if (itmap.containsKey(id)) {
        List<Double> newList = itmap.get(id);
        newList.add(rating);
        itmap.put(id, newList);
      } else {
        List<Double> list = new ArrayList<Double>();
        list.add(rating);
        itmap.put(id, list);
      }
    }
    HashMap<String, Double> crmap = new HashMap<String, Double>();
    for (Object CR : CRList) {
      Object[] obj = (Object[]) CR;
      String id = (String) obj[1];
      double rating = ((Number) obj[3]).doubleValue();
      if (itmap.containsKey(id)) {
        List<Double> itRatings = itmap.get(id);
        Double avg = 0.0;
        for (Double itRating : itRatings) {
          avg += itRating;
        }
        avg = avg / itRatings.size();
        Double combined = avg * 0.5 + rating * 0.5;
        crmap.put(id, combined);
      }
    }
    String keyWithHighestValue = "";
    Double highestValue = null;

    for (Map.Entry<String, Double> entry : crmap.entrySet()) {
      String key = entry.getKey();
      Double value = entry.getValue();

      if (highestValue == null || value > highestValue) {
        keyWithHighestValue = key;
        highestValue = value;
      }
    }
    agent.set(keyWithHighestValue);
  }

  @OPERATION
  public void getHighest_IT_CR_WR_Agent(Object[] ITList, Object[] CRList,
      Object[] WRList, OpFeedbackParam<String> agent) {
    HashMap<String, List<Double>> itmap = new HashMap<String, List<Double>>();
    for (Object IT : ITList) {
      Object[] obj = (Object[]) IT;
      String id = (String) obj[1];
      double rating = ((Number) obj[3]).doubleValue();
      if (itmap.containsKey(id)) {
        List<Double> newList = itmap.get(id);
        newList.add(rating);
        itmap.put(id, newList);
      } else {
        List<Double> list = new ArrayList<Double>();
        list.add(rating);
        itmap.put(id, list);
      }
    }
    HashMap<String, Double> crmap = new HashMap<String, Double>();
    for (Object CR : CRList) {
      Object[] obj = (Object[]) CR;
      String id = (String) obj[1];
      double rating = ((Number) obj[3]).doubleValue();
      crmap.put(id, rating);
    }

    HashMap<String, List<Double>> wrmap = new HashMap<String, List<Double>>();
    for (Object WR : WRList) {
      Object[] obj = (Object[]) WR;
      String id = (String) obj[1];
      double rating = ((Number) obj[3]).doubleValue();
      if (wrmap.containsKey(id)) {
        List<Double> newList = wrmap.get(id);
        newList.add(rating);
        wrmap.put(id, newList);
      } else {
        List<Double> list = new ArrayList<Double>();
        list.add(rating);
        wrmap.put(id, list);
      }
    }
    String keyWithHighestValue = "";
    Double highestValue = null;

    for (Map.Entry<String, List<Double>> entry : itmap.entrySet()) {
      String key = entry.getKey();
      Double itValue = 0.0;
      for (Double rating : entry.getValue()) {
        itValue += rating;
      }
      itValue = itValue / entry.getValue().size();
      Double wrValue = 0.0;
      for (Double rating : wrmap.get(key)) {
        wrValue += rating;
      }
      wrValue = wrValue / wrmap.get(key).size();
      Double value = itValue / 3 + wrValue / 3 + crmap.get(key) / 3;
      if (highestValue == null || value > highestValue) {
        keyWithHighestValue = key;
        highestValue = value;
      }
    }
    agent.set(keyWithHighestValue);
  }
}