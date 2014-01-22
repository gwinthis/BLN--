/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.util.pair;

/**
 * 
 */
public interface IPair<L, R>{

	 /**
     * {@inheritDoc}
     */
    public L getLeft();

    /**
     * Sets the left element of the pair.
     * 
     * @param left  the new value of the left element, may be null
     */
    public void setLeft(L left);

    /**
     * {@inheritDoc}
     */
    public R getRight();

    /**
     * Sets the right element of the pair.
     * 
     * @param right  the new value of the right element, may be null
     */
    public void setRight(R right);

    /**
     * Sets the {@code Map.Entry} value.
     * This sets the right element of the pair.
     * 
     * @param value  the right value to set, not null
     * @return the old value for the right element
     */
    public R setValue(R value);
    
    /**
     * <p>Gets the key from this pair.</p>
     * 
     * <p>This method implements the {@code Map.Entry} interface returning the
     * left element as the key.</p>
     * 
     * @return the left element as the key, may be null
     */
    public L getKey();

    /**
     * <p>Gets the value from this pair.</p>
     * 
     * <p>This method implements the {@code Map.Entry} interface returning the
     * right element as the value.</p>
     * 
     * @return the right element as the value, may be null
     */
    public R getValue();
}
