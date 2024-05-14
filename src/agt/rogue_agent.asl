// rogue agent is a type of sensing agent

/* Initial beliefs and rules */
// initially, the agent believes that it hasn't received any temperature readings

/* Initial goals */
!set_up_plans. // the agent has the goal to add pro-rogue plans

/* 
 * Plan for reacting to the addition of the goal !set_up_plans
 * Triggering event: addition of goal !set_up_plans
 * Context: true (the plan is always applicable)
 * Body: adds pro-rogue plans for reading the temperature without using a weather station
*/
+!set_up_plans : true
<-
  // removes plans for reading the temperature with the weather station
  .relevant_plans({ +!read_temperature }, _, LL);
  .remove_plan(LL);
  .relevant_plans({ -!read_temperature }, _, LL2);
  .remove_plan(LL2);

  // Adds plan for relaying the temperature reading of the rogue leader agent.
  // Note 1: This plan now sends whatever the rogue leader agent is sending.
  // Note 2: No need for wating for multiple readings anymore.
  // Note 3: We need to abolish the temperature belief, otherwise the rogue could broadcast a deprecated reading.
  .add_plan({ +!read_temperature : temperature(RogueLeaderTempReading)[source(Agent)] & Agent == sensing_agent_9 <-
      .print("Read temperature (relaying) of rogue leader agent (Celcius): ", RogueLeaderTempReading);
      .broadcast(tell, temperature(RogueLeaderTempReading)) });
      .abolish(temperature(_));
  
  // Adds default plan for when the goal read_temperature has been received,
  // but no temperature reading has been received yet by the rogue leader agent.
  .add_plan({ +!read_temperature : true <-
    .print("Rogue agent needs to wait for the rogue leader agent to broadcast the temperature reading.");
    // temperature reading from rogue leader agent not yet received.
    // wait for 50ms and try again
    .wait(50);
    !read_temperature;
  }).

  

/* Import behavior of sensing agent */
{ include("sensing_agent.asl")}