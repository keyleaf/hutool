package cn.hutool.db.sql;

import cn.hutool.core.collection.ListUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author keyleaf
 * @since 5.8.19
 **/
public class ConditionExistsTest {

	@Test
	public void ConditionExistsToStringTest() {
		Condition condition1 = new Condition("a", "A");
		Condition condition2 = new Condition("b", "B");
		condition2.setLinkOperator(LogicalOperator.OR);
		Condition condition3 = new Condition("c", "C");
		Condition condition4 = new Condition("d", "D");

		ConditionGroup cg = new ConditionGroup();
		cg.addConditions(condition1, condition2);

		// 条件组嵌套情况
		ConditionGroup cg2 = new ConditionGroup();
		cg2.addConditions(cg, condition3);

		Condition condition5 = new Condition("e", "E");
		Condition condition6 = new Condition("f", "F");
		Condition condition7 = new Condition("g", "G");

		// 条件组嵌套情况
		ConditionGroup cg3 = new ConditionGroup();
		cg3.setDescription("条件组嵌套情况3");
		cg3.addConditions(condition5, condition6);

		Condition conditionExists = ConditionExists.exists("t_user", cg3, condition7).setDescription("子表筛选条件");

		final ConditionBuilder conditionBuilder = ConditionBuilder.of(cg2, condition4, conditionExists);
		System.out.println(conditionBuilder.build(true));

		Assert.assertEquals("((a = ? OR b = ?) AND c = ?) AND d = ? AND exists (select 1 from t_user where (e = ? AND f = ?) AND g = ?)", conditionBuilder.build());
		Assert.assertEquals(ListUtil.of("A", "B", "C", "D", "E", "F", "G"), conditionBuilder.getParamValues());
	}
}
