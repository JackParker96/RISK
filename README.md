## Objective

Conquer the world and take over every territory on the map to win!

## Map

Initial territory allocation is automatically determined at the start of the game. The world consists of 24 territories, split equally among the players. Each player begins with 30 units to distribute throughout their territories.

![Alt text](maps.JPG)

## Setup

- Determine the number of players (2-4)
- Open one terminal for the server and a terminal for each player (can be on separate computers)
- In the server terminal, enter: **./gradlew :server:run --args "4444 \[number of players]"**
- In each player terminal, enter: **./gradlew :client:run --args "\[server name] 4444"**
- Follow in-game instructions to play game
