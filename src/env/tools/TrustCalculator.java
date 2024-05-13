package tools;

import java.util.ArrayList;
import java.util.Arrays;

import cartago.*;

public class TrustCalculator extends Artifact {

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

  private class WitnessReputation {

    private String witnessAgent;
    private String targetAgent;
    private String messageContent;
    private double WRRating;

    public WitnessReputation(String witnessAgent, String targetAgent, String messageContent,
        double WRRating) {
      this.witnessAgent = witnessAgent;
      this.targetAgent = targetAgent;
      this.messageContent = messageContent;
      this.WRRating = WRRating;
    }

    public String getWitnessAgent() {
      return witnessAgent;
    }

    public String getTargetAgent() {
      return targetAgent;
    }

    public String getMessageContent() {
      return messageContent;
    }

    public double getWRRating() {
      return WRRating;
    }
  }

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

  private ArrayList<WitnessReputation> parseWRList(Object[] WRList) {
    ArrayList<WitnessReputation> WRArrayList = new ArrayList<WitnessReputation>();
    for (Object entry : WRList) {
      Object[] WR = (Object[]) entry;
      String witnessAgent = (String) WR[0];
      String targetAgent = (String) WR[1];
      String messageContent = (String) WR[2];
      Double WRRating = ((Number) WR[3]).doubleValue();
      WitnessReputation WRObj = new WitnessReputation(witnessAgent, targetAgent, messageContent,
          WRRating);
      WRArrayList.add(WRObj);
    }
    return WRArrayList;
  }

  @OPERATION
  public void getHighest_IT_AVG_Agent(Object[] ITList,
      OpFeedbackParam<String> mostTrustworthyAgent) {

    System.out.println("=========================================");
    System.out.println("TrustCalculator Artifact: getHighest_IT_AVG_Agent");

    // Parsing the lists
    ArrayList<InteractionTrust> ITArrayList = parseITList(ITList);

    mostTrustworthyAgent.set("sensing_agent_1");

    System.out.println("Most trustworthy agent: " + mostTrustworthyAgent.get());
    System.out.println("=========================================");
  }

  @OPERATION
  public void getHighest_IT_CR_Agent(Object[] ITList, Object[] CRList,
      OpFeedbackParam<String> mostTrustworthyAgent) {

    System.out.println("=========================================");
    System.out.println("TrustCalculator Artifact: getHighest_IT_CR_Agent");

    // Parsing the lists
    ArrayList<InteractionTrust> ITArrayList = parseITList(ITList);
    ArrayList<CertificationReputation> CRArrayList = parseCRList(CRList);

    mostTrustworthyAgent.set("sensing_agent_1");

    System.out.println("Most trustworthy agent: " + mostTrustworthyAgent.get());
    System.out.println("=========================================");
  }

  @OPERATION
  public void getHighest_IT_CR_WR_Agent(Object[] ITList, Object[] CRList,
      Object[] WRList, OpFeedbackParam<String> mostTrustworthyAgent) {

    System.out.println("=========================================");
    System.out.println("TrustCalculator Artifact: getHighest_IT_CR_WR_Agent");

    // Parsing the lists
    ArrayList<InteractionTrust> ITArrayList = parseITList(ITList);
    ArrayList<CertificationReputation> CRArrayList = parseCRList(CRList);
    ArrayList<WitnessReputation> WRArrayList = parseWRList(WRList);

    mostTrustworthyAgent.set("sensing_agent_1");

    System.out.println("Most trustworthy agent: " + mostTrustworthyAgent.get());
    System.out.println("=========================================");
  }
}