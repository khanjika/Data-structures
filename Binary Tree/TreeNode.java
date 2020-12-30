
public class TreeNode {

	// To store the attributes of binary tree by keeping track of the
	// rightchild,leftchild,
	// parent of the Treenode, data present in the Treenode and how many times the
	// data in Treenode has been searched.
	public String data;
	public TreeNode leftChild;
	public TreeNode rightChild;
	public TreeNode parent;
	public int count = 0;

	public static int levelCount = 1;
	public static String keyDepth = "";

	// Create two constructors for the class:
	// - one that creates a Treenode with an initial string
	// - one that creates a Treenode with an initial string and parent of that node

	public TreeNode(String value, TreeNode parent) {
		this.data = value;
		this.parent = parent;
	}

	public TreeNode(String value) {
		this.data = value;
	}

	/*
	 * This method adds the key to the tree if it is unique
	 * 
	 * @param key This is the first parameter that provides the key to be added to
	 * the tree
	 * 
	 * @param key This is the second parameter that provides the information of the
	 * parent of inserted key
	 * 
	 * @return boolean This returns true if key is added to tree else false incase
	 * of any error in adding
	 */
	public boolean insertNode(String value, TreeNode parentData) {
		// To check if the given element is greater or smaller
		int comp = data.compareTo(value.toUpperCase());
		// If element is already present in the tree
		if (comp == 0) {
			return false;
		}
		// If given element is smaller
		else if (comp >= 1) {
			// Check for the leftchild and if present, Recursively traverse through the
			// tree.
			if (leftChild == null) {
				leftChild = new TreeNode(value.toUpperCase(), parentData);
			} else {
				boolean leftAns = leftChild.insertNode(value, leftChild);
				return leftAns;
			}
		}
		// if given element is greater
		else if (comp <= 1) {
			// Check for the rightchild and if present, Recursively traverse through the
			// tree.
			if (rightChild == null) {
				rightChild = new TreeNode(value.toUpperCase(), parentData);
			} else {
				boolean rightAns = rightChild.insertNode(value, rightChild);
				return rightAns;
			}
		}
		return true;
	}

	/*
	 * This method rotates the tree if the search count of child is greater than the
	 * parent.
	 * 
	 * @return boolean This returns true if the rotation takes place between root
	 * and its child otherwise returns false if counter of child is less than that
	 * of parent or change of root is not required
	 */
	public boolean treeRotate() {
		// Defining treenode to store values
		TreeNode temp;
		TreeNode tempParent;
		String tempData;
		boolean flag = false;

		// Check if search count of child is greater than parent
		if (count > parent.count) {
			// Check if the child is the leftchild of parent
			if (parent.leftChild != null && (parent.leftChild.data.compareTo(data)) == 0) {
				temp = rightChild;
				tempParent = parent.leftChild;
				tempData = parent.data;

				// to change parent of the rightchild of current node
				if (temp != null) {
					temp.parent = parent;
				}

				// to change rightchild of current node
				rightChild = parent;
				// to change parent's leftchild
				parent.leftChild = temp;
				// to change the parent of the parent's leftchild
				temp = parent.parent;
				parent.parent = tempParent;
				// to change parent of current node
				parent = temp;

				// To check if the rotation of root and root's child is done
				if (parent == null) {
					flag = true;
				} else {
					// To change the left/right child of current's parent's parent
					if (parent.leftChild != null && parent.leftChild.data == tempData) {
						parent.leftChild = tempParent;
					} else {
						parent.rightChild = tempParent;
					}
				}

			} else {
				temp = leftChild;
				tempParent = parent.rightChild;
				tempData = parent.data;

				// to change parent of the leftchild of current node
				if (temp != null) {
					temp.parent = parent;
				}

				leftChild = parent;
				parent.rightChild = temp;
				temp = parent.parent;

				parent.parent = tempParent;
				parent = temp;

				// To check if the rotation of root and root's child is done
				if (parent == null) {
					flag = true;
				} else {
					// To change the left/right child of current's parent's parent
					if (parent.leftChild != null && parent.leftChild.data == tempData) {
						parent.leftChild = tempParent;
					} else {
						parent.rightChild = tempParent;
					}
				}
			}
		}
		return flag;
	}

	/*
	 * This method traverses through the tree and finds the depth of the treenode
	 * from the root.
	 * 
	 * @return String This returns treenode in the tree along with depth in the
	 * sorted order
	 * 
	 */
	public String traverse() {
		// Checking for the leftchild and if present Recursively traverse left subtree.
		if (leftChild != null) {
			levelCount++;
			leftChild.traverse();
			levelCount--;
		}

		keyDepth = keyDepth + data + " " + levelCount + "\n";

		// Checking for the rightchild and if present Recursively traverse right
		// subtree.
		if (rightChild != null) {
			levelCount++;
			rightChild.traverse();
			levelCount--;
		}
		return keyDepth;
	}

	/*
	 * This method resets the search counter for all treenode in the tree
	 */
	public void resetNode() {
		// Checking for the leftchild and if present Recursively traverse left subtree.
		if (leftChild != null) {
			leftChild.resetNode();
		}
		// Making the counter zero for TreeNode
		count = 0;
		// Checking for the rightchild and if present Recursively traverse right
		// subtree.
		if (rightChild != null) {
			rightChild.resetNode();
		}
	}

}
