package gr.sullenart.games.fruitcatcher.text;

public class TextResources {

	private String locale;
	
	public TextResources(String locale) {
		this.locale = locale;
	}
	
	private static String [] helpLines = {

		"Eating fruits is good for your health. As fruits fall",
		"from the sky you can try to catch as many as you",
		"can. But wait, you must prefer in-season fruits. These",
		"are better both for your health and the environment.",
		"",
		"When fruits are falling, slide your device to move the",
		"basket and catch them. Alternatively you can touch",
		"at the point, where you want the basket to move. An",
		"out ofseason fruit is worth 1 point. In-season fruits",
		"are worth double. Try to catch falling stars for",
		"bonus points. Avoid bad apples, which will decrease",
		"your score.",
		"",
		"The game consists of three levels. In every level you",
		"need to beat 4 challenges - one challenge for every",
		"season. At the beginning of the challenge you see the",
		"fruits you should prefer and the number of points",
		"you need to collect. If you manage to reach or exceed",
		"the goal, you advance to the next level. Otherwise",
		"you lose and the game is over. The difficulty",
		"increases with every new challenge and level. The",
		"first time you beat all challenges in Level 1 and 2,",
		"you unlock the next level. Next time you can start",
		"from that level and not from the beginning. When",
		"you beat all 4 challenges in level 3, you start",
		"playing it from the beginning. Try to collect as",
		"many points in total as possible and write your",
		"name in the high score table.",
		"",
		"When you leave the application the game isn't ended,",
		"but it is paused. You can come back at a later time to",
		"catch all the fruits you need. Have fun."
	};
	
	private static String [] de_helpLines = {

		"Früchte fangen",
		"",
		"Es ist gut für die Gesundheit, Früchte zu essen. Als",
		"Früchte vom Himmel fallen, versuchen Sie, so viele",
		"wie möglich zu fangen. Aber warten Sie, Sie müssen",
		"in der Saison Früchte bevorzugen. Diese sind besser,",
		"sowohl für Ihre Gesundheit und die Umwelt.",
		"",
		"Wenn Früchte fallen, schieben Sie Ihr Gerät, um den",
		"Korb zu bewegen. Alternativ können Sie an der Stelle",
		"berühren, wo Sie den Korb verschieben möchten.",
		"Eine nicht in der Saison Frucht ist 1 Punkt wert.",
		"In der Saison Früchte sind 2 Punkte wert. Versuchen", 
		"Sie, Sterne für Bonus-Punkte zu fangen.",
		"Vermeiden Sie faule Äpfel!",
		"", 
		"Das Spiel besteht aus drei Level. In jedem Level",
		"müssen Sie 4 Herausforderungen schlagen - eine",
		"Herausforderung für jede Jahreszeit. Anfangs sehen",
		"Sie, die Früchte die Sie bevorzugen sollen und die" ,
		"Anzahl der Punkte, die Sie brauchen um zu gewinnen.",
		"Wenn Sie das Ziel erreichen oder übertreffen, sind " ,
		"Sie zum nächsten Level gelangen. Ansonsten" ,
		"verlieren Sie und das Spiel ist vorbei. Der" ,
		"Schwierigkeitsgrad steigt mit jeder neuen" ,
		"Her ausforderung.Das erste, Mal wenn Sie alle" ,
		"Herausforderungen in Level 1 und 2 schlagen," ,
		"entsperren Sie das nächste Level. Das nächste Mal" ,
		"können Sie von dort und nicht von vorne beginnen.",
		"Wenn Sie alle 4 Herausforderungen in Level 3",
		"schlagen, spielen Sie es wieder von Anfang.",
		"Versuchen Sie, so viele Punkte wie möglich in",
		"insgesamt zu sammeln und schreiben Sie Ihren",
		"Namen in der Highscore-Tabelle.",
		"",
		"Wenn Sie die Anwendung verlassen, ist das Spiel",
		"nicht beendet. Sondern wird es angehalten. Sie",
		"können später wieder kommen, um mehr Früchte zu",
		"fangen. Viel Spaß."
	};	
	
	public String getDefaultPlayerName() {
		if (locale.equals("de")) {
			return "Spieler";
		}
		return "player";
	}
	
	public String [] getHelpLines() {
		if (locale.equals("de")) {
			return de_helpLines;
		}		
		return helpLines;
	}
}
