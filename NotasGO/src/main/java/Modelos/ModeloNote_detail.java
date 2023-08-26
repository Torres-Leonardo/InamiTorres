package Modelos;

public class ModeloNote_detail {
	private Integer id;
	private String student_id;
	private String competence_id;
	private String note;
	private String date_note;
	
public ModeloNote_detail() {
}

public ModeloNote_detail(Integer id, String student_id, String competence_id, String note, String date_note) {
    this.id = id;
    this.student_id = student_id;
    this.competence_id = competence_id;
    this.note = note;
    this.date_note = date_note;
}

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}


public String getStudent_id() {
	return student_id;
}

public void setStudent_id(String student_id) {
	this.student_id = student_id;
}

public String getCompetence_id() {
	return competence_id;
}

public void setCompetence_id(String competence_id) {
	this.competence_id = competence_id;
}

public String getNote() {
	return note;
}

public void setNote(String note) {
	this.note = note;
}

public String getDate_note() {
	return date_note;
}

public void setDate_note(String date_note) {
	this.date_note = date_note;
}

@Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    String horizontalLine = "------------------------------------------------------------------------------------------------------------------------------------------------";
    sb.append(horizontalLine).append("\n");
    sb.append("|  ID  |   ESTUDIANTE   |     COMPETENCIA     |                       NOTA                      |       FECHA DE REGISTRO      |\n");
    sb.append(horizontalLine).append("\n");
    sb.append(String.format("|%6d|%16s|%14s|%13s|%17s|\n", id, student_id, competence_id, note, date_note));
    sb.append(horizontalLine);

    return sb.toString();
}

}
