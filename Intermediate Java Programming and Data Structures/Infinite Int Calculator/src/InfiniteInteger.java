
public class InfiniteInteger implements Comparable<InfiniteInteger>
{
	// -5+-5=-10 5+5=+10 10     -6+5=-1  6+-5=1  -1
	// TO DO: Instance Variables
	// Note that it is a good idea to declare them final to
	// prevent you from accidentally modify them.
	int[] number=new int[0];
	int length=0;
	boolean negative=false;
	boolean higher=false;
	/**
	 * Constructor: Constructs this infinite integer from a string
	 * representing an integer.
	 * @param s  a string represents an integer
	 */
	public InfiniteInteger(String s)
	{
		char[] hold=s.toCharArray();
		if (hold[0]=='-'){
			negative=true;
			int firstDigitIndex=checkLeadingZeros(hold);
			length=hold.length-firstDigitIndex;
			number=new int[length];
			if (length==0){
				number=new int[1];
				number[0]=0;
				length=1;
			}
			else{
				int index=0;
				for (;index<length;firstDigitIndex++){
					number[index]=Character.getNumericValue(hold[firstDigitIndex]);
					index++;
				}
			}
		}
		else{
			int firstDigitIndex=checkLeadingZeros(hold);
			length=hold.length-firstDigitIndex;
			number=new int[length];
			if (length==0){
				number=new int[1];
				number[0]=0;
				length=1;
			}
			else{
				int index=0;
				for (;index<length;firstDigitIndex++){
					number[index]=Character.getNumericValue(hold[firstDigitIndex]);
					index++;
				}
			}
		}
	}

	/**
	 * Constructor: Constructs this infinite integer from an integer.
	 * @param anInteger  an integer
	 */
	public InfiniteInteger(int anInteger)
	{
		String intString=Integer.toString(anInteger);
		char[] hold=intString.toCharArray();
		if (hold[0]=='-'){
			negative=true;
			int firstDigitIndex=checkLeadingZeros(hold);
			length=hold.length-firstDigitIndex;
			number=new int[length];
			if (length==0){
				number=new int[1];
				number[0]=0;
				length=1;
			}
			else{
				int index=0;
				for (;index<length;firstDigitIndex++){
					number[index]=Character.getNumericValue(hold[firstDigitIndex]);
					index++;
				}
			}
		}
		else{
			int firstDigitIndex=checkLeadingZeros(hold);
			length=hold.length-firstDigitIndex;
			number=new int[length];
			if (length==0){
				number=new int[1];
				number[0]=0;
				length=1;
			}
			else{
				int index=0;
				for (;index<length;firstDigitIndex++){
					number[index]=Character.getNumericValue(hold[firstDigitIndex]);
					index++;
				}
			}
		}
	}
	
	/**
	 * Gets the number of digits of this infinite integer.
	 * @return an integer representing the number of digits
	 * of this infinite integer.
	 */
	public int getNumberOfDigits()
	{
		return number.length;
	}

	/**
	 * Checks whether this infinite integer is a negative number.
	 * @return true if this infinite integer is a negative number.
	 * Otherwise, return false.
	 */
	public boolean isNegative()
	{
		if (number[0]<0){
			negative=true;
			return negative;
		}
		return negative;
	}

