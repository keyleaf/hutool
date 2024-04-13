package cn.hutool.db.sql;

import cn.hutool.core.collection.ListUtil;
import org.junit.Assert;
import org.junit.Test;

public class ConditionGroupTest {
	@Test
	public void ConditionGroupToStringTest() {
		Condition condition1 = new Condition("a", "A");
		Condition condition2 = new Condition("b.b", "B");
		condition2.setLinkOperator(LogicalOperator.OR);
		Condition condition3 = new Condition("c", "C");
		Condition condition4 = new Condition("d", "D");
		condition4.setWrap(false);
		condition4.setPlaceHolder(false);

		ConditionGroup cg = new ConditionGroup();
		cg.addConditions(condition1, condition2);

		// 条件组嵌套情况
		ConditionGroup cg2 = new ConditionGroup();
		cg2.addConditions(cg, condition3);

		// 条件组嵌套情况
		ConditionGroup cg3 = new ConditionGroup();
		cg3.addConditions(cg2, new Condition("e", "E"));

		final SqlBuilder where = SqlBuilder.create(new Wrapper('`')).from("user").where(cg3, condition4);
		System.out.println(where);

		final ConditionBuilder conditionBuilder = ConditionBuilder.of(cg3, condition4);

		Assert.assertEquals("((a = ? OR b = ?) AND c = ?) AND d = ?", conditionBuilder.build());
		Assert.assertEquals(ListUtil.of("A", "B", "C", "D"), conditionBuilder.getParamValues());
	}
}
