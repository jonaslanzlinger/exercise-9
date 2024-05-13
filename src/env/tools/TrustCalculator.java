package tools;

import cartago.*;

import java.util.*;

public class TrustCalculator extends Artifact {

  @OPERATION
  public void getHighest_IT_AVG_Agent(Object[] ITList,
      OpFeedbackParam<String> mostTrustworthyAgent) {
    mostTrustworthyAgent.set("sensing_agent_1");
  }

  @OPERATION
  public void getHighest_IT_CR_Agent(Object[] ITList, Object[] CRList,
      OpFeedbackParam<String> mostTrustworthyAgent) {
    mostTrustworthyAgent.set("sensing_agent_1");
  }

  @OPERATION
  public void getHighest_IT_CR_WR_Agent(Object[] ITList, Object[] CRList,
      Object[] WRList, OpFeedbackParam<String> mostTrustworthyAgent) {
    mostTrustworthyAgent.set("sensing_agent_1");
  }
}