	/**
	 * Calculates the result of this infinite integer plus anInfiniteInteger
	 * @param anInfiniteInteger the infinite integer to be added to this
	 * infinite integer.
	 * @return a NEW infinite integer representing the result of this
	 * infinite integer plus anInfiniteInteger
	 */
	public InfiniteInteger plus(final InfiniteInteger anInfiniteInteger)
	{
		int[] newNumber=null;
		int carry=0;
		int addition=0;
		if (length>anInfiniteInteger.length){
			newNumber=new int[length+1];
		}
		else {
			newNumber=new int[anInfiniteInteger.length+1];
		}
		
		if ((anInfiniteInteger.isNegative() && isNegative()) || (!anInfiniteInteger.isNegative() && !isNegative())){
			int j = anInfiniteInteger.length-1;
			int k = newNumber.length-1;
			int i= length-1;
			for (;i>=0 || j>=0;i--){
				if (i<0 && j>=0){
					newNumber[k]=anInfiniteInteger.number[j];
					if (carry==1) newNumber[k]+=1;
					carry=0;
					if (newNumber[k]>=10){
						carry=1;
						newNumber[k]=newNumber[k]%10;
					}
				}
				else if (i>=0 && j<0){
					newNumber[k]=number[i];
					if (carry==1) newNumber[k]+=1;
					carry=0;
					if (newNumber[k]>=10){
						carry=1;
						newNumber[k]=newNumber[k]%10;
					}
				}
				else{
					addition=anInfiniteInteger.number[j]+number[i];
					if (carry==1){
						addition+=1;
						carry=0;
					}
					if (addition>=10){
						carry=1;
					}
					newNumber[k]=addition%10;
				}
				j--;
				k--;
			}
			if (carry==1) newNumber[k]=1;
			String newInt="";
			if (negative){
				newInt="-";
			}
			for (int c=0;c<newNumber.length;c++){
				newInt+=Integer.toString(newNumber[c]);
			}
			InfiniteInteger newInfiniteInteger=new InfiniteInteger(newInt);
			return newInfiniteInteger;
		}
		else{
			int j = anInfiniteInteger.length-1;
			int k = newNumber.length-1;
			int i=length-1;
			if (i>j) higher=true;
			else if (i<j) anInfiniteInteger.higher=true;
			else{
				for (int a=0;a<length;a++){
					if (number[a]>anInfiniteInteger.number[a]){
						higher=true;
						break;
					}
					else if (anInfiniteInteger.number[a]>number[a]){
						anInfiniteInteger.higher=true;
						break;
					}
				}
			}
			if (higher){
				while (i>=0 || j>=0){
					if (j<0){
						addition=number[i];
					}
					else{
						addition=number[i]-anInfiniteInteger.number[j];
					}
					if (carry==1){
						addition-=1;
						carry=0;
					}
					if (addition<0){
						addition+=10;
						carry=1;
					}
					newNumber[k]=addition;
					k--;
					i--;
					j--;
				}
			}
			else {
				while (i>=0 || j>=0){
					if (i<0){
						addition=anInfiniteInteger.number[j];
					}
					else{
						addition=anInfiniteInteger.number[j]-number[i];
					}
					if (carry==1){
						addition-=1;
						carry=0;
					}
					if (addition<0){
						addition+=10;
						carry=1;
					}
					newNumber[k]=addition;
					k--;
					i--;
					j--;
				}
			}
		}
		String newInt="";
		if ((higher && negative)){
			newInt="-";
		}
		else if (anInfiniteInteger.higher && anInfiniteInteger.negative){
			newInt="-";
			
		}
		for (int i=0;i<newNumber.length;i++){
			newInt+=Integer.toString(newNumber[i]);
		}
		InfiniteInteger newInfiniteInteger=new InfiniteInteger(newInt);
		return newInfiniteInteger;
	}

