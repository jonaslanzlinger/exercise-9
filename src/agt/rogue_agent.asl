// rogue agent is a type of sensing agent

/* Initial beliefs and rules */
// initially, the agent believes that it hasn't received any temperature readings
received_readings([]).
all_present_agents_witness_ratings[
  [sensing_agent_1, -1],
  [sensing_agent_2, -1],
  [sensing_agent_3, -1],
  [sensing_agent_4, -1],
  [sensing_agent_5, 1],
  [sensing_agent_6, 1],
  [sensing_agent_7, 1],
  [sensing_agent_8, 1],
  [sensing_agent_9, 1]
].

/* Initial goals */
!set_up_plans. // the agent has the goal to add pro-rogue plans

/* 
 * Plan for reacting to the addition of the goal !set_up_plans
 * Triggering event: addition of goal !set_up_plans
 * Context: true (the plan is always applicable)
 * Body: adds pro-rogue plans for reading the temperature without using a weather station
*/
+!set_up_plans : all_present_agents_witness_ratings(WR) <-

  // removes plans for reading the temperature with the weather station
  .relevant_plans({ +!read_temperature }, _, LL);
  .remove_plan(LL);
  .relevant_plans({ -!read_temperature }, _, LL2);
  .remove_plan(LL2);

  .print("Rogue: Sending witness reputation to acting agent at startup.");
  .print(WR);
  for ( .member([TargetAgent, WRating], WR) ) {
		if (TargetAgent /== .my_name()) {
			.send(acting_agent, tell, witness_reputation(.my_name(), TargetAgent, WRating));
		}
	};

  // adds a new plan for reading the temperature that doesn't require contacting the weather station
  // the agent will pick one of the first three temperature readings that have been broadcasted,
  // it will slightly change the reading, and broadcast it
  .add_plan({ +!read_temperature : received_readings(TempReadings)[source(sensing_agent_9)] &
    .length(TempReadings) >=3
    <-
    .print("Reading the temperature");

    //picks one of the 3 first received readings randomly
    .random([0,1,2], SourceIndex);
    .reverse(TempReadings, TempReadingsReversed);
    .print("Received temperature readings: ", TempReadingsReversed);
    .nth(SourceIndex, TempReadingsReversed, Celcius);

    // adds a small deviation to the selected temperature reading
    .random(Deviation);

    // broadcasts the temperature
    .print("Read temperature (Celcius): ", Celcius + Deviation);
    .broadcast(tell, temperature(Celcius + Deviation)) });

  // adds plan for reading temperature in case fewer than 3 readings have been received
  .add_plan({ +!read_temperature : received_readings(TempReadings)[source(sensing_agent_9)] &
    .length(TempReadings) < 3
    <-

    // waits for 2000 milliseconds and finds all beliefs about received temperature readings
    .wait(2000);
    .findall(TempReading, temperature(TempReading)[source(Ag)], NewTempReadings);

    // updates the belief about all reaceived temperature readings
    -+received_readings(NewTempReadings);

    // tries again to "read" the temperature
    !read_temperature });
  
    // adds default plan for when a temperature reading has been received by an agent that is NOT the rogue leader agent
  .add_plan({ +!read_temperature : received_readings(TempReadings) <-
    .print("Rogue agent is only listening on temperature readings from the rogue leadger agent."); }).

/* Import behavior of sensing agent */
{ include("sensing_agent.asl")}