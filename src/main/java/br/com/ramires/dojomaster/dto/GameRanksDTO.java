package br.com.ramires.dojomaster.dto;

public class GameRanksDTO {

	String ranksReport = null;

	public GameRanksDTO() {
	}
	public GameRanksDTO(String ranksReport) {
		this.ranksReport = ranksReport;
	}

	public void setRanksReports(String ranksReport) {
		this.ranksReport = ranksReport;
	}
	public String getRanksReports() {
		return this.ranksReport;
	}
}