	/**
	 * Calculates the result of this infinite integer subtracted by anInfiniteInteger
	 * @param anInfiniteInteger the infinite integer to subtract.
	 * @return a NEW infinite integer representing the result of this
	 * infinite integer subtracted by anInfiniteInteger
	 */
	public InfiniteInteger minus(final InfiniteInteger anInfiniteInteger)
	{
		int[] newNumber;
		int carry=0;
		int subtraction=0;
		boolean same=false;
		if (length>anInfiniteInteger.length){
			newNumber=new int[length+1];
		}
		else {
			newNumber=new int[anInfiniteInteger.length+1];
		}
		int j = anInfiniteInteger.length-1;
		int k = newNumber.length-1;
		int i=length-1;
		if (i==j){
			for (int a=0;a<length;a++){
				if (number[a]>anInfiniteInteger.number[a]){
					higher=true;
					break;
				}
				else if (anInfiniteInteger.number[a]>number[a]){
					anInfiniteInteger.higher=true;
					break;
				}
			}
			if (!higher && !anInfiniteInteger.higher) same=true;
		}
		else if (i>j) higher=true;
		else if (j>i) anInfiniteInteger.higher=true;
		
		if ((anInfiniteInteger.isNegative() && isNegative()) || (!anInfiniteInteger.isNegative() && !isNegative())){
			if (higher || i>j){
				while (i>=0 || j>=0){
					if (j<0){
						subtraction=number[i];
					}
					else{
						subtraction=number[i]-anInfiniteInteger.number[j];
					}
					if (carry==1){
						subtraction-=1;
						carry=0;
					}
					if (subtraction<0){
						subtraction+=10;
						carry=1;
					}
					newNumber[k]=subtraction;
					k--;
					i--;
					j--;
				}
			}
			else {
				while (i>=0 || j>=0){
					if (i<0){
						subtraction=anInfiniteInteger.number[j];
					}
					else{
						subtraction=anInfiniteInteger.number[j]-number[i];
					}
					if (carry==1){
						subtraction-=1;
						carry=0;
					}
					if (subtraction<0){
						subtraction+=10;
						carry=1;
					}
					newNumber[k]=subtraction;
					k--;
					i--;
					j--;
				}
			}
			String newInt="";
			if ((negative && anInfiniteInteger.negative) && same) newInt="0";
			else if (((!negative && !anInfiniteInteger.negative) && same)) newInt="0";
			else if (negative && higher) newInt="-";
			else if (!anInfiniteInteger.negative && anInfiniteInteger.higher) newInt="-";
			for (int c=0;c<newNumber.length;c++){
				newInt+=Integer.toString(newNumber[c]);
			}
			InfiniteInteger newInfiniteInteger=new InfiniteInteger(newInt);
			return newInfiniteInteger;
		}
		else{
			for (;i>=0 || j>=0;i--){
				if (i<0 && j>=0){
					newNumber[k]=anInfiniteInteger.number[j];
					if (carry==1) newNumber[k]+=1;
					carry=0;
					if (newNumber[k]>=10){
						carry=1;
						newNumber[k]=newNumber[k]%10;
					}
				}
				else if (i>=0 && j<0){
					newNumber[k]=number[i];
					if (carry==1) newNumber[k]+=1;
					carry=0;
					if (newNumber[k]>=10){
						carry=1;
						newNumber[k]=newNumber[k]%10;
					}
				}
				else{
					subtraction=anInfiniteInteger.number[j]+number[i];
					if (carry==1){
						subtraction+=1;
						carry=0;
					}
					if (subtraction>=10){
						carry=1;
					}
					newNumber[k]=subtraction%10;
				}
				j--;
				k--;
			}
			if (carry==1) newNumber[k]=1;
			String newInt="";
			if (negative & higher) newInt="-";
			else if (negative & !higher) newInt="-";
			for (int c=0;c<newNumber.length;c++){
				newInt+=Integer.toString(newNumber[c]);
			}
			InfiniteInteger newInfiniteInteger=new InfiniteInteger(newInt);
			return newInfiniteInteger;
		}
	}

