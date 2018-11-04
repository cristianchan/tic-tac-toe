# TIC-TAC-TOE

## Configuration File
To set board size, player names, symbols set this configuration in config.properties file en the next route

`src/main/resources`

```console

board.size=4 (this number should be values between 3 and 10)
player.number=3 (this number should be 2 or 3 when the number is 3 the cpu player is on)

player-one.name=Jose
player-one.symbol=X

player-two.name=Joe
player-two.symbol=O

player-tree.name=CPU
player-tree.symbol=Z

```
## Run 

```console
./gradlew jar
java -jar ./build/libs/tic-tac-toe-1.0-SNAPSHOT.jar
```

## Play 
Select postition separate by coma (column,row)

```
     1   2   3   4
1  |   |   |   |   |   
2  |   |   |   |   |   
3  |   |   |   |   |   
4  |   |   |   |   |   

Jose Select X,Y position
2,2
```
Position selection next turn.

```
     1   2   3   4
1  |   |   |   |   |   
2  |   | X |   |   |   
3  |   |   |   |   |   
4  |   |   |   |   |   

Joe Select X,Y position
1,1

```
Computer select position automatically

```
CPU Select X,Y position

     1   2   3   4
1  | O | Z |   |   |   
2  |   | X |   |   |   
3  |   |   |   |   |   
4  |   |   |   |   |  

```

