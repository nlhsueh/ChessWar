# ChessWar

ChessWare 是一個教育用的框架，用來學習
* 軟體框架
* AI 象棋

The packages
* default
	* class Main: launch the program
* AI
	* AbstractPlayer: 
		* chesses, chessSide, 		
		* a constructor to set the chesses and side
		* the nextMove is abstract, should be defined by your AI
	* BuildInAI: a stupid AI, serves as a demo, shows how to "extends" AbstractPalyer
	* CommunicateObserver: a strange class. Maybe the student mis-understand the Subject design pattern
	* ChessUtility: 提供象棋狀態檢查的一些功能
		* isGameOver(Chess[])
		* couldMove(Chess[], chess, Point)
		* hasChess(Chess[], Point): is there any chess located at that point?
		* getChess(Chess[], Point)
		* hasMyChess(Chess[], Point, ChessSide)
		* kingExist(Chess[], Point, C		hessSide): if the opponent's king is at the Point
		* countObstacle(Chess[], Chess, Point): number of obstacle chess between a chess and a point //計算某棋子到某點中間有多少（障礙）棋子

* ChessGame
	* Chess: A GUI object, but also implement Observer interface. Encapsulate the basic information of a chess such as name, image, live or not, side, index etc.
	* ChessBoard: A GUI object, defining the layout of a chess board, such as its background, chess allocation.
	* ChessMap: transform the format of a chess location. (x,y) to Point
		* NEED MORE STDUY. The difference is not clear
	* ChessRule: check if a chess can move or eat other chesses
	* ChessSide: an enum type: ChessSide.BLACK or ChessSide.RED
	* GameModel: a huge class that include every class
	* GameControl: implements MouseListener and MouseMotion; interact with user and control with GameModel
	* PlayerInfo: a small class just model the player's information 

* Develop
	* DevelopAI: 
	
* DevelopPattern: 開發模式
	* 
	
* NormalPattern: 一般模式
	* 一班

更多的訊息：
* https://sites.google.com/view/chesswar
