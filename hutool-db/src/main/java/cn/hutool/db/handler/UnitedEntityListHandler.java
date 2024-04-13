package cn.hutool.db.handler;

import cn.hutool.db.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author keyleaf
 **/
public class UnitedEntityListHandler implements RsHandler<List<Entity>> {

	private static final long serialVersionUID = 1L;

	/**
	 * key为tableName，value为要封装成的json对象名称
	 * 例如：
	 * t_a t_b t_c三个表连表查询
	 * unitedMap为 {"t_b": "b", "t_c": "c"}
	 * 则最终返回的entity结构为
	 * {"a1":"1", "a2": "2", "b": {"b1": "1", "b2": "2"}, "c": {"c1": "1", "c2": "2"}}
	 */
	private final Map<String, String> unitedMap;

	/** 是否大小写不敏感 */
	private final boolean caseInsensitive;

	/**
	 * 创建一个 UnitedEntityListHandler对象
	 * @return UnitedEntityListHandler对象
	 */
	public static EntityListHandler create() {
		return new EntityListHandler();
	}

	/**
	 * 构造
	 */
	public UnitedEntityListHandler() {
		this(false, null);
	}

	/**
	 * 构造
	 *
	 * @param caseInsensitive 是否大小写不敏感
	 * @param unitedMap 连表查询时，将某个表下的所有数据封装到单独的一个entity中进行嵌套
	 */
	public UnitedEntityListHandler(boolean caseInsensitive, Map<String, String> unitedMap) {
		this.caseInsensitive = caseInsensitive;
		this.unitedMap = unitedMap;
	}

	@Override
	public List<Entity> handle(ResultSet rs) throws SQLException {
		return HandleHelper.handleRs(rs, new ArrayList<>(), this.caseInsensitive, unitedMap);
	}
}