	/**
	 * Calculates the result of this infinite integer multiplied by anInfiniteInteger
	 * @param anInfiniteInteger the multiplier.
	 * @return a NEW infinite integer representing the result of this
	 * infinite integer multiplied by anInfiniteInteger.
	 */
	public InfiniteInteger multiply(final InfiniteInteger anInfiniteInteger)
	{
		InfiniteInteger newII=null;
		int[] currentNumber;
		int multiply=0;
		int count=0;
		int carry=0;
		int i=number.length-1;
		String newInt="";
		int j=anInfiniteInteger.number.length-1;
		int m=number.length+anInfiniteInteger.number.length+1;
		currentNumber=new int[m];
		int k=currentNumber.length-1;
		for (;j>=0;j--){
			currentNumber=new int[m];
			k=currentNumber.length-count-1;
			i=number.length-1;
			for (;i>=0;i--){
				multiply=number[i]*anInfiniteInteger.number[j];
				multiply+=carry;
				carry=0;
				while (multiply>9){
					multiply-=10;
					carry+=1;
				}
				currentNumber[k]=multiply;
				k--;
			}
			if (carry>0){
				currentNumber[k]=carry;
				carry=0;
			}
			count+=1;
			if (count==1){
				for (int c=0;c<currentNumber.length;c++){
					newInt+=Integer.toString(currentNumber[c]);
				}
				newII= new InfiniteInteger(newInt);
				newInt="";
			}
			else{
				for (int c=0;c<currentNumber.length;c++){
					newInt+=Integer.toString(currentNumber[c]);
				}
				InfiniteInteger addToNewII= new InfiniteInteger(newInt);
				newII=newII.plus(addToNewII);
				newInt="";
			}
		}
		if ((negative && !anInfiniteInteger.negative) || (!negative && anInfiniteInteger.negative)){
			newInt="-" + newII.toString();
			newII=new InfiniteInteger(newInt);
		}
		if (newII.toString().equals("-0")){
			newII=new InfiniteInteger(0);
		}
		return newII;
	}
	
	/**
	 * Generates a string representing this infinite integer. If this infinite integer
	 * is a negative number a minus symbol should be in the front of numbers. For example,
	 * "-12345678901234567890". But if the infinite integer is a positive number, no symbol
	 * should be in the front of the numbers (e.g., "12345678901234567890").
	 * @return a string representing this infinite integer number.
	 */
	public String toString()
	{
		String printNumber="";
		if (negative){
			printNumber="-";
		}
		for (int i=0;i<number.length;i++){
			printNumber+=Integer.toString(number[i]);
		}
		return printNumber;
	}
	
	/**
	 * Compares this infinite integer with anInfiniteInteger
	 * @return either -1, 0, or 1 as follows:
	 * If this infinite integer is less than anInfiniteInteger, return -1.
	 * If this infinite integer is equal to anInfiniteInteger, return 0.
	 * If this infinite integer is greater than anInfiniteInteger, return 1.
	 */
	public int compareTo(final InfiniteInteger anInfiniteInteger)
	{
		if (negative && !anInfiniteInteger.negative){
			return -1;
		}
		else if (!negative && anInfiniteInteger.negative) return 1; 
		else if (!negative && !anInfiniteInteger.negative){
			if (length>anInfiniteInteger.getNumberOfDigits()) return 1;
			else if (length<anInfiniteInteger.getNumberOfDigits()){
				return -1;
			}
			else{
				for (int i=0;i<number.length;i++){
					if (number[i]<anInfiniteInteger.number[i]){
						return -1;
					}
					else if (number[i]>anInfiniteInteger.number[i]) return 1;
				}
				return 0;
			}
		}
		else {
			if (length>anInfiniteInteger.getNumberOfDigits()){
				return -1;
			}
			else if (length<anInfiniteInteger.getNumberOfDigits()) return 1;
			else{
				for (int i=0;i<number.length;i++){
					if (number[i]<anInfiniteInteger.number[i]) return 1;
					else if (number[i]>anInfiniteInteger.number[i]){
						return -1;
					}
				}
				return 0;
			}
		}
	}
	
	private int checkLeadingZeros(char[] hold){
		int holdLength=hold.length;
		boolean isZero=false;
		int index=0;
		while (!isZero&&index<holdLength){
			if (hold[index]=='0' || hold[index]=='-'){
				index++;
			}
			else isZero=true;
		}
		return index;
	}
}