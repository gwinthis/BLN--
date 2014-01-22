/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.util.pair;

/**
 * 可变的伴侣对象
 */
public class Pair<L, R> extends org.apache.commons.lang3.tuple.MutablePair<L, R> implements IPair<L, R>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3614940760908714299L;

	 /**
     * Create a new pair instance of two nulls.
     */
    public Pair() {
        super();
    }

    /**
     * Create a new pair instance.
     *
     * @param left  the left value, may be null
     * @param right  the right value, may be null
     */
    public Pair(L left, R right) {
        super();
        this.left = left;
        this.right = right;
    }
}
