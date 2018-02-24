package com.RobotechWar;


/**
 * 
 * @author 康天楠
 *
 * @param <E> 可以适用于所有类型的数据
 * 
 * 包含元素的增加，删减，修改，获取，和数据个数的的查看
 * 需要修改请联系康天楠
 * 
 */
public class MyArrayList<E> implements ElementsList<E> {
	private Object data[];
	private int size = 0;

	/**
	 * 增加元素
	 * 
	 * @param e
	 *            传入特殊类型数据并保存进data数组
	 */
	@Override
	public void addElement(E e) {
		Object temper[] = new Object[size + 1];
		for (int i = 0; i < size; i++) {
			temper[i] = data[i];
		}
		data = temper;
		data[size] = e;
		size++;
	}

	/**
	 * 获取元素数量
	 * 
	 * @return 元素个数
	 */
	@Override
	public int getSize() {
		return size;
	}

	/**
	 * 删除元素
	 * 
	 * @return 返回删除的元素
	 * @index 删除元素的索引
	 */
	@SuppressWarnings("unchecked")
	@Override
	public E delete(int index) {
		if (index < size && index >= 0) {
			Object dataTemper = data[index];
			for (int i = index; i < size - 1; i++) {
				data[i] = data[i + 1];
			}
			size--;
			Object temper[] = new Object[size];
			for (int i = 0; i < size; i++) {
				temper[i] = data[i];
			}
			data = temper;

			return (E) dataTemper;
		} else
			return null;
	}

	/**
	 * @param 修改元素
	 * @index 修改元素的索引
	 * @return 返回修改的元素
	 */
	@SuppressWarnings("unchecked")
	@Override
	public E update(int index, E e) {
		// TODO Auto-generated method stub
		if (index < size && index >= 0) {
			data[index] = e;
			return (E) data[index];
		} else
			return null;
	}

	/**
	 * @param 获取元素
	 * @index 获取元素的索引
	 * @return 获取元素
	 */
	@SuppressWarnings("unchecked")
	@Override
	public E getElement(int index) {
		// TODO Auto-generated method stub
		if (index < size && index >= 0)
			return (E) data[index];
		else
			return null;
	}

}
