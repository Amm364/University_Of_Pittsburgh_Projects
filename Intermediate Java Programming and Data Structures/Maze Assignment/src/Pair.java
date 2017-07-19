
public class Pair<T1,T2>
{
	private T1 first;
	private T2 second;
	
	public Pair(T1 aFirst, T2 aSecond)
	{
		first = aFirst;
		second = aSecond;
	}
	
	/* Gets the first element of this pair.
	 * @return the first element of this pair.
	 */
	public T1 fst()
	{
		return first;
	}
	
	/* Gets the second element of this pair.
	 * @return the second element of this pair.
	 */
	public T2 snd()
	{
		return second;
	}
	
	/* Generates a string representing this pair. Note that
	 * the String representing the pair (x,y) is "(x,y)". There
	 * is no whitespace unless x or y or both contain whitespace
	 * themselves.
	 * @return a string representing this pair.
	 */
	public String toString()
	{
		return "(" + first + "," + second + ")";
	}
}