package receive;

import java.util.Date;

public class Receive {
	private String subject;
	private String from;
	private Date sentday;
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public Date getSentday() {
		return sentday;
	}
	public void setSentday(Date sentday) {
		this.sentday = sentday;
	}
}
