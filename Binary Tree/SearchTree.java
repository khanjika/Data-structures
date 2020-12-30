
public class SearchTree {

	// Defining a Treenode to store the root of binary tree
	public TreeNode root;

	// A new binary tree is empty. It contains no Treenode.
	public SearchTree() {
		TreeNode root = null;
	}

	/*
	 * This method adds the key to the tree if it is unique
	 * 
	 * @param key This parameter provides the key to be added to the tree
	 * 
	 * @return boolean This returns true if key is added to tree else false incase
	 * of any error in adding
	 */
	public boolean add(String key) {
		// To check if key is null or empty
		if (key == null || key == "") {
			return false;
		} else {
			// Check if root is added to the tree
			if (root == null) {
				// Add first element to the tree
				root = new TreeNode(key.toUpperCase());
				return true;
			} else {
				// Append element to the end of the tree
				boolean ans = root.insertNode(key.toUpperCase(), root);
				return ans;
			}
		}

	}

	/*
	 * This method searches for “key” in the tree and rotates the tree to move the
	 * child closer to the root if the search count of child is greater than parent
	 * 
	 * @param key This parameter provides the key to be searched in the tree
	 * 
	 * @return boolean This returns the depth of the Treenode in the tree and
	 * returns 0 if not found or if some error occurs
	 */
	public int find(String key) {

		TreeNode current = root;
		int depth = 1;

		// To check if key is null or empty
		if (key == null || key == "") {
			return 0;
		} else {
			// Walk the path from root to where the node should be
			while (current != null && (current.data.compareTo(key.toUpperCase()) != 0)) {
				// Check if key is the right or left child of the parent
				if (current.data.compareTo(key.toUpperCase()) < 0) {
					// Increment the depth
					depth = depth + 1;
					current = current.rightChild;
				} else {
					depth = depth + 1;
					current = current.leftChild;
				}
			}
			// If the key is not found
			if (current == null) {
				return 0;
			} else {
				// Increment the count for the key
				current.count = current.count + 1;
				// Check if the key is not same as that of the root
				if (current.data != root.data) {
					// Rotate tree if search count of child is greater than parent
					boolean flag = current.treeRotate();
					if (flag == true) {
						// Changing the root if rotation takes place between root and its child
						root = current;
					}
				}
				return depth;
			}
		}
	}

	/*
	 * This method prints the treenode present in the tree along with its depth from
	 * the root
	 * 
	 * @return String This returns the treenode in sorted order along with its depth
	 * from the root else returns null incase tree is empty
	 */
	public String printTree() {
		String print;
		// To check if Treenode is present in the tree
		if (root != null) {
			// to traverse through the tree
			print = root.traverse();
			return print;
		} else {
			return null;
		}

	}

	/*
	 * This method resets the search counter for all treenode in the tree
	 */
	public void reset() {
		// To check if Treenode is present in the tree
		if (root != null) {
			// To reset the search counter for all the nodes
			root.resetNode();
		}
	}

}
