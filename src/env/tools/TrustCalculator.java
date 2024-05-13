package tools;

import java.util.Arrays;

import cartago.*;

public class TrustCalculator extends Artifact {

  @OPERATION
  public void getHighest_IT_AVG_Temp(Object[] interactionTrustList,
      OpFeedbackParam<Double> selectedTemp) {

    for (Object entry : interactionTrustList) {
      // System.out.println(interactionTrust);
      Object[] interactionTrust = (Object[]) entry;
    }

    selectedTemp.set(1.0);
  }

  @OPERATION
  public void getHighest_IT_CR_Temp(Object[] interactionTrustList, Object[] certifiedTrustList,
      OpFeedbackParam<Double> selectedTemp) {

    System.out.println(Arrays.toString(interactionTrustList));
    System.out.println(Arrays.toString(certifiedTrustList));

    for (Object entry : interactionTrustList) {
      // System.out.println(interactionTrust);
      Object[] interactionTrust = (Object[]) entry;
    }

    selectedTemp.set(1.0);
  }

  @OPERATION
  public void getHighest_IT_CR_WR_Temp(Object[] interactionTrustList,
      OpFeedbackParam<Double> selectedTemp) {

    for (Object entry : interactionTrustList) {
      // System.out.println(interactionTrust);
      Object[] interactionTrust = (Object[]) entry;
    }

    selectedTemp.set(1.0);
  }

}
