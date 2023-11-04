# Stream processing engine comparsion
The goal of the this project is to acquire expertise in different streaming technologies. These include Kafka Streams, Flink and Spark Structured Streaming.

## Use Case - Cheater detection in chess
Lichess.org has an open API that can serve information about chess games and players. We 	can use the API to calculate statistics about players and attempt to detect when a player uses a chess engine to generate their moves.
Kafka producers

### Producers
We have created four types of Kafka producers that query the Lichess API and produce their outputs to Kafka topics:
1. `Games producer` - queries https://lichess.org/api/tv/bullet to get a list of currently active bullet games. The output of this producer includes information such as the game ID, white and black player IDs, etc.
2. `Moves producer` - queries https://lichess.org/api/stream/game/{gameId} to get a stream of moves in a single game. The output contains information about each move played and the index of the move in the game (event timestamp)
3. `Game result producer` - queries https://lichess.org/api/stream/game/{gameId} to get information about when the game ended and what the result was
4. `Players producer` - queries https://lichess.org/api/user/{userId} to get information about players, such as their current rank in different game modes
<br/><br/><b>None of the topics guarantee correct order of the messages sent, and all of them can produce duplicate messages for the same event.</b>
<br/>Produced messages follow schemas described at lichess.api  for corresponding api calls. Some commonly used abbreviations are:
```
fen : Forsyth-Edwards Notation is the standard notation to describe positions of a chess game
bc, wc: black counter, white counter, reprsents time left for each player in seconds
perf: user’s performance for each game variant (e.g. blitz, bullet..) 
```

## Tasks
1. Deduplicate messages from each topic (Guarantee end-to-end exactly once semantics e.g. a game result message must be processed only once)
2. Use players producer as “initial import” of the players - that is, only consider the first message for each unique player. The goal is to use Yauza to calculate player statistics through time
3. For each player, calculate the following KPIs:
   - Win/loss count
   - Win rate
   - Number of total correct/incorrect moves - Use the Stockfish chess engine to determine the number of correct and incorrect moves the player makes
   - Mean player accuracy
   - Correct Incorrect moves ratio - Total number of correct moves / total number of incorrect moves
   - Macro - Calculate accuracy per game and then take the average of all accuracies
   - Median player accuracy - Calculate accuracy per game and then take the value at the 50th percentile
   - Standard deviation of accuracy
   - Whether a player is cheating - If a player has a streak of games where his accuracy is an outlier (e.g. a user has 3 games where his accuracy is higher than mean + 2 * standard deviation)

## Output topics:
### player-kpi-topic  
```
Key: playerId: string
Value: PlayerKpi.avsc
```
### suspicious-players 
```
Key: playerId: string
Value: SuspiciousPlayer.avsc 
````
## Streaming technology metrics
Other than the code, the output of Yauza should contain a document in which all of the used streaming technologies are compared. <br/> The list of metrics/characteristics that should be described:
- Latency
- Time domain skew
- Delivery guarantees
- State management - how is the state managed, recovering from faults
- Windowing - which types of windows are supported?
- Watermarking - how to know where the stream is?
- Time management - event time, processing time etc.
- Operational complexity - how is the stream processing program deployed? How easily can it be scaled? What extra infrastructure tools are necessary?
- Development experience - how good are the docs? How developed is the community - how easy/hard is it to find answers on forums? Which programming languages, testing frameworks are supported?
