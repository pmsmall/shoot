package com.RobotechWar;


/**
 * 
 * @author �����
 *
 * @param <E> �����������������͵�����
 * 
 * ����Ԫ�ص����ӣ�ɾ�����޸ģ���ȡ�������ݸ����ĵĲ鿴
 * ��Ҫ�޸�����ϵ�����
 * 
 */
public class MyArrayList<E> implements ElementsList<E> {
	private Object data[];
	private int size = 0;

	/**
	 * ����Ԫ��
	 * 
	 * @param e
	 *            ���������������ݲ������data����
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
	 * ��ȡԪ������
	 * 
	 * @return Ԫ�ظ���
	 */
	@Override
	public int getSize() {
		return size;
	}

	/**
	 * ɾ��Ԫ��
	 * 
	 * @return ����ɾ����Ԫ��
	 * @index ɾ��Ԫ�ص�����
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
	 * @param �޸�Ԫ��
	 * @index �޸�Ԫ�ص�����
	 * @return �����޸ĵ�Ԫ��
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
	 * @param ��ȡԪ��
	 * @index ��ȡԪ�ص�����
	 * @return ��ȡԪ��
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
