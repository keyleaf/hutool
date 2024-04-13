package cn.hutool.db.handler;

import cn.hutool.db.Entity;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author keyleaf
 **/
public class UnitedEntityHandler implements RsHandler<Entity> {
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
	 * 构造
	 *
	 * @param caseInsensitive 是否大小写不敏感
	 * @param unitedMap 连表查询时，将某个表下的所有数据封装到单独的一个entity中进行嵌套
	 */
	public UnitedEntityHandler(boolean caseInsensitive, Map<String, String> unitedMap) {
		this.caseInsensitive = caseInsensitive;
		this.unitedMap = unitedMap;
	}


	/**
	 * 创建一个 EntityHandler对象
	 * @return EntityHandler对象
	 */
	public static UnitedEntityHandler create() {
		return new UnitedEntityHandler();
	}

	/**
	 * 构造
	 */
	public UnitedEntityHandler() {
		this(false, null);
	}


	@Override
	public Entity handle(ResultSet rs) throws SQLException {
		final ResultSetMetaData meta = rs.getMetaData();
		final int columnCount = meta.getColumnCount();

		return rs.next() ? HandleHelper.handleRow(columnCount, meta, rs, this.caseInsensitive, unitedMap) : null;
	}
}
