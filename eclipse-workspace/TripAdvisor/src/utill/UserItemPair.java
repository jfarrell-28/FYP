/**
 * A class to store a user-item pair.
 */

package utill;

public class UserItemPair
{
	private String userId; // the numeric user ID
	private String itemId; // the numeric item ID 
	
	/**
	 * constructor - creates a new UserItemPair object
	 * @param userId - the numeric user ID
	 * @param itemId - the numeric item ID
	 */
	public UserItemPair(final String userId, final String itemId)
	{
		this.userId = userId;
		this.itemId = itemId;
	}
	
	/**
	 * @returns the user ID
	 */
	public String getUserId()
	{
		return userId;
	}

	/**
	 * @returns the item ID
	 */
	public String getItemId()
	{
		return itemId;
	}

	/**
	 * @returns a string representation of this object
	 */
	public String toString()
	{
		return userId + " " + itemId;
	}
}