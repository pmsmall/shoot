package com.RobotechWar;

public interface ElementsList<E> {

	/**
	 * @param ����һ�����
	 *            ������Ԫ�ص����ӣ�ɾ�����޸ģ���ȡԪ������
	 */

	/**
	 * ����Ԫ��
	 * 
	 * @param e
	 *            ���������������ݲ������data����
	 */
	public void addElement(E e);

	/**
	 * ��ȡԪ������
	 * 
	 * @return Ԫ�ظ���
	 */
	public int getSize();

	/**
	 * ɾ��Ԫ��
	 * 
	 * @return ����ɾ����Ԫ��
	 * @index ɾ��Ԫ�ص�����
	 */
	public E delete(int index);

	/**
	 * @param �޸�Ԫ��
	 * @index �޸�Ԫ�ص�����
	 * @return �����޸ĵ�Ԫ��
	 */
	public E update(int index, E e);

	/**
	 * @param ��ȡԪ��
	 * @index ��ȡԪ�ص�����
	 * @return ��ȡԪ��
	 */
	public E getElement(int index);

}
