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

  // Data structure for storing certification reputation ratings
  private class CertificationReputation {

    private String certificationAgent;
    private String targetAgent;
    private String messageContent;
    private double CRRating;

    public CertificationReputation(String certificationAgent, String targetAgent, String messageContent,
        double CRRating) {
      this.certificationAgent = certificationAgent;
      this.targetAgent = targetAgent;
      this.messageContent = messageContent;
      this.CRRating = CRRating;
    }

    public String getCertificationAgent() {
      return certificationAgent;
    }

    public String getTargetAgent() {
      return targetAgent;
    }

    public String getMessageContent() {
      return messageContent;
    }

    public double getCRRating() {
      return CRRating;
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

  // Creats an array of CertificationReputation objects from the given CRList
  private ArrayList<CertificationReputation> parseCRList(Object[] CRList) {
    ArrayList<CertificationReputation> CRArrayList = new ArrayList<CertificationReputation>();
    for (Object entry : CRList) {
      Object[] CR = (Object[]) entry;
      String certificationAgent = (String) CR[0];
      String targetAgent = (String) CR[1];
      String messageContent = (String) CR[2];
      Double CRRating = ((Number) CR[3]).doubleValue();
      CertificationReputation CRObj = new CertificationReputation(certificationAgent, targetAgent,
          messageContent, CRRating);
      CRArrayList.add(CRObj);
    }
    return CRArrayList;
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

  // Compute the average certification reputation rating for each target agent
  // NOTE: This implementation is not needed if only 1 certification_reputation
  // per target_agent is allowed...but it is here for completeness (and still
  // works)
  private HashMap<String, Double> getAvgCRRatingMap(ArrayList<CertificationReputation> CRArrayList) {
    HashMap<String, Double> avgCRRatingMap = new HashMap<String, Double>();
    for (CertificationReputation CRObj : CRArrayList) {
      String targetAgent = CRObj.getTargetAgent();
      double CRRating = CRObj.getCRRating();
      double sumCRRating = CRRating;
      int countCRRating = 1;
      for (CertificationReputation CRObj2 : CRArrayList) {
        if (CRObj2.getTargetAgent().equals(targetAgent) && !CRObj2.equals(CRObj)) {
          sumCRRating += CRObj2.getCRRating();
          countCRRating++;
        }
      }
      double avgCRRating = sumCRRating / countCRRating;
      avgCRRatingMap.put(targetAgent, avgCRRating);
    }
    return avgCRRatingMap;
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

  // Method for obtaining the agent with the highest IT_CR rating
  @OPERATION
  public void getHighest_IT_CR_Agent(Object[] ITList, Object[] CRList,
      OpFeedbackParam<String> mostTrustworthyAgent) {

    System.out.println("=========================================");
    System.out.println("TrustCalculator Artifact: getHighest_IT_CR_Agent");

    // Parsing the lists
    ArrayList<InteractionTrust> ITArrayList = parseITList(ITList);
    ArrayList<CertificationReputation> CRArrayList = parseCRList(CRList);

    // Compute the most trustworthy agent based on the average interaction trust
    // and the certification reputation:
    // => IT_CR = 0.5 * (ITRating1 + ITRating2 + ... + ITRatingN) / N + 0.5 *
    // (CRRating1 + CRRating2 + ... + CRRatingN) / N
    HashMap<String, Double> avgITRatingMap = getAvgITRatingMap(ITArrayList);
    HashMap<String, Double> avgCRRatingMap = getAvgCRRatingMap(CRArrayList);

    System.out.println("avgITRatingMap: " + avgITRatingMap.toString());
    System.out.println("avgCRRatingMap: " + avgCRRatingMap.toString());

    // Find the targetAgent with the highest IT_CR rating
    double maxIT_CRRating = 0;
    String mostTrustworthyAgentName = "";
    for (String targetAgent : avgITRatingMap.keySet()) {
      double avgITRating = avgITRatingMap.get(targetAgent);
      double avgCRRating = avgCRRatingMap.get(targetAgent);
      double IT_CRRating = 0.5 * avgITRating + 0.5 * avgCRRating;
      if (IT_CRRating > maxIT_CRRating) {
        maxIT_CRRating = IT_CRRating;
        mostTrustworthyAgentName = targetAgent;
      }
    }

    mostTrustworthyAgent.set(mostTrustworthyAgentName);

    System.out.println(
        "Most trustworthy agent: " + mostTrustworthyAgent.get() + " with IT_CR rating: " + maxIT_CRRating);
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