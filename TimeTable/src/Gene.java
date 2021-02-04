import java.util.*;

public class Gene implements Cloneable {
	//Gene == Class
	//������(����) �ϳ��� ������ ���� ������ ������.

	//��米��
	//�����
	//�ð��븦 ������ 18���� ����*5(����)
	//���ǽ� ������ ������ ����

	public String  professor;
	public String  className;
	public int[][] timeTable;
	public int[][] classTable;
	public int[]   dayVector;
	public int	   theory;
	public int	   practice;

	public Gene(String professor, String classInfo) {
		this.timeTable = new int[5][18];
		this.classTable = new int[5][9]; //0~5�̷�, 6~8 �ǽ�
		this.dayVector = new int[5]; //���� ���� ����
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
			//�̷�
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
			//�ǽ�
			hour1 = (int)(Math.random()*15);
			hour2 = (int)(Math.random()*15);
			if((int)(Math.random()*2)==0) { //�ǽ�2 �ǽ�2
				class1 = (int)(Math.random()*3+6);
				class2 = (int)(Math.random()*3+6);
			}else { //�ǽ�2 �̷�2
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
		
		//�ݿ��Ͽ� ������ �����Ѱ�? +10
		if(dayVector[4]==1) {
			if(	professor.equals("���汳") || 
					professor.equals("��ȣ��") || 
					professor.equals("��ȣ��") || 
					professor.equals("��Ȳ��") || 
					professor.equals("���м�") || 
					professor.equals("��ȭ��") ) {

			}else {
				penalty+=10;
			}
		}

		//���ɽð��� ������ �����Ѱ�? +10
		for(int i=0; i<dayVector.length; i++) {
			if(dayVector[i]==1) {
				if(this.timeTable[i][6]==1 || this.timeTable[i][7]==1) {
					if( professor.equals("�踸��") ||
							classTable[i][6] == 1 ||
							classTable[i][7] == 1 ||
							classTable[i][8] == 1 ) {

					}else {
						penalty+=15;
					}
				}
			}
		}

		//����� �����԰� ���м� �������� 10�� ���� ������ �����Ǿ��°�? +10
		if(professor.equals("���м�") || professor.equals("�����")) {
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
		str.append("\n\n����:"+this.professor+"\n");
		str.append("�����:"+this.className+"("+this.theory+"/"+this.practice+")"+"\n");
		str.append("�ð�ǥ"+Arrays.deepToString(this.timeTable)+"\n");
		str.append("���ǽ�"+Arrays.deepToString(this.classTable)+"\n\n");
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
