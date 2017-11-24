import java.util.*;

public class Gene implements Cloneable {
	//Gene == Class
	//유전자(과목) 하나는 다음과 같은 정보를 가진다.

	//담당교수
	//과목명
	//시간대를 가지는 18개의 벡터*5(요일)
	//강의실 정보를 가지는 벡터

	public String  professor;
	public String  className;
	public int[][] timeTable;
	public int[][] classTable;
	public int[]   dayVector;
	public int	   theory;
	public int	   practice;

	public Gene(String professor, String classInfo) {
		this.timeTable = new int[5][18];
		this.classTable = new int[5][9]; //0~5이론, 6~8 실습
		this.dayVector = new int[5]; //강의 요일 벡터
		this.professor = professor;
		this.className = classInfo.split("-")[0];
		this.theory	   = Integer.parseInt(classInfo.split("-")[1].split("/")[0]);
		this.practice  = Integer.parseInt(classInfo.split("-")[1].split("/")[1]);
	}

	public void setRandom() {
		int day1,day2 = 999;

		day1 = (int)(Math.random()*5);
		while(day1==day2 || day2==999)
			day2 = (int)(Math.random()*5);

		this.dayVector[day1]=1;
		this.dayVector[day2]=1;

		int hour1,hour2;
		int class1,class2;
		if(practice==0) {
			//이론
			hour1 = (int)(Math.random()*16);
			hour2 = (int)(Math.random()*16);
			class1 = (int)(Math.random()*6);
			class2 = (int)(Math.random()*6);

			this.timeTable[day1][hour1]=1;
			this.timeTable[day1][hour1+1]=1;
			this.timeTable[day1][hour1+2]=1;
			this.classTable[day1][class1]=1;

			this.timeTable[day2][hour2]=1;
			this.timeTable[day2][hour2+1]=1;
			this.timeTable[day2][hour2+2]=1;
			this.classTable[day2][class2]=1;
		}
		else {
			//실습
			hour1 = (int)(Math.random()*15);
			hour2 = (int)(Math.random()*15);
			if((int)(Math.random()*2)==0) { //실습2 실습2
				class1 = (int)(Math.random()*3+6);
				class2 = (int)(Math.random()*3+6);
			}else { //실습2 이론2
				class1 = (int)(Math.random()*6);
				class2 = (int)(Math.random()*3+6);
			}

			this.timeTable[day1][hour1]=1;
			this.timeTable[day1][hour1+1]=1;
			this.timeTable[day1][hour1+2]=1;
			this.timeTable[day1][hour1+3]=1;
			this.classTable[day1][class1]=1;

			this.timeTable[day2][hour2]=1;
			this.timeTable[day2][hour2+1]=1;
			this.timeTable[day2][hour2+2]=1;
			this.timeTable[day2][hour2+3]=1;
			this.classTable[day2][class2]=1;
		}
	}

	public int getPenalty() {
		
		int penalty = 0;
		
		//금요일에 수업이 가능한가? +10
		if(dayVector[4]==1) {
			if(	professor.equals("정충교") || 
					professor.equals("석호식") || 
					professor.equals("권호열") || 
					professor.equals("최황규") || 
					professor.equals("김학수") || 
					professor.equals("김화종") ) {

			}else {
				penalty+=10;
			}
		}

		//점심시간에 수업이 가능한가? +10
		for(int i=0; i<dayVector.length; i++) {
			if(dayVector[i]==1) {
				if(this.timeTable[i][6]==1 || this.timeTable[i][7]==1) {
					if( professor.equals("김만배") ||
							classTable[i][6] == 1 ||
							classTable[i][7] == 1 ||
							classTable[i][8] == 1 ) {

					}else {
						penalty+=15;
					}
				}
			}
		}

		//이헌길 교수님과 김학수 교수님이 10시 이전 수업이 배정되었는가? +10
		if(professor.equals("김학수") || professor.equals("이헌길")) {
			for(int i=0; i<dayVector.length; i++) {
				if(dayVector[i]==1) {
					if(this.timeTable[i][0]==1 || this.timeTable[i][1]==1) {
						penalty+=5;
					}
				}
			}
		}

		return penalty;
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("\n\n교수:"+this.professor+"\n");
		str.append("과목명:"+this.className+"("+this.theory+"/"+this.practice+")"+"\n");
		str.append("시간표"+Arrays.deepToString(this.timeTable)+"\n");
		str.append("강의실"+Arrays.deepToString(this.classTable)+"\n\n");
		return str.toString();
	}
	
	public Gene clone(){
		Gene clone = null;
		try{
			clone = (Gene) super.clone();
			clone.timeTable = this.timeTable.clone();
			clone.classTable = this.classTable.clone();
			clone.dayVector = this.dayVector.clone();
		}catch(Exception e){

		}
		return clone; 
	} 

}
