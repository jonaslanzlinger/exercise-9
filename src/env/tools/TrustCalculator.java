package tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import cartago.*;

// Artifact for calculating the trustworthiness of agents based on various trust/reputation ratings.
public class TrustCalculator extends Artifact {

  // Data structure for storing certification reputation ratings
  private class InteractionTrust {

    private String sourceAgent;
    private String targetAgent;
    private String messageContent;
    private double ITRating;

    public InteractionTrust(String sourceAgent, String targetAgent, String messageContent,
        double ITRating) {
      this.sourceAgent = sourceAgent;
      this.targetAgent = targetAgent;
      this.messageContent = messageContent;
      this.ITRating = ITRating;
    }

    public String getSourceAgent() {
      return sourceAgent;
    }

    public String getTargetAgent() {
      return targetAgent;
    }

    public String getMessageContent() {
      return messageContent;
    }

    public double getITRating() {
      return ITRating;
    }
  }

  // Creats an array of InteractionTrust objects from the given ITList
  private ArrayList<InteractionTrust> parseITList(Object[] ITList) {
    ArrayList<InteractionTrust> ITArrayList = new ArrayList<InteractionTrust>();
    for (Object entry : ITList) {
      Object[] IT = (Object[]) entry;
      String sourceAgent = (String) IT[0];
      String targetAgent = (String) IT[1];
      String messageContent = (String) IT[2];
      Double ITRating = ((Number) IT[3]).doubleValue();
      InteractionTrust ITObj = new InteractionTrust(sourceAgent, targetAgent, messageContent,
          ITRating);
      ITArrayList.add(ITObj);
    }
    return ITArrayList;
  }

  // Computes the average interaction trust rating for each target agent
  private HashMap<String, Double> getAvgITRatingMap(ArrayList<InteractionTrust> ITArrayList) {
    HashMap<String, Double> avgITRatingMap = new HashMap<String, Double>();
    for (InteractionTrust ITObj : ITArrayList) {
      String targetAgent = ITObj.getTargetAgent();
      double ITRating = ITObj.getITRating();
      double sumITRating = ITRating;
      int countITRating = 1;
      for (InteractionTrust ITObj2 : ITArrayList) {
        if (ITObj2.getTargetAgent().equals(targetAgent) && !ITObj2.equals(ITObj)) {
          sumITRating += ITObj2.getITRating();
          countITRating++;
        }
      }
      double avgITRating = sumITRating / countITRating;
      avgITRatingMap.put(targetAgent, avgITRating);
    }
    return avgITRatingMap;
  }

  // Method for obtaining the agent with the highest average interaction trust
  // rating
  @OPERATION
  public void getHighest_IT_AVG_Agent(Object[] ITList,
      OpFeedbackParam<Object> mostTrustworthyAgent) {

    System.out.println("=========================================");
    System.out.println("TrustCalculator Artifact: getHighest_IT_AVG_Agent");

    // Parsing the lists
    ArrayList<InteractionTrust> ITArrayList = parseITList(ITList);

    // Compute the most trustworthy agent based on the average interaction trust
    // Group the InteractionTrust objects by targetAgent and compute the average
    // ITRating for each targetAgent
    HashMap<String, Double> avgITRatingMap = getAvgITRatingMap(ITArrayList);

    System.out.println("avgITRatingMap: " + avgITRatingMap.toString());

    // Find the targetAgent with the highest average ITRating
    double maxAvgITRating = 0;
    String mostTrustworthyAgentName = "";
    for (String targetAgent : avgITRatingMap.keySet()) {
      double avgITRating = avgITRatingMap.get(targetAgent);
      if (avgITRating > maxAvgITRating) {
        maxAvgITRating = avgITRating;
        mostTrustworthyAgentName = targetAgent;
      }
    }

    mostTrustworthyAgent.set(mostTrustworthyAgentName);

    System.out.println(
        "Most trustworthy agent: " + mostTrustworthyAgent.get() + " with average IT rating: " + maxAvgITRating);
    System.out.println("=========================================");
  }

  // Method for obtaining the temperature reading of a given agent
  @OPERATION
  public void getTempReadingByAgent(String agentName, Object[] tempReadings, OpFeedbackParam<Double> tempReading) {

    System.out.println("=========================================");
    System.out.println("TrustCalculator Artifact: getTempReadingByAgent");

    // Parsing the lists
    ArrayList<Object> tempReadingList = new ArrayList<Object>(Arrays.asList(tempReadings));

    // Find the temperature reading for the given agent
    for (Object entry : tempReadingList) {
      Object[] tempReadingEntry = (Object[]) entry;
      double temp = ((Number) tempReadingEntry[0]).doubleValue();
      String agent = (String) tempReadingEntry[1];
      if (agent.equals(agentName)) {
        tempReading.set(temp);
        System.out.println("Temperature reading for agent " + agentName + ": " + temp);
        break;
      }
    }

    System.out.println("=========================================");
  }

}