/*
 * Copyright 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * http://stackoverflow.com/questions/3522454/java-OTree-data-structure
 */

package taAllocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author ycoppel@google.com (Yohann Coppel)
 * 
 * @param <T>
 *            Object's type in the OTree.
 */
public class OTree<T> {
	private T head;
	private ArrayList<OTree<T>> leafs = new ArrayList<OTree<T>>();
	private OTree<T> parent = null;
	private HashMap<T, OTree<T>> locate = new HashMap<T, OTree<T>>();

	public OTree(T head) {
		this.head = head;
		locate.put(head, this);
	}

	public void addLeaf(T root, T leaf) {
		if (locate.containsKey(root)) {
			locate.get(root).addLeaf(leaf);
		} else {
			addLeaf(root).addLeaf(leaf);
		}
	}

	public OTree<T> addLeaf(T leaf) {
		OTree<T> t = new OTree<T>(leaf);
		leafs.add(t);
		t.parent = this;
		t.locate = this.locate;
		locate.put(leaf, t);
		return t;
	}

	public OTree<T> setAsParent(T parentRoot) {
		OTree<T> t = new OTree<T>(parentRoot);
		t.leafs.add(this);
		this.parent = t;
		t.locate = this.locate;
		t.locate.put(head, this);
		t.locate.put(parentRoot, t);
		return t;
	}

	public T getHead() {
		return head;
	}

	public OTree<T> getOTree(T element) {
		return locate.get(element);
	}

	public OTree<T> getParent() {
		return parent;
	}

	public Collection<T> getSuccessors(T root) {
		Collection<T> successors = new ArrayList<T>();
		OTree<T> OTree = getOTree(root);
		if (null != OTree) {
			for (OTree<T> leaf : OTree.leafs) {
				successors.add(leaf.head);
			}
		}
		return successors;
	}

	public Collection<OTree<T>> getSubOTrees() {
		return leafs;
	}

	public static <T> Collection<T> getSuccessors(T of, Collection<OTree<T>> in) {
		for (OTree<T> OTree : in) {
			if (OTree.locate.containsKey(of)) {
				return OTree.getSuccessors(of);
			}
		}
		return new ArrayList<T>();
	}

	@Override
	public String toString() {
		return printOTree(0);
	}

	private static final int indent = 2;

	private String printOTree(int increment) {
		String s = "";
		String inc = "";
		for (int i = 0; i < increment; ++i) {
			inc = inc + " ";
		}
		s = inc + head;
		for (OTree<T> child : leafs) {
			s += "\n" + child.printOTree(increment + indent);
		}
		return s;
	}
}