package Modelos;

public class ModeloNote {

	    private int id;
	    private String users_id;
	    private String course_id;
	    private String student_id;
	    private String note_detail_id;
	    
	    public ModeloNote() {
	    	
	    }
	    
	    // Getters and Setters
	   
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getUsers_id() {
			return users_id;
		}

		public void setUsers_id(String users_id) {
			this.users_id = users_id;
		}

		public String getCourse_id() {
			return course_id;
		}

		public void setCourse_id(String course_id) {
			this.course_id = course_id;
		}

		public String getStudent_id() {
			return student_id;
		}

		public void setStudent_id(String student_id) {
			this.student_id = student_id;
		}
		
		public String getNote_detail_id() {
		        return note_detail_id;
		}
		    
		public void setNote_detail_id(String note_detail_id) {
		        this.note_detail_id = note_detail_id;
		 }
		
	    // toString() method
	    @Override
	    public String toString() {
	        StringBuilder sb = new StringBuilder();
	        String horizontalLine = "-------------------------------------------------------------------------------------";
	        sb.append(horizontalLine).append("\n");
	        sb.append("|   Id   |   Usuario    |          Curso            | Estudiante | Nota |\n");
	        sb.append(horizontalLine).append("\n");
	        sb.append(String.format("|%8d|%12s|%8s|%13s|%7s|\n", id, users_id, course_id, student_id, note_detail_id));
	        sb.append(horizontalLine);

	        return sb.toString();
	    }




	}