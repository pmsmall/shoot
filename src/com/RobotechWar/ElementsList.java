package com.RobotechWar;

public interface ElementsList<E> {

	/**
	 * @param 创建一个借口
	 *            ，包含元素的增加，删除，修改，获取元素数量
	 */

	/**
	 * 增加元素
	 * 
	 * @param e
	 *            传入特殊类型数据并保存进data数组
	 */
	public void addElement(E e);

	/**
	 * 获取元素数量
	 * 
	 * @return 元素个数
	 */
	public int getSize();

	/**
	 * 删除元素
	 * 
	 * @return 返回删除的元素
	 * @index 删除元素的索引
	 */
	public E delete(int index);

	/**
	 * @param 修改元素
	 * @index 修改元素的索引
	 * @return 返回修改的元素
	 */
	public E update(int index, E e);

	/**
	 * @param 获取元素
	 * @index 获取元素的索引
	 * @return 获取元素
	 */
	public E getElement(int index);

}
