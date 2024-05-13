package tools;

import cartago.*;

public class TrustCalculator extends Artifact {

  @OPERATION
  public void getTemperatureByInteractionTrust(Object[] interactionTrustList,
      OpFeedbackParam<Double> temperature) {

    for (Object entry : interactionTrustList) {
      // System.out.println(interactionTrust);
      Object[] interactionTrust = (Object[]) entry;
    }

  }

}
