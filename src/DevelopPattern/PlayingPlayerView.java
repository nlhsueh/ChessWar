package DevelopPattern;

import ChessGame.ChessSide;
import Share.AbstractPlayingPlayerView;
import Share.GameSetting;
import Share.PlayingLastMoveView;
import Share.PlayingTimeoutView;

public class PlayingPlayerView extends AbstractPlayingPlayerView{

	private static final long serialVersionUID = 1L;
	
	public PlayingPlayerView(GameSetting gameSetting, int player){
		
		this.gameSetting = gameSetting;
		/* 設定PlayerInfo裡的Player Number參數 與 panel Attributes 設定 */
		this.setLayout(null);
		this.setSize(167,555);
		this.setOpaque(false);
		if(player == 1){
			this.setLocation(10, 10);
			this.playerInfo.setPlayerColor(ChessSide.RED);
			this.playerInfo.setColorWord("紅子");
			this.playerInfo.setColorChess(PlayingPlayerView.colorRed);
			this.playerInfo.setPlayerType(this.gameSetting.player1); 
			
			this.playerInfoView = new PlayingPlayerInfoView(this, this.playerInfo);
		}else if(player == 2){
			this.setLocation(747, 10);
			this.playerInfo.setPlayerColor(ChessSide.BLACK);
			this.playerInfo.setColorWord("黑子");
			this.playerInfo.setColorChess(PlayingPlayerView.colorBlack);
			this.playerInfo.setPlayerType(this.gameSetting.player2); 
			
			this.playerInfoView = new PlayingPlayerInfoView(this, this.playerInfo);
		}
		// 初始化思考時間Panel
		this.playerTimeoutView = new PlayingTimeoutView(this, this.playerInfo, this.gameSetting.timeout);
		// 初始化最後一手Panel
		this.playerLastMoveView = new PlayingLastMoveView(this, this.playerInfo) ;
	}
	
}
