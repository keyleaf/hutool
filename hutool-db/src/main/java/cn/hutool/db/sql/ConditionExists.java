package cn.hutool.db.sql;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

import java.util.List;

/**
 * @author keyleaf
 * @since 5.8.19
 **/
public class ConditionExists extends ConditionGroup {

	/**
	 * exists语句中的目标表
	 */
	private final String targetTable;

	/**
	 * 构造器
	 *
	 * @param targetTable exists语句中的目标表
	 */
	public ConditionExists(String targetTable) {
		this.targetTable = targetTable;
	}

	/**
	 * 构建exists条件
	 *
	 * @param targetTable exists语句中的目标表
	 * @param conditions  exists语句中的条件
	 * @return exists条件
	 */
	public static ConditionExists exists(String targetTable, Condition... conditions) {
		ConditionExists conditionExists = new ConditionExists(targetTable);
		conditionExists.addConditions(conditions);
		return conditionExists;
	}

	/**
	 * 将条件组转换为条件字符串，使用括号包裹，并回填占位符对应的参数值
	 *
	 * @param paramValues       参数列表，用于回填占位符对应参数值
	 * @param attachDescription 是否显示条件描述
	 * @return 条件字符串
	 */
	@Override
	public String toString(List<Object> paramValues, boolean attachDescription) {
		Condition[] conditions = super.getConditions();
		if (ArrayUtil.isEmpty(conditions)) {
			return StrUtil.EMPTY;
		}
		final StringBuilder conditionStrBuilder = StrUtil.builder();
		conditionStrBuilder.append("exists (select 1 from ").append(targetTable);

		String whereCondition = ConditionBuilder.of(conditions).build(paramValues, attachDescription);

		if (StrUtil.isNotBlank(whereCondition)) {
			conditionStrBuilder.append(" where ").append(StrUtil.unWrap(whereCondition, "(", ")"));
		}
		conditionStrBuilder.append(")");

		if (attachDescription) {
			return StrUtil.isBlank(this.getDescription()) ? conditionStrBuilder.toString() : conditionStrBuilder.append(" /* ").append(this.getDescription()).append(" */ ").toString();
		} else {
			return conditionStrBuilder.toString();
		}
	}
}
