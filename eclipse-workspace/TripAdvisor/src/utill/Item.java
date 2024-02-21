/**
 * This class represents an item
 */

package utill;

import java.util.HashSet;
import java.util.Set;

public class Item
{
	private String id; // the numeric ID of the item
	private String name; // the name of the item
	private Set<String> genres; // a hash set containing genres
	private Profile genomeScores; // a profile containing genome tag scores
	
	/**
	 * constructor - creates a new Item object
	 * @param id - the item id
	 * @param name - the item name
	 */
	public Item(final String id, final String name)
	{
		this.id = id;
		this.name = name;
		this.genres = new HashSet<String>();
		this.genomeScores = new Profile(id);
	}

	/**
	 * constructor - creates a new Item object
	 * @param id - the item id
	 * @param name - the item name
	 * @param genres - the set of genres associated with the item 
	 * @param genomeScores - the genome tag scores for the item
	 */
	public Item(final String id, final String name, final Set<String> genres, final Profile genomeScores)
	{
		this.id = id;
		this.name = name;
		this.genres = genres;
		this.genomeScores = genomeScores;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id - the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name - the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the genres
	 */
	public Set<String> getGenres() {
		return genres;
	}

	/**
	 * @param genres - the genres to set
	 */
	public void setGenres(Set<String> genres) {
		this.genres = genres;
	}

	/**
	 * @return the genomeScores
	 */
	public Profile getGenomeScores() {
		return genomeScores;
	}

	/**
	 * @param genomeScores - the genomeScores to set
	 */
	public void setGenomeScores(Profile genomeScores) {
		this.genomeScores = genomeScores;
	}	
}
