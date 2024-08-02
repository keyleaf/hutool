package cn.hutool.core.collection;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 模拟水桶（容器包装）
 * <p>
 * 效仿水满自溢，每当桶内（容器）达到一定数量，将桶内（容器）中的元素通过指定的函数消费并清空集合，即一个简便的批量消费
 *
 * @param <E> 元素类型
 * @author keyleaf
 **/
public class Bucket<E> {

	/**
	 * 桶子（容器）
	 */
	private final Collection<E> collection;

	/**
	 * 桶子（容器）大小
	 */
	private final int size;

	/**
	 * 桶子（容器）元素消费函数
	 */
	private final Consumer<Collection<E>> pourConsumer;

	/**
	 * 构造函数
	 *
	 * @param collectionSupplier 桶子（容器）提供函数
	 * @param size               桶子（容器）大小
	 * @param pourConsumer       桶子（容器）元素消费函数
	 * @param <C>                桶子（容器）类型
	 */
	public <C extends Collection<E>> Bucket(Supplier<C> collectionSupplier, int size, Consumer<Collection<E>> pourConsumer) {
		this.collection = collectionSupplier.get();
		this.size = size;
		this.pourConsumer = pourConsumer;
	}

	/**
	 * 创建桶子（容器）
	 *
	 * @param collectionSupplier 桶子（容器）提供函数
	 * @param size               桶子（容器）大小
	 * @param pourConsumer       桶子（容器）元素消费函数
	 * @param <E>                元素类型
	 * @param <C>                桶子（容器）类型
	 * @return 桶子（容器）
	 */
	public static <E, C extends Collection<E>> Bucket<E> wrap(Supplier<C> collectionSupplier, int size, Consumer<Collection<E>> pourConsumer) {
		return new Bucket<>(collectionSupplier, size, pourConsumer);
	}

	/**
	 * 添加元素
	 * @param e 元素
	 */
	public void add(E e) {
		if (size <= 0) {
			// 桶子大小设定小于等于0，则直接进行消费，不需要收集到容器
			pourConsumer.accept(collection);
			return;
		}
		collection.add(e);
		if (collection.size() >= size) {
			pourConsumer.accept(collection);
			collection.clear();
		}
	}

	/**
	 * 梭哈
	 */
	public void pourAll() {
		pourConsumer.accept(collection);
	}
}
